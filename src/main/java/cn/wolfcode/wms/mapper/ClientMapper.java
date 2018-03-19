package cn.wolfcode.wms.mapper;

import cn.wolfcode.wms.domain.Client;
import cn.wolfcode.wms.domain.Client;
import cn.wolfcode.wms.query.QueryObject;

import java.util.List;

public interface ClientMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Client entity);

    Client selectByPrimaryKey(Long id);

    List<Client> selectAll();

    int updateByPrimaryKey(Client entity);
    List<Client> query4List(QueryObject qo);

    Integer query4Count(QueryObject qo);
}