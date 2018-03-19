package cn.wolfcode.wms.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class Role extends BaseDomain{

    private String name;

    private String sn;

    //多对多关系,角色维护权限
    List<Permission> permissions = new ArrayList<>();
    //多对多关系,角色维护权限
    List<SystemMenu> menus = new ArrayList<>();
}