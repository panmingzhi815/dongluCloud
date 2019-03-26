package com.donglu.cloud.controller;

import com.donglu.cloud.bean.MsgResponse;
import com.donglu.cloud.bean.QSystemLog;
import com.donglu.cloud.bean.SystemLog;
import com.donglu.cloud.repository.SystemLogRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SystemLogController {

    @Autowired
    private SystemLogRepository systemLogRepository;


    @GetMapping("/log")
    @ResponseBody
    public MsgResponse getList(@RequestParam(required = false, defaultValue = "1") Integer page, @RequestParam(required = false, defaultValue = "10") Integer limit, @RequestParam(required = false) String startTime,@RequestParam(required = false) String endTime) {
        BooleanExpression notNull = QSystemLog.systemLog.id.isNotNull();
        if (StringUtils.isNotBlank(startTime)) {
            notNull = notNull.and(QSystemLog.systemLog.create.gt(DateTime.parse(startTime, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate()));
        }
        if (StringUtils.isNotBlank(endTime)) {
            notNull = notNull.and(QSystemLog.systemLog.create.lt(DateTime.parse(endTime,DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate()));
        }
        Page<SystemLog> all = systemLogRepository.findAll(notNull, PageRequest.of(page - 1, limit));
        return MsgResponse.success(0, "", all.getTotalElements(), all.getContent());
    }

    @DeleteMapping("/log")
    @ResponseBody
    @Transactional
    public MsgResponse delList(@RequestParam(value = "ids[]") String[] ids) {
        for (String id : ids) {
            systemLogRepository.deleteById(id);
        }
        return MsgResponse.success(0,"操作成功");
    }

}
