package com.ddib.product.product.domain;

import com.ddib.product.notification.domain.SubscriptionCategory;
import com.ddib.product.notification.repository.SubscriptionCategoryRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Getter
@RequiredArgsConstructor
@Slf4j
public enum ProductCategory {

    NONE("없음", "None") {
        @Override
        public List<SubscriptionCategory> getListByCategory(SubscriptionCategoryRepository repository) {
            return null;
        }
    },
    FASHION("패션", "Fashion") {
        @Override
        public List<SubscriptionCategory> getListByCategory(SubscriptionCategoryRepository repository) {
            log.info("CATEGORY CALL BY FASHION");
            return repository.findBySubscribeFashion(true);
        }
    },
    BEAUTY("뷰티", "Beauty") {
        @Override
        public List<SubscriptionCategory> getListByCategory(SubscriptionCategoryRepository repository) {
            return repository.findBySubscribeBeauty(true);
        }
    },
    FOOD("식품", "Food") {
        @Override
        public List<SubscriptionCategory> getListByCategory(SubscriptionCategoryRepository repository) {
            return repository.findBySubscribeFood(true);
        }
    },
    APPLIANCE("가전", "Appliance") {
        @Override
        public List<SubscriptionCategory> getListByCategory(SubscriptionCategoryRepository repository) {
            return repository.findBySubscribeAppliance(true);
        }
    },
    SPORTS("스포츠", "Sports") {
        @Override
        public List<SubscriptionCategory> getListByCategory(SubscriptionCategoryRepository repository) {
            return repository.findBySubscribeSports(true);
        }
    },
    LIVING("생활용품", "Living") {
        @Override
        public List<SubscriptionCategory> getListByCategory(SubscriptionCategoryRepository repository) {
            return repository.findBySubscribeLiving(true);
        }
    },
    PET("반려용품", "Pet") {
        @Override
        public List<SubscriptionCategory> getListByCategory(SubscriptionCategoryRepository repository) {
            return repository.findBySubscribePet(true);
        }
    },
    TRAVEL("여행", "Travel") {
        @Override
        public List<SubscriptionCategory> getListByCategory(SubscriptionCategoryRepository repository) {
            return repository.findBySubscribeTravel(true);
        }
    };

    private final String meaning;

    private final String value;

    public static ProductCategory searchCategoryByKeyword(String keyword) {
        try {
            return ProductCategory.valueOf(keyword);
        } catch (IllegalArgumentException e) {
            return ProductCategory.NONE;
        }
    }

    public abstract List<SubscriptionCategory> getListByCategory(SubscriptionCategoryRepository repository);

}
