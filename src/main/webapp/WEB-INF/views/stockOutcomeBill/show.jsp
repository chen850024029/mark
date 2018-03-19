<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    <script type="text/javascript" src="/js/plugins/artDialog/iframeTools.js"></script>
<script type="text/javascript" src="/js/plugins/jQueryForm/jQueryForm.js"></script>
    <script type="text/javascript" src="/js/plugins/My97DatePicker/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="/js/commonAll.js"></script>
    <script type="text/javascript">
        $(function () {
            $(":text").prop("readonly",true);
        });

    </script>

</head>
<body>
	<input type="hidden" name="id" value="${entity.id}">
	<div id="container">
		<div id="nav_links">
			<span style="color: #1A5CC6;">查看仓库库存</span>
		</div>
		<div class="ui_content">
			<table cellspacing="0" cellpadding="0" width="100%" align="left" border="0">
				<tr>
					<td class="ui_text_rt" width="140">库存编码</td>
					<td class="ui_text_lt">
						<input name="sn" class="ui_input_txt02" value="${entity.sn}"/>
					</td>
				</tr>
				<tr>
					<td class="ui_text_rt" width="140">仓库</td>
					<td class="ui_text_lt">
						<input class="ui_input_txt02" name="depot" value="${entity.depot.name}"/>
					</td>
				</tr>
				<tr>
					<td class="ui_text_rt" width="140">业务时间</td>
					<td class="ui_text_lt">
                        <fmt:formatDate value="${entity.vdate}" pattern="yyyy-MM-dd" var="vdate"/>
						<input name="vdate" class="ui_input_txt02" value="${vdate}"/>
					</td>
				</tr>

                <tr>
                    <td class="ui_text_rt" width="140">单据明细</td>
                </tr>
                <tr>
                    <td></td>
                    <td>
                        <table class="edit_table" cellspacing="0" cellpadding="0" border="0" style="width: auto">
                            <thead>
                            <tr>
                                <th width="10"></th>
                                <th width="200">货品</th>
                                <th width="120">品牌</th>
                                <th width="80">价格</th>
                                <th width="80">数量</th>
                                <th width="80">金额小计</th>
                                <th width="150">备注</th>
                                <th width="60"></th>
                            </tr>
                            </thead>
                            <tbody id="edit_table_body">

                            <c:forEach items="${entity.items}" var="item">
                                <tr>
                                    <td></td>
                                    <td>
                                        <input disabled="true" readonly="true" value="${item.product.name}"
                                               class="ui_input_txt02" tag="name"/>
                                        <input type="hidden" tag="pid" value="${item.product.id}"/>
                                    </td>
                                    <td><span tag="brand">${item.product.brandName}</span></td>
                                    <td><input tag="salePrice" type="number" value="${item.salePrice}"
                                               class="ui_input_txt02"/></td>
                                    <td><input tag="number" type="number" value="${item.number}"
                                               class="ui_input_txt02"/></td>
                                    <td><span tag="amount">${item.amount}</span></td>
                                    <td><input tag="remark" value="${item.remark}"
                                               class="ui_input_txt02"/></td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </td>
                </tr>
				<tr>
					<td>&nbsp;</td>
					<td class="ui_text_lt">
						&nbsp;<input type="button" value="返回列表" onclick="window.history.back();" class="ui_input_btn01"/>
					</td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>