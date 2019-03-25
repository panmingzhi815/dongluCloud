package com.donglu.cloud.config;

import com.donglu.cloud.bean.*;
import com.donglu.cloud.repository.*;
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
    private SystemRoleRepository systemRoleRepository;
    @Autowired
    private SystemMenuRepository systemMenuRepository;
    @Autowired
    private SystemDictionaryRepository systemDictionaryRepository;

    @Override
    public void run(String... args) {
        if(systemMenuRepository.count() == 0){
            initMenu();
        }

        if(systemRoleRepository.count() == 0){
            initRole();
        }

        if (systemUserRepository.count() == 0) {
            initUser();
        }

        if(systemDictionaryRepository.count() == 0){
            initDictionary();
        }

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

    private void initRole() {
        log.info("初始化系统角色");
        SystemRole systemRole = new SystemRole();
        systemRole.setRoleName("项目管理员");
        systemRole.setRoleDescribe("管理项目");
        systemRoleRepository.save(systemRole);

        SystemRole systemRole1 = new SystemRole();
        systemRole1.setRoleName("普通操作员");
        systemRole1.setRoleDescribe("查询记录");
        systemRoleRepository.save(systemRole1);
    }

    private void initUser() {
        log.info("初始化系统登录用户");
        SystemUser systemUser = new SystemUser();
        systemUser.setStateEnum(SystemUserStateEnum.正常);
        systemUser.setUsername("admin");
        systemUser.setEmail("154341736@qq.com");
        systemUser.setPassword(passwordEncoder.encode("123456"));
        systemUser.setNickName("系统管理员");
        systemUserRepository.save(systemUser);
    }

    private void initMenu() {
        log.info("初始化系统菜单");

        SystemMenu systemMenu1 = saveSystemMenu(null, "系统管理", null, "folder");

        saveSystemMenu(systemMenu1, "用户管理", "system/user", "menu");
        saveSystemMenu(systemMenu1, "角色管理", "system/role", "menu");
        saveSystemMenu(systemMenu1, "菜单管理", "system/menu", "menu");
        saveSystemMenu(systemMenu1, "系统配置", "system/dictionary", "menu");

        SystemMenu systemMenu2 = saveSystemMenu(null, "支付管理", null, "folder");

        saveSystemMenu(systemMenu2,"项目管理","pay/project","menu");
        saveSystemMenu(systemMenu2,"微信支付","pay/weixinMerchant","menu");
        saveSystemMenu(systemMenu2,"支付宝支付","pay/zhifubaoMerchant","menu");

        SystemMenu systemMenu3 = saveSystemMenu(null, "订单管理", null, "folder");
        saveSystemMenu(systemMenu3,"支付订单","order/pay","menu");
        saveSystemMenu(systemMenu3,"退款记录","order/refund","menu");
    }

    public SystemMenu saveSystemMenu(SystemMenu parent, String menuName, String menuUrl, String menuType){
        SystemMenu systemMenu3 = new SystemMenu();
        systemMenu3.setParent(parent);
        systemMenu3.setMenuName(menuName);
        systemMenu3.setMenuUrl(menuUrl);
        systemMenu3.setMenuType(menuType);
        systemMenuRepository.save(systemMenu3);
        return systemMenu3;
    }
}
