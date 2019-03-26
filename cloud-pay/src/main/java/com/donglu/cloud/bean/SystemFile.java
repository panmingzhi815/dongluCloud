package com.donglu.cloud.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Lob;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class SystemFile extends DateTimeDomain {
    private String fileName;
    @Lob
    private byte[] fileData;
    private String fileDescribe;
}
