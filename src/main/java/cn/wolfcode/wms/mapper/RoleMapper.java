package cn.wolfcode.wms.mapper;

import cn.wolfcode.wms.domain.Department;
import cn.wolfcode.wms.domain.Role;
import cn.wolfcode.wms.query.QueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Role entity);

    Role selectByPrimaryKey(Long id);

    List<Role> selectAll();

    int updateByPrimaryKey(Role entity);

    List<Department> query4List(QueryObject qo);

    Integer query4Count(QueryObject qo);

    void insertRelation(@Param("roleId")Long roleId, @Param("permissionId")Long permissionId);

    void deleteRelation(Long id);

    void selectByEmployeeId();

    /**
     *  角色和菜单的关系维护
     * @param roleId
     * @param menuId
     */
    void insertMenuRelation(@Param("roleId") Long roleId, @Param("menuId") Long menuId);

    void deleteMenuRelation(Long id);
}