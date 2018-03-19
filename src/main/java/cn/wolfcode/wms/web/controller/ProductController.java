package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.domain.Product;
import cn.wolfcode.wms.query.ProductQueryObject;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IBrandService;
import cn.wolfcode.wms.service.IProductService;
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
@RequestMapping("product")
public class ProductController {
    @Autowired
    private IProductService productService;
    @Autowired
    private IBrandService brandService;
    @Autowired
    private ServletContext context;


    @RequestMapping("list")
    @RequiredPermission("品牌列表")
    public String list(Model model, @ModelAttribute("qo") ProductQueryObject qo)throws Exception{
        PageResult result = productService.query(qo);
        model.addAttribute("result",result);
        model.addAttribute("brands",brandService.list());
        return "product/list";
    }


    @RequestMapping("selectProductList")
    @RequiredPermission("品牌列表")
    public String selectProductList(Model model, @ModelAttribute("qo") ProductQueryObject qo)throws Exception{
        PageResult result = productService.query(qo);
        model.addAttribute("result",result);
        model.addAttribute("brands",brandService.list());
        return "product/selectProductList";
    }

    @RequestMapping("input")
    @RequiredPermission("品牌编辑")
    public String input(Long id,Model model)throws Exception{
        if (id != null) {
            Product product = productService.get(id);
            model.addAttribute("entity",product);
            model.addAttribute("brands",brandService.list());
        }
        return "product/input";
    }

    @RequiredPermission("保存品牌/更新品牌")
    @RequestMapping("saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(Product dept, MultipartFile pic)throws Exception{
        JSONResult result = new JSONResult();
        try {
            //删除商品图片:1.用户传递了图片2,当前商品之前是有图片的
            if(pic!=null&& !StringUtils.isEmpty(dept.getImagePath())){
                UploadUtil.deleteFile(context,dept.getImagePath());
            }
            //保存文件上传
            if (pic != null){
                String imagePath = UploadUtil.upload(pic, context.getRealPath("/upload"));
                dept.setImagePath(imagePath);
            }
            productService.saveOrUpdate(dept);
        }catch (Exception e){
            e.printStackTrace();
            result.mark("保存失败");
        }
        return result;
    }

    @RequiredPermission("删除品牌")
    @RequestMapping("delete")
    @ResponseBody
    public Object delete(Long id,String imagePath)throws Exception{
        if (id != null) {
            productService.delete(id);
            UploadUtil.deleteFile(context,imagePath);
        }
        return new JSONResult();
    }
}
