package com.donglu.cloud.controller;

import com.donglu.cloud.bean.*;
import com.donglu.cloud.repository.SystemMenuRepository;
import com.donglu.cloud.repository.SystemUserRepository;
import com.google.common.collect.Lists;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class SystemController {

    @Autowired
    private SystemMenuRepository systemMenuRepository;
    @Autowired
    private SystemUserRepository systemUserRepository;


    @GetMapping(path = {"/", "/index"},name = "用户登录")
    public String index(Model model) {
        model.addAttribute("menus", getSystemMenuList());
        model.addAttribute("user", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return "index";
    }

    private List<SystemMenu> getSystemMenuList() {
        BooleanExpression folder = QSystemMenu.systemMenu.parent.isNull();
        OrderSpecifier<String> asc = QSystemMenu.systemMenu.menuOrder.asc();
        return Lists.newArrayList(systemMenuRepository.findAll(folder, asc));
    }

    @GetMapping("/systemUser")
    @ResponseBody
    public MsgResponse getSystemUserList(@RequestParam(required = false, defaultValue = "1") Integer page, @RequestParam(required = false, defaultValue = "10") Integer limit, @RequestParam(required = false) String username) {
        BooleanExpression notNull = QSystemUser.systemUser.id.isNotNull();
        if (StringUtils.isNotBlank(username)) {
            notNull = notNull.and(QSystemUser.systemUser.username.like("%" + username + "%"));
        }
        Page<SystemUser> all = systemUserRepository.findAll(notNull, PageRequest.of(page - 1, limit));
        return MsgResponse.success(0, "", all.getTotalElements(), all.getContent());
    }

    @PutMapping(value = "/systemUser",name = "修改用户")
    @ResponseBody
    public MsgResponse updateSystemUser(@RequestBody SystemUser systemUser) {
        Optional<SystemUser> byId = systemUserRepository.findById(systemUser.getId());
        if (!byId.isPresent()) {
            return MsgResponse.fail(MsgCode.code_10001);
        }

        SystemUser user = byId.get();
        user.setUsername(systemUser.getUsername());
        user.setNickName(systemUser.getNickName());
        user.setRole(systemUser.getRole());
        user.setEmail(systemUser.getEmail());
        user.setStateEnum(systemUser.getStateEnum());
        systemUserRepository.save(user);
        return MsgResponse.success(0, "操作成功");

    }

    @PostMapping(value = "/systemUser",name = "添加用户")
    @ResponseBody
    public MsgResponse SaveSystemUser(@RequestBody SystemUser systemUser) {
        systemUserRepository.save(systemUser);
        return MsgResponse.success(0, "操作成功");
    }

    @DeleteMapping(value = "/systemUser/{id}",name = "删除用户")
    @ResponseBody
    public MsgResponse delSystemUser(@PathVariable String id) {
        systemUserRepository.deleteById(id);
        return MsgResponse.success(0, "操作成功");
    }

    @GetMapping("/systemUser_info/{id}")
    public String redirectEditPage(@PathVariable String id, Model model) {
        Optional<SystemUser> one = systemUserRepository.findOne(QSystemUser.systemUser.id.eq(id));
        model.addAttribute("systemUser", one.get());
        return "system/user_info";
    }

    @GetMapping("/systemUser_info")
    public String redirectAddPage(Model model) {
        model.addAttribute("systemUser", new SystemUser());
        return "system/user_info";
    }
}
