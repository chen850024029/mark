package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.domain.ProductStock;
import cn.wolfcode.wms.query.ProductStockQueryObject;
import cn.wolfcode.wms.service.IBrandService;
import cn.wolfcode.wms.service.IDepotService;
import cn.wolfcode.wms.service.IProductStockService;
import cn.wolfcode.wms.util.JSONResult;
import cn.wolfcode.wms.util.PageResult;
import cn.wolfcode.wms.util.RequiredPermission;
import cn.wolfcode.wms.util.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;

@Controller
@RequestMapping("productStock")
public class ProductStockController {
    @Autowired
    private IProductStockService productStockService;
    @Autowired
    private IBrandService brandService;
    @Autowired
    private IDepotService depotService;


    @RequestMapping("list")
    @RequiredPermission("库存列表")
    public String list(Model model, @ModelAttribute("qo") ProductStockQueryObject qo)throws Exception{
        PageResult result = productStockService.query(qo);
        model.addAttribute("result",result);
        model.addAttribute("brands",brandService.list());
        model.addAttribute("depots",depotService.list());
        return "productStock/list";
    }


   
}
