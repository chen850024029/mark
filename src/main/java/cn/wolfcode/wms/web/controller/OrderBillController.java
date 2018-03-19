package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.domain.OrderBill;
import cn.wolfcode.wms.query.OrderBillQueryObject;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IOrderBillService;
import cn.wolfcode.wms.service.ISupplierService;
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

import java.util.function.Supplier;

@Controller
@RequestMapping("orderBill")
public class OrderBillController {
    @Autowired
    private IOrderBillService orderBillService;
    @Autowired
    private ISupplierService supplierService;


    @RequestMapping("list")
    @RequiredPermission("采购订单列表")
    public String list(Model model, @ModelAttribute("qo") OrderBillQueryObject qo)throws Exception{
        PageResult result = orderBillService.query(qo);
        model.addAttribute("result",result);
        model.addAttribute("suppliers",supplierService.list());
        return "orderBill/list";
    }

    @RequestMapping("input")
    @RequiredPermission("采购订单编辑")
    public String input(Long id,Model model)throws Exception{

        model.addAttribute("suppliers", supplierService.list());
        if (id != null) {
            OrderBill orderBill = orderBillService.get(id);
            model.addAttribute("entity",orderBill);
        }
        return "orderBill/input";
    }

    @RequiredPermission("保存采购订单/更新采购订单")
    @RequestMapping("saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(OrderBill dept)throws Exception{
        orderBillService.saveOrUpdate(dept);
        return new JSONResult();
    }

    @RequiredPermission("删除采购订单")
    @RequestMapping("delete")
    @ResponseBody
    public Object delete(Long id)throws Exception{
        if (id != null) {
            orderBillService.delete(id);
        }
        return new JSONResult();
    }

    @RequiredPermission("审核采购订单")
    @RequestMapping("audit")
    @ResponseBody
    public Object audit(Long id)throws Exception{
        if (id != null) {
            orderBillService.audit(id);
        }
        return new JSONResult();
    }

    @RequestMapping("show")
    @RequiredPermission("采购订单编辑")
    public String show(Long id,Model model)throws Exception{
        if (id != null) {
            OrderBill orderBill = orderBillService.get(id);
            model.addAttribute("entity",orderBill);
        }
        return "orderBill/show";
    }
}
