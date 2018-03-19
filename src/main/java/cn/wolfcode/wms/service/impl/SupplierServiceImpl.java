package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.domain.Supplier;
import cn.wolfcode.wms.mapper.SupplierMapper;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.ISupplierService;
import cn.wolfcode.wms.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierServiceImpl implements ISupplierService{

    @Autowired
    private SupplierMapper supplierMapper;

    @Override
    public void saveOrUpdate(Supplier entity) {
        if (entity.getId() == null) {
            supplierMapper.insert(entity);
        }else {
            supplierMapper.updateByPrimaryKey(entity);
        }
    }

    @Override
    public void delete(Long id) {
        if (id != null) {
            supplierMapper.deleteByPrimaryKey(id);
        }
    }

    @Override
    public Supplier get(Long id) {
        return supplierMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Supplier> list() {
        return supplierMapper.selectAll();
    }

    public PageResult query(QueryObject qo) {
        Integer rows = supplierMapper.query4Count(qo);
        if (rows == 0){
            return PageResult.PAGE_RESULT;
        }

        System.err.println(supplierMapper.selectAll());
        List<?> data = supplierMapper.query4List(qo);

        return new PageResult(data,rows,qo.getPageSize(),qo.getCurrentPage());
    }

}
