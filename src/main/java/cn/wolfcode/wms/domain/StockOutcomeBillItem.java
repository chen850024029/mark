package cn.wolfcode.wms.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class StockOutcomeBillItem extends BillItem{

    private BigDecimal salePrice;

}