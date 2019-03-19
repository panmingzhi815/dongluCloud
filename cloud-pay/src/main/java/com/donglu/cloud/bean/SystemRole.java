package com.donglu.cloud.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class SystemRole extends BasicDomain {
    private String roleName;
    private String roleCode;
    @OneToMany(mappedBy = "systemRole")
    private List<SystemMenu> systemMenuList;
    @ManyToOne
    @JoinTable(name = "user_role")
    private SystemUser systemUser;
}
