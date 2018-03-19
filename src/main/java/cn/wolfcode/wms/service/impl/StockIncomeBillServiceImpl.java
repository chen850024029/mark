package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.domain.ProductStock;
import cn.wolfcode.wms.domain.StockIncomeBill;
import cn.wolfcode.wms.domain.StockIncomeBillItem;
import cn.wolfcode.wms.mapper.ProductMapper;
import cn.wolfcode.wms.mapper.ProductStockMapper;
import cn.wolfcode.wms.mapper.StockIncomeBillItemMapper;
import cn.wolfcode.wms.mapper.StockIncomeBillMapper;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IProductStockService;
import cn.wolfcode.wms.service.IStockIncomeBillService;
import cn.wolfcode.wms.util.PageResult;
import cn.wolfcode.wms.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class StockIncomeBillServiceImpl implements IStockIncomeBillService{

    @Autowired
    private StockIncomeBillMapper stockIncomeBillMapper;
    @Autowired
    private StockIncomeBillItemMapper stockIncomeBillItemMapper;
    @Autowired
    private IProductStockService productStockService;



    @Override
    public void audit(Long id) {
        //查询出审核之前的单据
        StockIncomeBill stockIncomeBill = stockIncomeBillMapper.selectByPrimaryKey(id);
        if (stockIncomeBill.getStatus()==StockIncomeBill.STATUS_NOMAL){
            stockIncomeBill.setStatus(StockIncomeBill.STATUS_AUDIT);
            stockIncomeBill.setAuditor(UserContext.getCurrentUser());
            stockIncomeBill.setAuditTime(new Date());
            stockIncomeBillMapper.audit(stockIncomeBill);
        }
        //修改库存
        productStockService.stockIncomeBill(stockIncomeBill);//业务执行在productStock实现类里


    }

    public void saveOrUpdate(StockIncomeBill entity) {
        entity.setInputUser(UserContext.getCurrentUser());
        entity.setInputTime(new Date());
        //封装订单的信息
        entity.setInputUser(UserContext.getCurrentUser());
        entity.setInputTime(new Date());
        //初始化
        BigDecimal totalNumber = BigDecimal.ZERO;
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<StockIncomeBillItem> items = entity.getItems();
        for (StockIncomeBillItem item : items){
            //总数量
            totalNumber = totalNumber.add(item.getNumber());
            //总金额
            totalAmount = totalAmount.add(item.getNumber().multiply(item.getCostPrice()));
        }
        entity.setTotalAmount(totalAmount);
        entity.setTotalNumber(totalNumber);

        if (entity.getId() == null) {
            //保存单据信息
            stockIncomeBillMapper.insert(entity);
            //保存明细
            for (StockIncomeBillItem item :items){
                item.setAmount(item.getNumber().multiply(item.getCostPrice()));
                item.setBillId(entity.getId());
                stockIncomeBillItemMapper.insert(item);
            }
        }else {
            stockIncomeBillMapper.updateByPrimaryKey(entity);
            //删除之前的所有的明细
            stockIncomeBillItemMapper.deleteByBillId(entity.getId());
            //保存新明细
            for (StockIncomeBillItem item : items){
                item.setAmount(item.getNumber().multiply(item.getCostPrice()).setScale(2,BigDecimal.ROUND_HALF_UP));
                item.setBillId(entity.getId());
                stockIncomeBillItemMapper.insert(item);
            }
        }
    }

    public void delete(Long id) {
        stockIncomeBillMapper.deleteByPrimaryKey(id);
        //删除明细
        stockIncomeBillItemMapper.deleteByBillId(id);
    }

    public StockIncomeBill get(Long id) {
        return stockIncomeBillMapper.selectByPrimaryKey(id);
    }

    public List<StockIncomeBill> list() {
        return stockIncomeBillMapper.selectAll();
    }


    public PageResult query(QueryObject qo) {
        Integer rows = stockIncomeBillMapper.query4Count(qo);
        if (rows == 0){
            return PageResult.PAGE_RESULT;
        }

        List<?> data = stockIncomeBillMapper.query4List(qo);

        return new PageResult(data,rows,qo.getPageSize(),qo.getCurrentPage());
    }

}
