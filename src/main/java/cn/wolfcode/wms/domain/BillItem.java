package cn.wolfcode.wms.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class BillItem extends BaseDomain{
    private BigDecimal number;

    private BigDecimal amount;

    private String remark;

    private Product product;

    private Long billId;
}
