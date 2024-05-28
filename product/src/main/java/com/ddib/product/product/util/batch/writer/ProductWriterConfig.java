package com.ddib.product.product.util.batch.writer;

import com.ddib.product.product.domain.FavoriteProduct;
import com.ddib.product.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ProductWriterConfig {

    private final ProductService productService;

    @StepScope
    @Bean
    public ItemWriter<FavoriteProduct> productAlarmWriter() {
        return chunk -> {
            for (FavoriteProduct favoriteProduct : chunk.getItems()) {
                try {
                    productService.createFavoriteAlarm(favoriteProduct);
                } catch (Exception e) {
                    log.info("productAlarmWriter ERROR : {} ", e.getMessage());
                }
            }
        };
    }
}
