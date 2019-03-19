package com.donglu.cloud.controller;

import com.donglu.cloud.bean.QSystemMenu;
import com.donglu.cloud.bean.SystemMenu;
import com.donglu.cloud.config.SystemUserDetail;
import com.donglu.cloud.repository.SystemMenuRepository;
import com.google.common.collect.Lists;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class SystemController {

    @Autowired
    private SystemMenuRepository systemMenuRepository;

    @GetMapping(path = {"/", "/dashborad"})
    public String dashborad(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SystemUserDetail systemUserDetail = (SystemUserDetail) authentication.getPrincipal();
        model.addAttribute("menus", getSystemMenuList(systemUserDetail.getUsername()));
        return "dashborad";
    }

    private List<SystemMenu> getSystemMenuList(String username) {
        if("admin".equals(username)){
            OrderSpecifier<String> asc = QSystemMenu.systemMenu.menuOrder.asc();
            return Lists.newArrayList(systemMenuRepository.findAll(asc));
        }

        BooleanExpression eq = QSystemMenu.systemMenu.systemRole.systemUser.username.eq(username);
        return Lists.newArrayList(systemMenuRepository.findAll(eq));
    }
}
