package com.ddib.product.notification.repository;

import com.ddib.product.notification.domain.SubscriptionCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubscriptionCategoryRepository extends JpaRepository<SubscriptionCategory, Integer> {

    List<SubscriptionCategory> findBySubscribeFashion(boolean subscribeFashion);

    List<SubscriptionCategory> findBySubscribeBeauty(boolean subscribeBeauty);

    List<SubscriptionCategory> findBySubscribeFood(boolean subscribeFood);

    List<SubscriptionCategory> findBySubscribeAppliance(boolean subscribeAppliance);

    List<SubscriptionCategory> findBySubscribeSports(boolean subscribeSports);

    List<SubscriptionCategory> findBySubscribeLiving(boolean subscribeLiving);

    List<SubscriptionCategory> findBySubscribePet(boolean subscribePet);

    List<SubscriptionCategory> findBySubscribeTravel(boolean subscribeTravel);

}
