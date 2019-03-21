package com.donglu.cloud.controller;

import com.donglu.cloud.bean.*;
import com.donglu.cloud.repository.SystemMenuRepository;
import com.donglu.cloud.repository.SystemUserRepository;
import com.google.common.collect.Lists;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class SystemController {

    @Autowired
    private SystemMenuRepository systemMenuRepository;
    @Autowired
    private SystemUserRepository systemUserRepository;

    @GetMapping(path = {"/", "/index"})
    public String dashborad(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        model.addAttribute("menus", getSystemMenuList(user.getUsername()));
        return "index";
    }

    private List<SystemMenu> getSystemMenuList(String username) {
        if("admin".equals(username)){
            BooleanExpression folder = QSystemMenu.systemMenu.menuType.eq("folder");
            OrderSpecifier<String> asc = QSystemMenu.systemMenu.menuOrder.asc();
            return Lists.newArrayList(systemMenuRepository.findAll(folder,asc));
        }

        BooleanExpression folder = QSystemMenu.systemMenu.menuType.eq("folder");
        BooleanExpression eq = QSystemMenu.systemMenu.systemRole.systemUser.username.eq(username);
        OrderSpecifier<String> asc = QSystemMenu.systemMenu.menuOrder.asc();
        return Lists.newArrayList(systemMenuRepository.findAll(folder.and(eq),asc));
    }

    @GetMapping("/systemUser")
    @ResponseBody
    public MsgResponse getUsers(@RequestParam(required = false,defaultValue = "1") Integer page,@RequestParam(required = false,defaultValue = "10") Integer limit) {
        Page<SystemUser> all = systemUserRepository.findAll(PageRequest.of(page-1, limit));
       return MsgResponse.success(0,"",all.getTotalElements(),all.getContent());
    }

    @GetMapping("/systemUser/{id}")
    public String getUsers(@PathVariable String id,Model model) {
        Optional<SystemUser> one = systemUserRepository.findOne(QSystemUser.systemUser.id.eq(id));
        model.addAttribute("systemUser",one.get());
        return "system/user_info";
    }

    @PutMapping("/systemUser/{id}")
    @ResponseBody
    public MsgResponse getUsers(@PathVariable String id,@RequestBody SystemUser systemUser) {
        Optional<SystemUser> byId = systemUserRepository.findById(id);
        if (byId.isPresent()) {
            SystemUser user = byId.get();
            user.setUsername(systemUser.getUsername());
            user.setNickName(systemUser.getNickName());
            user.setEmail(systemUser.getEmail());
            user.setStateEnum(systemUser.getStateEnum());
            systemUserRepository.save(user);
            return MsgResponse.success(0,"操作成功");
        }

        return MsgResponse.fail(MsgCode.code_10001);
    }

    @PostMapping("/systemUser")
    @ResponseBody
    public MsgResponse getUsers(@RequestBody SystemUser systemUser) {
        systemUserRepository.save(systemUser);
        return MsgResponse.success(0,"操作成功");
    }
}
