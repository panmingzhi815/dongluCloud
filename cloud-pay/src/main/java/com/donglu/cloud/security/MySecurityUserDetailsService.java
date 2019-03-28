package com.donglu.cloud.security;

import com.donglu.cloud.bean.QSystemUser;
import com.donglu.cloud.bean.SystemUser;
import com.donglu.cloud.repository.SystemUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MySecurityUserDetailsService implements UserDetailsService {

    @Autowired
    private SystemUserRepository systemUserRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public MySecurityUser loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<SystemUser> one = systemUserRepository.findOne(QSystemUser.systemUser.username.eq(username));
        if (!one.isPresent()) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        return new MySecurityUser(one.get(), passwordEncoder);
    }
}
