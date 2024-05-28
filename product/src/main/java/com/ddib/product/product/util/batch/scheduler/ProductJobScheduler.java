package com.ddib.product.product.util.batch.scheduler;

import com.ddib.product.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductJobScheduler {

    private final Job productJob;

    private final JobLauncher jobLauncher;

    private final ProductService productService;

//    @Scheduled(cron = "*/10 * * * * ?", zone = "Asia/Seoul")
    @Scheduled(cron = "0 0 0/1 * * *")
    public void runAlarmJob() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        log.info("[PRODUCT] ALARM BATCH JOB 실행");
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();

        jobLauncher.run(productJob, jobParameters);
    }

//    @Scheduled(cron = "*/10 * * * * ?", zone = "Asia/Seoul")
    @Scheduled(cron = "0 0 0/1 * * *")
    public void updateTimeOverProduct() {
        log.info("[PRODUCT] UPDATE TIME OVER SCHEDULE 실행");
        productService.updateTimeOverProduct();
    }
}
