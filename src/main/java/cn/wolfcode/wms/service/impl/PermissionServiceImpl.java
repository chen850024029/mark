package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.domain.Permission;
import cn.wolfcode.wms.mapper.PermissionMapper;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IPermissionService;
import cn.wolfcode.wms.util.PageResult;
import cn.wolfcode.wms.util.PermissionUtil;
import cn.wolfcode.wms.util.RequiredPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

@Service
public class PermissionServiceImpl implements IPermissionService{

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private ApplicationContext ctx;

    public void reload() {
        //1:查询数据库中以后的权限表达式
        List<String> exps = permissionMapper.selectAllExpression();
        //2:从容器中获取出所有的Controller对象
        Collection<Object> ctrls = ctx.getBeansWithAnnotation(Controller.class).values();
        //3:迭代每一个Controller中的每一个方法
        for (Object ctrl : ctrls){
            Method[] ms = ctrl.getClass().getDeclaredMethods();
            for (Method m : ms){
                //4:判断当前被迭代的方法是否贴有权限注解
                RequiredPermission anno = m.getAnnotation(RequiredPermission.class);
                if (anno != null) {//创建当前方法对象的权限表达式
                    String exp = PermissionUtil.buildExpression(m);
                    //5:判断该权限是否已经存在数据中,不存在则新增权限
                    if (!exps.contains(exp)){
                        //新增权限
                        Permission p = new Permission();
                        p.setName(anno.value());
                        p.setExpression(exp);
                        permissionMapper.insert(p);
                    }
                }
            }
        }

    }

    public List<String> selectByEmployeeId(Long id) {
        return null;
    }


    public void saveOrUpdate(Permission entity) {
        if (entity.getId() == null) {
            permissionMapper.insert(entity);
        }else {
            permissionMapper.updateByPrimaryKey(entity);
        }
    }

    @Override
    public void delete(Long id) {
        if (id != null) {
            permissionMapper.deleteByPrimaryKey(id);
        }
    }


    @Override
    public List<Permission> list() {
        return permissionMapper.selectAll();
    }

    public PageResult query(QueryObject qo) {
        Integer rows = permissionMapper.query4Count(qo);
        if (rows == 0){
            return PageResult.PAGE_RESULT;
        }

        List<?> data = permissionMapper.query4List(qo);

        return new PageResult(data,rows,qo.getPageSize(),qo.getCurrentPage());
    }


}
