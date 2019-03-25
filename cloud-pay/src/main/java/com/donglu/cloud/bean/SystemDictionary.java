package com.donglu.cloud.bean;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * 系统字典
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "key")})
public class SystemDictionary extends DateTimeDomain {
    private String key;
    private String value;
    private String describe;
}
