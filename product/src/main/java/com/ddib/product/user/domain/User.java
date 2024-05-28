package com.ddib.product.user.domain;

import com.ddib.product.product.domain.FavoriteProduct;
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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FavoriteProduct> likedProducts;

    public void cancelLikedProducts(FavoriteProduct favoriteProduct){
        likedProducts.remove(favoriteProduct);
    }
}
