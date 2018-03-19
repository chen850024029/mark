//禁用数组参数名后面加[]
jQuery.ajaxSettings.traditional = true;

//弹出对话框
function  showDialog(msg,ok,cancel) {
    $.dialog({
        title:"温馨提示",
        icon:"face-smile",
        content:msg,
        lock:true,
        ok:ok || true,
        cancel:cancel
    });
}


/** table鼠标悬停换色* */
$(function () {

    //异步请求(把editForm表单提交,变成ajax提交)
        $("#editForm").ajaxForm(function (data){
            var obj = $("#editForm").data("obj");
            if (data.success){
                showDialog("操作成功",function () {
                    location.href = "/"+obj+"/list.do";
                });
            }else {
                showDialog("操作失败:"+data.msg);
            }
        });



    //点击删除按钮时,提示用户是否要进行删除操作
    $(".btn_delete").click(function () {
        //获取删除按钮对应的data-url属性值(该属性值指定:如果点击了确定,会执行什么操作)
        var operate = $(this).data("url");
        var obj = $(this).data("obj");
        //调用artDialog插件提示用户
        showDialog("确定要删除吗?",function () {

            //指定如果点击了确定按钮,则发送ajax请求到后台执行删除操作,不需要指定ajax请求携带的参数(因为已经包含在data-url属性值中)
            $.get(operate,function (data) {
                if(data.success){
                    //提示用户操作成功
                    showDialog("操作成功",function () {
                        //跳转到当前对象的list界面
                        location.href="/"+obj+"/list.do";//在list删除超链接传来的obj,list的obj是自己定义的
                    });
                }else{
                    //执行失败,提示用户
                    showDialog("操作失败:"+data.msg);
                }
            },"json");
        },true);
    });

    //提示用户是否要审核
    $(".btn_audit").click(function () {
        var url = $(this).data("url");
        var obj = $(this).data("obj");
        showDialog("确定要审核吗?",function () {
            $.get(url,function (data) {
                if(data.success){
                    //提示用户操作成功
                    showDialog("操作成功",function () {
                        //跳转到当前对象的list界面
                        location.href="/"+obj+"/list.do";//在list删除超链接传来的obj,list的obj是自己定义的
                    });
                }else{
                    //执行失败,提示用户
                    showDialog("操作失败:"+data.msg);
                }
            },"json");
        },true);
    });





    // 如果鼠标移到行上时，执行函数
    $(".table tr").mouseover(function () {
        $(this).css({
            background: "#CDDAEB"
        });
        $(this).children('td').each(function (index, ele) {
            $(ele).css({
                color: "#1D1E21"
            });
        });
    }).mouseout(function () {
        $(this).css({
            background: "#FFF"
        });
        $(this).children('td').each(function (index, ele) {
            $(ele).css({
                color: "#909090"
            });
        });
    });

    //跳转到编辑的界面中
    $(".btn_input").click(function () {
        var url = $(this).data("url");
        location.href = url;
    });

});
