package cn.wolfcode.wms.domain;

import com.alibaba.druid.filter.config.ConfigTools;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BaseDomain {
    protected Long id;

   /* public static  void main(String[] args) throws Exception {
        System.out.println(ConfigTools.encrypt("admin"));
    }*/
}
