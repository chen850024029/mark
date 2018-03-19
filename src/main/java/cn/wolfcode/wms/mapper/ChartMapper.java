package cn.wolfcode.wms.mapper;

import cn.wolfcode.wms.domain.Brand;
import cn.wolfcode.wms.query.QueryObject;

import java.util.List;
import java.util.Map;

public interface ChartMapper {
    /**
     * 查询订货报表
     * @param qo 高级查询的条件
     * @return 返回页面中需要的数据
     */
    List<Map<String,Object>> orderChart(QueryObject qo);

    /**
     * 生成销售报表
     * @param qo 高级查询的条件
     * @return 返回页面中需要的数据
     */
    List<Map<String,Object>> selectSaleChart(QueryObject qo);
}