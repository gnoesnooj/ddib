package com.ddib.user.user.service.user;


import com.ddib.user.user.dto.request.TokenRequestDto;
import com.ddib.user.user.dto.request.UserModifyRequestDto;
import com.ddib.user.user.dto.resposne.UserInfoDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserInfoDto findUser(Integer userId);
    void modifyUserInfo(UserModifyRequestDto requestDto, Integer userId);
    void deleteUser(Integer userId);
    void saveToken(TokenRequestDto tokenRequestDto);
}
