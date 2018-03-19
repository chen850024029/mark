package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.domain.Department;
import cn.wolfcode.wms.util.JSONResult;
import cn.wolfcode.wms.util.PageResult;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IDepartmentService;
import cn.wolfcode.wms.util.RequiredPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("department")
public class DepartmentController {
    @Autowired
    private IDepartmentService departmentService;

    @RequestMapping("list")
    @RequiredPermission("部门列表")
    public String list(Model model, @ModelAttribute("qo") QueryObject qo)throws Exception{
        PageResult result = departmentService.query(qo);
        model.addAttribute("result",result);
        return "department/list";
    }

    @RequestMapping("input")
    @RequiredPermission("部门编辑")
    public String input(Long id,Model model)throws Exception{
        if (id != null) {
            Department department = departmentService.get(id);
            model.addAttribute("entity",department);
        }
        return "department/input";
    }

    @RequiredPermission("保存部门/更新部门")
    @RequestMapping("saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(Department dept)throws Exception{
        departmentService.saveOrUpdate(dept);
        return new JSONResult();
    }

    @RequiredPermission("删除部门")
    @RequestMapping("delete")
    @ResponseBody
    public Object delete(Long id)throws Exception{
        if (id != null) {
            departmentService.delete(id);
        }
        return new JSONResult();
    }
}
