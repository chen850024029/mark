package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.domain.Supplier;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.ISupplierService;
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
@RequestMapping("supplier")
public class SupplierController {
    @Autowired
    private ISupplierService supplierService;

    @RequestMapping("list")
    @RequiredPermission("供应商列表")
    public String list(Model model, @ModelAttribute("qo") QueryObject qo)throws Exception{
        PageResult result = supplierService.query(qo);
        model.addAttribute("result",result);
        return "supplier/list";
    }

    @RequestMapping("input")
    @RequiredPermission("供应商编辑")
    public String input(Long id,Model model)throws Exception{
        if (id != null) {
            Supplier supplier = supplierService.get(id);
            model.addAttribute("entity",supplier);
        }
        return "supplier/input";
    }

    @RequiredPermission("保存供应商/更新供应商")
    @RequestMapping("saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(Supplier dept)throws Exception{
        supplierService.saveOrUpdate(dept);
        return new JSONResult();
    }

    @RequiredPermission("删除供应商")
    @RequestMapping("delete")
    @ResponseBody
    public Object delete(Long id)throws Exception{
        if (id != null) {
            supplierService.delete(id);
        }
        return new JSONResult();
    }
}
