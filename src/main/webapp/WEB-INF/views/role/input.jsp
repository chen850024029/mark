<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>信息管理系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="/style/basic_layout.css" rel="stylesheet" type="text/css">
<link href="/style/common_style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/js/jquery/jquery.js"></script>
	<script type="text/javascript" src="/js/plugins/artDialog/jquery.artDialog.js?skin=blue"></script>
<script type="text/javascript" src="/js/plugins/jQueryForm/jQueryForm.js"></script>
<script type="text/javascript" src="/js/commonAll.js"></script>
	<script type="text/javascript">
		$(function () {
			//给左移-右移按钮绑定事件
			$("#selectAll").click(function () {
				$(".all_permissions option").appendTo(".selected_permission");
            });
            $("#select").click(function () {
                $(".all_permissions option:selected").appendTo(".selected_permission");
            });

            $("#deselectAll").click(function () {
                $(".selected_permission option").appendTo(".all_permissions");
            });
            $("#deselect").click(function () {
                $(".selected_permission option:selected").appendTo(".all_permissions");
            });


            //给左移右移按钮绑定事件
			/*
			* 这个是菜单列表移动
			* */
			$("#selectMenuAll").click(function () {
				$(".all_menus option").appendTo(".selected_menus");
            });
			$("#selectMenu").click(function () {
				$(".all_menus option:selected").appendTo(".selected_menus")
            });
            $("#deselectMenuAll").click(function () {
                $(".selected_menus option").appendTo(".all_menus");
            });
            $("#deselectMenu").click(function () {
                $(".selected_menus option:selected").appendTo(".all_menus");
            });


			//去除列表重复
            var pIds = $.map($(".selected_menus option"),function (ele) {
                return ele.value;
            });
            $.each($(".all_menus option"),function (index,val) {
                // inArray:获取到指定元素在数组中的索引
                if ($.inArray(val.value,pIds) != -1){//当前有重复
                    $(val).remove();
                }
            });

            //页面加载完成之后,将已经分配的权限从左边删除
			/*var ids = [];
			$.each($(".selected_permission option"),function (index,option) {
				ids[index] = $(option).val();
            });
			//循环遍历左边的所有选项,比较他们的id是否相等
			$.each($(".all_permissions option"),function (index,option) {
				//inArray:获取到指定元素在数组中的索引
				if ($.inArray($(option).val(),ids) > -1){
				    $(option).remove();
				}
            });*/
            // $("#editForm").submit(function () {
				// $.each($(".selected_permission option"),function (index,option) {
				// 	$(option).prop("selected",true);
            //     });
            // });

			var pIds = $.map($(".selected_permission option"),function (ele) {
				return ele.value;
            });
            $.each($(".all_permissions option"),function (index,val) {
               // inArray:获取到指定元素在数组中的索引
                if ($.inArray(val.value,pIds) != -1){//当前有重复
                    $(val).remove();
                }
            });


            //给提交按钮绑定点击事件
            $(".btn_submit").click(function () {
				//选中右边所有的option,在提交表单
                $(".selected_permission option").prop("selected",true);
				$("#editForm").submit();
            });
        });
	</script>


</head>
<body>
<form id="editForm" action="/role/saveOrUpdate.do" method="post"data-obj="role" >
	<input type="hidden" name="id" value="${entity.id}">
	<div id="container">
		<div id="nav_links">
			<span style="color: #1A5CC6;">角色编辑</span>
			<div id="page_close">
				<a>
					<img src="/images/common/page_close.png" width="20" height="20" style="vertical-align: text-top;"/>
				</a>
			</div>
		</div>
		<div class="ui_content">
			<table cellspacing="0" cellpadding="0" width="100%" align="left" border="0">
				<tr>
					<td class="ui_text_rt" width="140">角色名称</td>
					<td class="ui_text_lt">
						<input name="name" class="ui_input_txt02" value="${entity.name}"/>
					</td>
				</tr>
				<tr>
					<td class="ui_text_rt" width="140">角色编号</td>
					<td class="ui_text_lt">
						<input name="sn" class="ui_input_txt02" value="${entity.sn}"/>
					</td>
				</tr>
				<tr>
					<td class="ui_text_rt" width="140">角色</td>
					<td class="ui_text_lt">
						<table>
							<tr>
								<td>
									<%--系统中所有的权限--%>
									<select multiple="true" class="ui_multiselect01 all_permissions">
										<c:forEach items="${permission}" var="obj">
											<option value="${obj.id}">${obj.name}</option>
										</c:forEach>
									</select>
								</td>
								<td align="center">
									<input type="button" id="select" value="-->" class="left2right"/><br/>
									<input type="button" id="selectAll" value="==>" class="left2right"/><br/>
									<input type="button" id="deselect" value="<--" class="left2right"/><br/>
									<input type="button" id="deselectAll" value="<==" class="left2right"/>
								</td>
								<td>
									<select multiple="true" class="ui_multiselect01 selected_permission" name="ids"><%--这个是表单提交,要name--%>
										<c:forEach items="${entity.permissions}" var="obj"><%--这是维护关系的对象,从role传入来的--%>
											<option value="${obj.id}">${obj.name}</option>
										</c:forEach>
									</select>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<%--===================================================================================--%>
				<tr>
					<td class="ui_text_rt" width="140">菜单</td>
					<td class="ui_text_lt">
						<table>
							<tr>
								<td>
									<%--系统中所有的菜单--%>
									<select multiple="true" class="ui_multiselect01 all_menus">
										<c:forEach items="${menus}" var="obj">
											<option value="${obj.id}">${obj.name}</option>
										</c:forEach>
									</select>
								</td>
								<td align="center">
									<input type="button" id="selectMenu" value="-->" class="left2right"/><br/>
									<input type="button" id="selectMenuAll" value="==>" class="left2right"/><br/>
									<input type="button" id="deselectMenu" value="<--" class="left2right"/><br/>
									<input type="button" id="deselectMenuAll" value="<==" class="left2right"/>
								</td>
								<td>
									<select multiple="true" class="ui_multiselect01 selected_menus" name="menuIds"><%--这个是表单提交,要name--%>
										<c:forEach items="${entity.menus}" var="obj"><%--这是维护关系的对象,从role传入来的--%>
											<option value="${obj.id}">${obj.name}</option>
										</c:forEach>
									</select>
								</td>
							</tr>
						</table>
					</td>
				</tr>

				<tr>
					<td>&nbsp;</td>
					<td class="ui_text_lt">
						&nbsp;<input type="button" value="确定保存" class="ui_input_btn01 btn_submit"/>
						&nbsp;<input id="cancelbutton" type="button" value="重置" class="ui_input_btn01"/>
					</td>
				</tr>
			</table>
		</div>
	</div>
</form>
</body>
</html>