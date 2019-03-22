package com.donglu.cloud.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "code")})
public class Merchant extends DateTimeDomain {

    //商户编号
    private String code;
    //商户名称
    private String name;
    //商户项目实际地址
    private String address;
    //商户重要事件联系电话
    private String tel;
    //商户重要事件邮件通知
    private String email;

}
