package com.donglu.cloud.config;

import com.donglu.cloud.bean.SystemMenu;
import com.donglu.cloud.bean.SystemRole;
import com.donglu.cloud.bean.SystemUser;
import com.donglu.cloud.repository.SystemMenuRepository;
import com.donglu.cloud.repository.SystemRoleRepository;
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
    private SystemRoleRepository systemRoleRepository;
    @Autowired
    private SystemMenuRepository systemMenuRepository;

    @Override
    public void run(String... args) {
        long menuCount = systemMenuRepository.count();
        if(menuCount == 0){
            initMenu();
        }


        long roleCount = systemRoleRepository.count();
        if(roleCount == 0){
            initRole();
        }

        long userCount = systemUserRepository.count();
        if (userCount == 0) {
            initUser();
        }
    }

    private void initRole() {
        log.info("初始化系统角色");
        SystemRole systemRole = new SystemRole();
        systemRole.setRoleCode("project");
        systemRole.setRoleName("项目管理员");
        systemRoleRepository.save(systemRole);

        SystemRole systemRole1 = new SystemRole();
        systemRole1.setRoleCode("user");
        systemRole1.setRoleName("普通操作员");
        systemRoleRepository.save(systemRole1);
    }

    private void initUser() {
        log.info("初始化系统登录用户");
        SystemUser systemUser = new SystemUser();
        systemUser.setUsername("admin");
        systemUser.setPassword(passwordEncoder.encode("123456"));
        systemUser.setNickName("系统管理员");
        systemUserRepository.save(systemUser);
    }

    private void initMenu() {
        log.info("初始化系统菜单");
        SystemMenu systemMenu = new SystemMenu();
        systemMenu.setMenuName("系统管理");
        systemMenu.setMenuType("folder");
        systemMenuRepository.save(systemMenu);

        SystemMenu systemMenu1 = new SystemMenu();
        systemMenu1.setParent(systemMenu);
        systemMenu1.setMenuName("用户管理");
        systemMenu1.setMenuUrl("system/user");
        systemMenu1.setMenuType("folder");
        systemMenuRepository.save(systemMenu1);

        SystemMenu systemMenu2 = new SystemMenu();
        systemMenu2.setParent(systemMenu);
        systemMenu2.setMenuName("角色管理");
        systemMenu2.setMenuUrl("system/role");
        systemMenu2.setMenuType("folder");
        systemMenuRepository.save(systemMenu2);

        SystemMenu systemMenu3 = new SystemMenu();
        systemMenu3.setParent(systemMenu);
        systemMenu3.setMenuName("菜单管理");
        systemMenu3.setMenuUrl("system/menu");
        systemMenu3.setMenuType("folder");
        systemMenuRepository.save(systemMenu3);
    }
}
