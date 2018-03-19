package cn.wolfcode.wms.query;

import cn.wolfcode.wms.util.DateUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
public class ProductStockQueryObject extends QueryObject{

    private String keyword;
    private Long depotId = -1L;
    private Long brandId = -1L;
    private Integer warnNum;

    public String getKeyword(){
        return empty2Null(keyword);
    }
}
