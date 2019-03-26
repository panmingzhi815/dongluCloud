package com.donglu.cloud.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class SystemLog extends DateTimeDomain {
    //标题
    private String title;
    //功能
    private String methodType;
    //用户
    private String userName;
    //ip地址
    private String ip;
    //请求url
    private String url;
    //状态
    private Integer status;

}
