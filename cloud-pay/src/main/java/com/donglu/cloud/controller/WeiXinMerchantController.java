package com.donglu.cloud.controller;

import com.donglu.cloud.bean.*;
import com.donglu.cloud.repository.ProjectRepository;
import com.donglu.cloud.repository.WeiXinMerchantRepository;
import com.google.common.collect.Lists;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.apache.commons.lang3.StringUtils;
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
    @Autowired
    private ProjectRepository projectRepository;

    @GetMapping("/weixinMerchant")
    @ResponseBody
    public MsgResponse getMerchantList(@RequestParam(required = false, defaultValue = "1") Integer page, @RequestParam(required = false, defaultValue = "10") Integer limit,
                                       @RequestParam(required = false) String appName,
                                       @RequestParam(required = false) String appId,
                                       @RequestParam(required = false) String mchId,
                                       @RequestParam(required = false) String serviceMchId) {
        BooleanExpression notNull = QWeiXinMerchant.weiXinMerchant.id.isNotNull();
        if (StringUtils.isNotEmpty(appName)) {
            notNull = notNull.and(QWeiXinMerchant.weiXinMerchant.appName.like("%" + appName + "%"));
        }
        if (StringUtils.isNotEmpty(appId)) {
            notNull = notNull.and(QWeiXinMerchant.weiXinMerchant.appId.like("%" + appId + "%"));
        }
        if (StringUtils.isNotEmpty(mchId)) {
            notNull = notNull.and(QWeiXinMerchant.weiXinMerchant.mchId.like("%" + mchId + "%"));
        }
        if (StringUtils.isNotEmpty(serviceMchId)) {
            notNull = notNull.and(QWeiXinMerchant.weiXinMerchant.serviceMchId.like("%" + serviceMchId + "%"));
        }
        Page<WeiXinMerchant> all = weiXinMerchantRepository.findAll(notNull,PageRequest.of(page - 1, limit));
        return MsgResponse.success(0, "", all.getTotalElements(), all.getContent());
    }

    @PostMapping(value = "/weixinMerchant",name = "添加微信商户")
    @ResponseBody
    public MsgResponse AddMerchant(@RequestBody WeiXinMerchant merchant) {
        weiXinMerchantRepository.save(merchant);
        return MsgResponse.success(0, "操作成功");
    }

    @PutMapping(value = "/weixinMerchant",name = "修改微信商户")
    @ResponseBody
    public MsgResponse updateMerchant(@RequestBody WeiXinMerchant merchant) {
        Optional<WeiXinMerchant> byId = weiXinMerchantRepository.findById(merchant.getId());
        if (byId.isPresent()) {
            WeiXinMerchant save = byId.get();
            save.setAppId(merchant.getAppId());
            save.setMchId(merchant.getMchId());
            save.setAppName(merchant.getAppName());
            save.setServiceMchId(merchant.getServiceMchId());
            save.setAppKey(merchant.getAppKey());
            save.setProject(merchant.getProject());
            weiXinMerchantRepository.save(save);
            return MsgResponse.success(0, "操作成功");
        }
        return MsgResponse.fail(MsgCode.code_10001);
    }

    @GetMapping("/weixinMerchant_info/{id}")
    public String editMerchantPage(@PathVariable String id, Model model) {
        Optional<WeiXinMerchant> one = weiXinMerchantRepository.findOne(QWeiXinMerchant.weiXinMerchant.id.eq(id));
        model.addAttribute("weixinMerchant", one.get());
        model.addAttribute("projects", Lists.newArrayList(projectRepository.findAll(QProject.project.code.asc()).iterator()));
        return "pay/weixinMerchant_info";
    }

    @GetMapping("/weixinMerchant_info")
    public String addMerchantPage(Model model) {
        model.addAttribute("weixinMerchant", new WeiXinMerchant());
        model.addAttribute("projects", Lists.newArrayList(projectRepository.findAll(QProject.project.code.asc()).iterator()));
        return "pay/weixinMerchant_info";
    }

}
