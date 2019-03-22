package com.donglu.cloud.controller;

import com.donglu.cloud.bean.*;
import com.donglu.cloud.repository.MerchantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class MerchantController {

    @Autowired
    private MerchantRepository merchantRepository;

    @GetMapping("/merchant")
    @ResponseBody
    public MsgResponse getMerchantList(@RequestParam(required = false, defaultValue = "1") Integer page, @RequestParam(required = false, defaultValue = "10") Integer limit) {
        Page<Merchant> all = merchantRepository.findAll(PageRequest.of(page - 1, limit));
        return MsgResponse.success(0, "", all.getTotalElements(), all.getContent());
    }

    @PostMapping("/merchant")
    @ResponseBody
    public MsgResponse AddMerchant(@RequestBody Merchant merchant) {
        merchantRepository.save(merchant);
        return MsgResponse.success(0,"操作成功");
    }

    @PutMapping("/merchant")
    @ResponseBody
    public MsgResponse updateMerchant(@RequestBody Merchant merchant) {
        Optional<Merchant> byId = merchantRepository.findById(merchant.getId());
        if (byId.isPresent()) {
            Merchant save = byId.get();
            save.setCode(merchant.getCode());
            save.setName(merchant.getName());
            save.setAddress(merchant.getAddress());
            save.setEmail(merchant.getEmail());
            save.setTel(merchant.getTel());
            merchantRepository.save(save);
            return MsgResponse.success(0,"操作成功");
        }
        return MsgResponse.fail(MsgCode.code_10001);
    }

    @GetMapping("/merchant_info/{id}")
    public String editMerchantPage(@PathVariable String id, Model model) {
        Optional<Merchant> one = merchantRepository.findOne(QMerchant.merchant.id.eq(id));
        model.addAttribute("merchant",one.get());
        return "pay/merchant_info";
    }

    @GetMapping("/merchant_info")
    public String addMerchantPage(Model model) {
        model.addAttribute("merchant",new Merchant());
        return "pay/merchant_info";
    }
}
