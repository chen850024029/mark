package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.domain.Brand;
import cn.wolfcode.wms.query.OrderBillQueryObject;
import cn.wolfcode.wms.query.OrderChartQueryObject;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.query.SaleChartQueryObject;
import cn.wolfcode.wms.service.IBrandService;
import cn.wolfcode.wms.service.IChartService;
import cn.wolfcode.wms.service.IClientService;
import cn.wolfcode.wms.service.ISupplierService;
import cn.wolfcode.wms.util.JSONResult;
import cn.wolfcode.wms.util.PageResult;
import cn.wolfcode.wms.util.RequiredPermission;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("chart")
public class ChartController {
    @Autowired
    private IChartService chartService;
    @Autowired
    private IClientService clientService;
    @Autowired
    private IBrandService brandService;
    @Autowired
    private ISupplierService supplierService;

    @RequestMapping("order")
    @RequiredPermission("订单列表")
    public String orderChart(Model model, @ModelAttribute("qo") OrderChartQueryObject qo)throws Exception{
        List<Map<String, Object>> list = chartService.orderChart(qo);
        model.addAttribute("result",list);
        model.addAttribute("suppliers",supplierService.list());
        model.addAttribute("brands",brandService.list());
        model.addAttribute("groupByTypeMap",OrderChartQueryObject.groupByTypeMap);
        return "chart/order";
    }

    @RequestMapping("sale")
    @RequiredPermission("订单列表")
    public String saleChart(Model model, @ModelAttribute("qo") SaleChartQueryObject qo)throws Exception{
        List<Map<String, Object>> list = chartService.selectSaleChart(qo);
        model.addAttribute("result",list);
        model.addAttribute("clients",clientService.list());
        model.addAttribute("brands",brandService.list());
        model.addAttribute("groupByTypeMap",SaleChartQueryObject.groupByTypeMap);
        return "chart/sale";
    }

    @RequestMapping("saleByPie")
    public String saleChartByPie(Model model,SaleChartQueryObject qo){
        List<Map<String,Object>> charts = chartService.selectSaleChart(qo);
        //存放分组的类型
        List<String> groupByTypes = new ArrayList<>();
        //存放销售总额
        List<Map<String,Object>> datas= new ArrayList<>();
        //分组类型
        for(Map<String,Object> chart : charts){
            groupByTypes.add(chart.get("groupByType").toString());
            Map<String,Object> data = new HashMap<>();
            data.put("value",chart.get("totalAmount"));
            data.put("name",chart.get("groupByType"));
            datas.add(data);
        }
        //将分组类型转换成json格式的字符串,共享回去
        model.addAttribute("groupByTypes", JSON.toJSONString(groupByTypes));
        model.addAttribute("datas", JSON.toJSONString(datas));

        return "/chart/saleChartByPie";
    }


    @RequestMapping("saleByBar")
    public String saleChartByBar(Model model,SaleChartQueryObject qo){
        List<Map<String,Object>> charts = chartService.selectSaleChart(qo);
        //存放分组的类型
        List<String> groupByTypes = new ArrayList<>();
        //存放销售总额
        List<Object> totalAmounts = new ArrayList<>();
        //分组类型
        for(Map<String,Object> chart : charts){
            groupByTypes.add(chart.get("groupByType").toString());
            totalAmounts.add(chart.get("totalAmount").toString());
        }
        //将分组类型转换成json格式的字符串,共享回去
        model.addAttribute("groupByTypes", JSON.toJSONString(groupByTypes));
        model.addAttribute("totalAmounts", JSON.toJSONString(totalAmounts));
        model.addAttribute("groupByType",SaleChartQueryObject.groupByTypeMap.get(qo.getGroupByType()));

        return "chart/saleChartByBar";
    }
}
