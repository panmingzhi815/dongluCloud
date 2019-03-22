package com.donglu.cloud.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class WeiXinMerchant extends DateTimeDomain {
    private String appId;
    private String mchId;
    private String appSecret;
    private String apiSecret;
    @ManyToOne
    private WeiXinMerchant parent;
    @OneToMany(mappedBy = "parent")
    private List<WeiXinMerchant> children;
}
