package cn.wolfcode.wms.query;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class ProductQueryObject extends QueryObject{
    private String keyword;
    private Long brandId=-1L;

}
