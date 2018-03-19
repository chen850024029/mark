<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
	<script type="text/javascript" src="/js/plugins/artDialog/iframeTools.js"></script>
	<script type="text/javascript" src="/js/plugins/My97DatePicker/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="/js/plugins/echarts/echarts.js"></script>
<script type="text/javascript" src="/js/commonAll.js"></script>
	<script type="text/javascript">
        $(function () {
            $(".Wdate").click(function () {
                WdatePicker({
                    //最小时间
                    minDate: $("input[name='beginDate']").val(),
                    //最大时间
                    maxDate: new Date(),
                    //设置日历插件是只读状态
                    readOnly: true
                });
            });


            $(".chart").click(function () {
                //获取到当前放大镜所在的行
				var url = $(this).data("url") + "?" + $("#searchForm").serialize();//进行序列化操作
                $.artDialog.open(url, {
                    id: "saleChart",
                    title: "销售报表",
                    width: 1000,
                    height: 550,
                    lock: true,
                    opacity: 0.1,//设置透明度
                    resize: false,

                });
            });
        });
	</script>


	<title>WMS-销售报表</title>
<style>
	.alt td{ background:black !important;}
</style>
</head>
<body>
	<form id="searchForm" action="/chart/sale.do" method="post">
		<div id="container">
			<div class="ui_content">
				<div class="ui_text_indent">
					<div id="box_border">
						<div id="box_top">搜索</div>
						<div id="box_center">
							业务时间
							<fmt:formatDate value="${qo.beginDate}" pattern="yyyy-MM-dd" var="beginDate"/>
							<fmt:formatDate value="${qo.endDate}" pattern="yyyy-MM-dd" var="endDate"/>
							<input type="text" class="ui_input_txt02 Wdate" readonly="readonly" name="beginDate"
								   value="${beginDate}"/> ~
							<input type="text" class="ui_input_txt02 Wdate" readonly="readonly" name="endDate"
								   value="${endDate}"/>
							货品名称/编码
							<input type="text" class="ui_input_txt01" name="keyword" value="${qo.keyword}"/>
							客户
							<select id="clientId" class="ui_select01" name="clientId">
								<option value="-1">全部客户</option>
								<c:forEach var="client" items="${clients}">
									<option value="${client.id}" ${qo.clientId==client.id?"selected='selected'":""}>${client.name}</option>
								</c:forEach>
							</select>
							品牌
							<select id="brandId" class="ui_select01" name="brandId">
								<option value="-1">全部品牌</option>
								<c:forEach var="brand" items="${brands}">
									<option value="${brand.id}" ${qo.brandId==brand.id?"selected='selected'":""}>${brand.name}</option>
								</c:forEach>
							</select>
							类型
							<select id="groupByType" class="ui_select01" name="groupByType">
								<c:forEach items="${groupByTypeMap}" var="groupByTy">
									<option value="${groupByTy.key}" ${qo.groupByType==groupByTy.key?"selected='selected'":""}>${groupByTy.value}</option>
								</c:forEach>
							</select>
						</div>

						<div id="box_bottom">
							<input type="button" value="查询" class="ui_input_btn01 btn_page" data-page="1"/>
							<input type="button" value="柱状图" class="left2right chart" data-url="/chart/saleByBar.do"/>
							<input type="button" value="饼状图" class="left2right chart" data-url="/chart/saleByPie.do"/>
						</div>

					</div>
				</div>
			</div>
			<div class="ui_content">
				<div class="ui_tb">
					<table class="table" cellspacing="0" cellpadding="0" width="100%" align="center" border="0">
						<tr>
							<th width="30"><input type="checkbox" id="all" /></th>
							<th>分组类型</th>
							<th>销售总数</th>
							<th>销售总额</th>
							<th>利润</th>
							<th></th>
						</tr>

						<c:forEach items="${result}" var="entity">
							<tr>
								<td><input type="checkbox" class="acb" /></td>
								<td>${entity.groupByType}</td>
								<td>${entity.totalNumber}</td>
								<td>${entity.totalAmount}</td>
								<td>${entity.profit}</td>
							</tr>
						</c:forEach>
					</table>
				</div>
				<script type="text/javascript">
                    //翻页操作
                    $(".btn_page").click(function () {
                        var pageNo = $(this).data("page") || $(":text[name='currentPage']").val();
                        //设置当前页面的值
                        $(":text[name='currentPage']").val(pageNo);
                        $("#searchForm").submit();//提交表单
                    });
				</script>

			</div>
		</div>
	</form>
</body>
</html>
