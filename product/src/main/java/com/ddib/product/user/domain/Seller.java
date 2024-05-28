package com.ddib.product.user.domain;

import com.ddib.product.product.domain.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "판매회원 식별키")
    private Integer sellerId;

    @Column(nullable = false)
    @Schema(description = "판매회원 이메일")
    private String sellerEmail;

    @Schema(description = "기업명")
    private String companyName;

    @Schema(description = "사업자 등록 번호")
    private String businessNumber;

    @Schema(description = "대표명")
    private String ceoName;

    @Schema(description = "대표 이메일")
    private String ceoEmail;

    @Schema(description = "대표 전화번호")
    private String ceoPhone;

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products;

}
