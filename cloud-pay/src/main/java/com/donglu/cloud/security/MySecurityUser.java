package com.donglu.cloud.security;

import com.donglu.cloud.bean.SystemUser;
import com.donglu.cloud.bean.SystemUserStateEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.Optional;

public class MySecurityUser extends SystemUser implements UserDetails {

    private final PasswordEncoder passwordEncoder;

    public MySecurityUser(SystemUser systemUser, PasswordEncoder passwordEncoder) {
        BeanUtils.copyProperties(systemUser,this);
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList(getRole());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return getStateEnum() == SystemUserStateEnum.正常;
    }

    @Override
    public String getPassword() {
        return Optional.ofNullable(super.getPassword()).orElse(passwordEncoder.encode("123456"));
    }
}
