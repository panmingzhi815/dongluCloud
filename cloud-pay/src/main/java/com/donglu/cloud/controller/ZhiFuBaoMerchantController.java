package com.donglu.cloud.controller;

import com.donglu.cloud.bean.*;
import com.donglu.cloud.repository.WeiXinMerchantRepository;
import com.donglu.cloud.repository.ZhiFuBaoMerchantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class ZhiFuBaoMerchantController {

    @Autowired
    private ZhiFuBaoMerchantRepository zhiFuBaoMerchantRepository;

    @GetMapping("/zhifubaoMerchant")
    @ResponseBody
    public MsgResponse getMerchantList(@RequestParam(required = false, defaultValue = "1") Integer page, @RequestParam(required = false, defaultValue = "10") Integer limit) {
        Page<ZhiFuBaoMerchant> all = zhiFuBaoMerchantRepository.findAll(PageRequest.of(page - 1, limit));
        return MsgResponse.success(0, "", all.getTotalElements(), all.getContent());
    }

    @PostMapping("/zhifubaoMerchant")
    @ResponseBody
    public MsgResponse AddMerchant(@RequestBody ZhiFuBaoMerchant merchant) {
        zhiFuBaoMerchantRepository.save(merchant);
        return MsgResponse.success(0,"操作成功");
    }

    @PutMapping("/zhifubaoMerchant")
    @ResponseBody
    public MsgResponse updateMerchant(@RequestBody ZhiFuBaoMerchant merchant) {
        Optional<ZhiFuBaoMerchant> byId = zhiFuBaoMerchantRepository.findById(merchant.getId());
        if (byId.isPresent()) {
            ZhiFuBaoMerchant save = byId.get();
            save.setAppId(merchant.getAppId());
            save.setMchId(merchant.getMchId());
            save.setAlipayPublicKey(merchant.getAlipayPublicKey());
            save.setAppPrivateKey(merchant.getAppPrivateKey());
            zhiFuBaoMerchantRepository.save(save);
            return MsgResponse.success(0,"操作成功");
        }
        return MsgResponse.fail(MsgCode.code_10001);
    }

    @GetMapping("/zhifubaoMerchant_info/{id}")
    public String editMerchantPage(@PathVariable String id, Model model) {
        Optional<ZhiFuBaoMerchant> one = zhiFuBaoMerchantRepository.findOne(QZhiFuBaoMerchant.zhiFuBaoMerchant.id.eq(id));
        model.addAttribute("zhifubaoMerchant",one.get());
        return "pay/zhifubaoMerchant_info";
    }

    @GetMapping("/zhifubaoMerchant_info")
    public String addMerchantPage(Model model) {
        model.addAttribute("zhifubaoMerchant",new ZhiFuBaoMerchant());
        return "pay/zhifubaoMerchant_info";
    }
}
