package com.donglu.cloud.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "username")})
public class SystemUser extends BasicDomain {
    private String username;
    private String password;
    private String nickName;
    private String email;
    @OneToMany(mappedBy = "systemUser")
    private List<SystemRole> systemRoleList;
}