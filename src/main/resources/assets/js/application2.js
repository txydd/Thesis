var arr = new Array();
var num=new Array();
$(document).ready(function () {
    value = $('#idea').attr("data-value");
    //文件编号数组
    table_files = [];
    $(".wxj_search_but").click(function () {
        searchClick(1);
    });
    $('#detailSearch').click(function(){
        searchClick1(1)
    })



    $('.delete').each(function () {
        var fileNumber = $(this).attr('data-fileNumber');
        table_files.push(fileNumber);
    });
    $('#classification').change(function () {
        $('#key_word').val('');
        $('.temp').remove();
        $('.file_area_temp').remove();
        table_files = [];
    });
    //新增"资料借阅"
    $(".wxj_file_add_but").click(function () {
        $(document).keyup(function(e){
            var key = e.which;
            if(key==13){
                searchClick(1);
            }
        });
        $('#wxj_add_file').modal('show')
    });

    $(function(){
        var fileNum=$('#fileNum').val();
        console.log(fileNum);
         num=fileNum.split(",");
        for(var i=0;i<num.length;i++) {
            var fileNumber = num[i];
            $.ajax({
                url:"/viewer/user/getFile",
                method:"post",
                data:{
                    fileNumber:fileNumber
                },
                success:function(data) {
                    data =JSON.parse(data);
                    arr.push(data.datasetName);

                    addFileList(data);
                    /* addAddedList(number, data);
                     number++;
                     table_files.push(data.fileNumber);*/
                }
            })
        }
    })
    //添加文件至申请列表
    $(".wxj_result").on("click", ".wxj_res_application", function () {

        //判断是否已添加至清单
        var i = $(this).hasClass("wxj_added");
        number = $(this).attr("data-number");
        if (!i) {
            $(this).html("已添加").addClass("wxj_added");
            // 加入页面
            var resdata = result.filesResultVOList[parseInt(number) - 1];
            addFileList(resdata);
            arr.push(resdata.datasetName);
            num.push(resdata.fileNumber);
            addAddedList(number,resdata);
            table_files.push(resdata.fileNumber);
        }
    });

    // 删除选中文件

    $(".wxj_file_list").on("click", ".wxj_file_info_edit_del", function () {
        console.log("成功dao");
        var i = $(this).attr("data-fileNumber");
        //DOM
        $(this).parent().parent().remove();
        //arr
        table_files.splice($.inArray(i, table_files), 1);
        $("td[data-filenumber='" + i + "']").html("添加").removeClass("wxj_added");
        $(".delete[data-fileNumber='"+i+"']").parent().remove();
    });
    $(".wxj_added_list").on("click", ".delete", function () {

        var i = $(this).attr("data-fileNumber")
        $(".wxj_file_info_edit_del[data-filenumber='"+i+"']").parent().parent().remove();
        //DOM
        $(this).parent().remove();
        //arr
        table_files.splice($.inArray(i, table_files), 1);
        $("td[data-filenumber='" + i + "']").html("添加").removeClass("wxj_added");

    });
    $('#dateline').click(function () {
        $(this).removeAttr("style");
    });
    $('#situation').click(function () {
        $(this).removeAttr("style");
    });
    //init();
    $('#submit').click(function () {
        var useOfReference = $('#use_of_reference').val();
        var classification = $('#classification').text();
        var situationText = $('#situation').val();
        if (situationText == null || situationText == "") {
            tips("请填写借阅说明");
            $('#situation').attr("style", "border-color:#E56048");
            return;
        }
        if (table_files.length == 0 && $('.wxj_addBut_area').length >0) {
            tips("请添加借阅资料");
            return;
        }
        if(!$('#accept_agreement').prop('checked')){
            tips("请阅读并同意用户保密协议");
            return;
        }
        $.ajax({
            type: 'POST',
            data: {
                useOfReference: useOfReference,
                classification: classification,
                situation: situationText,
                fileList: table_files
            },
            traditional: true,
            url: "createApply",
            success: function (data) {
                if(data == '1'){
                    tips2("提交成功");

                }else{
                    tips2("提交失败");
                }

            }
        })


    });
    $('#for_force_read').click(function () {
        var classification = $('#classification').text();
        var situationText = $('#situation').val();
        if (situationText == null || situationText == "") {
            tips("请填写借阅说明");
            $('#situation').attr("style", "border-color:#E56048");
            return;
        }
        if (table_files.length == 0 && $('.wxj_addBut_area').length >0) {
            tips("请添加借阅资料");
            return;
        }
        $('#force_read').modal('show');
    })
});

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

