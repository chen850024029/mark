package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.domain.StockOutcomeBill;
import cn.wolfcode.wms.exception.LogicException;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.query.StockOutcomeBillQueryObject;
import cn.wolfcode.wms.service.IClientService;
import cn.wolfcode.wms.service.IDepotService;
import cn.wolfcode.wms.service.IStockOutcomeBillService;
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
@RequestMapping("stockOutcomeBill")
public class StockOutcomeBillController {
    @Autowired
    private IStockOutcomeBillService stockOutcomeBillService;
    @Autowired
    private IDepotService depotService;
    @Autowired
    private IClientService clientService;


    @RequestMapping("list")
    @RequiredPermission("销售出库列表")
    public String list(Model model, @ModelAttribute("qo") StockOutcomeBillQueryObject qo)throws Exception{
        PageResult result = stockOutcomeBillService.query(qo);
        model.addAttribute("result",result);
        model.addAttribute("depots",depotService.list());
        model.addAttribute("clients",clientService.list());
        return "stockOutcomeBill/list";
    }

    @RequestMapping("input")
    @RequiredPermission("销售出库编辑")
    public String input(Long id,Model model)throws Exception{

        model.addAttribute("depots", depotService.list());
        model.addAttribute("clients",clientService.list());
        if (id != null) {
            StockOutcomeBill stockOutcomeBill = stockOutcomeBillService.get(id);
            model.addAttribute("entity",stockOutcomeBill);
        }
        return "stockOutcomeBill/input";
    }

    @RequiredPermission("保存销售出库/更新销售出库")
    @RequestMapping("saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(StockOutcomeBill dept)throws Exception{
        stockOutcomeBillService.saveOrUpdate(dept);
        return new JSONResult();
    }

    @RequiredPermission("删除销售出库")
    @RequestMapping("delete")
    @ResponseBody
    public Object delete(Long id)throws Exception{
        if (id != null) {
            stockOutcomeBillService.delete(id);
        }
        return new JSONResult();
    }

    @RequiredPermission("审核销售出库")
    @RequestMapping("audit")
    @ResponseBody
    public Object audit(Long id)throws Exception{
        JSONResult result = new JSONResult();
        try {
           if (id != null) {
               stockOutcomeBillService.audit(id);
           }
       }catch (LogicException e){
           e.printStackTrace();
           result.mark(e.getMessage());
       }
       return result;
    }

    @RequestMapping("show")
    @RequiredPermission("销售出库编辑")
    public String show(Long id,Model model)throws Exception{
        if (id != null) {
            StockOutcomeBill stockOutcomeBill = stockOutcomeBillService.get(id);
            model.addAttribute("entity",stockOutcomeBill);
        }
        return "stockOutcomeBill/show";
    }
}
