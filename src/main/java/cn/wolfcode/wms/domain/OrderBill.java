package cn.wolfcode.wms.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter@Setter
public class OrderBill extends Bill{

      //供应商:多对一关系
    private Supplier supplier;

    //单据明细:一对多关系
    private List<OrderBillItem> items = new ArrayList<>();
}
