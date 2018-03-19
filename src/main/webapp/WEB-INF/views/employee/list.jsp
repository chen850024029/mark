<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="/style/basic_layout.css" rel="stylesheet" type="text/css">
    <link href="/style/common_style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/js/jquery/jquery.js"></script>
    <script type="text/javascript" src="/js/plugins/artDialog/jquery.artDialog.js?skin=blue"></script>
<script type="text/javascript" src="/js/plugins/jQueryForm/jQueryForm.js"></script>
    <script type="text/javascript" src="/js/commonAll.js"></script>
    <title>WMS-账户管理</title>
    <style>
        .alt td{ background:black !important;}
    </style>
    <script type="text/javascript">
        $(function () {
           //给复选框绑定点击事件->全选/全不选
           $("#all").click(function () {
               $(".acb").prop("checked",this.checked);
           });
           //给批量删除的按钮绑定点击事件
            $(".btn_batchDelete").click(function () {
                var url = $(this).data("url");
               //先检查是否有数据选择
               if($(".acb:checked").size() > 0){
                   showDialog("傻逼,确定要批量删除吗",function () {
                       var ids=$.map($(".acb:checked"),function (ele) {
                           return $(ele).data("eid");
                       });
                       //发ajax请求执行批量操作
                       var sendData = {ids:ids};
                       $.post(url,sendData,function (data) {
                           if (data.success){
                               //提示操作成功
                               showDialog("删除成功",function () {
                                  //跳转到当前对象的list界面
                                  location.href="/employee/list.do";
                               });
                           }else {
                               //提示用户操作失败
                               showDialog("删除失败:"+data.msg);
                           }
                       });
                   },true);
               } else {
                   showDialog("傻逼,你能不能选一个再删除");
               }
            });
        });
    </script>


</head>
<body>
<form id="searchForm" action="/employee/list.do" method="post">
    <div id="container">
        <div class="ui_content">
            <div class="ui_text_indent">
                <div id="box_border">
                    <div id="box_top">搜索</div>
                    <div id="box_center">
                        姓名/邮箱
                        <input type="text" name="keyword" value="${qo.keyword}" class="ui_input_txt02"/>
                        所属部门

                        <select id="dept" class="ui_select01" name="deptId">
                            <option value="-1">全部部门</option>
                            <c:forEach var="obj" items="${depts}">
                                <option value="${obj.id}">${obj.name}</option>
                            </c:forEach>
                        </select>

                        <script type="text/javascript">
                            $.each($("#dept option"), function(index, item) {
                                if (item.value == ${qo.deptId}) {
                                    item.selected = true;
                                }
                            });
                        </script>

                    </div>

                    <div id="box_bottom">
                        <input type="button" value="查询" class="ui_input_btn01 btn_page" data-page="1"/>
                        <input type="button" value="新增" class="ui_input_btn01 btn_input" data-url="/employee/input.do"/>
                        <input type="button" value="批量删除" class="ui_input_btn01 btn_batchDelete" data-url="/employee/batchDelete.do"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="ui_content">
            <div class="ui_tb">
                <table class="table" cellspacing="0" cellpadding="0" width="100%" align="center" border="0">
                    <tr>
                        <th width="30"><input type="checkbox" id="all" /></th>
                        <th>编号</th>
                        <th>用户名</th>
                        <th>EMAIL</th>
                        <th>年龄</th>
                        <th>所属部门</th>
                        <th></th>
                    </tr>
                    <c:forEach var="entity" items="${result.data}" varStatus="num">
                        <tr>
                            <td><input type="checkbox" class="acb" data-eid="${entity.id}"/></td>
                            <td>${num.count}</td>
                            <td>${entity.name}</td>
                            <td>${entity.email}</td>
                            <td>${entity.age}</td>
                            <td>${entity.dept.name}</td>
                            <td>
                                <a href="/employee/input.do?id=${entity.id}">编辑</a>
                                <%--<a href="/employee/delete.do?id=${entity.id}">删除</a>--%>
                                <a href="javascript:" class="btn_delete"
                                   data-url="/employee/delete.do?id=${entity.id}"
                                   data-obj="employee"
                                >删除</a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
            <%@ include file="../common/commonPage.jsp"%>
        </div>
    </div>
</form>
</body>
</html>
