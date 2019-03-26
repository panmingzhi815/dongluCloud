package com.donglu.cloud.controller;

import com.donglu.cloud.bean.MsgCode;
import com.donglu.cloud.bean.MsgResponse;
import com.donglu.cloud.bean.QSystemDictionary;
import com.donglu.cloud.bean.SystemDictionary;
import com.donglu.cloud.repository.SystemDictionaryRepository;
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
public class SystemDictionaryController {

    @Autowired
    private SystemDictionaryRepository systemDictionaryRepository;


    @GetMapping("/dictionary")
    @ResponseBody
    public MsgResponse getList(@RequestParam(required = false, defaultValue = "1") Integer page, @RequestParam(required = false, defaultValue = "10") Integer limit, @RequestParam(required = false) String key) {
        BooleanExpression notNull = QSystemDictionary.systemDictionary.id.isNotNull();
        if (StringUtils.isNotBlank(key)) {
            notNull = notNull.and(QSystemDictionary.systemDictionary.key.like("%" + key + "%"));
        }
        Page<SystemDictionary> all = systemDictionaryRepository.findAll(notNull, PageRequest.of(page - 1, limit));
        return MsgResponse.success(0, "", all.getTotalElements(), all.getContent());
    }

    @PutMapping(value = "/dictionary",name = "修改配置")
    @ResponseBody
    public MsgResponse update(@RequestBody SystemDictionary dictionary) {
        Optional<SystemDictionary> byId = systemDictionaryRepository.findById(dictionary.getId());
        if (!byId.isPresent()) {
            return MsgResponse.fail(MsgCode.code_10001);
        }

        SystemDictionary user = byId.get();
        user.setKey(dictionary.getKey());
        user.setValue(dictionary.getValue());
        user.setDescribe(dictionary.getDescribe());
        systemDictionaryRepository.save(user);
        return MsgResponse.success(0, "操作成功");

    }

    @PostMapping(value = "/dictionary",name = "添加配置")
    @ResponseBody
    public MsgResponse save(@RequestBody SystemDictionary dictionary) {
        systemDictionaryRepository.save(dictionary);
        return MsgResponse.success(0, "操作成功");
    }

    @GetMapping("/dictionary_info/{id}")
    public String redirectEditPage(@PathVariable String id, Model model) {
        Optional<SystemDictionary> one = systemDictionaryRepository.findOne(QSystemDictionary.systemDictionary.id.eq(id));
        model.addAttribute("dictionary", one.get());
        return "system/dictionary_info";
    }

    @GetMapping("/dictionary_info")
    public String redirectAddPage(Model model) {
        model.addAttribute("dictionary", new SystemDictionary());
        return "system/dictionary_info";
    }
}
