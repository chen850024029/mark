package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.domain.Department;
import cn.wolfcode.wms.mapper.DepartmentMapper;
import cn.wolfcode.wms.util.PageResult;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DepartmentServiceImpl implements IDepartmentService{

    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public void saveOrUpdate(Department entity) {
        if (entity.getId() == null) {
            departmentMapper.insert(entity);
        }else {
            departmentMapper.updateByPrimaryKey(entity);
        }
    }

    @Override
    public void delete(Long id) {
        if (id != null) {
            departmentMapper.deleteByPrimaryKey(id);
        }
    }

    @Override
    public Department get(Long id) {
        return departmentMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Department> list() {
        return departmentMapper.selectAll();
    }

    public PageResult query(QueryObject qo) {
        Integer rows = departmentMapper.query4Count(qo);
        if (rows == 0){
            return PageResult.PAGE_RESULT;
        }

        List<?> data = departmentMapper.query4List(qo);

        return new PageResult(data,rows,qo.getPageSize(),qo.getCurrentPage());
    }

}
