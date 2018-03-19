package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.domain.Brand;
import cn.wolfcode.wms.mapper.BrandMapper;
import cn.wolfcode.wms.mapper.ChartMapper;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IBrandService;
import cn.wolfcode.wms.service.IChartService;
import cn.wolfcode.wms.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ChartServiceImpl implements IChartService {

    @Autowired
    private ChartMapper chartMapper;

    public List<Map<String, Object>> orderChart(QueryObject qo) {
        return chartMapper.orderChart(qo);
    }

    public List<Map<String, Object>> selectSaleChart(QueryObject qo) {
        return chartMapper.selectSaleChart(qo);
    }
}
