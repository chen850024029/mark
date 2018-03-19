package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.domain.ProductStock;
import cn.wolfcode.wms.domain.SaleAccount;
import cn.wolfcode.wms.domain.StockOutcomeBill;
import cn.wolfcode.wms.domain.StockOutcomeBillItem;
import cn.wolfcode.wms.exception.LogicException;
import cn.wolfcode.wms.mapper.ProductStockMapper;
import cn.wolfcode.wms.mapper.SaleAccountMapper;
import cn.wolfcode.wms.mapper.StockOutcomeBillItemMapper;
import cn.wolfcode.wms.mapper.StockOutcomeBillMapper;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IProductStockService;
import cn.wolfcode.wms.service.IStockOutcomeBillService;
import cn.wolfcode.wms.util.PageResult;
import cn.wolfcode.wms.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class StockOutcomeBillServiceImpl implements IStockOutcomeBillService{

    @Autowired
    private StockOutcomeBillMapper stockOutcomeBillMapper;
    @Autowired
    private StockOutcomeBillItemMapper stockOutcomeBillItemMapper;
    @Autowired
    private IProductStockService productStockService;
    @Autowired
    private SaleAccountMapper saleAccountMapper;


    @Override
    public void audit(Long id) {
        //查询出审核之前的单据
        StockOutcomeBill stockOutcomeBill = stockOutcomeBillMapper.selectByPrimaryKey(id);
        if (stockOutcomeBill.getStatus()==StockOutcomeBill.STATUS_NOMAL){
            stockOutcomeBill.setStatus(StockOutcomeBill.STATUS_AUDIT);
            stockOutcomeBill.setAuditor(UserContext.getCurrentUser());
            stockOutcomeBill.setAuditTime(new Date());
            stockOutcomeBillMapper.audit(stockOutcomeBill);
        }
        //修改库存
        productStockService.stockOutcomeBill(stockOutcomeBill);//业务执行在productStock实现类里
    }

    public void saveOrUpdate(StockOutcomeBill entity) {
        entity.setInputUser(UserContext.getCurrentUser());
        entity.setInputTime(new Date());
        //封装订单的信息
        entity.setInputUser(UserContext.getCurrentUser());
        entity.setInputTime(new Date());
        //初始化
        BigDecimal totalNumber = BigDecimal.ZERO;
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<StockOutcomeBillItem> items = entity.getItems();
        for (StockOutcomeBillItem item : items){
            //总数量
            totalNumber = totalNumber.add(item.getNumber());
            //总金额
            totalAmount = totalAmount.add(item.getNumber().multiply(item.getSalePrice()));
        }
        entity.setTotalAmount(totalAmount);
        entity.setTotalNumber(totalNumber);

        if (entity.getId() == null) {
            //保存单据信息
            stockOutcomeBillMapper.insert(entity);
            //保存明细
            for (StockOutcomeBillItem item :items){
                item.setAmount(item.getNumber().multiply(item.getSalePrice()));
                item.setBillId(entity.getId());
                stockOutcomeBillItemMapper.insert(item);
            }
        }else {
            stockOutcomeBillMapper.updateByPrimaryKey(entity);
            //删除之前的所有的明细
            stockOutcomeBillItemMapper.deleteByBillId(entity.getId());
            //保存新明细
            for (StockOutcomeBillItem item : items){
                item.setAmount(item.getNumber().multiply(item.getSalePrice()).setScale(2,BigDecimal.ROUND_HALF_UP));
                item.setBillId(entity.getId());
                stockOutcomeBillItemMapper.insert(item);
            }
        }
    }

    public void delete(Long id) {
        stockOutcomeBillMapper.deleteByPrimaryKey(id);
        //删除明细
        stockOutcomeBillItemMapper.deleteByBillId(id);
    }

    public StockOutcomeBill get(Long id) {
        return stockOutcomeBillMapper.selectByPrimaryKey(id);
    }

    public List<StockOutcomeBill> list() {
        return stockOutcomeBillMapper.selectAll();
    }


    public PageResult query(QueryObject qo) {
        Integer rows = stockOutcomeBillMapper.query4Count(qo);
        if (rows == 0){
            return PageResult.PAGE_RESULT;
        }

        List<?> data = stockOutcomeBillMapper.query4List(qo);

        return new PageResult(data,rows,qo.getPageSize(),qo.getCurrentPage());
    }

}
