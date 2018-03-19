package cn.wolfcode.wms.mapper;

import cn.wolfcode.wms.domain.StockIncomeBill;
import cn.wolfcode.wms.query.QueryObject;

import java.util.List;

public interface StockIncomeBillMapper {
    int deleteByPrimaryKey(Long id);

    int insert(StockIncomeBill entity);

    StockIncomeBill selectByPrimaryKey(Long id);

    List<StockIncomeBill> selectAll();

    int updateByPrimaryKey(StockIncomeBill entity);

    List<StockIncomeBill> query4List(QueryObject qo);

    Integer query4Count(QueryObject qo);

    void updateByPrimaryKey(Long id);

    void audit(StockIncomeBill stockIncomeBill);

}