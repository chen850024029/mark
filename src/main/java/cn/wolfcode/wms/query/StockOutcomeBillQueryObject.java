package cn.wolfcode.wms.query;

import cn.wolfcode.wms.util.DateUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
public class StockOutcomeBillQueryObject extends QueryObject{
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    private long depotId = -1;
    private long clientId = -1;
    private int status = -1;

    public Date getEndDate(){
        if (endDate==null){
            return null;
        }
        return endDate == null ? null : DateUtil.getEndDate(endDate);
    }
}
