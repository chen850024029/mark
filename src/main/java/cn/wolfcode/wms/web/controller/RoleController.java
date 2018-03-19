package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.domain.Role;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IPermissionService;
import cn.wolfcode.wms.service.IRoleService;
import cn.wolfcode.wms.service.ISystemMenuService;
import cn.wolfcode.wms.util.JSONResult;
import cn.wolfcode.wms.util.PageResult;
import cn.wolfcode.wms.util.RequiredPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("role")
public class RoleController {
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IPermissionService permissionService;
    @Autowired
    private ISystemMenuService systemMenuService;

    @RequestMapping("list")
    @RequiredPermission("角色列表")
    public String list(Model model, @ModelAttribute("qo") QueryObject qo)throws Exception{
        PageResult result = roleService.query(qo);
        model.addAttribute("result",result);
        return "role/list";
    }

    @RequestMapping("input")
    @RequiredPermission("角色编辑")
    public String input(Long id,Model model)throws Exception{

        if (id != null) {
            Role role = roleService.get(id);
            model.addAttribute("entity",role);
        }
        model.addAttribute("permission",permissionService.list());
        model.addAttribute("menus",systemMenuService.getChildMenus());

        return "role/input";
    }

    @RequiredPermission("保存角色/更新角色")
    @RequestMapping("saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(Role dept,Long[] ids,Long[] menuIds)throws Exception{
        roleService.saveOrUpdate(dept,ids,menuIds);
        return new JSONResult();
    }

    @RequiredPermission("删除角色")
    @RequestMapping("delete")
    @ResponseBody
    public Object delete(Long id)throws Exception{
        if (id != null) {
            roleService.delete(id);
        }
        return new JSONResult();
    }
}
