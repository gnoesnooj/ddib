package com.ddib.product.product.repository;

import com.ddib.product.product.domain.Product;
import com.ddib.product.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByProductId(int id);

//    @Query("select distinct fp.user from FavoriteProduct fp join fp.Product p where date(now()) = date(date_sub(p.eventDate, INTERVAL '24' HOUR)) OR HOUR(NOW()) = p.eventStartTime + '1'")
//    @Query("select distinct fp.user \n" +
//            "from FavoriteProduct fp\n" +
//            "where date(now()) = date(date_sub(fp.product.eventDate, INTERVAL 24 HOUR)) OR HOUR(NOW()) = fp.product.eventStartTime + 1")

//    @Query("SELECT DISTINCT fp.user \n" +
//            "FROM FavoriteProduct fp\n" +
//            "WHERE FUNCTION('DATE', CURRENT_DATE()) = FUNCTION('DATE', FUNCTION('DATE_SUB', fp.product.eventDate, 24, 'HOUR'))\n" +
//            "   OR FUNCTION('HOUR', CURRENT_TIME()) = fp.product.eventStartTime + 1")
//    @Query("SELECT DISTINCT fp.product.productId \n" +
//            "FROM FavoriteProduct fp\n" +
//            "where fp.product.eventStartTime + 10 = hour(now())")
//@Query("SELECT DISTINCT hour(now()) - fp.product.productId \n" +
//        "FROM FavoriteProduct fp\n" +
//        "where fp.product.eventStartTime + 10 = hour(now())")
//    @Query("select fp.product.productId from FavoriteProduct" +
//            " fp where fp.product.eventStartTime - hour(now()) = 1 ")
//    @Query("select function(date_sub((now()), interval `24` hour))")


//    @Query("select fp.product.productId from FavoriteProduct fp " +
//            "where (day(fp.product.eventDate) - day(now()) = 1 and hour(fp.product.eventDate) = hour(now())) " +
//            "OR (day(fp.product.eventDate) - day(now()) = 0 and fp.product.eventStartTime - hour(now()) = 1 )")


//    @Query("select day(fp.product.eventDate) - day(now()) from FavoriteProduct fp where fp.product.productId = 17")
//    @Query("select hour(now()) from FavoriteProduct fp where fp.product.productId = 17")
//
//    @Query("SELECT\n" +
//            "    CASE \n" +
//            "        WHEN (day(fp.product.eventDate) - day(now()) = 1 and hour(fp.product.eventDate) = hour(now()))\n" +
//            "            THEN 1\n" +
//            "        WHEN (day(fp.product.eventDate) - day(now()) = 0 and fp.product.eventStartTime - hour(now()) = 1 )\n" +
//            "            THEN 2\n" +
//            "        ELSE 0\n" +
//            "    END\n" +
//            "FROM FavoriteProduct fp\n" +
//            "WHERE\n" +
//            "    (day(fp.product.eventDate) - day(now()) = 1 and hour(fp.product.eventDate) = hour(now()))\n" +
//            "    OR (day(fp.product.eventDate) - day(now()) = 0 and fp.product.eventStartTime - hour(now()) = 1 )")
//    List<Integer> check();
}
