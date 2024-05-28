package com.ddib.product.product.domain;

import com.ddib.product.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_key",
                        columnNames = {"product_product_id", "user_user_id"}
                )
        }
)
public class FavoriteProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer favoriteProductId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

}
