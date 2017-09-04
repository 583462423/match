
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
    <title>主页</title>
</head>
<body>
<mydiv>
    <div style="width: 100%;height: 100%;">
        后期把只有申请阶段的显示出来，其他阶段不能再申请了。
        <!-- 显示table，在最后显示报名选项，注意，报名，自己就是队长，不允许队员选队长，只能队长选队员！！记住了 -->
        <table class="table table-bordered">
            <thead>
            <tr>
                <th class="col-sm-2">比赛名称</th>
                <th class="col-sm-2">团队人数</th>
                <th>报名</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${info}" var="item">
                <tr>
                    <td>${item.name}</td>
                    <td>${item.memberInNum}</td>
                    <td><a href="/student/matchs/apply/${item.id}">报名</a></td>
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

        });
    </script>
</myscript>
</body>
</html>
