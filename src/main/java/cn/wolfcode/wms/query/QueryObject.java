package cn.wolfcode.wms.query;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

@Setter
@Getter
//@NoArgsConstructor
public class QueryObject {
    private int currentPage=1;
    private int pageSize=5;

    public Integer getStart(){
        return (currentPage - 1)*pageSize;
    }

    protected String empty2Null(String s){

        return StringUtils.hasLength(s) ? s : null;
    }

}
