<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="/style/basic_layout.css" rel="stylesheet" type="text/css">
<link href="/style/common_style.css" rel="stylesheet" type="text/css">
	<link href="/js/plugins/fancybox/jquery.fancybox.css"rel="stylesheet" type="text/css">
<script type="text/javascript" src="/js/jquery/jquery.js"></script>
	<script type="text/javascript" src="/js/plugins/jQueryForm/jQueryForm.js"></script>
<script type="text/javascript" src="/js/plugins/artDialog/jquery.artDialog.js?skin=blue"></script>
<script type="text/javascript" src="/js/plugins/artDialog/iframeTools.js"></script>
<script type="text/javascript" src="/js/commonAll.js"></script>
	<script type="text/javascript">
		$(function () {
			$(".btn_selectProduct").click(function () {
				//将父窗口中所需要的数据共享回去
				var productInfo = $(this).data("productinfo");
                $.artDialog.data("productInfo", productInfo);
				//关闭当前子窗口
				$.artDialog.close();
            });
        });

	</script>

	<title>WMS-商品管理</title>
<style>
	.alt td{ background:black !important;}
</style>
</head>
<body>
	<form id="searchForm" action="/product/selectProductList.do" method="post">
		<div id="container">
			<div class="ui_content">
				<div class="ui_text_indent">
					<div id="box_border">
						<div id="box_top">搜索</div>
						<div id="box_center">
							商品名称/编码
							<input type="text" name="keyword" value="${qo.keyword}" class="ui_input_txt02"/>
							商品品牌
							<select id="brandId" class="ui_select01" name="brandId">
								<option value="-1" >--品牌选择--</option>
								<c:forEach var="brand" items="${brands}">
									<option value="${brand.id}" ${brand.id==qo.brandId?"selected":""}>${brand.name}</option>
								</c:forEach>
							</select>

						</div>
						<div id="box_bottom">
							<input type="button" value="查询" class="ui_input_btn01 btn_page" data-page="1"/>
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
							<th>商品图片</th>
							<th>商品名称</th>
							<th>商品编码</th>
							<th>商品品牌</th>
							<th>成本价</th>
							<th>零售价</th>
							<th></th>
						</tr>

						<c:forEach items="${result.data}" var="entity" varStatus="num">
							<tr>
								<td><input type="checkbox" class="acb" /></td>
								<td>${num.count}</td>
								<td>
									<a class="fancybox" href="${entity.imagePath}" data-fancybox-group="gallery" title="${entity.name}">
										<img src="${entity.smallImagePath}" width="80px"/>
									</a>
								</td>
								<td>${entity.name}</td>
								<td>${entity.sn}</td>
								<td>${entity.brandName}</td>
								<td>${entity.costPrice}</td>
								<td>${entity.salePrice}</td>
								<td>
									<input type="button" value="选中" data-productinfo='${entity.productInfo}' class="left2right btn_selectProduct"/>
								</td>
							</tr>
						</c:forEach>
					</table>
				</div>
				<%--<%@ include file="/WEB-INF/views/common/commonPage.jsp"%>--%>
				<div class="ui_tb_h30">
					<div class="ui_flt" style="height: 30px; line-height: 30px;">
						共有
						<span class="ui_txt_bold04">${result.rows}</span>
						条记录，当前第
						<span class="ui_txt_bold04">${result.currentPage}/${result.endPage}</span>
						页
					</div>
					<div class="ui_frt">
						<input type="button" value="首页" class="ui_input_btn01 btn_page" data-page="1"/>
						<input type="button" value="上一页" class="ui_input_btn01 btn_page" data-page="${result.prevPage}"/>
						<input type="button" value="下一页" class="ui_input_btn01 btn_page" data-page="${result.nextPage}"/>
						<input type="button" value="尾页" class="ui_input_btn01 btn_page" data-page="${result.endPage}"/>

						<select name="pageSize" class="ui_select02 page_size">
							<option value="5">5</option>
							<option value="10">10</option>
							<option value="15">15</option>
							<option value="20">20</option>
						</select>
						转到第<input type="text" name="currentPage" value="${result.currentPage}" class="ui_input_txt01" />页
						<input type="button" class="ui_input_btn01 btn_page" value="跳转"/>
					</div>

					<script type="text/javascript">
                        //翻页操作
                        $(".btn_page").click(function () {
                            var pageNo = $(this).data("page") || $(":text[name='currentPage']").val();
                            //设置当前页面的值
                            $(":text[name='currentPage']").val(pageNo);
                            $("#searchForm").submit();//提交表单
                        });

                        //每页显示多少条数据
                        $(".page_size option[value='${qo.pageSize}']").prop("selected",true);
                        $(".page_size").change(function () {
                            $(":text[name='currentPage']").val(1);
                            $("#searchForm").submit();
                        });
					</script>
				</div>
			</div>
		</div>
	</form>
</body>
</html>
