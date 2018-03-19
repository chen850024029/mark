package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.domain.Client;
import cn.wolfcode.wms.mapper.ClientMapper;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IClientService;
import cn.wolfcode.wms.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements IClientService{

    @Autowired
    private ClientMapper clientMapper;

    @Override
    public void saveOrUpdate(Client entity) {
        if (entity.getId() == null) {
            clientMapper.insert(entity);
        }else {
            clientMapper.updateByPrimaryKey(entity);
        }
    }

    @Override
    public void delete(Long id) {
        if (id != null) {
            clientMapper.deleteByPrimaryKey(id);
        }
    }

    @Override
    public Client get(Long id) {
        return clientMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Client> list() {
        return clientMapper.selectAll();
    }

    public PageResult query(QueryObject qo) {
        Integer rows = clientMapper.query4Count(qo);
        if (rows == 0){
            return PageResult.PAGE_RESULT;
        }

        List<?> data = clientMapper.query4List(qo);

        return new PageResult(data,rows,qo.getPageSize(),qo.getCurrentPage());
    }

}
