package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.domain.Permission;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IPermissionService;
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
@RequestMapping("permission")
public class PermissionController {
    @Autowired
    private IPermissionService permissionService;

    @RequestMapping("list")
    @RequiredPermission("权限列表")
    public String list(Model model, @ModelAttribute("qo") QueryObject qo)throws Exception{
        PageResult result = permissionService.query(qo);
        model.addAttribute("result",result);
        return "permission/list";
    }

    @RequestMapping("reload")
    @ResponseBody
    public Object reload()throws Exception{
        permissionService.reload();//加载权限
        return new JSONResult();
    }

    @RequiredPermission("权限删除")
    @RequestMapping("delete")
    @ResponseBody
    public Object delete(Long id)throws Exception{
        if (id != null) {
            permissionService.delete(id);
        }
        return new JSONResult();
    }
}
