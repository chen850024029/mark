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
          //点击放大镜,打开选中商品的页面
          $(".searchproduct").click(function () {
              //获取到当前放大镜所在的行
              var currentTr = $(this).closest("tr");
              $.artDialog.open("/product/selectProductList.do",{
                  id:"selectProduct",
                  title:"选择商品",
                  width:1000,
                  height:500,
                  lock:true,
                  opacity:0.1,//设置透明度
                  resize:false,
                  close:function () {
                      var productInfo = $.artDialog.data("productInfo");
                      // console.log(productInfo);
                      if (productInfo){
                          //数据的回填
                          currentTr.find("[tag=name]").val(productInfo.name);
                          currentTr.find("[tag=pid]").val(productInfo.id);
                          currentTr.find("[tag=brand]").text(productInfo.brandName);
                          currentTr.find("[tag=costPrice]").val(productInfo.costPrice);
                      }
                      //清空商品数据
                       $.artDialog.removeData("productInfo");
                  }
              });
          });
          //小计
          $("[tag=costPrice],[tag=number]").blur(function () {
             var currentTr = $(this).closest("tr");
             var costPrice = parseFloat(currentTr.find("[tag=costPrice]").val()) || 0;
             var number = parseFloat(currentTr.find("[tag=number]").val()) || 0;
             var amount = (costPrice * number).toFixed(2);
             currentTr.find("[tag=amount]").text(amount);
          });
          //添加明细
          $(".appendRow").click(function () {

             var newTr = $("#edit_table_body tr:first").clone(true);
             //清空行数据
             newTr.find("[tag=name]").val("");
             newTr.find("[tag=pid]").val("");
             newTr.find("[tag=brand]").text("");
             newTr.find("[tag=costPrice]").val("");
             newTr.find("[tag=number]").val("");
             newTr.find("[tag=amount]").text("");
             newTr.find("[tag=remark]").val("");
              $("#edit_table_body").append(newTr);
          });
          //在表单提交之前,修改元素name属性值
          $("#pid").click(function () {
              if ($("[tag='pid']").val()){
                  $.each($("#edit_table_body tr"),function (index,tr) {
                      $(tr).find("[tag=pid]").prop("name","items["+index+"].product.id");
                      $(tr).find("[tag=remark]").prop("name","items["+index+"].remark");
                      $(tr).find("[tag=costPrice]").prop("name","items["+index+"].costPrice");
                      $(tr).find("[tag=number]").prop("name","items["+index+"].number");
                  });
                  $("#editForm").submit();//提交表单
              }else {
                 showDialog("你这傻吊!你就不能选一个么?");
             }
          });
          $("#editForm").ajaxForm(function (data) {
              if (data.success){
                  showDialog("傻逼你成功了",function () {
                      location.href="/stockIncomeBill/list.do";
                  });
              }else {
                  showDialog("你这傻吊!真失败:" + data.msg);
              }
          });
          //删除明细
          $(".removeItem").click(function () {
             var currentTr = $(this).closest("tr");
             if ($("#edit_table_body tr").size()==1){
                 //清空行数据
                 currentTr.find("[tag=name]").val("");
                 currentTr.find("[tag=pid]").val("");
                 currentTr.find("[tag=brand]").text("");
                 currentTr.find("[tag=costPrice]").val("");
                 currentTr.find("[tag=number]").val("");
                 currentTr.find("[tag=amount]").text("");
                 currentTr.find("[tag=remark]").val("");
             }else {
                 currentTr.remove();
             }
          });
      });


    </script>

</head>
<body>
<form id="editForm" action="/stockIncomeBill/saveOrUpdate.do" method="post"data-obj="stockIncomeBill" >
	<input type="hidden" name="id" value="${entity.id}">
	<div id="container">
		<div id="nav_links">
			<span style="color: #1A5CC6;">库存编辑</span>
			<div id="page_close">
				<a>
					<img src="/images/common/page_close.png" width="20" height="20" style="vertical-align: text-top;"/>
				</a>
			</div>
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
						<select name="depot.id" class="ui_select03">
							<option value="-1">--请君选择--</option>
							<c:forEach items="${depots}" var="depot">
								<option value="${depot.id}" ${entity.depot.id==depot.id?"selected":""}>${depot.name}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td class="ui_text_rt" width="140">业务时间</td>
					<td class="ui_text_lt">
                        <fmt:formatDate value="${entity.vdate}" pattern="yyyy-MM-dd" var="vdate"/>
						<input name="vdate" class="ui_input_txt02" value="${vdate}" onclick="WdatePicker({skin:'whyGreen',minDate:new Date()});" readonly="readonly"/>
					</td>
				</tr>

                <tr>
                    <td class="ui_text_rt" width="140">单据明细</td>
                </tr>
                <tr>
                    <td></td>
                    <td>
                        <input type="button" value="添加明细" class="ui_input_btn01 appendRow"/>
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
                            <c:if test="${entity.id==null}">
                                <tr>
                                    <td></td>
                                    <td>
                                        <input disabled="true" readonly="true" value="${item.product.name}"
                                               class="ui_input_txt02" tag="name"/>
                                        <img src="/images/common/search.png" class="searchproduct"/>
                                        <input type="hidden" tag="pid" value="${item.product.id}"/>
                                    </td>

                                    <td><span tag="brand">${item.product.brandName}</span></td>
                                    <td><input tag="costPrice" type="number" value="${item.costPrice}"
                                               class="ui_input_txt02"/></td>
                                    <td><input tag="number" type="number" value="${item.number}"
                                               class="ui_input_txt02"/></td>
                                    <td><span tag="amount">${item.amount}</span></td>
                                    <td><input tag="remark" value="${item.remark}"
                                               class="ui_input_txt02"/></td>
                                    <td>
                                        <a href="javascript:;" class="removeItem">删除明细</a>
                                    </td>
                                </tr>
                            </c:if>

                            <c:forEach items="${entity.items}" var="item">
                                <tr>
                                    <td></td>
                                    <td>
                                        <input disabled="true" readonly="true" value="${item.product.name}"
                                               class="ui_input_txt02" tag="name"/>
                                        <img src="/images/common/search.png" class="searchproduct"/>
                                        <input type="hidden" tag="pid" value="${item.product.id}"/>
                                    </td>
                                    <td><span tag="brand">${item.product.brandName}</span></td>
                                    <td><input tag="costPrice" type="number" value="${item.costPrice}"
                                               class="ui_input_txt02"/></td>
                                    <td><input tag="number" type="number" value="${item.number}"
                                               class="ui_input_txt02"/></td>
                                    <td><span tag="amount">${item.amount}</span></td>
                                    <td><input tag="remark" value="${item.remark}"
                                               class="ui_input_txt02"/></td>
                                    <td>
                                        <a href="javascript:;" class="removeItem">删除明细</a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </td>
                </tr>
				<tr>
					<td>&nbsp;</td>
					<td class="ui_text_lt">
						&nbsp;<input type="button" value="确定保存" class="ui_input_btn01" id="pid" data-obj="stockIncomeBill"/>
						&nbsp;<input id="cancelbutton" type="button" value="重置" class="ui_input_btn01"/>
					</td>
				</tr>
			</table>
		</div>
	</div>
</form>
</body>
</html>