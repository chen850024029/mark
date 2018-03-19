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
            $("#editForm").ajaxForm(function (data){
                if (data.success){
                    showDialog("操作成功",function () {
                        location.href = "/systemMenu/list.do?parentId=${parent.id}";
                    });
                }else {
                    showDialog("操作失败:"+data.msg);
                }
            });
        });

	</script>

</head>
<body>
<form id="editForm" action="/systemMenu/saveOrUpdate.do" method="post"data-obj="systemMenu" >
	<input type="hidden" name="id" value="${entity.id}">
	<div id="container">
		<div id="nav_links">
			<span style="color: #1A5CC6;">菜单编辑</span>
			<div id="page_close">
				<a>
					<img src="/images/common/page_close.png" width="20" height="20" style="vertical-align: text-top;"/>
				</a>
			</div>
		</div>
		<div class="ui_content">
			<table cellspacing="0" cellpadding="0" width="100%" align="left" border="0">
				<tr>
					<td class="ui_text_rt" width="140">父菜单</td>
					<td class="ui_text_lt">
						<input type="hidden" name="parent.id" value="${parent.id}"/>
						<input disabled class="ui_input_txt02" value="${parent.name}"/>
					</td>
				</tr>
				<tr>
					<td class="ui_text_rt" width="140">菜单名称</td>
					<td class="ui_text_lt">
						<input name="name" class="ui_input_txt02" value="${entity.name}"/>
					</td>
				</tr>
				<tr>
					<td class="ui_text_rt" width="140">菜单编码</td>
					<td class="ui_text_lt">
						<input name="sn" class="ui_input_txt02" value="${entity.sn}"/>
					</td>
				</tr>
				<tr>
					<td class="ui_text_rt" width="140">URL</td>
					<td class="ui_text_lt">
						<input name="url" class="ui_input_txt02" value="${entity.url}"/>
					</td>
				</tr>

				<tr>
					<td>&nbsp;</td>
					<td class="ui_text_lt">
						&nbsp;<input type="submit" value="确定保存" class="ui_input_btn01" />
						&nbsp;<input id="cancelbutton" type="button" value="重置" class="ui_input_btn01"/>
					</td>
				</tr>
			</table>
		</div>
	</div>
</form>
</body>
</html>