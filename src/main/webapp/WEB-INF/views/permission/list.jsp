<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="/style/basic_layout.css" rel="stylesheet" type="text/css">
<link href="/style/common_style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/js/jquery/jquery.js"></script>
	<script type="text/javascript" src="/js/plugins/jQueryForm/jQueryForm.js"></script>
<script type="text/javascript" src="/js/plugins/artDialog/jquery.artDialog.js?skin=blue"></script>
<script type="text/javascript" src="/js/commonAll.js"></script>
<script>
    $(function () {
        $(".btn_reload").click(function () {
            var url = $(this).data("url");
            showDialog("加载权限需要耗费较长时间,确实要重新加载吗?",function () {
                $.get(url,function (data) {
                    if(data.success){
                        //跳转到当前对象的list界面
                        // location.href="/permission/reload.do";
                        //提示用户加载成功
                        showDialog("加载成功",function () {
                            location.reload();
                        });
                    }else{
                        //提示用户,加载失败
                        showDialog("加载失败:"+data.msg);
                    }
                },"json");
            });
        });
    });
</script>

	<title>WMS-权限管理</title>
<style>
	.alt td{ background:black !important;}
</style>
</head>
<body>
	<form id="searchForm" action="/permission/list.do" method="post">
		<div id="container">
			<div class="ui_content">
				<div class="ui_text_indent">
					<div id="box_border">
						<div id="box_top">搜索</div>

						<div id="box_bottom">
							<input type="button" value="加载权限" class="ui_input_btn01 btn_reload" data-url="/permission/reload.do"/>
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
							<th>权限名称</th>
							<th>权限表达式</th>
							<th></th>
						</tr>

						<c:forEach items="${result.data}" var="entity" varStatus="num">
							<tr>
								<td><input type="checkbox" class="acb" /></td>
								<td>${num.count}</td>
								<td>${entity.name}</td>
								<td>${entity.expression}</td>
								<td>
									<%--<a href="/permission/delete.do?id=${entity.id}">删除</a>--%>
									<a href="javascript:" class="btn_delete"
									   data-url="/permission/delete.do?id=${entity.id}"
									   data-obj="permission"
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
