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
</head>
<body>
<form id="editForm" action="/product/saveOrUpdate.do" method="post"enctype="multipart/form-data" data-obj="product">
	<input type="hidden" name="id" value="${entity.id}">
	<div id="container">
		<div id="nav_links">
			<span style="color: #1A5CC6;">商品编辑</span>
			<div id="page_close">
				<a>
					<img src="/images/common/page_close.png" width="20" height="20" style="vertical-align: text-top;"/>
				</a>
			</div>
		</div>
		<div class="ui_content">
			<table cellspacing="0" cellpadding="0" width="100%" align="left" border="0">
				<tr>
					<td class="ui_text_rt" width="140">品牌名称</td>
					<td class="ui_text_lt">
						<input name="name" class="ui_input_txt02" value="${entity.name}"/>
					</td>
				</tr>
				<tr>
					<td class="ui_text_rt" width="140">品牌编码</td>
					<td class="ui_text_lt">
						<input name="sn" class="ui_input_txt02" value="${entity.sn}"/>
					</td>
				</tr>
				<tr>
					<td class="ui_text_rt" width="140">成本价</td>
					<td class="ui_text_lt">
						<input name="costPrice" class="ui_input_txt02" value="${entity.costPrice}"/>
					</td>
				</tr>
				<tr>
					<td class="ui_text_rt" width="140">零售价</td>
					<td class="ui_text_lt">
						<input name="salePrice" class="ui_input_txt02" value="${entity.salePrice}"/>
					</td>
				</tr>
				<tr>
					<td class="ui_text_rt" width="140">商品品牌</td>
					<td class="ui_text_lt">
						<select name="brand_id" class="ui_select03">
							<option value="-1">--请君选择--</option>
                            <c:forEach items="${brands}" var="brand">
                                <option value="${brand.id}" ${entity.brand_id==brand.id?"selected":""}>${brand.name}</option>
                            </c:forEach>
						</select>
						<input type="hidden" name="imagePath" value="${entity.imagePath}"/>
					</td>
				</tr>
				<tr>
					<td class="ui_text_rt" width="140">商品图片</td>
					<td class="ui_text_lt">
						<input type="file" name="pic" class="ui_file"/>
					</td>
				</tr>
				<tr>
					<td class="ui_text_rt" width="140">商品简介</td>
					<td class="ui_text_lt">
						<textarea rows="20" cols="50" class="ui_input_txtarea">${entity.intro}</textarea>
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