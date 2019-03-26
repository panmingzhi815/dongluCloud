package com.donglu.cloud.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class WeiXinMerchant extends DateTimeDomain {
    //商户名称
    private String appName;
    //微信分配的公众账号ID
    private String appId;
    //微信支付分配的商户号
    private String mchId;
    //微信支付sign签名加密密钥
    private String appKey;
    //服务商商户号
    private String serviceMchId;
    @ManyToOne(optional = false)
    private Project project;
}
