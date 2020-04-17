$(document).ready(function () {
    showOrganization();
});

function showOrganization() {
    $.ajax({
        url: "NGCindexOrganization/getDepartmentTreeNode",
        method: "post",
        async: false,
        data: {}
        ,
        success: function (data) {
            $("#basicTree").empty();
            //得到的所有下一级
            var currNodeinList = data.currNode;
            var str = '<ul ><li onclick="reTree()" alick="showDepartmentInfo(' + currNodeinList.id + "," + currNodeinList.subCompanyId + ',$(this))" data-jstree=' + "'" + '{"opened":true}' + "'" + '>\n' +
                currNodeinList.fullname;
            str = str + linkDepartment(data) + '</li></ul>\n';
            $("#basicTree").append(str);
        }
    })

}

// refresh
var u=0;
function refresh() {
    if(u!=0){
        alert("正在同步，请稍后");
    }
    else{
        u=1;
        $("#OrganizationSyn").text("同步中");
        $.ajax({
            url: "NGCindexOrganization/refresh",
            method: "post",
            async: false,
            data: {}
            ,
            success: function (data) {
                u=0;
                $("#OrganizationSyn").text("数据同步");
                alert(data.message)
                window.location.reload();
            }
        })}

}

function reTree() {
    //    //
    $("#basicTree").find("li").each(function () {
        $(this).find("a").attr("onclick", $(this).attr("alick"));
    })
//     //
}

function linkDepartment(Departments) {
    //得到的所有下一级
    if (Departments.listNodes.length == 0) return "";
    var str = '<ul>';
    var listNodes = Departments.listNodes;
    for (var i = 0; i < listNodes.length; i++) {

        var currNodeinList = listNodes[i].currNode;
        str = str + '<li onmousemove="reTree()" alick="showDepartmentInfo(' + currNodeinList.id + "," + currNodeinList.subCompanyId + ',$(this))" data-jstree=' + "'" + '{"opened":false}' + "'" + '>\n' +
            currNodeinList.fullname;
        var Department = listNodes[i];
        str = str + linkDepartment(Department);
        str = str + '</li>';
    }
    str = str + '</ul>';
    return str;
}

function showDepartmentInfo(departmentId, subCompanyId,_this) {
    $(".jstree-anchor").removeClass("treeHigtlight");
    _this.addClass("treeHigtlight");
    console.log(departmentId)
    $.ajax({
        url: "NGCindexOrganization/getDepartmentById",
        method: "post",
        async: true,
        data: {
            DepartmentId: departmentId
        }
        ,
        success: function (data) {
            console.log(data);
            $("#input-Default1").val(data.fullname)
            $("#input-Default2").val("")
            $("#input-Default3").val("")
            $("#input-Default4").val("")
            $("#input-Default5").val("")
        }
    });
    $("#departmentUserTable").empty();
    $.ajax({
        url: "NGCindexOrganization/getDepartmentUserDetail",
        method: "post",
        async: false,
        data: {
            DepartmentId: departmentId,
            SubCompanyId: subCompanyId
        },
        success: function (data) {
            console.log(data);
            userinfo = data;
            for (var i = 0; i < data.length; i++) {
                $("#departmentUserTable").append('<tr userid="' + data[i].userId + '">\n' +
                    '<th scope="row" >' + (Number(i) + 1) + '</th>\n' +
                    '<td>' + data[i].lastname + '</td>\n' +
                    '<td>' + data[i].workcode + '</td>\n' +
                    '<td>' + (data[i].mobile == null ? "" : data[i].mobile) + '</td>\n' +
                    '<td> <a onclick = "showDepartmentUserDetail(' + "'" + data[i].workcode + "'" + ',' + i + ') " \n' +
                    'type="button">' + '详情' + '</a></td>\n' +
                    '</tr>\n'
                );
                // '<td><a data-target=".bs-example-modal-lg" data-toggle="modal"\n' +
            }
        }
    });
}

