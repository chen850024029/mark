package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.domain.Employee;
import cn.wolfcode.wms.mapper.EmployeeMapper;
import cn.wolfcode.wms.query.EmployeeQueryObject;
import cn.wolfcode.wms.service.IPermissionService;
import cn.wolfcode.wms.util.MD5;
import cn.wolfcode.wms.util.PageResult;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IEmployeeService;
import cn.wolfcode.wms.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class EmployeeServiceImpl implements IEmployeeService{

    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private IPermissionService permissionService;


    public void login(String username, String password) {
        String s = MD5.encoder(password);
        Employee emp = employeeMapper.selectEmployeeByInfo(username,s);//给密码解密
        if (emp == null) {
            //登录失败
            throw new RuntimeException("账号密码不匹配");
        }
        //当前登录成功的用户存入session中
//        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpSession session = attrs.getRequest().getSession();
//        session.setAttribute("EMPLOYEE_IN_SESSION",emp);


        //把获取session的方法抽取到工具类了,UserContext类
        UserContext.setCurrentUser(emp);

        //如果不是超级管理员,查询登录用户的权限表达式存入session
        List<String> exps = permissionService.selectByEmployeeId(emp.getId());
//       session.setAttribute("EXPS_IN_SESSION",exps);
        UserContext.setExpression(exps);
    }

    public void batchDelete(Long[] ids) {
        employeeMapper.batchDelete(ids);
    }

    //在新增员工时,保存密码要加密
    public void saveOrUpdate(Employee entity,Long[] ids) {
        if (entity.getId() == null) {
            //给密码加密
            String encoder = MD5.encoder(entity.getPassword());
            entity.setPassword(encoder);
            employeeMapper.insert(entity);
        }else {
            //打破关系
            employeeMapper.deleteRelation(entity.getId());
            employeeMapper.updateByPrimaryKey(entity);
        }
        //处理关联关系
        if (ids != null){
            for (Long roleId : ids){
                employeeMapper.insertRelation(entity.getId(),roleId);
            }
        }
    }


    public void delete(Long id) {
        employeeMapper.deleteRelation(id);
        employeeMapper.deleteByPrimaryKey(id);
    }

    public Employee get(Long id) {
        return employeeMapper.selectByPrimaryKey(id);
    }

    public List<Employee> list() {
        return employeeMapper.selectAll();
    }

    public PageResult query(EmployeeQueryObject qo) {
        Integer rows = employeeMapper.query4Count(qo);
        if (rows == 0){
            return PageResult.PAGE_RESULT;
        }

        List<?> data = employeeMapper.query4List(qo);

        return new PageResult(data,rows,qo.getPageSize(),qo.getCurrentPage());
    }

}
