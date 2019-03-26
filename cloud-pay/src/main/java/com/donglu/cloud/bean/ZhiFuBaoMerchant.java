package com.donglu.cloud.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class ZhiFuBaoMerchant extends DateTimeDomain {
    private String appName;
    private String appId;
    private String mchId;
    private String appPrivateKey;
    private String alipayPublicKey;
    @ManyToOne(optional = false)
    private Project project;
}