function showDepartmentUserDetail(workcode, i) {
    console.log(userinfo[i]);
    $('#detail-name').val(userinfo[i].lastname)
    $('#detail-department').val(userinfo[i].departmentName)

    var leader = {lastname: ""};
    // $.ajax({
    //     url: "NGCindexOrganization/selectDepartmentUser",
    //     method: "post",
    //     async: false,
    //     data: {
    //         UserId: userinfo[i].mangerId
    //     },
    //     success: function (data) {
    //         leader = data
    //     }
    // });
    $.ajax({
        url: "userRole/getRolesByUsers",
        method: "post",
        async: false,
        data: {
            userId: userinfo[i].userId
        },
        success: function (data) {
            var role="";
            for(var c=0;c<data.length; c++){
                role=role+data[c].roleName+"   "

            }
            $("#detail—role").val(role);
        }
    });

    $('#detail-department-leader').val(userinfo[i].loginId)

    $('#detail-workcode').val(userinfo[i].workcode)
    $('#detail-mobile').val(userinfo[i].mobile)
    $('#detail-email').val(userinfo[i].email)
    $('#ryxx1').modal("show")

}

$('#simplesearch').click(function () {
    var name = $('#name2').val();
    if (name == "" || name == null) {
        return;
    }
    $.ajax({
        url: "NGCindexOrganization/simpleSearchDepartmentUser",
        method: "post",
        async: true,
        data: {
            username: name
        },
        success: function (data) {
            console.log(data);

            userinfo = data;
            if (data.length < 1) {
                alert("无结果");
                return;
            }
            if (data.length == 1) {
                openSearchingResult(data[0].userId);
            } else {
                $("#searchingResult").empty();
                showSearched(data)
                $("#ryxx2").modal("show")
            }

        }
    });
})
document.onkeyup = function(e) {
    e = window.event;
    if (e.keyCode == 13) {
        search1();
    }
}
function search1(){
    var name = $('#name2').val();
    if (name == "" || name == null) {
        return;
    }
    $.ajax({
        url: "NGCindexOrganization/simpleSearchDepartmentUser",
        method: "post",
        async: true,
        data: {
            username: name
        },
        success: function (data) {
            console.log(data);

            userinfo = data;
            if (data.length < 1) {
                alert("无结果");
                return;
            }
            if (data.length == 1) {
                openSearchingResult(data[0].userId);
            } else {
                $("#searchingResult").empty();
                showSearched(data)
                $("#ryxx2").modal("show")
            }

        }
    });
}

function showSearched(data) {
    for (var i = 0; i < data.length; i++) {
        $("#searchingResult").append('<tr>\n' +
            '<th scope="row">' + (Number(i) + 1) + '</th>\n' +
            '<td>' + data[i].lastname + '</td>\n' +
            '<td>' + data[i].workcode + '</td>\n' +
            '<td>' + (data[i].departmentName == null ? "" : data[i].departmentName) + '</td>\n' +
            '<td> <a onclick = "openSearchingResult(' + "'" + data[i].userId + "'" + ') " \n' +
            'type="button" data-dismiss="modal">' + '详情' + '</a></td>\n' +
            '</tr>\n'
        );
    }
}

function openSearchingResult(userId) {
    $(".jstree-node.jstree-open>i").click()
    $.ajax({
        url: "NGCindexOrganization/getDepartmentTreeNodeBYuserID",
        method: "post",
        async: true,
        data: {
            departmentUserID: userId
        },
        success: function (data) {

            while (data.listNodes.length >= 1) {
                var currNodeinList = data.currNode;
                $("[alick='showDepartmentInfo(" + currNodeinList.id + "," + currNodeinList.subCompanyId + ",$(this))']>i").click()

                data = data.listNodes[0]
            }
            reTree();
            $("[alick='showDepartmentInfo(" + data.currNode.id + "," + data.currNode.subCompanyId + ",$(this))']>a").click()

            //这里给高亮
            $("tr[userid='" + userId + "']").addClass("_heightLight");
            $("html,body").scrollTop($("tr[userid='" + userId + "']").offset().top)

        }
    });
}

$(".pickPerson").click(function () {
    var dataId = $(this).attr("data-id");

})
