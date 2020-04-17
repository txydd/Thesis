$(document).ready(function () {
    showOrganization();
});
function showOrganization() {
    $.ajax({
        url: "../NGCindexOrganization/getDepartmentTreeNode",
        method: "post",
        async: false,
        data: {}
        ,
        success: function (data) {
            $("#basicTree").empty();
            //得到的所有下一级
            var currNodeinList = data.currNode;
            var str = '<ul ><li onclick="reTree()" alick="showDepartmentInfo(' + currNodeinList.id + "," + currNodeinList.subCompanyId + ')" data-jstree=' + "'" + '{"opened":true}' + "'" + '>\n' +
                currNodeinList.fullname;
            str = str + linkDepartment(data) + '</li></ul>\n';
            $("#basicTree").append(str);
        }
    })

}

// // refresh
// function refresh() {
//     $.ajax({
//         url: "NGCindexOrganization/refresh",
//         method: "post",
//         async: false,
//         data: {}
//         ,
//         success: function (data) {
//             alert(data.message)
//             window.location.reload();
//         }
//     })
//
// }

function linkDepartment(Departments) {
    //得到的所有下一级
    if (Departments.listNodes.length == 0) return "";
    var str = '<ul>';
    var listNodes = Departments.listNodes;
    for (var i = 0; i < listNodes.length; i++) {

        var currNodeinList = listNodes[i].currNode;
        str = str + '<li onmousemove="reTree()" alick="showDepartmentInfo(' + currNodeinList.id + "," + currNodeinList.subCompanyId + ')" data-jstree=' + "'" + '{"opened":false}' + "'" + '>\n' +
            currNodeinList.fullname;
        var Department = listNodes[i];
        str = str + linkDepartment(Department);
        str = str + '</li>';
    }
    str = str + '</ul>';
    return str;
}

