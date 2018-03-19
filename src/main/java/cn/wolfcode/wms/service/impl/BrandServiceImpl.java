package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.domain.Brand;
import cn.wolfcode.wms.mapper.BrandMapper;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IBrandService;
import cn.wolfcode.wms.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements IBrandService{

    @Autowired
    private BrandMapper brandMapper;

    @Override
    public void saveOrUpdate(Brand entity) {
        if (entity.getId() == null) {
            brandMapper.insert(entity);
        }else {
            brandMapper.updateByPrimaryKey(entity);
        }
    }

    @Override
    public void delete(Long id) {
        if (id != null) {
            brandMapper.deleteByPrimaryKey(id);
        }
    }

    @Override
    public Brand get(Long id) {
        return brandMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Brand> list() {
        return brandMapper.selectAll();
    }

    public PageResult query(QueryObject qo) {
        Integer rows = brandMapper.query4Count(qo);
        if (rows == 0){
            return PageResult.PAGE_RESULT;
        }

        List<?> data = brandMapper.query4List(qo);

        return new PageResult(data,rows,qo.getPageSize(),qo.getCurrentPage());
    }

}
