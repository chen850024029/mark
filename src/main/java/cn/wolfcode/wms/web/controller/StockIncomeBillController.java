package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.domain.StockIncomeBill;
import cn.wolfcode.wms.query.StockIncomeBillQueryObject;
import cn.wolfcode.wms.service.IDepotService;
import cn.wolfcode.wms.service.IStockIncomeBillService;
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
@RequestMapping("stockIncomeBill")
public class StockIncomeBillController {
    @Autowired
    private IStockIncomeBillService stockIncomeBillService;
    @Autowired
    private IDepotService depotService;


    @RequestMapping("list")
    @RequiredPermission("采购入库列表")
    public String list(Model model, @ModelAttribute("qo") StockIncomeBillQueryObject qo)throws Exception{
        PageResult result = stockIncomeBillService.query(qo);
        model.addAttribute("result",result);
        model.addAttribute("depots",depotService.list());
        return "stockIncomeBill/list";
    }

    @RequestMapping("input")
    @RequiredPermission("采购入库编辑")
    public String input(Long id,Model model)throws Exception{

        model.addAttribute("depots", depotService.list());
        if (id != null) {
            StockIncomeBill stockIncomeBill = stockIncomeBillService.get(id);
            model.addAttribute("entity",stockIncomeBill);
        }
        return "stockIncomeBill/input";
    }

    @RequiredPermission("保存采购入库/更新采购入库")
    @RequestMapping("saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(StockIncomeBill dept)throws Exception{
        stockIncomeBillService.saveOrUpdate(dept);
        return new JSONResult();
    }

    @RequiredPermission("删除采购入库")
    @RequestMapping("delete")
    @ResponseBody
    public Object delete(Long id)throws Exception{
        if (id != null) {
            stockIncomeBillService.delete(id);
        }
        return new JSONResult();
    }

    @RequiredPermission("审核采购入库")
    @RequestMapping("audit")
    @ResponseBody
    public Object audit(Long id)throws Exception{
        if (id != null) {
            stockIncomeBillService.audit(id);
        }
        return new JSONResult();
    }

    @RequestMapping("show")
    @RequiredPermission("采购入库编辑")
    public String show(Long id,Model model)throws Exception{
        if (id != null) {
            StockIncomeBill stockIncomeBill = stockIncomeBillService.get(id);
            model.addAttribute("entity",stockIncomeBill);
        }
        return "stockIncomeBill/show";
    }
}
