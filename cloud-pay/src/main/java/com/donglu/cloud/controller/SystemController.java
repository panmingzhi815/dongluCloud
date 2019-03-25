package com.donglu.cloud.controller;

import com.donglu.cloud.bean.*;
import com.donglu.cloud.repository.SystemMenuRepository;
import com.donglu.cloud.repository.SystemRoleRepository;
import com.donglu.cloud.repository.SystemUserRepository;
import com.google.common.collect.Lists;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class SystemController {

    @Autowired
    private SystemMenuRepository systemMenuRepository;
    @Autowired
    private SystemUserRepository systemUserRepository;
    @Autowired
    private SystemRoleRepository systemRoleRepository;

    @GetMapping(path = {"/", "/index"})
    public String index(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        model.addAttribute("menus", getSystemMenuList(user.getUsername()));
        return "index";
    }

    private List<SystemMenu> getSystemMenuList(String username) {
        BooleanExpression folder = QSystemMenu.systemMenu.menuType.eq("folder");
        OrderSpecifier<String> asc = QSystemMenu.systemMenu.menuOrder.asc();
        if ("admin".equals(username)) {
            return Lists.newArrayList(systemMenuRepository.findAll(folder, asc));
        } else {
            BooleanExpression expression = QSystemUser.systemUser.username.eq(username);
            Optional<SystemUser> one = systemUserRepository.findOne(expression);
            return one.get().getSystemRoleList().stream().map(m -> m.getSystemMenuList()).flatMap(c -> c.stream()).collect(Collectors.toList());
        }
    }

    @GetMapping("/systemUser")
    @ResponseBody
    public MsgResponse getSystemUserList(@RequestParam(required = false, defaultValue = "1") Integer page, @RequestParam(required = false, defaultValue = "10") Integer limit,@RequestParam(required = false)String username) {
        BooleanExpression notNull = QSystemUser.systemUser.id.isNotNull();
        if(StringUtils.isNotBlank(username)){
            notNull = notNull.and(QSystemUser.systemUser.username.like("%" + username + "%"));
        }
        Page<SystemUser> all = systemUserRepository.findAll(notNull,PageRequest.of(page - 1, limit));
        return MsgResponse.success(0, "", all.getTotalElements(), all.getContent());
    }

    @PutMapping("/systemUser")
    @ResponseBody
    public MsgResponse updateSystemUser(@RequestBody SystemUser systemUser) {
        Optional<SystemUser> byId = systemUserRepository.findById(systemUser.getId());
        if (!byId.isPresent()) {
            return MsgResponse.fail(MsgCode.code_10001);
        }

        SystemUser user = byId.get();
        user.setUsername(systemUser.getUsername());
        user.setNickName(systemUser.getNickName());
        user.setEmail(systemUser.getEmail());
        user.setStateEnum(systemUser.getStateEnum());
        systemUserRepository.save(user);
        return MsgResponse.success(0, "操作成功");

    }

    @PostMapping("/systemUser")
    @ResponseBody
    public MsgResponse SaveSystemUser(@RequestBody SystemUser systemUser) {
        systemUserRepository.save(systemUser);
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

    //---------------------------------------------------

    @GetMapping("/systemRole")
    @ResponseBody
    public MsgResponse getSystemRoleList(@RequestParam(required = false, defaultValue = "1") Integer page, @RequestParam(required = false, defaultValue = "10") Integer limit,@RequestParam(required = false)String roleName) {
        BooleanExpression notNull = QSystemRole.systemRole.id.isNotNull();
        if(StringUtils.isNotBlank(roleName)){
            notNull = notNull.and(QSystemRole.systemRole.roleName.like("%" + roleName + "%"));
        }
        Page<SystemRole> all = systemRoleRepository.findAll(notNull,PageRequest.of(page - 1, limit));
        return MsgResponse.success(0, "", all.getTotalElements(), all.getContent());
    }

    @PutMapping("/systemRole")
    @ResponseBody
    public MsgResponse updateSystemRole(@RequestBody SystemRole systemRole) {
        Optional<SystemRole> byId = systemRoleRepository.findById(systemRole.getId());
        if (!byId.isPresent()) {
            return MsgResponse.fail(MsgCode.code_10001);
        }

        SystemRole user = byId.get();
        user.setRoleName(systemRole.getRoleName());
        user.setRoleDescribe(systemRole.getRoleDescribe());
        systemRoleRepository.save(user);
        return MsgResponse.success(0, "操作成功");

    }

    @PostMapping("/systemRole")
    @ResponseBody
    public MsgResponse SaveSystemRole(@RequestBody SystemRole systemUser) {
        systemRoleRepository.save(systemUser);
        return MsgResponse.success(0, "操作成功");
    }

    @GetMapping("/systemRole_info/{id}")
    public String redirectRoleEditPage(@PathVariable String id, Model model) {
        Optional<SystemRole> one = systemRoleRepository.findOne(QSystemRole.systemRole.id.eq(id));
        model.addAttribute("systemRole", one.get());
        return "system/role_info";
    }

    @GetMapping("/systemRole_info")
    public String redirectRoleAddPage(Model model) {
        model.addAttribute("systemRole", new SystemRole());
        return "system/role_info";
    }
}
