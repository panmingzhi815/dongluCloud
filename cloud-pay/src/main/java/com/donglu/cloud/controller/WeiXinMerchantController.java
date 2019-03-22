package com.donglu.cloud.controller;

import com.donglu.cloud.bean.*;
import com.donglu.cloud.repository.WeiXinMerchantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class WeiXinMerchantController {

    @Autowired
    private WeiXinMerchantRepository weiXinMerchantRepository;

    @GetMapping("/weixinMerchant")
    @ResponseBody
    public MsgResponse getMerchantList(@RequestParam(required = false, defaultValue = "1") Integer page, @RequestParam(required = false, defaultValue = "10") Integer limit) {
        Page<WeiXinMerchant> all = weiXinMerchantRepository.findAll(PageRequest.of(page - 1, limit));
        return MsgResponse.success(0, "", all.getTotalElements(), all.getContent());
    }

    @PostMapping("/weixinMerchant")
    @ResponseBody
    public MsgResponse AddMerchant(@RequestBody WeiXinMerchant merchant) {
        weiXinMerchantRepository.save(merchant);
        return MsgResponse.success(0,"操作成功");
    }

    @PutMapping("/weixinMerchant")
    @ResponseBody
    public MsgResponse updateMerchant(@RequestBody WeiXinMerchant merchant) {
        Optional<WeiXinMerchant> byId = weiXinMerchantRepository.findById(merchant.getId());
        if (byId.isPresent()) {
            WeiXinMerchant save = byId.get();
            save.setAppId(merchant.getAppId());
            save.setMchId(merchant.getMchId());
            save.setApiSecret(merchant.getApiSecret());
            save.setAppSecret(merchant.getAppSecret());
            save.setParent(merchant.getParent());
            weiXinMerchantRepository.save(save);
            return MsgResponse.success(0,"操作成功");
        }
        return MsgResponse.fail(MsgCode.code_10001);
    }

    @GetMapping("/weixinMerchant_info/{id}")
    public String editMerchantPage(@PathVariable String id, Model model) {
        Optional<WeiXinMerchant> one = weiXinMerchantRepository.findOne(QWeiXinMerchant.weiXinMerchant.id.eq(id));
        model.addAttribute("weixinMerchant",one.get());
        return "pay/weixinMerchant_info";
    }

    @GetMapping("/weixinMerchant_info")
    public String addMerchantPage(Model model) {
        model.addAttribute("weixinMerchant",new WeiXinMerchant());
        return "pay/weixinMerchant_info";
    }
}
