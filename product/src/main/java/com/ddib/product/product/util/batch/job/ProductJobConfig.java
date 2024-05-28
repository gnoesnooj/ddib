package com.ddib.product.product.util.batch.job;

import com.ddib.product.product.domain.FavoriteProduct;
import com.ddib.product.product.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class ProductJobConfig {

    public static final Integer CHUNK_SIZE = 5;

    @Bean
    public Job productJob(JobRepository jobRepository, Step productAlarmStep){
        return new JobBuilder("productJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(productAlarmStep)
                .build();
    }

    @JobScope
    @Bean
    public Step productAlarmStep(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                                ItemReader productAlarmReader, ItemWriter productAlarmWriter, TaskExecutor taskExecutor){
        return new StepBuilder("productAlarmStep", jobRepository)
                .<FavoriteProduct, FavoriteProduct>chunk(CHUNK_SIZE, transactionManager)
                .reader(productAlarmReader)
                .writer(productAlarmWriter)
                .taskExecutor(taskExecutor)
                .build();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(CHUNK_SIZE);
        taskExecutor.setMaxPoolSize(CHUNK_SIZE * 2);
        taskExecutor.setThreadNamePrefix("async-thread");
        return taskExecutor;
    }
}
