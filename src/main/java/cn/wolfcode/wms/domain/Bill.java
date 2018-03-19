package cn.wolfcode.wms.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
@Setter
@Getter
public class Bill extends BaseDomain{
    //待审核
    public static final int STATUS_NOMAL=0;
    //以审核
    public static final int STATUS_AUDIT=1;
    //编码
    private String sn;
    //时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date vdate;
    //0:待审核---1:以审核
    private int status = STATUS_NOMAL;
    //总金额
    private BigDecimal totalAmount;
    //总数量
    private BigDecimal totalNumber;
    //审核时间
    private Date auditTime;
    //录入时间
    private Date inputTime;
    //录入人:多对一关系
    private Employee inputUser;
    //审核人:多对一关系
    private Employee auditor;

}
