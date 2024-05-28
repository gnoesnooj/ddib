package com.ddib.product.product.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productDetailId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Product product;

    private String imageUrl;

    public static List<ProductDetail> of(List<String> urls, Product product) {
        return urls.stream()
                .map(url -> ProductDetail.builder()
                        .product(product)
                        .imageUrl(url)
                        .build())
                .collect(Collectors.toList());
    }

}
