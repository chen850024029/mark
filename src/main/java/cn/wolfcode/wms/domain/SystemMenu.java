package cn.wolfcode.wms.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SystemMenu extends BaseDomain{

   private String name;
   private String url;
   private String sn;

   //多对一关系
   private SystemMenu parent;
}
