package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.domain.Brand;
import cn.wolfcode.wms.domain.Product;
import cn.wolfcode.wms.mapper.BrandMapper;
import cn.wolfcode.wms.mapper.ProductMapper;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IProductService;
import cn.wolfcode.wms.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements IProductService{

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private BrandMapper brandMapper;

    @Override
    public void saveOrUpdate(Product entity) {
        if (entity.getId() == null) {
            productMapper.insert(entity);
        }else {
            Brand brand = brandMapper.selectByPrimaryKey(entity.getBrand_id());
            entity.setBrandName(brand.getName());
            productMapper.updateByPrimaryKey(entity);
        }
    }

    @Override
    public void delete(Long id) {
        if (id != null) {
            productMapper.deleteByPrimaryKey(id);
        }
    }

    @Override
    public Product get(Long id) {
        return productMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Product> list() {
        return productMapper.selectAll();
    }

    public PageResult query(QueryObject qo) {
        Integer rows = productMapper.query4Count(qo);
        if (rows == 0){
            return PageResult.PAGE_RESULT;
        }

        List<?> data = productMapper.query4List(qo);

        return new PageResult(data,rows,qo.getPageSize(),qo.getCurrentPage());
    }

}
