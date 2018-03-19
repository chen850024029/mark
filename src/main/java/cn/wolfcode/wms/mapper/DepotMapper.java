package cn.wolfcode.wms.mapper;

import cn.wolfcode.wms.domain.Brand;
import cn.wolfcode.wms.domain.Depot;
import cn.wolfcode.wms.query.QueryObject;

import java.util.List;

public interface DepotMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Depot entity);

    Depot selectByPrimaryKey(Long id);

    List<Depot> selectAll();

    int updateByPrimaryKey(Depot entity);
    List<Depot> query4List(QueryObject qo);

    Integer query4Count(QueryObject qo);
}