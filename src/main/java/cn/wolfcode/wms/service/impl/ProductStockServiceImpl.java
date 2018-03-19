package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.domain.*;
import cn.wolfcode.wms.exception.LogicException;
import cn.wolfcode.wms.mapper.BrandMapper;
import cn.wolfcode.wms.mapper.ProductMapper;
import cn.wolfcode.wms.mapper.ProductStockMapper;
import cn.wolfcode.wms.mapper.SaleAccountMapper;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IProductService;
import cn.wolfcode.wms.service.IProductStockService;
import cn.wolfcode.wms.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class ProductStockServiceImpl implements IProductStockService{

    @Autowired
    private ProductStockMapper productStockMapper;
    @Autowired
    private SaleAccountMapper saleAccountMapper;

    public PageResult query(QueryObject qo) {
        //查询总记录数
        Integer rows = productStockMapper.query4Count(qo);
        if (rows == 0){
            return PageResult.PAGE_RESULT;
        }

        List<?> data = productStockMapper.query4List(qo);

        return new PageResult(data,rows,qo.getPageSize(),qo.getCurrentPage());
    }

    public void stockIncomeBill(StockIncomeBill oldBill) {
        //判断当前商品是否存在:商品的编号和仓库的编号
        List<StockIncomeBillItem> items = oldBill.getItems();
        for(StockIncomeBillItem item : items ){
            Long proId = item.getProduct().getId();
            Long depotId = oldBill.getDepot().getId();
            ProductStock ps = productStockMapper.selectByProductIdAndDepotId(proId, depotId);
            if(ps == null){
                //ps为null是,即不存在:就直接插入一条新的数据
                ps = new ProductStock();
                ps.setPrice(item.getCostPrice());
                ps.setStoreNumber(item.getNumber());
                ps.setAmount(item.getAmount());
                ps.setDepot(oldBill.getDepot());
                ps.setProduct(item.getProduct());
                //保存
                productStockMapper.insert(ps);
            }else {
                //存在:修改库存量:数据/价格/总额
                ps.setStoreNumber(ps.getStoreNumber().add(item.getNumber()));
                ps.setPrice(item.getAmount().add(ps.getAmount()).divide(ps.getStoreNumber(),2, BigDecimal.ROUND_HALF_UP));
                ps.setAmount(ps.getStoreNumber().multiply(ps.getPrice()).setScale(2,BigDecimal.ROUND_HALF_UP));
                productStockMapper.updateByPrimaryKey(ps);
            }
        }
    }

    public void stockOutcomeBill(StockOutcomeBill stockOutcomeBill) {
        //判断当前商品是否存在:商品的编号和仓库的编号
        List<StockOutcomeBillItem> items = stockOutcomeBill.getItems();
        for(StockOutcomeBillItem item : items ){
            ProductStock ps = productStockMapper.selectByProductIdAndDepotId(item.getProduct().getId(), stockOutcomeBill.getDepot().getId());
            //商品不存在
            if(ps == null){
                throw new LogicException("商品["+item.getProduct().getName()+"]在仓库["+stockOutcomeBill.getDepot().getName()+"]中不存在");
            }

            //BigDecimal的大小比较,使用compareTo方法
            //a.compareTo(b)
            //>0  a > b
            //=0  a = b
            //<0  a < b

            if(item.getNumber().compareTo(ps.getStoreNumber()) > 0){
                throw new LogicException("商品["+item.getProduct().getName()+"]在仓库["+stockOutcomeBill.getDepot().getName()+
                        "]中的数量["+ ps.getStoreNumber() + "]不足[" + item.getNumber() + "]");
            }

            //数量足够
            ps.setStoreNumber(ps.getStoreNumber().subtract(item.getNumber()));
            ps.setAmount(ps.getStoreNumber().multiply(ps.getPrice()));
            productStockMapper.updateByPrimaryKey(ps);

            //生成销售账单
            SaleAccount saleAccount = new SaleAccount();
            saleAccount.setClient(stockOutcomeBill.getClient());
            saleAccount.setCostPrice(ps.getPrice());
            //总数量
            saleAccount.setNumber(item.getNumber());
            saleAccount.setCostAmount(saleAccount.getCostPrice().multiply(saleAccount.getNumber()).setScale(2,BigDecimal.ROUND_HALF_UP));
            //总金额
            saleAccount.setSalePrice(item.getSalePrice());
            saleAccount.setSaleAmount(saleAccount.getSalePrice().multiply(saleAccount.getNumber()).setScale(2,BigDecimal.ROUND_HALF_UP));
            saleAccount.setProduct(item.getProduct());
            saleAccount.setSaleMan(stockOutcomeBill.getInputUser());
            saleAccount.setVdate(new Date());
            //保存到数据库
            saleAccountMapper.insert(saleAccount);
        }
    }

}
