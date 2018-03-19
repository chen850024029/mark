package cn.wolfcode.wms.service;

import cn.wolfcode.wms.domain.Brand;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.util.PageResult;

import java.util.List;
import java.util.Map;

public interface IChartService {

    List<Map<String,Object>> orderChart(QueryObject qo);
    List<Map<String,Object>> selectSaleChart(QueryObject qo);
}
