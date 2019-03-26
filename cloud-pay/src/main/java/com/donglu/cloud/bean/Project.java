package com.donglu.cloud.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.chanjar.weixin.common.util.RandomUtils;

import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "code")})
public class Project extends DateTimeDomain {

    //项目编号
    private String code;
    //项目名称
    private String name;
    //项目项目实际地址
    private String address;
    //项目重要事件联系电话
    private String tel;
    //项目重要事件邮件通知
    private String email;
    //项目接口API密钥
    private String apiKey;

    @PrePersist
    public void prePersist() {
        setCreate(new Date());
        setUpdate(new Date());
        setApiKey(RandomUtils.getRandomStr());
    }
}
