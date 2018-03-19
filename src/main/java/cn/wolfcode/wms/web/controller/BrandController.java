package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.domain.Brand;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IBrandService;
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
@RequestMapping("brand")
public class BrandController {
    @Autowired
    private IBrandService brandService;

    @RequestMapping("list")
    @RequiredPermission("部门列表")
    public String list(Model model, @ModelAttribute("qo") QueryObject qo)throws Exception{
        PageResult result = brandService.query(qo);
        model.addAttribute("result",result);
        return "brand/list";
    }

    @RequestMapping("input")
    @RequiredPermission("部门编辑")
    public String input(Long id,Model model)throws Exception{
        if (id != null) {
            Brand brand = brandService.get(id);
            model.addAttribute("entity",brand);
        }
        return "brand/input";
    }

    @RequiredPermission("保存部门/更新部门")
    @RequestMapping("saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(Brand dept)throws Exception{
        brandService.saveOrUpdate(dept);
        return new JSONResult();
    }

    @RequiredPermission("删除部门")
    @RequestMapping("delete")
    @ResponseBody
    public Object delete(Long id)throws Exception{
        if (id != null) {
            brandService.delete(id);
        }
        return new JSONResult();
    }
}