function reTree() {
    //    //
    $("#basicTree").find("li").each(function () {
        $(this).find("a").attr("onclick", $(this).attr("alick"));
    })
//     //
}
function showDepartmentInfo(departmentId, subCompanyId) {
    if(departmentId==null||departmentId==""||departmentId=="0") return;
    console.log(departmentId)
    // $.ajax({
    //     url: "../NGCindexOrganization/getDepartmentById",
    //     method: "post",
    //     async: true,
    //     data: {
    //         DepartmentId: departmentId
    //     }
    //     ,
    //     success: function (data) {
    //         console.log(data);
    //         $("#input-Default1").val(data.fullname)
    //         $("#input-Default2").val("无此字段")
    //         $("#input-Default3").val("无此字段")
    //         $("#input-Default4").val("无此字段")
    //         $("#input-Default5").val("无此字段")
    //     }
    // });
    $("#departmentUserTable").empty();
    var guid =$("#guid").val();
    var num=1;
    $.ajax({
        url: "../NGCindexOrganization/getDepartmentUserByRoleDetail",
        method: "post",
        async: false,
        data: {
            DepartmentId: departmentId,
            SubCompanyId: subCompanyId,
            RoleGuid:guid
        },
        success: function (data) {
            console.log(data);
            // userinfo = data;
            for ( var i = 0; i < data.length; i++) {
                $("#departmentUserTable").append(
                    '<tr userid="' + data[i].userId + '">\n' +
                    '<th scope="row"> '+ (Number(num)) +'</th>\n'+
                    '<td>' + data[i].lastname +'</td>\n'+
                    '<td width="90"><a type="button" onclick="userAddRole('+"'"+data[i].userId+"'"+','+"'"+guid +"'"+','+"'"+ departmentId +"'"+','+"'"+ subCompanyId+"'"+')">添加</a></td>\n'+
                    '</tr>\n'
                );
                // '<td><a data-target=".bs-example-modal-lg" data-toggle="modal"\n' +
                num=num+1;
            }
        }
    });
    $.ajax({
        url: "../userRole/getDepartmentHadRoleUsers",
        method: "post",
        async: true,
        data: {
            DepartmentId: departmentId,
            SubCompanyId: subCompanyId,
            RoleGuid:guid
        },
        success: function (data) {
            for (var i = 0; i < data.length; i++) {
                $("#departmentUserTable").append(
                    '<tr userid="' + data[i].userId + '">\n' +
                    '<th scope="row"> '+ (num) +'</th>\n'+
                    '<td>' + data[i].lastname +'</td>\n'+
                    '<td width="90"><a type="button" onclick="userDeleteRole('+"'"+data[i].userRoleGuid+"'"+','+"'"+guid+"'"+','+"'"+ data[i].departmentId +"'"+','+"'"+ data[i].subCompanyId1+"'"+')">删除</a></td>\n'+
                    '</tr>\n'
                );
                num+=1
                // '<td><a data-target=".bs-example-modal-lg" data-toggle="modal"\n' +
            }
        }
    });

}
function userAddRole(userId,roleguid,departmentId,subCompanyId) {

    $.ajax({
        url: "../userRole/addUserRole",
        method: "post",
        async: true,
        data: {
            userId: userId,
            RoleGuid:roleguid
        },
        success: function (data) {
            $.alert(data,'提示');
            DPID=departmentId;
            SCID=subCompanyId;
            showDepartmentInfo(departmentId,subCompanyId);
        }
    });

}
function showUserView(RoleGuid) {
    $("#roleUsersTable").empty();
    $.ajax({
        url: "../userRole/getRoleUsers",
        method: "post",
        async: true,
        data: {
            RoleGuid:RoleGuid
        },
        success: function (data) {
            for (var i = 0; i < data.length; i++) {
                $("#roleUsersTable").append(
                    '<tr>\n' +
                    '<th scope="row"> '+ (Number(i) + 1) +'</th>\n'+
                    '<td>' + data[i].lastname +'</td>\n'+
                    '<td width="90"><a type="button" onclick="userDeleteRole('+"'"+data[i].userRoleGuid+"'"+','+"'"+RoleGuid+"'"+','+"'"+ data[i].departmentId +"'"+','+"'"+ data[i].subCompanyId1+"'"+')">删除</a></td>\n'+
                    '</tr>\n'
                );
                // '<td><a data-target=".bs-example-modal-lg" data-toggle="modal"\n' +
            }
        }
    });
}
function userDeleteRole(userRoleGuid,RoleGuid,departmentId,subCompanyId) {
    $.ajax({
        url: "../userRole/deleteUserRole",
        method: "post",
        async: true,
        data: {
            UserRoleGuid: userRoleGuid
        },
        success: function (data) {
            $.alert(data,'提示');
            showUserView(RoleGuid);
            showDepartmentInfo(departmentId,subCompanyId);
        }
    });

}
//
// function showDepartmentDetail(workcode, i) {
//     console.log(userinfo[i]);
//     $('#detail-name').val(userinfo[i].lastname)
//     $('#detail-department').val(userinfo[i].departmentName)
//
//     var leader={lastname:"OA系统没有这个字段"};
//     $.ajax({
//         url: "NGCindexOrganization/selectDepartmentUser",
//         method: "post",
//         async: false,
//         data: {
//             UserId: userinfo[i].mangerId
//         },
//         success: function (data) {
//             leader=data
//         }
//     } );
//
//
//     $('#detail-department-leader').val(leader.lastname)
//
//     $('#detail-workcode').val(userinfo[i].workcode)
//     $('#detail-mobile').val(userinfo[i].mobile)
//     $('#detail-email').val(userinfo[i].email)
//     $('.bs,.example,.modal,.lg').modal("show")
//
// }
$('#simplesearchUser').click(function () {
    var name = $('#name3').val();
    if (name == "" || name == null) {
        return;
    }
    $.ajax({
        url: "../NGCindexOrganization/simpleSearchDepartmentUser",
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
function openSearchingResult(userId) {
    //关了全部点开的
    $(".jstree-node.jstree-open>i").click()
    $.ajax({
        url: "../NGCindexOrganization/getDepartmentTreeNodeBYuserID",
        method: "post",
        async: true,
        data: {
            departmentUserID: userId
        },
        success: function (data) {

            while (data.listNodes.length >= 1) {
                var currNodeinList = data.currNode;
                $("[alick='showDepartmentInfo(" + currNodeinList.id + "," + currNodeinList.subCompanyId + ")']>i").click()

                data = data.listNodes[0]
            }
            reTree();
            $("[alick='showDepartmentInfo(" + data.currNode.id + "," + data.currNode.subCompanyId + ")']>a").click()

            //这里给高亮
            $("[userid='" + userId + "']").addClass("_heightLight");
            $("#scrollUser").scrollTop($("tr[userid='" + userId + "']").offset().top-250)
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

