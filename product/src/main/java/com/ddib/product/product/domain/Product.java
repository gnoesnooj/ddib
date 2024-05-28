package com.ddib.product.product.domain;

import com.ddib.product.product.exception.ProductStockShortageException;
import com.ddib.product.user.domain.Seller;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Slf4j
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;

    @Column(nullable = false)
    private String name;

    private int totalStock;

    private int stock;

    @Column(nullable = false)
    private Timestamp eventStartDate;

    private Timestamp eventEndDate;

    private int eventStartTime;

    private int eventEndTime;

    private int price;

    private double discount;

    private String thumbnailImage;

    @Column(nullable = false)
    private ProductCategory category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ProductDetail> details = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FavoriteProduct> likedUsers;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Seller seller;

    private boolean isOver;

    public void decreaseStock(int amount) {
        isStockShortage(amount);
        stock -= amount;

        if (stock <= 0) {
            this.isOver = true;
        }
    }

    public void updateStock(int amount) {
        this.stock = amount;
    }

    public void updateIsOver(boolean isOver) {
        this.isOver = isOver;
    }

    public void updateThumbnail(String thumbnailImageUrl) {
        this.thumbnailImage = thumbnailImageUrl;
    }

    public void insertProductDetails(List<ProductDetail> details) {
        this.details.addAll(details);
    }

    private void isStockShortage(int amount) {
        if (stock - amount < 0) {
            throw new ProductStockShortageException();
        }
    }

    public boolean isBefore24Hours() {
        LocalDateTime eventDateTime = eventStartDate.toLocalDateTime();
        LocalDate eventDate = eventDateTime.toLocalDate();
        int eventTime = eventDateTime.toLocalTime().getHour();

        LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));
        int now = LocalTime.now(ZoneId.of("Asia/Seoul")).getHour();
//        log.info("24 시간전 : {} = {} ?? {} = {}", eventDate.minusDays(1), today, eventTime, now);
        return eventDate.minusDays(1).equals(today) && eventTime == now;
    }

    public boolean isBefore1Hour() {
        LocalDateTime eventDateTime = eventStartDate.toLocalDateTime();
        LocalDate eventDate = eventDateTime.toLocalDate();
        int eventTime = eventDateTime.toLocalTime().getHour();

        LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));
        int now = LocalTime.now(ZoneId.of("Asia/Seoul")).getHour();

//        log.info("1시간전 : 이벤날짜 {} = 실제날짜 {} ?? 이벤트 시작시간 : {} = 현재 시간 {}", eventDate, today, eventTime, now);
        return eventDate.equals(today) && eventTime - 1 == now;
    }

}
