package com.ddib.product.product.repository;

import com.ddib.product.product.service.ProductService;
import com.ddib.product.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class QueryTestController {

    private final ProductRepository productRepository;
    private final ProductRepositorySupport productRepositorySupport;

//    @GetMapping("/query/test")
//    public List<Integer> c(){
//        return productRepository.check();
//    }

    @GetMapping("/test/update/timeover")
    public void updateTimeOverProduct(){
        log.info("HELLO");
        productRepositorySupport.updateTimeOverProduct();
    }
}
