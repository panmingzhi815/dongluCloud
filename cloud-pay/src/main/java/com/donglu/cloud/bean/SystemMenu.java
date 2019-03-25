package com.donglu.cloud.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class SystemMenu extends BasicDomain {
    private String menuCode;
    private String menuName;
    private String menuIcon;
    private String menuUrl;
    private String menuType;
    private String menuOrder;
    @ManyToOne
    private SystemMenu parent;
    @OneToMany(mappedBy = "parent")
    private List<SystemMenu> children;
}
