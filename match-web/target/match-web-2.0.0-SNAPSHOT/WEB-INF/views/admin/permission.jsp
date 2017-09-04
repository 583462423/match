
<%--
  Created by IntelliJ IDEA.
  User: qxg
  Date: 17-7-5
  Time: 下午9:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/base/base.jsp"%>
<html>
<head>
    <title>权限管理</title>
</head>
<body>
<mydiv>
    <div style="width: 100%;height: 100%;">
        <table class="table table-bordered">
            <thead>
            <tr>
                <th class="col-sm-4">角色名称</th>
                <th class="col-sm-4">权限管理</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${wrapper.roleTOS}" var="item">
                <tr>
                    <form class="form${item.id}" action="/permission/alter" method="post">
                        <input type="hidden" name="id" value="${item.id}" />
                        <td>${item.des}</td>
                        <td>
                            <select name="permissionIds" id="permissionIds${item.id}" class="selectpicker show-tick form-control" multiple>
                                <c:forEach items="${wrapper.allPermissions}" var="permission">
                                    <c:choose>
                                        <c:when test="${fn:contains(item.ps,permission.id)}">
                                            <option value="${permission.id}" selected>${permission.des}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${permission.id}">${permission.des}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
                        </td>
                        <td>
                            <a style="cursor: pointer" class="alter" data-id="${item.id}"><span class="label label-info"><i class="glyphicon glyphicon-remove"></i>修改</span></a>
                        </td>
                    </form>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <!--模态框，显示详细信息 不用的时候删除-->
    <div class="match_info modal fade" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">类别添加</h4>
                </div>
                <div class="modal-body">
                    <input type="text" id="name" class="form-control" placeholder="请输入添加的比赛类别"/>
                    <select id="type">

                    </select>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" id="add" class="btn btn-primary">创建</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    <!--模态框结束-->
</mydiv>

<myscript>
    <script>
        $(document).ready(function(){

            $(".alter").click(function () {
                var id = $(this).attr("data-id");
                var permissionIds = $("#permissionIds"+id).val();
                var data = {
                    id:id,
                    permissionIds:permissionIds
                }
                var action = $(".form"+id).attr("action");
                $.ajax({
                    url:action,
                    type:'post',
                    data:data,
                    success:function(json){
                        if(json["success"] == true) {
                            alert("修改成功");
                            window.location.href = "/permission/all";
                        }
                    }
                });
            });
        });
    </script>
</myscript>
</body>
</html>
