package com.ddib.product.product.controller;

import com.ddib.product.product.dto.request.ProductCreateRequestDto;
import com.ddib.product.product.dto.response.ProductMainResponseDto;
import com.ddib.product.product.dto.response.ProductResponseDto;
import com.ddib.product.product.dto.response.ProductViewResponseDto;
import com.ddib.product.product.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    private MockMvc mockMvc;

    private ProductResponseDto productResponseDto;

    private List<ProductResponseDto> productResponseDtos;

    private ProductMainResponseDto productMainResponseDto;

    private ProductViewResponseDto productViewResponseDto;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();

        productResponseDto = new ProductResponseDto();
        productResponseDtos = new ArrayList<>();
        productResponseDtos.add(productResponseDto);

        productMainResponseDto = ProductMainResponseDto.builder()
                .todayProducts(productResponseDtos)
                .todayNotOverProducts(productResponseDtos)
                .build();
    }

    @DisplayName("POST /api/product : 상품 생성 성공")
    @Test
    void createProduct() throws Exception {
        ProductCreateRequestDto dto = new ProductCreateRequestDto();
        MockMultipartFile thumbnail = new MockMultipartFile("thumbnailImage", "thumbnail1.jpg", "image/jpeg", "thumbnail1".getBytes());
        MockMultipartFile detail = new MockMultipartFile("productDetails", "detail1.jpg", "image/jpeg", "detail1".getBytes());
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonDto = objectMapper.writeValueAsString(dto);
        MockMultipartFile testDto = new MockMultipartFile("dto", "dto", "application/json", jsonDto.getBytes(StandardCharsets.UTF_8));

        // 요청 생성
        mockMvc.perform(MockMvcRequestBuilders.multipart(HttpMethod.POST, "/api/product")
                        .file(thumbnail)
                        .file(detail)
                        .file(testDto)
                ).andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void getMainPageData() throws Exception {
        given(productService.getMainPageData()).willReturn(productMainResponseDto);

        mockMvc.perform(get("/api/product/main"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.todayNotOverProducts").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.todayProducts").exists())
                .andDo(print());

        verify(productService, times(1)).getMainPageData();
    }

    @Test
    void findProductsByConditions() throws Exception {
        given(productService.findProductsByConditions("keyword","CATEGORY", true)).willReturn(productResponseDtos);

        mockMvc.perform(get("/api/product/search?keyword=keyword&category=CATEGORY"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].productId").exists())
                .andDo(print());

        verify(productService, times(1)).findProductsByConditions("keyword", "CATEGORY", true);
    }

    @Test
    void decreaseStock() {
    }

    @Test
    void updateStock() {
    }

    @Test
    void likeProduct() {
    }

    @Test
    void findFavoriteProductsByUserId() throws Exception {
        given(productService.findFavoriteProductByUserId(1)).willReturn(productResponseDtos);

        mockMvc.perform(get("/api/product/like/user/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].productId").exists())
                .andDo(print());

        verify(productService, times(1)).findFavoriteProductByUserId(1);

    }

    @Test
    void findProductsBySellerId() throws Exception {
        given(productService.findProductsBySellerId(1)).willReturn(productResponseDtos);

        mockMvc.perform(get("/api/product/seller/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].productId").exists())
                .andDo(print());

        verify(productService, times(1)).findProductsBySellerId(1);

    }

    @Test
    void findProductsInWeekend() throws Exception {
        List<List<ProductResponseDto>> examples = new ArrayList<>();
        given(productService.findProductsInWeekend()).willReturn(examples);

        mockMvc.perform(get("/api/product/all"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].productId").exists())
                .andDo(print());

        verify(productService, times(1)).findProductsInWeekend();
    }

    @Test
    void findProductByProductId() throws Exception {
        given(productService.findProductByProductId(1, 1)).willReturn(productViewResponseDto);

        mockMvc.perform(get("/api/product/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.productId").isEmpty())
                .andDo(print());

        verify(productService, times(1)).findProductByProductId(1, 1);
    }
}