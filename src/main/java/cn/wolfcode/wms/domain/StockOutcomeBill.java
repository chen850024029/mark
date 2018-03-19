package cn.wolfcode.wms.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter@Setter
public class StockOutcomeBill extends Bill{
    //仓库:多对一关系
    private Depot depot;
    //客户
    private Client client;
    //单据明细:一对多关系
    private List<StockOutcomeBillItem> items = new ArrayList<>();
}
