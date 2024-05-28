package com.ddib.product.product.dto.request;

import com.ddib.product.product.domain.Product;
import com.ddib.product.product.domain.ProductCategory;
import com.ddib.product.user.domain.Seller;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateRequestDto {

    @Schema(description = "상품명", defaultValue = "name")
    private String name;

    @Schema(description = "총 재고량", defaultValue = "1000")
    private int totalStock;

    @Schema(description = "타임딜 시작날짜", defaultValue = "2024-04-25")
    private String eventStartDate;

    @Schema(description = "타임딜 시작시각", defaultValue = "2")
    private int startTime;

    @Schema(description = "타임딜 종료시각", defaultValue = "15")
    private int endTime;

    @Schema(description = "가격", defaultValue = "20000")
    private int price;

    @Schema(description = "할인율", defaultValue = "20")
    private double discount;

    @Schema(description = "상품 카테고리", defaultValue = "fashion",
            allowableValues = {"Fashion", "Beauty", "Food", "Appliance", "Sports", "Living", "Pet", "Travel"})
    private String category;

    @Schema(description = "판매자 ID", defaultValue = "1")
    private int sellerId;

    public Product toEntity(Seller seller) {
        return Product.builder()
                .name(getName())
                .price(getPrice())
                .stock(getTotalStock())
                .totalStock(getTotalStock())
                .discount(getDiscount())
                .eventStartDate(convertDate(eventStartDate, startTime))
                .eventEndDate(convertDate(eventStartDate, endTime))
                .eventStartTime(startTime)
                .eventEndTime(endTime)
                .isOver(false)
                .seller(seller)
                .category(ProductCategory.valueOf(category.toUpperCase()))
                .build();
    }

    private static Timestamp convertDate(String inputDate, int time){
        LocalDate date = LocalDate.parse(inputDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // Combine the date with startTime and endTime to create LocalDateTime
        LocalDateTime dateTime = LocalDateTime.of(date, LocalTime.of(time, 0));

        // Convert LocalDateTime to Timestamp
        Timestamp timestamp = Timestamp.valueOf(dateTime);

        return timestamp;
    }
}
