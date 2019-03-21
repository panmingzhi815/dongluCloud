package com.donglu.cloud.config;

import com.donglu.cloud.bean.QSystemUser;
import com.donglu.cloud.bean.SystemUser;
import com.donglu.cloud.repository.SystemUserRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

/**
 * @author panmingzhi815
 * 系统权限配置
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SystemUserRepository systemUserRepository;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**", "/favicon.ico","/layuiadmin/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors().disable();
        http.requestCache().disable();
        http.headers().frameOptions().disable();
        http.authorizeRequests().antMatchers("/login").permitAll()
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/login").defaultSuccessUrl("/").and().logout().permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(username -> {
            Optional<SystemUser> one = systemUserRepository.findOne(QSystemUser.systemUser.username.eq(username));
            if (!one.isPresent()) {
                throw new UsernameNotFoundException("用户名或密码错误");
            }
            SystemUser systemUser = one.get();
            return User.builder().username(systemUser.getUsername()).password(systemUser.getPassword()).authorities(Lists.newArrayList()).build();
        }).passwordEncoder(new BCryptPasswordEncoder());
    }

}
