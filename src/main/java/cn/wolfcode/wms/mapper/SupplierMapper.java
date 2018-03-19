package cn.wolfcode.wms.mapper;

import cn.wolfcode.wms.domain.Supplier;
import cn.wolfcode.wms.domain.Supplier;
import cn.wolfcode.wms.query.QueryObject;

import java.util.List;

public interface SupplierMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Supplier entity);

    Supplier selectByPrimaryKey(Long id);

    List<Supplier> selectAll();

    int updateByPrimaryKey(Supplier entity);
    List<Supplier> query4List(QueryObject qo);

    Integer query4Count(QueryObject qo);
}