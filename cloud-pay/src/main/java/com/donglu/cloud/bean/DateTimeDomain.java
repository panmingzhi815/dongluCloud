package com.donglu.cloud.bean;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@Data
public class DateTimeDomain extends BasicDomain {
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date create;
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date update;

    @PrePersist
    public void prePersist() {
        setCreate(new Date());
        setUpdate(new Date());
    }

    @PreUpdate
    public void preUpdate() {
        setUpdate(new Date());
    }
}
