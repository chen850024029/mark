package cn.wolfcode.wms.mapper;

import cn.wolfcode.wms.domain.OrderBillItem;
import cn.wolfcode.wms.domain.StockIncomeBillItem;
import java.util.List;

public interface StockIncomeBillItemMapper {
   /* int deleteByPrimaryKey(Long id);

    int insert(StockIncomeBillItem entity);

    StockIncomeBillItem selectByPrimaryKey(Long id);

    List<StockIncomeBillItem> selectAll();

    int updateByPrimaryKey(StockIncomeBillItem entity);*/
   int deleteByPrimaryKey(Long id);

    int insert(StockIncomeBillItem record);

    void deleteByBillId(Long id);

}