package com.donglu.cloud.config;


import com.donglu.cloud.bean.SystemLog;
import com.donglu.cloud.repository.SystemLogRepository;
import com.donglu.cloud.utils.UserAgentUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Aspect
@Component
@Slf4j
public class WebLogConfig {

    @Autowired
    private SystemLogRepository systemLogRepository;

    @Pointcut("execution(public * com.donglu.cloud.controller.*.*(..))")
    public void cut() {
    }

    /**
     * 请求Controller 日志处理
     *
     * @param joinPoint {@link JoinPoint}
     */
    @Before("cut()")
    public void logDeBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        log.info("----------------------------------------------------------");
        log.info("用户代理:{}", UserAgentUtils.getUserAgent(request));
        log.info("请求路径:{}", request.getRequestURL().toString());
        log.info("请求类型:{}", request.getMethod());
        log.info("客户端IP:{}", request.getRemoteAddr());
        log.info("请求方法:{}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        log.info("请求参数:{}", Arrays.toString(joinPoint.getArgs()));
    }

    /**
     * 之后操作
     *
     * @param returnValue {@link Object}
     */
    @AfterReturning(pointcut = "cut()", returning = "returnValue")
    public void logDoAfterReturning(Object returnValue) {
        if (StringUtils.isEmpty(returnValue)) {
            returnValue = "";
        }
        log.info("请求响应:{}", returnValue);
        log.info("----------------------------------------------------------");
    }

    @AfterReturning(pointcut = "cut()")
    public void log(JoinPoint joinPoint){
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();

        String ip = request.getRemoteAddr();
        String method = request.getMethod();
        String url = request.getRequestURI();

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        PutMapping putMapping = methodSignature.getMethod().getDeclaredAnnotation(PutMapping.class);
        PostMapping postMapping = methodSignature.getMethod().getDeclaredAnnotation(PostMapping.class);
        DeleteMapping deleteMapping = methodSignature.getMethod().getDeclaredAnnotation(DeleteMapping.class);
        GetMapping getMapping = methodSignature.getMethod().getDeclaredAnnotation(GetMapping.class);

        String putName = Optional.ofNullable(putMapping).map(PutMapping::name).orElse("");
        String postName = Optional.ofNullable(postMapping).map(PostMapping::name).orElse("");
        String deleteName = Optional.ofNullable(deleteMapping).map(DeleteMapping::name).orElse("");
        String getName = Optional.ofNullable(getMapping).map(GetMapping::name).orElse("");
        String title = putName + postName + deleteName + getName;
        if (StringUtils.isEmpty(title)) {
            return;
        }

        log.info("----------------------------------------------------------");
        log.info("开始记录数据库日志");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = Optional.ofNullable(authentication).map(Principal::getName).orElse("平台");

        SystemLog systemLog = new SystemLog();
        systemLog.setIp(ip);
        systemLog.setUrl(url);
        systemLog.setMethodType(method);

        systemLog.setTitle(title);
        systemLog.setStatus(response.getStatus());
        systemLog.setUserName(username);
        systemLogRepository.save(systemLog);
        log.info("记录数据库日志结束:{}",systemLog);
        log.info("----------------------------------------------------------");
    }

}
