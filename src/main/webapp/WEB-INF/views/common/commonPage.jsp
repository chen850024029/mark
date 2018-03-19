<%@ page language="java" contentType="text/html; charset=UTF-8" %>

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
        转到第<input type="text" name="currentPage" value="${result.currentPage}" class="ui_input_txt01"/>页
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
        $(".page_size option[value='${qo.pageSize}']").prop("selected", true);
        $(".page_size").change(function () {
            $(":text[name='currentPage']").val(1);
            $("#searchForm").submit();
        });
    </script>
</div>


