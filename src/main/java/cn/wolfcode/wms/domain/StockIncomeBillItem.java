package cn.wolfcode.wms.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Setter
@Getter
public class StockIncomeBillItem extends BillItem{

    private BigDecimal costPrice;

}