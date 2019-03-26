package com.donglu.cloud.config;

import com.donglu.cloud.bean.*;
import com.donglu.cloud.repository.ProjectRepository;
import com.donglu.cloud.repository.SystemDictionaryRepository;
import com.donglu.cloud.repository.SystemMenuRepository;
import com.donglu.cloud.repository.SystemUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Slf4j
public class SystemInitCommand implements CommandLineRunner {
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private SystemUserRepository systemUserRepository;
    @Autowired
    private SystemMenuRepository systemMenuRepository;
    @Autowired
    private SystemDictionaryRepository systemDictionaryRepository;
    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public void run(String... args) {
        if (systemMenuRepository.count() == 0) {
            initMenu();
        }

        if (systemUserRepository.count() == 0) {
            initUser();
        }

        if (systemDictionaryRepository.count() == 0) {
            initDictionary();
        }

        if (projectRepository.count() == 0) {
            initProject();
        }
    }

    private void initProject() {
        log.info("初始化项目信息");
        Project project = new Project();
        project.setCode("10001");
        project.setName("默认项目");
        project.setAddress("深圳市宝田三路66号");
        project.setEmail("154341736@qq.com");
        project.setTel("18589024202");
        projectRepository.save(project);
    }

    private void initDictionary() {
        log.info("初始化系统配置");
        createDictionary("alipay.gateway", "https://openapi.alipaydev.com/gateway.do", "阿里支付网关");
        createDictionary("alipay.callback", "https://www.donluhitec.net/alipay/callback", "阿里支付回调地址");
        createDictionary("wxpay.callback", "https://www.donluhitec.net/wxpay/callback", "微信支付回调地址");
    }

    private void createDictionary(String key, String value, String describe) {
        SystemDictionary systemDictionary = new SystemDictionary();
        systemDictionary.setKey(key);
        systemDictionary.setValue(value);
        systemDictionary.setDescribe(describe);
        systemDictionaryRepository.save(systemDictionary);
    }

    private void initUser() {
        log.info("初始化系统登录用户");
        SystemUser systemUser = new SystemUser();
        systemUser.setStateEnum(SystemUserStateEnum.正常);
        systemUser.setUsername("admin");
        systemUser.setNickName("超管");
        systemUser.setEmail("154341736@qq.com");
        systemUser.setPassword(passwordEncoder.encode("123456"));
        systemUser.setRole("ADMIN");
        systemUserRepository.save(systemUser);
    }

    private void initMenu() {
        log.info("初始化系统菜单");

        SystemMenu systemMenu1 = saveSystemMenu(null, "系统管理", null, "folder", "ADMIN");

        saveSystemMenu(systemMenu1, "用户管理", "system/user", "menu", "ADMIN");
        saveSystemMenu(systemMenu1, "系统配置", "system/dictionary", "menu", "ADMIN");
        saveSystemMenu(systemMenu1, "系统日志", "system/log", "menu", "ADMIN");

        SystemMenu systemMenu2 = saveSystemMenu(null, "支付管理", null, "folder", "ADMIN");

        saveSystemMenu(systemMenu2, "项目管理", "pay/projectTpl", "menu", "ADMIN");
        saveSystemMenu(systemMenu2, "微信商户", "pay/weixinMerchantTpl", "menu", "ADMIN");
        saveSystemMenu(systemMenu2, "支付宝商户", "pay/zhifubaoMerchantTpl", "menu", "ADMIN");

        SystemMenu systemMenu3 = saveSystemMenu(null, "订单管理", null, "folder", "ADMIN,USER");
        saveSystemMenu(systemMenu3, "支付订单", "order/pay", "menu", "ADMIN,USER");
        saveSystemMenu(systemMenu3, "退款记录", "order/refund", "menu", "ADMIN,USER ");
    }

    public SystemMenu saveSystemMenu(SystemMenu parent, String menuName, String menuUrl, String menuType, String menuCode) {
        SystemMenu systemMenu3 = new SystemMenu();
        systemMenu3.setMenuCode(menuCode);
        systemMenu3.setParent(parent);
        systemMenu3.setMenuName(menuName);
        systemMenu3.setMenuUrl(menuUrl);
        systemMenu3.setMenuType(menuType);
        systemMenuRepository.save(systemMenu3);
        return systemMenu3;
    }
}
