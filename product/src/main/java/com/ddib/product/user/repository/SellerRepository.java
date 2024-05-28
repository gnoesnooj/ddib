package com.ddib.product.user.repository;

import com.ddib.product.user.domain.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SellerRepository extends JpaRepository<Seller, Long> {

    Optional<Seller> findBySellerId(int sellerId);

}
