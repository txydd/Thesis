


function fn1() {
    var num = 0;
    timer = setInterval(function () {
        num++;
        $("#_load").text(num + "/100");
        if (num == 100) {
            clearInterval(timer);   //定时器清除；
            $("#synchronization").removeClass("run");
            $("#_load").text("完成");
        }
    }, 100);
}
/*$("#synchronization").click(function () {
    $.ajax({

    })
    $.confirm({
        title: '提示',
        content: '开始同步PLM数据',
        buttons: {
            确认: function () {
                var _run=$("#synchronization").hasClass("run");
                console.log(_run);
                if(!_run){
                    setTimeout("fn1()",100);
                    $("#synchronization").addClass("run");
                    console.log("开始同步")
                    $.get('getFilesData')
                }else {
                    $.alert("请等待同步完成","提示")
                }
            },
            取消: function () {

            },
        }
    });
})*/
var u=0;
$("#synchronization").click(function () {
    if(u!=0){
        alert("正在同步中，请稍后");
    }
    else{
    $.ajax({

    })
    $.confirm({
        title: '提示',
        content: '开始同步PLM数据',
        buttons: {
            确认: function () {
                u=1;
                $("#_load").text("同步中");
                $.ajax({
                    url:"/viewer/admin/getFilesData",
                    type:"post",
                    data:{

                    },
                    success:function(data){
                        if(data=="1"){
                            $("#_load").text("");
                            u=0;
                            alert("同步完成");
                        }
                        else{
                            $("#_load").text("");
                            u=0;
                            alert(data);
                        }
                    }
                })


            },
            取消: function () {

            },
        }
    });}
})






$('#confirm').click(function () {
    var oldPassword = $('#old_password').val();
    var newPassword=$('#new_password').val();
    var reNewPassword=$('#repeat_new_password').val();
    var employeeId = $('#employeeId').text();
    if(oldPassword == ''|| oldPassword == null){
        alert("请输入原密码");
        return false;
    }
    if(newPassword == ''|| newPassword == null){
        alert("请输入新密码");
        return false;
    }
    if(reNewPassword == ''||reNewPassword==null){
        alert("请重复输入新密码");
        return false;
    }

    if(newPassword!=reNewPassword){
        alert('两次密码输入不同');
        $('#new_password').val("");
        $('#repeat_new_password').val("");
        return false;
    }
    if(oldPassword == newPassword){
        alert("新旧密码不能相同");
        return false;
    }
    $.post('admin/update',{
        employeeId:employeeId,
        oldPassword:oldPassword,
        newPassword:newPassword,
        repeatNewPassword:reNewPassword
    },function (data) {
        if(data=='3001'){
            alert("两次新密码输入不同");
        }else if(data =='3002'){
            alert("旧密码不匹配");
        }else if(data == '1'){
            alert("修改成功,下次登录请使用新密码");
            window.location.reload();
        }else{
            alert("修改失败");
        }
    })


});
var s=0;
$("#clearFile").click(function () {
    if(s!=0){
        alert("正在清除中，请稍后");
    }
    else{
    $.confirm({
        title: '提示',
        content: '开始清除缓存文件',
        buttons: {
            确认: function () {
                s=1;
                $("#_clear").text("清除中");
                $.ajax({
                    async:false,
                    url:"/viewer/admin/clearFile",
                    type:"post",
                    data:{
                    },
                    success:function(data){
                        if(data=="1"){
                            $("#_clear").text("");
                            s=0;
                            alert("清除完成");
                        }
                    }
                })

            },
            取消: function () {

            },
        }
    });}
})



