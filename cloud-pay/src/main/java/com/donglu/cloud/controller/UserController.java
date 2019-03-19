package com.donglu.cloud.controller;

import com.donglu.cloud.bean.MsgResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author panmingzhi815
 * 用户管理
 */
@Controller
public class UserController {

    @PostMapping("/user")
    @ResponseBody
    public MsgResponse addUser() {
        return new MsgResponse();
    }
}
