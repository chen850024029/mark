package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.domain.SystemMenu;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.query.SystemMenuQueryObject;
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

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("systemMenu")
public class SystemMenuController {
    @Autowired
    private ISystemMenuService systemMenuService;

    @RequestMapping("list")
    @RequiredPermission("菜单列表")
    public String list(Model model, @ModelAttribute("qo") SystemMenuQueryObject qo)throws Exception{
        if (qo.getParentId() != null) {//非根菜单,应该查询当前菜单的父菜单集合
            SystemMenu menu = systemMenuService.get(qo.getParentId());

            model.addAttribute("parents",systemMenuService.getParentMenus(menu));
        }
        List<SystemMenu> list = systemMenuService.list(qo);
        model.addAttribute("list",list);
        return "systemMenu/list";
    }

    @RequestMapping("input")
    @RequiredPermission("菜单编辑")
    public String input(Long id,Model model,Long parentId)throws Exception{
        if (parentId != null) {
            SystemMenu systemMenu = systemMenuService.get(parentId);
            model.addAttribute("parent",systemMenu);
        }
        if (id != null) {
            SystemMenu systemMenu = systemMenuService.get(id);
            model.addAttribute("entity",systemMenu);
        }
        return "systemMenu/input";
    }

    @RequiredPermission("保存菜单/更新菜单")
    @RequestMapping("saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(SystemMenu dept)throws Exception{
        systemMenuService.saveOrUpdate(dept);
        return new JSONResult();
    }

    @RequiredPermission("删除菜单")
    @RequestMapping("delete")
    @ResponseBody
    public Object delete(Long id)throws Exception{
        if (id != null) {
            systemMenuService.delete(id);
        }
        return new JSONResult();
    }

    @RequestMapping("loadMenu")
    @ResponseBody
    public List<Map<String,Object>> loadMenu(String parentSn)throws Exception{
        List<Map<String,Object>> menus = systemMenuService.selectByParentSn(parentSn);
        return menus;
    }

}
