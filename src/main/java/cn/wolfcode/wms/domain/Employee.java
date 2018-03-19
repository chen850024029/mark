package cn.wolfcode.wms.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class Employee extends BaseDomain{

    private String name;

    private String password;

    private String email;

    private Integer age;

    private boolean admin;
    //员工对部门的关系:多对一
    private Department dept;
    //员工对角色的关系:多对多关系
    private List<Role> roles = new ArrayList<>();

}