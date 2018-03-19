package cn.wolfcode.wms.mapper;

import cn.wolfcode.wms.domain.Brand;
import cn.wolfcode.wms.domain.OrderBill;
import cn.wolfcode.wms.query.QueryObject;

import java.util.List;

public interface OrderBillMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderBill entity);

    OrderBill selectByPrimaryKey(Long id);

    List<OrderBill> selectAll();

    int updateByPrimaryKey(OrderBill entity);

    List<OrderBill> query4List(QueryObject qo);

    Integer query4Count(QueryObject qo);

    void updateByPrimaryKey(Long id);

    void audit(OrderBill orderBill);

}