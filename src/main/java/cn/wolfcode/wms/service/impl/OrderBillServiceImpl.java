package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.domain.OrderBill;
import cn.wolfcode.wms.domain.OrderBillItem;
import cn.wolfcode.wms.mapper.OrderBillItemMapper;
import cn.wolfcode.wms.mapper.OrderBillMapper;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IOrderBillService;
import cn.wolfcode.wms.util.PageResult;
import cn.wolfcode.wms.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class OrderBillServiceImpl implements IOrderBillService{

    @Autowired
    private OrderBillMapper orderBillMapper;
    @Autowired
    private OrderBillItemMapper orderBillItemMapper;


    @Override
    public void audit(Long id) {
        //查询出审核之前的单据
        OrderBill orderBill = orderBillMapper.selectByPrimaryKey(id);
        if (orderBill.getStatus()==OrderBill.STATUS_NOMAL){
            orderBill.setStatus(OrderBill.STATUS_AUDIT);
            orderBill.setAuditor(UserContext.getCurrentUser());
            orderBill.setAuditTime(new Date());
            orderBillMapper.audit(orderBill);
        }
    }

    public void delete(Long id) {
        orderBillMapper.deleteByPrimaryKey(id);
        //删除明细
        orderBillItemMapper.deleteByBillId(id);
    }

    public OrderBill get(Long id) {
        return orderBillMapper.selectByPrimaryKey(id);
    }

    public List<OrderBill> list() {
        return orderBillMapper.selectAll();
    }

    public PageResult query(QueryObject qo) {
        Integer rows = orderBillMapper.query4Count(qo);
        if (rows == 0){
            return PageResult.PAGE_RESULT;
        }

        List<?> data = orderBillMapper.query4List(qo);

        return new PageResult(data,rows,qo.getPageSize(),qo.getCurrentPage());
    }


    public void saveOrUpdate(OrderBill entity) {
        //封装订单的信息
        entity.setInputUser(UserContext.getCurrentUser());
        entity.setInputTime(new Date());
        //初始化
        BigDecimal totalNumber = BigDecimal.ZERO;
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<OrderBillItem> items = entity.getItems();
        for (OrderBillItem item : items){
            //总数量
            totalNumber = totalNumber.add(item.getNumber());
            //总金额
            totalAmount = totalAmount.add(item.getNumber().multiply(item.getCostPrice()));
        }
        entity.setTotalAmount(totalAmount);
        entity.setTotalNumber(totalNumber);

        if (entity.getId() == null) {
            //保存单据信息
            orderBillMapper.insert(entity);
            //保存明细
            for (OrderBillItem item :items){
                item.setAmount(item.getNumber().multiply(item.getCostPrice()));
                item.setBillId(entity.getId());
                orderBillItemMapper.insert(item);
            }
        }else {
            orderBillMapper.updateByPrimaryKey(entity);
            //删除之前的所有的明细
            orderBillItemMapper.deleteByBillId(entity.getId());
            //保存新明细
            for (OrderBillItem item : items){
                item.setAmount(item.getNumber().multiply(item.getCostPrice()));
                item.setBillId(entity.getId());
                orderBillItemMapper.insert(item);
            }
        }
    }


}
