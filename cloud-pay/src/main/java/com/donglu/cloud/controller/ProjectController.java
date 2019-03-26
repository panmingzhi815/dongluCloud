package com.donglu.cloud.controller;

import com.donglu.cloud.bean.MsgCode;
import com.donglu.cloud.bean.MsgResponse;
import com.donglu.cloud.bean.Project;
import com.donglu.cloud.bean.QProject;
import com.donglu.cloud.repository.ProjectRepository;
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
public class ProjectController {

    @Autowired
    private ProjectRepository repository;

    @GetMapping("/project")
    @ResponseBody
    public MsgResponse getList(@RequestParam(required = false, defaultValue = "1") Integer page, @RequestParam(required = false, defaultValue = "10") Integer limit, @RequestParam(required = false) String name) {
        BooleanExpression notNull = QProject.project.id.isNotNull();
        if (StringUtils.isNotBlank(name)) {
            notNull = notNull.and(QProject.project.name.like("%" + name + "%"));
        }
        Page<Project> all = repository.findAll(notNull, PageRequest.of(page - 1, limit));
        return MsgResponse.success(0, "", all.getTotalElements(), all.getContent());
    }

    @PostMapping(value = "/project",name = "添加项目")
    @ResponseBody
    public MsgResponse AddMerchant(@RequestBody Project project) {
        repository.save(project);
        return MsgResponse.success(0, "操作成功");
    }

    @PutMapping(value = "/project",name = "修改项目")
    @ResponseBody
    public MsgResponse updateMerchant(@RequestBody Project project) {
        Optional<Project> byId = repository.findById(project.getId());
        if (byId.isPresent()) {
            Project save = byId.get();
            save.setCode(project.getCode());
            save.setName(project.getName());
            save.setAddress(project.getAddress());
            save.setEmail(project.getEmail());
            save.setTel(project.getTel());
            repository.save(save);
            return MsgResponse.success(0, "操作成功");
        }
        return MsgResponse.fail(MsgCode.code_10001);
    }

    @GetMapping("/project_info/{id}")
    public String editMerchantPage(@PathVariable String id, Model model) {
        Optional<Project> one = repository.findOne(QProject.project.id.eq(id));
        model.addAttribute("project", one.get());
        return "pay/project_info";
    }

    @GetMapping("/project_info")
    public String addMerchantPage(Model model) {
        model.addAttribute("project", new Project());
        return "pay/project_info";
    }
}
