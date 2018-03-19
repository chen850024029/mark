package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.domain.Department;
import cn.wolfcode.wms.domain.Employee;
import cn.wolfcode.wms.query.EmployeeQueryObject;
import cn.wolfcode.wms.service.IRoleService;
import cn.wolfcode.wms.util.JSONResult;
import cn.wolfcode.wms.util.PageResult;
import cn.wolfcode.wms.service.IDepartmentService;
import cn.wolfcode.wms.service.IEmployeeService;
import cn.wolfcode.wms.util.RequiredPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("employee")
public class EmployeeController {
    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private IDepartmentService departmentService;

    @Autowired
    private IRoleService roleService;

    @RequestMapping("list")
    @RequiredPermission("员工列表")
    public String list(Model model, @ModelAttribute("qo") EmployeeQueryObject qo)throws Exception{
        model.addAttribute("qo",qo);

        List<Department> departments = departmentService.list();
        model.addAttribute("depts",departments);

        PageResult result = employeeService.query(qo);
        model.addAttribute("result",result);
        return "employee/list";
    }

    @RequestMapping("input")
    @RequiredPermission("员工编辑")
    public String input(Long id,Model model)throws Exception{
        if (id != null) {
            Employee employee = employeeService.get(id);
            model.addAttribute("entity",employee);
        }

        List<Department> list = departmentService.list();
        model.addAttribute("depts",list);
        model.addAttribute("roles",roleService.list());
        return "employee/input";
    }

    @RequestMapping("saveOrUpdate")
    @RequiredPermission("员工保存/员工更新")
    @ResponseBody
    public Object saveOrUpdate(Employee dept,Long[] ids)throws Exception{
        employeeService.saveOrUpdate(dept,ids);
        return new JSONResult();
    }

    @RequestMapping("delete")
    @RequiredPermission("删除员工")
    @ResponseBody
    public Object delete(Long id)throws Exception{
        if(id != null){
            employeeService.delete(id);
        }
        return new JSONResult();
    }

    @RequestMapping("batchDelete")
    @RequiredPermission("批量删除员工")
    @ResponseBody
    public Object batchDelete(Long[] ids)throws Exception{
        if(ids != null){
            employeeService.batchDelete(ids);
        }
        return new JSONResult();
    }
}
