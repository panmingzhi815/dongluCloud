package com.donglu.cloud.controller;

import com.donglu.cloud.bean.*;
import com.donglu.cloud.repository.ProjectRepository;
import com.donglu.cloud.repository.ZhiFuBaoMerchantRepository;
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
@RequestMapping("/pay")
public class ZhiFuBaoMerchantController {

    @Autowired
    private ZhiFuBaoMerchantRepository zhiFuBaoMerchantRepository;
    @Autowired
    private ProjectRepository projectRepository;

    @GetMapping("/zhifubaoMerchant")
    @ResponseBody
    public MsgResponse getMerchantList(@RequestParam(required = false, defaultValue = "1") Integer page, @RequestParam(required = false, defaultValue = "10") Integer limit,
                                       @RequestParam(required = false) String appName,
                                       @RequestParam(required = false) String appId,
                                       @RequestParam(required = false) String mchId) {
        BooleanExpression notNull = QZhiFuBaoMerchant.zhiFuBaoMerchant.id.isNotNull();
        if (StringUtils.isNotEmpty(appName)) {
            notNull = notNull.and(QZhiFuBaoMerchant.zhiFuBaoMerchant.appName.like("%"+appName+"%"));
        }
        if (StringUtils.isNotEmpty(appId)) {
            notNull = notNull.and(QZhiFuBaoMerchant.zhiFuBaoMerchant.appId.like("%"+appId+"%"));
        }
        if (StringUtils.isNotEmpty(mchId)) {
            notNull = notNull.and(QZhiFuBaoMerchant.zhiFuBaoMerchant.mchId.like("%"+mchId+"%"));
        }
        Page<ZhiFuBaoMerchant> all = zhiFuBaoMerchantRepository.findAll(notNull,PageRequest.of(page - 1, limit));
        return MsgResponse.success(0, "", all.getTotalElements(), all.getContent());
    }

    @PostMapping(value = "/zhifubaoMerchant",name = "添加支付宝商户")
    @ResponseBody
    public MsgResponse AddMerchant(@RequestBody ZhiFuBaoMerchant merchant) {
        zhiFuBaoMerchantRepository.save(merchant);
        return MsgResponse.success(0, "操作成功");
    }

    @DeleteMapping(value = "/zhifubaoMerchant/{id}", name = "删除支付宝商户")
    @ResponseBody
    public MsgResponse DelMerchant(@PathVariable String id) {
        zhiFuBaoMerchantRepository.deleteById(id);
        return MsgResponse.success(0, "操作成功");
    }

    @PutMapping(value = "/zhifubaoMerchant",name = "修改支付宝商户")
    @ResponseBody
    public MsgResponse updateMerchant(@RequestBody ZhiFuBaoMerchant merchant) {
        Optional<ZhiFuBaoMerchant> byId = zhiFuBaoMerchantRepository.findById(merchant.getId());
        if (byId.isPresent()) {
            ZhiFuBaoMerchant save = byId.get();
            save.setAppName(merchant.getAppName());
            save.setProject(merchant.getProject());
            save.setAppId(merchant.getAppId());
            save.setMchId(merchant.getMchId());
            save.setAlipayPublicKey(merchant.getAlipayPublicKey());
            save.setAppPrivateKey(merchant.getAppPrivateKey());
            zhiFuBaoMerchantRepository.save(save);
            return MsgResponse.success(0, "操作成功");
        }
        return MsgResponse.fail(MsgCode.code_10001);
    }

    @GetMapping("/zhifubaoMerchant_info/{id}")
    public String editMerchantPage(@PathVariable String id, Model model) {
        Optional<ZhiFuBaoMerchant> one = zhiFuBaoMerchantRepository.findOne(QZhiFuBaoMerchant.zhiFuBaoMerchant.id.eq(id));
        model.addAttribute("zhifubaoMerchant", one.get());
        model.addAttribute("projects", Lists.newArrayList(projectRepository.findAll(QProject.project.code.asc()).iterator()));
        return "pay/zhifubaoMerchant_info";
    }

    @GetMapping("/zhifubaoMerchant_info")
    public String addMerchantPage(Model model) {
        model.addAttribute("zhifubaoMerchant", new ZhiFuBaoMerchant());
        model.addAttribute("projects", Lists.newArrayList(projectRepository.findAll(QProject.project.code.asc()).iterator()));
        return "pay/zhifubaoMerchant_info";
    }
}
