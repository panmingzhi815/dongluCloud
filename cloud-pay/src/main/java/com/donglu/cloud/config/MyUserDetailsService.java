package com.donglu.cloud.config;

import com.donglu.cloud.bean.QSystemUser;
import com.donglu.cloud.bean.SystemUser;
import com.donglu.cloud.bean.SystemUserStateEnum;
import com.donglu.cloud.repository.SystemUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private SystemUserRepository systemUserRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<SystemUser> one = systemUserRepository.findOne(QSystemUser.systemUser.username.eq(username));
        if (!one.isPresent()) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        SystemUser systemUser = one.get();
        String defaultPassword = Optional.ofNullable(systemUser.getPassword()).orElse(bCryptPasswordEncoder.encode("123456"));
        return User.builder()
                .username(systemUser.getUsername())
                .password(defaultPassword)
                .disabled(systemUser.getStateEnum() != SystemUserStateEnum.正常)
                .authorities(systemUser.getRole())
                .build();
    }
}
