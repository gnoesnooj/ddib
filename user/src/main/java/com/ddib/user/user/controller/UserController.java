package com.ddib.user.user.controller;

import com.ddib.user.user.dto.request.TokenRequestDto;
import com.ddib.user.user.dto.request.UserModifyRequestDto;
import com.ddib.user.user.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/user", produces = "application/json")
@Tag(name = "User Controller", description = "일반회원 API")
public class UserController {
    private final UserService userService;

    @GetMapping("/{userId}")
    @Operation(summary = "일반회원 정보 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "일반회원 정보 조회 성공"),
            @ApiResponse(responseCode = "400", description = "일반회원 정보 조회 실패")
    })
    public ResponseEntity<?> userDetails(@PathVariable Integer userId) {
        try {
            return new ResponseEntity<>(userService.findUser(userId), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{userId}")
    @Operation(summary = "일반회원 정보 수정 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "일반회원 정보 수정 성공"),
            @ApiResponse(responseCode = "400", description = "일반회원 정보 수정 실패")
    })
    public ResponseEntity<?> userInfoModify(@RequestBody UserModifyRequestDto requestDto, @PathVariable Integer userId) {
        try {
            userService.modifyUserInfo(requestDto, userId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "일반회원 탈퇴 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "일반회원 탈퇴 성공"),
            @ApiResponse(responseCode = "400", description = "일반회원 탈퇴 실패")
    })
    public ResponseEntity<?> userDelete(@PathVariable Integer userId) {
        try {
            userService.deleteUser(userId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/logout")
    @Operation(summary = "일반회원 로그아웃 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "일반회원 로그아웃 성공"),
            @ApiResponse(responseCode = "400", description = "일반회원 로그아웃 실패")
    })
    public ResponseEntity<?> logout(HttpServletResponse response) {
        try {
            Cookie refresh = new Cookie("refresh", null);
            Cookie access = new Cookie("Authorization", null);
            Cookie num = new Cookie("num", null);

            refresh.setMaxAge(0);
            access.setMaxAge(0);
            num.setMaxAge(0);

            response.addCookie(refresh);
            response.addCookie(access);
            response.addCookie(num);

            response.sendRedirect("https://ddib.kro.kr");

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/token")
    @Operation(summary = "일반회원 토큰 저장 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "일반회원 토큰 저장 성공"),
            @ApiResponse(responseCode = "400", description = "일반회원 토큰 저장 실패")
    })
    public ResponseEntity<?> tokenSave(@RequestBody TokenRequestDto tokenRequestDto) {
        try {
            userService.saveToken(tokenRequestDto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
    }
}
