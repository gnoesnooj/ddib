package com.ddib.product.product.repository;

import com.ddib.product.product.domain.Product;
import com.ddib.product.product.domain.ProductCategory;
import com.ddib.product.product.domain.QProduct;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.util.ToStringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ProductRepositorySupport {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    private final ProductRepository productRepository;

    private static final int WEEK_DAYS = 7;

    private static final int ONE_DAY = 1;

    QProduct qProduct = QProduct.product;

    public List<Product> getTodayListAll() {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);

        return jpaQueryFactory
                .selectFrom(qProduct)
                .where(
                        qProduct.eventStartDate.goe(Timestamp.valueOf(today.atStartOfDay())),
                        qProduct.eventStartDate.lt(Timestamp.valueOf(tomorrow.atStartOfDay()))
                )
                .orderBy(qProduct.eventStartTime.asc())
                .fetch();
    }

    public List<Product> getTodayListOver() {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(ONE_DAY);

        return jpaQueryFactory
                .selectFrom(qProduct)
                .where(
                        qProduct.eventStartDate.goe(Timestamp.valueOf(today.atStartOfDay())),
                        qProduct.eventStartDate.lt(Timestamp.valueOf(tomorrow.atStartOfDay())),
                        qProduct.isOver.eq(true)
                )
                .orderBy(qProduct.eventStartTime.asc())
                .fetch();
    }

    public List<Product> getWeekList() {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(WEEK_DAYS);

        return jpaQueryFactory
                .selectFrom(qProduct)
                .where(qProduct.eventStartDate.between(
                        Timestamp.valueOf(startDate.atStartOfDay()),
                        Timestamp.valueOf(endDate.atStartOfDay())))
                .orderBy(qProduct.eventStartDate.asc(), qProduct.eventStartTime.asc())
                .fetch();
    }

    public List<Product> getTodayListNotOver() {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(ONE_DAY);

        return jpaQueryFactory
                .selectFrom(qProduct)
                .where(
                        qProduct.eventStartDate.goe(Timestamp.valueOf(today.atStartOfDay())),
                        qProduct.eventStartDate.lt(Timestamp.valueOf(tomorrow.atStartOfDay())),
                        qProduct.isOver.eq(false)
                )
                .limit(3)
                .fetch();
    }

    public List<Product> findByConditions(String keyword, String category, Boolean isOver) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (keyword != null && !keyword.isEmpty()) {
            booleanBuilder.and(qProduct.name.contains(keyword));
//            booleanBuilder.or(qProduct.category.eq(ProductCategory.searchCategoryByKeyword(keyword.toUpperCase())));
        }

        if (category != null && !category.isEmpty()) {
            booleanBuilder.and(qProduct.category.eq(ProductCategory.valueOf(category.toUpperCase())));
        }

        if (isOver != null) {
            booleanBuilder.and(qProduct.isOver.eq(isOver)); // 종료되지 않은 것에 대한 추가
        }

        return jpaQueryFactory
                .selectFrom(qProduct)
                .distinct()
                .where(booleanBuilder)
                .fetch();
    }

    public boolean isAvailableTime(LocalDate date, int start, int end) {
        log.info("PRODUCT create CHECK DATE : {}", date);
        LocalDate startOfDay = date.atStartOfDay().toLocalDate();
        LocalDate endOfDay = startOfDay.plusDays(ONE_DAY);

        BooleanExpression isOverlappingTime = qProduct.eventEndTime.gt(start)
                .and(qProduct.eventStartTime.lt(end))
                .and(qProduct.eventStartDate.goe(Timestamp.valueOf(startOfDay.atStartOfDay())))
                .and(qProduct.eventStartDate.lt(Timestamp.valueOf(endOfDay.atStartOfDay())));

        List<Product> products = jpaQueryFactory
                .selectFrom(qProduct)
                .where(isOverlappingTime)
                .fetch();

        return products.isEmpty();
    }

    public boolean[] getAvailableTime(LocalDate date) {
        LocalDate startOfDay = date.atStartOfDay().toLocalDate();
        LocalDate endOfDay = startOfDay.plusDays(ONE_DAY);

        BooleanExpression isEqualDate = qProduct.eventStartDate.goe(Timestamp.valueOf(startOfDay.atStartOfDay()))
                .and(qProduct.eventStartDate.lt(Timestamp.valueOf(endOfDay.atStartOfDay())));
        // 지정된 날짜로 시작하는 상품 검색
        List<Product> products = jpaQueryFactory
                .selectFrom(qProduct)
                .where(isEqualDate)
                .fetch();

        // 시간별 상품의 가용 여부를 나타내는 배열 초기화
        boolean[] times = new boolean[24];

        // 각 상품의 시작 시간과 종료 시간을 이용하여 times 배열 갱신
        for (Product product : products) {
            int startTime = product.getEventStartTime();
            int endTime = product.getEventEndTime();
            for (int time = startTime; time < endTime; time++) {
                times[time] = true;
            }
        }

        return times;
    }

    public void updateTimeOverProduct() {
        log.info("PRODUCT : Update Time Over Product ");
        LocalDate startOfDay = LocalDate.now(ZoneId.of("Asia/Seoul")).atStartOfDay().toLocalDate();
        LocalDate endOfDay = startOfDay.plusDays(ONE_DAY);

        int presentHour = LocalDateTime.now(ZoneId.of("Asia/Seoul")).getHour();

        BooleanExpression isTodayAndOverEndTime = qProduct.eventStartDate.goe(Timestamp.valueOf(startOfDay.atStartOfDay()))
                .and(qProduct.eventStartDate.lt(Timestamp.valueOf(endOfDay.atStartOfDay())))
                .and(qProduct.eventEndTime.loe(presentHour))
                .and(qProduct.isOver.eq(false));

        List<Product> products = jpaQueryFactory
                .selectFrom(qProduct)
                .where(isTodayAndOverEndTime)
                .fetch();

        for (Product product : products) {
            log.info("TIME OVER Product : {}", product.getProductId());
            product.updateIsOver(true);
        }
    }
}
