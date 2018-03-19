package cn.wolfcode.wms.util;

import lombok.Getter;
import lombok.Setter;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
//@NoArgsConstructor
public class PageResult {

    public static final PageResult PAGE_RESULT =
                    new PageResult( Collections.EMPTY_LIST,0,1,1);

    private int currentPage;
    private int pageSize;

    private Integer rows;
    private List<?> data;

    private int endPage;
    private int prevPage;
    private int nextPage;

    public PageResult(List<?> data,Integer rows,int pageSize,int currentPage){
        this.data = data;
        this.rows = rows;
        this.pageSize = pageSize;
        this.currentPage = currentPage;

        if (rows <= pageSize){
            prevPage=1;
            nextPage=1;
            endPage =1;
            return;
        }

        endPage = rows % pageSize != 0 ? rows / pageSize + 1 :rows / pageSize;
        prevPage = currentPage > 1 ? currentPage - 1 : currentPage;
        nextPage = currentPage < endPage ? currentPage + 1 : endPage;
    }
}
