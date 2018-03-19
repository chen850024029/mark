package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.domain.Client;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IClientService;
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
@RequestMapping("client")
public class ClientController {
    @Autowired
    private IClientService clientService;

    @RequestMapping("list")
    @RequiredPermission("客户列表")
    public String list(Model model, @ModelAttribute("qo") QueryObject qo)throws Exception{
        PageResult result = clientService.query(qo);
        model.addAttribute("result",result);
        return "client/list";
    }

    @RequestMapping("input")
    @RequiredPermission("客户编辑")
    public String input(Long id,Model model)throws Exception{
        if (id != null) {
            Client client = clientService.get(id);
            model.addAttribute("entity",client);
        }
        return "client/input";
    }

    @RequiredPermission("保存客户/更新客户")
    @RequestMapping("saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(Client dept)throws Exception{
        clientService.saveOrUpdate(dept);
        return new JSONResult();
    }

    @RequiredPermission("删除客户")
    @RequestMapping("delete")
    @ResponseBody
    public Object delete(Long id)throws Exception{
        if (id != null) {
            clientService.delete(id);
        }
        return new JSONResult();
    }
}
