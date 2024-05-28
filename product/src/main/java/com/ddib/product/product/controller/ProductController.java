package com.ddib.product.product.controller;

import com.ddib.product.product.dto.request.ProductCreateRequestDto;
import com.ddib.product.product.dto.request.ProductLikeRequestDto;
import com.ddib.product.product.dto.request.ProductStockDecreaseRequestDto;
import com.ddib.product.product.dto.request.ProductStockUpdateRequestDto;
import com.ddib.product.product.dto.response.ProductMainResponseDto;
import com.ddib.product.product.dto.response.ProductResponseDto;
import com.ddib.product.product.dto.response.ProductViewResponseDto;
import com.ddib.product.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.Path;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.*;

@Tag(name = "Product Server API Docs", description = "상품 서버 Swagger 입니다 ㅎ_ㅎ")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "상품 생성", description = "상품을 생성한다.")
    @ApiResponse(responseCode = "200", description = "성공")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createProduct(@RequestPart(value = "thumbnailImage") List<MultipartFile> thumbnails,
                                           @RequestPart(value = "productDetails") List<MultipartFile> details,
                                           @RequestPart(value = "dto") ProductCreateRequestDto dto) {
        log.info("PRODUCT CREATE REQUEST");
        productService.createProduct(thumbnails, details, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "메인 페이지 랜딩 데이터 조회", description = "메인 페이지에서 사용할 당일 상품들에 대한 데이터를 조회한다.")
    @ApiResponse(responseCode = "200", description = "성공")
    @GetMapping("/main")
    public ResponseEntity<ProductMainResponseDto> getMainPageData() {
        ProductMainResponseDto dto = productService.getMainPageData();
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(summary = "조건 별 상품 검색 조회", description = "키워드, 카테고리에 따라 상품을 검색한다.")
    @ApiResponse(responseCode = "200", description = "성공")
    @GetMapping("/search")
    public ResponseEntity<List<ProductResponseDto>> findProductsByConditions(@RequestParam(required = false) String keyword,
                                                                             @RequestParam(required = false) String category,
                                                                             @RequestParam(required = false) Boolean isOver) {
        List<ProductResponseDto> dtos = productService.findProductsByConditions(keyword, category, isOver);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @Operation(summary = "재고량 감소 API", description = "구매 시 상품에 대한 재고량을 감소시킨다.")
    @ApiResponse(responseCode = "200", description = "성공")
    @PutMapping("/stock/decrease")
    public ResponseEntity<?> decreaseStock(@RequestBody ProductStockDecreaseRequestDto dto) {
        productService.decreaseStock(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "재고량 수정 API", description = "상품에 대한 재고량을 수정한다.")
    @ApiResponse(responseCode = "200", description = "성공")
    @PutMapping("/stock/update")
    public ResponseEntity<?> updateStock(@RequestBody ProductStockUpdateRequestDto dto) {
        productService.updateStock(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "상품 좋아요 API", description = "상품에 대한 좋아요를 통해 관심상품에 등록한다.")
    @ApiResponse(responseCode = "200", description = "성공")
    @PostMapping("/like")
    public ResponseEntity<?> likeProduct(@RequestBody ProductLikeRequestDto dto) {
        productService.likeProduct(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "회원 별 좋아요 상픔 조회 API", description = "회원이 좋아요를 누른 상품들에 대해 조회한다.")
    @ApiResponse(responseCode = "200", description = "성공")
    @GetMapping("/like/user/{userId}")
    public ResponseEntity<List<ProductResponseDto>> findFavoriteProductsByUserId(@PathVariable("userId") int userId) {
        List<ProductResponseDto> dtos = productService.findFavoriteProductByUserId(userId);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @Operation(summary = "판매자 판매 상품 조회 API", description = "판매자 본인이 등록한 상품들에 대해 조회한다.")
    @ApiResponse(responseCode = "200", description = "성공")
    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<List<ProductResponseDto>> findProductsBySellerId(@PathVariable("sellerId") int sellerId) {
        List<ProductResponseDto> dtos = productService.findProductsBySellerId(sellerId);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @Operation(summary = "1주일간 전체 상품 조회 API", description = "현 시점부터 1주일간의 모든 상품들을 조회한다.")
    @ApiResponse(responseCode = "200", description = "성공")
    @GetMapping("/all")
    public ResponseEntity<List<List<ProductResponseDto>>> findProductsInWeekend() {
        log.info("PRODUCT 1주일간 상품 조회");
        List<List<ProductResponseDto>> dtos = productService.findProductsInWeekend();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @Operation(summary = "상품 상세조회 API", description = "상품 상세정보에 대해 조회한다.")
    @ApiResponse(responseCode = "200", description = "성공")
    @GetMapping("/{productId}/{userId}")
    public ResponseEntity<ProductViewResponseDto> findProductByProductId(@PathVariable("productId") int productId, @PathVariable("userId") int userId) {
        ProductViewResponseDto dto = productService.findProductByProductId(productId, userId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(summary = "좋아요 취소 API", description = "좋아요한 상품에 대해 취소한다.")
    @ApiResponse(responseCode = "200", description = "성공")
    @DeleteMapping("/{productId}/{userId}")
    public ResponseEntity<?> cancelFavoriteProduct(@PathVariable("productId") int productId, @PathVariable("userId") int userId) {
        productService.cancelFavoriteProduct(productId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "해당 날짜에 어떤 시간대가 사용중인지에 대한 조회 API", description = "파라미터는 yyyy-mm-dd 로 입력합니다. 해당 날짜의 0~24시 시간대의 사용여부를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "성공")
    @GetMapping("/time/{date}")
    public ResponseEntity<?> getAvailableTime(@PathVariable("date") LocalDate date) {
        boolean [] times = productService.getAvailableTime(date);
        return new ResponseEntity<>(times, HttpStatus.OK);
    }

    @Operation(summary = "종료시간이 지난 상품들에 대해 상태 변경 API", description = "스케줄러로 작동 중인 타임딜 상품 종료에 따른 상태 변경에 대한 수동 호출 API 입니다.")
    @ApiResponse(responseCode = "200", description = "성공")
    @GetMapping("/checkTimeOver")
    public ResponseEntity<?> updateProductTimeOver() {
        productService.updateTimeOverProduct();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
