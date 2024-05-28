package com.ddib.product.notification.client;

import com.ddib.product.notification.dto.request.NotificationCreateDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "notification-local", url = "${feign-client.notification}/api/notification")
public interface NotificationClient {

    @PostMapping("/create")
    public void createAlarm(@RequestBody NotificationCreateDto dto);

    @GetMapping("/{userId}")
    public void notificationDetails(@PathVariable Integer userId);

}
