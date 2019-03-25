package com.donglu.cloud.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class SystemRole extends DateTimeDomain {
    private String roleName;
    private String roleDescribe;
    @OneToMany
    private List<SystemMenu> systemMenuList = new ArrayList<>();
    @ManyToMany
    private List<SystemUser> systemUserList = new ArrayList<>();
}
