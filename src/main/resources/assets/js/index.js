/**
 * Created by chenweixuan on 2018/8/30.
 */
$(function () {
    // 移动端顶部菜单 2019/01/01
    // var wi=$(window).width();
    // // alert(wi);
    // if(wi<=768){
    //     $(".sub_menu").parent().click(function () {
    //         $(".sub_menu").slideToggle();
    //     })
    // }
    // ======================= application.html < START > =======================
    $('.time').each(function () {
        var time = $(this).text();
        $(this).text(dateParse(time));

        time = $(this).val();
        $(this).val(dateParse(time));
    });
    $('.time1').each(function () {
        var time = $(this).text();
        $(this).text(dateParse1(time));

        time = $(this).val();
        $(this).val(dateParse1(time));
    });
    $('#logout').click(function () {
        window.location.href='/viewer/exit';
    });

    //日期选择插件 初始化
    $("[data-datepicker]").datepicker({
        autoclose: true,
        language: 'zh-CN',
        format: "yyyy-mm-dd",
        startDate: new Date()
    });
    //日期选择插件 初始化
    $("[data-datepicker_2]").datepicker({
        autoclose: true,
        language: 'zh-CN',
        format: "yyyy-mm-dd",
    });

    //申请时间
    //获取当天时间，格式YYYY-MM-DD
    function getNowFormatDate() {
        var date = new Date();
        var seperator1 = "-";
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var strDate = date.getDate();
        if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
        if (strDate >= 0 && strDate <= 9) {
            strDate = "0" + strDate;
        }
        var currentdate = year + seperator1 + month + seperator1 + strDate;
        $("[data-today]").val(currentdate)
    }

    getNowFormatDate();

    
});

/*
Date->String "yyy-MM-dd"
 */
function dateParse(time) {
    var date;
    if (!isNaN(time)) {
        date = new Date(time);
    } else {
        var dateStr = time.trim().split(" ");
        var strGMT = dateStr[0] + " " + dateStr[1] + " " + dateStr[2] + " " + dateStr[5] + " " + dateStr[3] + " GMT+0800";
        date = new Date(Date.parse(strGMT));
    }
    var year = date.getFullYear();  //获取年
    var month = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : (date.getMonth() + 1);    //获取月
    var day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate(); //获取日
    var hours = date.getHours();
    var minutes = date.getMinutes();
    var sec = date.getSeconds();
    return year + "-" + month + "-" + day
}

function dateParse1(time) {
    var date;
    if (!isNaN(time)) {
        date = new Date(time);
    } else {
        var dateStr = time.trim().split(" ");
        var strGMT = dateStr[0] + " " + dateStr[1] + " " + dateStr[2] + " " + dateStr[5] + " " + dateStr[3] + " GMT+0800";
        date = new Date(Date.parse(strGMT));
    }
    var year = date.getFullYear();  //获取年
    var month = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : (date.getMonth() + 1);    //获取月
    var day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate(); //获取日
    var hours = date.getHours();
    hours=hours<10?('0'+hours):hours;
    var minutes = date.getMinutes();
    minutes=minutes<10?('0'+minutes):minutes;
    var sec = date.getSeconds();
    sec=sec<10?('0'+sec):sec;
    return year + "-" + month + "-" + day+" "+hours+":"+minutes+":"+sec;
}

/*
获取url中参数值
name:参数名
 */
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}

function tips(mes) {
    $(".wxj_mes_word").html(mes);
    $(".wxj_mes_bg").fadeIn();
    $(".closeBut").click(function () {
        $(".wxj_mes_bg").fadeOut("fast");
    })
}


/*
5/30 chenweixuan add
 */
$(function () {
    // 导航栏收缩
    $(".parents_menu").click(function(){
        $(this).siblings(".sub_menu").slideToggle("fast");
    });
    // 图片浏览插件 写在 directoryIndex.html 中
    // 配置项说明地址：https://blog.csdn.net/qq_29132907/article/details/80136023
    // 3D 插件写在 fileIndex2.html 中
});
