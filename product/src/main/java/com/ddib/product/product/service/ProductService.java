package com.ddib.product.product.service;

import com.ddib.product.common.file.util.S3Uploader;
import com.ddib.product.notification.client.NotificationClient;
import com.ddib.product.notification.domain.SubscriptionCategory;
import com.ddib.product.notification.dto.request.NotificationCreateDto;
import com.ddib.product.notification.repository.SubscriptionCategoryRepository;
import com.ddib.product.product.domain.FavoriteProduct;
import com.ddib.product.product.domain.Product;
import com.ddib.product.product.domain.ProductDetail;
import com.ddib.product.product.dto.request.ProductCreateRequestDto;
import com.ddib.product.product.dto.request.ProductLikeRequestDto;
import com.ddib.product.product.dto.request.ProductStockDecreaseRequestDto;
import com.ddib.product.product.dto.request.ProductStockUpdateRequestDto;
import com.ddib.product.product.dto.response.ProductMainResponseDto;
import com.ddib.product.product.dto.response.ProductResponseDto;
import com.ddib.product.product.dto.response.ProductViewResponseDto;
import com.ddib.product.product.exception.ProductNotFoundException;
import com.ddib.product.product.repository.ProductRepository;
import com.ddib.product.product.repository.ProductRepositorySupport;
import com.ddib.product.user.domain.Seller;
import com.ddib.product.user.domain.User;
import com.ddib.product.user.exception.SellerNotFoundException;
import com.ddib.product.user.exception.UserNotFoundException;
import com.ddib.product.user.repository.SellerRepository;
import com.ddib.product.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static com.ddib.product.common.file.constant.S3Domain.PRODUCT_DETAIL;
import static com.ddib.product.common.file.constant.S3Domain.PRODUCT_THUMBNAIL;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    private final SellerRepository sellerRepository;

    private final ProductRepositorySupport productRepositorySupport;

    private final S3Uploader s3Uploader;

    private final NotificationClient notificationClient;

    private final SubscriptionCategoryRepository subscriptionCategoryRepository;

    public void createProduct(List<MultipartFile> thumbnails, List<MultipartFile> details, ProductCreateRequestDto dto) {
        log.info("PRODUCT SERVICE : SAVE PRODUCT : {}", dto.getName());
        Seller seller = sellerRepository.findBySellerId(dto.getSellerId())
                .orElseThrow(SellerNotFoundException::new);

//        if (productRepositorySupport.isAvailableTime(dto.getEventStartDate().toLocalDate(), dto.getEventStartDate().getHour(), dto.getEventEndDate().getHour())) {
//            throw new ProductNotAvailableTimeException();
//        }

        List<String> detail = s3Uploader.storeImages(PRODUCT_DETAIL, details);
        List<String> thumbnail = s3Uploader.storeImages(PRODUCT_THUMBNAIL, thumbnails);

        Product product = dto.toEntity(seller);
        product.updateThumbnail(thumbnail.get(0));
        product.insertProductDetails(ProductDetail.of(detail, product));

        productRepository.save(product);
//        callSubscriptionNotification(product);
        CompletableFuture.runAsync(() -> callSubscriptionNotification(product));
    }

    @Transactional(readOnly = true)
    public ProductMainResponseDto getMainPageData() {
        log.info("PRODUCT SERVICE : get main page data");
        // 하루, 종료되지 않은 이벤트 데이터
        List<ProductResponseDto> todayNotOverProducts = productRepositorySupport.getTodayListNotOver()
                .stream()
                .map(ProductResponseDto::of)
                .toList();

        // 하루 데이터
//        List<ProductResponseDto> todayOverProducts = productRepositorySupport.getTodayListOver()
//                .stream()
//                .map(ProductResponseDto::of)
//                .toList();

        List<ProductResponseDto> todayAllProducts = productRepositorySupport.getTodayListAll()
                .stream()
                .map(ProductResponseDto::of)
                .toList();

        return ProductMainResponseDto.builder()
                .todayNotOverProducts(todayNotOverProducts)
                .todayProducts(todayAllProducts)
                .build();
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDto> findProductsByConditions(String keyword, String category, Boolean isOver) {
        log.info("PRODUCT SERVICE : SEARCH BY CONDITIONS : {} , {}, {}", keyword, category, isOver);
        return productRepositorySupport.findByConditions(keyword, category, isOver)
                .stream()
                .map(ProductResponseDto::of)
                .toList();
    }

    public void decreaseStock(ProductStockDecreaseRequestDto dto) {
        Product product = productRepository.findByProductId(dto.getProductId())
                .orElseThrow(ProductNotFoundException::new);
        product.decreaseStock(dto.getAmount());
    }

    public void updateStock(ProductStockUpdateRequestDto dto) {
        Product product = productRepository.findByProductId(dto.getProductId())
                .orElseThrow(ProductNotFoundException::new);
        product.updateStock(dto.getAmount());
    }

    public void likeProduct(ProductLikeRequestDto dto) {
        Product product = productRepository.findByProductId(dto.getProductId())
                .orElseThrow(ProductNotFoundException::new);
        User user = userRepository.findByUserId(dto.getUserId())
                .orElseThrow(UserNotFoundException::new);

        FavoriteProduct favoriteProduct = FavoriteProduct.builder()
                .product(product)
                .user(user)
                .build();

        product.getLikedUsers().add(favoriteProduct);
        user.getLikedProducts().add(favoriteProduct);
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDto> findFavoriteProductByUserId(int userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(UserNotFoundException::new)
                .getLikedProducts()
                .stream()
                .map(FavoriteProduct::getProduct)
                .map(ProductResponseDto::of)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDto> findProductsBySellerId(int sellerId) {
        return sellerRepository.findBySellerId(sellerId)
                .orElseThrow(SellerNotFoundException::new)
                .getProducts()
                .stream()
                .map(ProductResponseDto::of)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<List<ProductResponseDto>> findProductsInWeekend() {
        List<ProductResponseDto> dtos = productRepositorySupport.getWeekList()
                .stream()
                .map(ProductResponseDto::of)
                .toList();

        // list -> map 에서 다시 key값 안쓰는 배열로 변환해주기 -> list<list>
        Map<String, List<ProductResponseDto>> resultMap = new LinkedHashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate start = LocalDate.parse(LocalDate.now().toString().split("T")[0], formatter);
        for (int day = 0; day < 7; day++) {
            String key = formatter.format(start.plusDays(day));
            resultMap.computeIfAbsent(key, k -> new ArrayList<>());
        }
        for (ProductResponseDto dto : dtos) {
            LocalDate startDate = LocalDate.parse(dto.getEventStartDate().toString().split("T")[0], formatter);
            String key = formatter.format(startDate);
            resultMap.get(key).add(dto);
        }

        return new ArrayList<>(resultMap.values());
    }

    @Transactional(readOnly = true)
    public ProductViewResponseDto findProductByProductId(int productId, int userId) {
        Product product = productRepository.findByProductId(productId)
                .orElseThrow(ProductNotFoundException::new);
        User user = userRepository.findByUserId(userId)
                .orElseThrow(UserNotFoundException::new);
        boolean isLiked = user.getLikedProducts()
                .stream()
                .anyMatch(fp -> fp.getProduct().equals(product));
        return ProductViewResponseDto.of(product, isLiked);
    }

    public void cancelFavoriteProduct(int productId, int userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(UserNotFoundException::new);

        Product product = productRepository.findByProductId(productId).orElseThrow(ProductNotFoundException::new);

        List<FavoriteProduct> fps = user.getLikedProducts();
        for (FavoriteProduct fp : fps) {
            if (fp.getProduct().equals(product)) {
                user.getLikedProducts().remove(fp);
                product.getLikedUsers().remove(fp);
                break;
            }
        }
    }

    @Transactional(readOnly = true)
    public boolean[] getAvailableTime(LocalDate date) {
        return productRepositorySupport.getAvailableTime(date);
    }

    public void updateTimeOverProduct() {
        productRepositorySupport.updateTimeOverProduct();
    }

    public void createFavoriteAlarm(FavoriteProduct favoriteProduct) {
        Product product = favoriteProduct.getProduct();
        if(product.isBefore1Hour() || product.isBefore24Hours()) {
            NotificationCreateDto dto = NotificationCreateDto.ofFavorite(favoriteProduct.getProduct(), favoriteProduct.getUser().getUserId());
            notificationClient.createAlarm(dto);
            log.info("{} : 알람 호출 성공", product.getProductId());
        }
    }


    public void notificationDetails(FavoriteProduct favoriteProduct) {
        notificationClient.notificationDetails(favoriteProduct.getUser().getUserId());
    }

    //해당 카테고리를 좋아요한 유저를 찾은 다음 해당 유저들에게 알람 보내는 메서드
    @Async
    private void callSubscriptionNotification(Product product) {
        List<SubscriptionCategory> findBySubscriptionCategories = product.getCategory().getListByCategory(subscriptionCategoryRepository);
        log.info("좋아요한 회원 수 : {}", findBySubscriptionCategories.size());

//        for(SubscriptionCategory sc : findBySubscriptionCategories){
//            NotificationCreateDto dto = NotificationCreateDto.ofSubscription(product, sc.getUser().getUserId());
//            log.info("요청보내는 DTO : {}, {}, {}", dto.getUserId(), dto.getCategory(), dto.getProductName());
//            notificationClient.createAlarm(dto);
//        }
        findBySubscriptionCategories.stream()
                .map(sc -> NotificationCreateDto.ofSubscription(product, sc.getUser().getUserId()))
//                .forEach(notificationClient::createAlarm);
                .forEach(dto -> CompletableFuture.runAsync(() -> notificationClient.createAlarm(dto)));
    }
}