function initApplicationContent(applicaiton) {
    $('#user').val(applicaiton.userId);
    $('#department').val(applicaiton.department);
    $("#use_of_reference option:contains(" + applicaiton.useOfReference + ")").attr("selected", true);
    $("#classification option:contains(" + applicaiton.classifyOfReference + ")").attr('selected', true);
    $('#dateline').val(dateParse(applicaiton.dateline));
    $('#apply_number').val(applicaiton.applyNumber);
    $('#situation').val(applicaiton.situation);
}

function initFileList(list) {
    for (var i = 0; i < list.length; i++) {
        addFileList(list[i]);
    }
}

function addSearchResult(i, data) {
    var file_number = data.fileNumber;
    var file_uid = data.uid;
    var file_name = data.datasetName;
    var revision = data.revision;
    var creator_id = data.creator;
    var create_time = dateParse(data.createTime);
    var description = data.matnr;
    var release_status = data.releaseStatus;
    var item_id = data.itemId;
    var number = i;
    if ($.inArray(file_number, table_files) > -1) {
        $(".wxj_result").append("<tr class='temp'><td>" + item_id + "</td><td>" + description + "</td><td>" + revision + "</td><td>" + file_name + "</td><td>" + creator_id + "</td><td>"+release_status+"</td><td class='wxj_res_application wxj_added' data-fileNumber=" + file_number + " data-number=" + number + ">已添加</td></tr>");
    } else if (data.currentStatus == 1) {
        $(".wxj_result").append("<tr class='temp'><td>" + item_id + "</td><td>" + description + "</td><td>" + revision + "</td><td>" + file_name + "</td><td>" + creator_id + "</td><td>"+release_status+"</td><td class='wxj_res_application wxj_added' data-fileNumber=" + file_number + " data-number=" + number + ">申请中</td></tr>");
    } else if (data.currentStatus == 2) {
        $(".wxj_result").append("<tr class='temp'><td>" + item_id + "</td><td>" + description + "</td><td>" + revision + "</td><td>" + file_name + "</td><td>" + creator_id + "</td><td>"+release_status+"</td><td class='wxj_res_application wxj_added' data-fileNumber=" + file_number + " data-number=" + number + ">已借阅</td></tr>");
    } else if (data.currentStatus == null) {
        $(".wxj_result").append("<tr class='temp'><td>" + item_id + "</td><td>" + description + "</td><td>" + revision + "</td><td>" + file_name + "</td><td>" + creator_id + "</td><td>"+release_status+"</td><td class='wxj_res_application _hand' data-fileNumber=" + file_number + " data-number=" + number + ">添加</td></tr>");
    }
}
//file_numer实际为dataset_uid+imanFile_uid,页面只显示imanFile_uid
function addAddedList(i, data) {
    var file_number = data.fileNumber;
    var file_uid = data.uid;
    var file_name = data.datasetName;
    var revision = data.revision;
    var creator_id = data.creator;
    var create_time = dateParse(data.createTime);
    var description = data.description;
    var item_id = data.itemId;
    var release_status = data.releaseStatus;
    var number = i;
    console.log("file_name为"+file_name);
    $(".wxj_added_list").append("<tr ><td>" + item_id + "</td><td>" + description + "</td><td>" + revision + "</td><td>" + file_name + "</td><td>" + creator_id + "</td><td>"+release_status+"</td><td class='wxj_res_application delete _hand _impDel' data-fileNumber=" + file_number + " data-number=" + number + ">删除</td></tr>");

}

function addFootPage(currentPage,totalPage) {
    $('#footPage').append("<nav  id='pageArea' aria-label=\"...\">\n" +
        "                        <ul class=\"pager pull-right\">\n" +
        "                            <li><span class=\"wxj_browser_pages\" id=\"pre_page\">上一页</span></li>\n" +
        "                            <li>第<span id=\"current_page\" >"+currentPage+"</span>页 | 共<span id=\"total_page\">"+totalPage+"</span>页</li>\n" +
        "                            <li><span class=\"wxj_browser_pages\" id=\"next_page\">下一页</span></li>\n" +
        "                        </ul>\n" +
        "                    </nav>")
}

