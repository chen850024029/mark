package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.domain.Role;
import cn.wolfcode.wms.mapper.RoleMapper;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IRoleService;
import cn.wolfcode.wms.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements IRoleService{

    @Autowired
    private RoleMapper roleMapper;


    @Override
    public void saveOrUpdate(Role entity, Long[] ids,Long[] menuIds) {
        if (entity.getId() == null) {
            roleMapper.insert(entity);
        }else {
            //打破关系,删除原来的数据
            roleMapper.deleteRelation(entity.getId());
            //先删除角色和菜单关系
            roleMapper.deleteMenuRelation(entity.getId());
            //保存新的数据
            roleMapper.updateByPrimaryKey(entity);
        }
        if (ids != null){
            for (Long id : ids){
                //保存角色和权限的关系数据
                roleMapper.insertRelation(entity.getId(),id);
            }
        }
        if (menuIds != null) {
            for (Long menuId : menuIds) {
                //保存角色和菜单的关系数据
                roleMapper.insertMenuRelation(entity.getId(),menuId);
            }
        }
    }

    public void delete(Long id) {
        //先删除角色和权限关系
        roleMapper.deleteRelation(id);
        //先删除角色和菜单关系
        roleMapper.deleteMenuRelation(id);
        //在删除角色
        roleMapper.deleteByPrimaryKey(id);
    }

    public Role get(Long id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    public List<Role> list() {
        return roleMapper.selectAll();
    }

    public PageResult query(QueryObject qo) {
        Integer rows = roleMapper.query4Count(qo);
        if (rows == 0){
            return PageResult.PAGE_RESULT;
        }

        List<?> data = roleMapper.query4List(qo);

        return new PageResult(data,rows,qo.getPageSize(),qo.getCurrentPage());
    }

}
