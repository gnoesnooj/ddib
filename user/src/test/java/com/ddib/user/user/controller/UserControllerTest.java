package com.ddib.user.user.controller;

import com.ddib.user.user.domain.User;
import com.ddib.user.user.dto.request.UserModifyRequestDto;
import com.ddib.user.user.repository.UserRepository;
import com.ddib.user.user.service.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @InjectMocks
    UserController userController;

    MockMvc mockMvc;

    @Mock
    UserService userService;

    @Mock
    UserRepository userRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    @DisplayName("일반회원 정보 조회 Controller Test")
    public void userDetails() throws Exception {
        // given
        User user = User.builder()
                .name("김유나")
                .email("kn9012@naver.com")
                .phone("01012345678")
                .roadAddress("하남산단6번로")
                .detailAddress("싸피건물")
                .zipcode(123)
                .build();

        userRepository.save(user);

        // when, then
        mockMvc.perform(get("/api/user") // 해당 url로 GET 요청을 보냄
                        .with(csrf())) // Spring Security 사용했기 때문에 csrf 요청 전송
                        .andExpect(status().isOk()) // HTTP 200 기대
                        .andDo(print()); // 화면에 결과 출력
    }

    @Test
    @DisplayName("일반회원 정보 수정 Controller Test")
    public void userInfoModify() throws Exception {
        // given
        User user = User.builder()
                .name("김유나")
                .email("kn9012@naver.com")
                .phone("01012345678")
                .roadAddress("하남산단6번로")
                .detailAddress("싸피건물")
                .zipcode(123)
                .build();

        userRepository.save(user);

        // given
        UserModifyRequestDto requestDto = UserModifyRequestDto.builder()
                .name("김유나")
                .phone("01038620912")
                .roadAddress("하남산단6번로")
                .detailAddress("싸피건물")
                .zipcode(123)
                .build();

        // when, then
        mockMvc.perform(put("/api/user")
                        .contentType(MediaType.APPLICATION_JSON) // json 형식으로 데이터를 보냄을 명시
                        .content(objectMapper.writeValueAsString(requestDto)) // input 값 json형식의 String으로 만들기 위해 objectMapper를 사용
                        .with(csrf())) // Spring Security 사용했기 때문에 csrf 요청 전송
                        .andExpect(status().isOk())
                        .andDo(print());
    }

    @Test
    @DisplayName("일반회원 탈퇴 Controller Test")
    public void userDelete() throws Exception {
        // given
        User user = User.builder()
                .name("김유나")
                .email("kn9012@naver.com")
                .phone("01012345678")
                .roadAddress("하남산단6번로")
                .detailAddress("싸피건물")
                .zipcode(123)
                .build();

        userRepository.save(user);

        // when, then
        mockMvc.perform(delete("/api/user")
                .with(csrf())) // Spring Security 사용했기 때문에 csrf 요청 전송
                .andExpect(status().isOk())
                .andDo(print());

        assertNull(userRepository.findByEmail("kn9012@google.com"), "user가 null이 아닙니다.");
    }

    @Test
    @DisplayName("로그아웃 Controller Test")
    public void logout() throws Exception {
        mockMvc.perform(post("/api/user/logout")
                .with(csrf()))
                .andExpect(unauthenticated()); // 로그아웃 되었기 때문에 인증되지 않은 상태
    }
}