function addFileList(data) {
    var file_number = data.fileNumber;
    var file_uid = data.uid;
    var file_name = data.datasetName;
    var revision = data.revision;
    var creator_id = data.creator;
    var matnr = data.matnr;
    var item_id = data.itemId;
    var release_status = data.releaseStatus;
    $(".wxj_addBut_area").before("<div class='col-md-3 file_area_temp'><ul class='wxj_file_info_edit'><li><span>资料编号: </span><span>" + item_id + "</span></li><li><span>资料版本: </span><span>" + revision + "</span></li><li><span>文件名: </span><span>" + file_name + "</span></li><li><span>创建者：</span><span>" + creator_id + "</span></li><li><span>状态：</span><span>" + release_status + "</span></li><li><span>物料号: </span><span>" + matnr + "</span></li><p class='wxj_file_info_edit_del _hand _impDel' data-fileNumber=" + file_number + ">删除</p></ul></div>");
}

function init() {
    var applyNumber = getQueryString("applyNumber");
    if (applyNumber != null) {
        $.post("/viewer/application/getApplicationInfo", {
            applyNumber: applyNumber
        }, function (data, status) {
            if (status == 'success') {
                if (data == 0) {
                    alert("加载失败");
                } else {
                    result = JSON.parse(data);
                    application = result.application;
                    list = result.referFilesListVOList;
                    initApplicationContent(application);
                    initFileList(list);

                }
            } else {
                alert("加载失败");
            }
        })
    }
}

function judgePage() {
    var currentPage = $('#current_page').text();
    currentPage = parseInt(currentPage);
    //var keyword = $('#keyword').val();
    //keyword = parseInt(keyword);
    var totalPage = $("#total_page").text();
    totalPage = parseInt(totalPage);
    if(currentPage<=1){
        $('#pre_page').attr("style","pointer-events: none; ");
    }
    if(currentPage>=totalPage){
        $('#next_page').attr("style","pointer-events: none; ");
    }
}
function searchClick(page)  {
    console.log("jinguo");
    var keyWord = $('#key_word').val();

    $.post("/viewer/application/getFiles", {
        keyWord: keyWord,
        pageNum:page,
    }, function (data, status) {
        if (status == "success") {
            result = JSON.parse(data);
            $('.temp').remove();
            $('#pageArea').remove();
            for (var i = 0; i < result.filesResultVOList.length; i++) {
                addSearchResult(i + 1, result.filesResultVOList[i]);
            }
            addFootPage(result.currentPage,result.totalPage);
            judgePage()
            $('#pre_page').click(function () {
                var currentPage = $('#current_page').text();
                currentPage = parseInt(currentPage);
                searchClick(currentPage-1);
            });
            $('#next_page').click(function () {
                var currentPage = $('#current_page').text();
                currentPage = parseInt(currentPage);
                searchClick(currentPage+1);
            })
        }
    });
}
$(function(){
    $("#revision").prop('checked',true);
})
function searchClick1(page)  {
    var fileName=$('#fileName').val();
    var fileType=$('#fileType').val();
    var vbeln=$('#vbeln').val();
    var matnr=$('#matnr').val();
    var aufnr=$('#aufnr').val();
    var isLast=$("#revision").prop("checked");
    var revision;
    if(isLast){
        revision=1;
    }
    else{
        revision=0;
    }

    $.post("/viewer/application/getFiles1", {
        fileName:fileName,
        fileType:fileType,
        vbeln:vbeln,
        matnr:matnr,
        aufnr:aufnr,
        revision:revision,
        pageNum:page
    }, function (data, status) {
        if (status == "success") {
            result = JSON.parse(data);
            $('.temp').remove();
            $('#pageArea').remove();
            for (var i = 0; i < result.filesResultVOList.length; i++) {
                addSearchResult(i + 1, result.filesResultVOList[i]);
            }
            addFootPage(result.currentPage,result.totalPage);
            judgePage()
            $('#pre_page').click(function () {
                var currentPage = $('#current_page').text();
                currentPage = parseInt(currentPage);
                searchClick1(currentPage-1);
            });
            $('#next_page').click(function () {
                var currentPage = $('#current_page').text();
                currentPage = parseInt(currentPage);
                searchClick1(currentPage+1);
            })
        }
    });
}