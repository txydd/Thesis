$('.werk').click(function(){
    var w=$(this).text();
    console.log("当前工厂为"+w);
    $.ajax({
        url:"/viewer/produce/changeWerk",
        type:"post",
        data:{
            werk:w
        },
        success:function(){
            $('#werkNow').text("当前工厂为: "+w);
        }
    })
    $('#werkNow').text("当前工厂为: "+w);

})

