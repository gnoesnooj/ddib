package com.ddib.user.user.dto.resposne;

import com.ddib.user.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {
    private final User user;

    // 권환을 확인하는 메서드 (role)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add((GrantedAuthority) () -> "성공");
        return collection;
    }

    @Override
    public String getPassword() {
        return null;
    }

    // 주의 : getUsername() 메소드이지만 userId 반환
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    // 계정이 만료되지 않음을 확인
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정이 막히지 않음을 확인
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 사용자의 자격 증명(암호)이 만료되었는지 여부
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정의 활성화 여부 리턴
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "CustomUserDetails{" +
                "user=" + user +
                '}';
    }
}