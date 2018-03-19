package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.domain.Depot;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IDepotService;
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
@RequestMapping("depot")
public class DepotController {
    @Autowired
    private IDepotService depotService;

    @RequestMapping("list")
    @RequiredPermission("仓库列表")
    public String list(Model model, @ModelAttribute("qo") QueryObject qo)throws Exception{
        PageResult result = depotService.query(qo);
        model.addAttribute("result",result);
        return "depot/list";
    }

    @RequestMapping("input")
    @RequiredPermission("仓库编辑")
    public String input(Long id,Model model)throws Exception{
        if (id != null) {
            Depot depot = depotService.get(id);
            model.addAttribute("entity",depot);
        }
        return "depot/input";
    }

    @RequiredPermission("保存仓库/更新仓库")
    @RequestMapping("saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(Depot dept)throws Exception{
        depotService.saveOrUpdate(dept);
        return new JSONResult();
    }

    @RequiredPermission("删除仓库")
    @RequestMapping("delete")
    @ResponseBody
    public Object delete(Long id)throws Exception{
        if (id != null) {
            depotService.delete(id);
        }
        return new JSONResult();
    }
}
