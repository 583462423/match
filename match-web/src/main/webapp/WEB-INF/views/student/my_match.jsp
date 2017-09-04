
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
        <!-- 显示当前的比赛，信息有比赛的名称，比赛的分工，比赛当前到哪个阶段了，比赛是否过期等信息 -->
        <table class="table table-bordered">
            <thead>
            <tr>
                <th class="col-sm-2">比赛名称</th>
                <th class="col-sm-2">分工</th>
                <th class="col-sm-2">阶段</th>
                <th class="col-sm-2">有效期</th>
                <th>详情</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${res}" var="item">
                <tr>
                    <td>${item.title}</td>
                    <td>${item.job}</td>
                    <td>${item.stage}</td>
                    <td>${item.expire}</td>
                    <td>
                        <c:if test="${item.upApply == 'true'}">
                            <a href="/student/matchs/upapply/${item.itemId}"> 上传申请表</a>
                            
                        </c:if>
                    </td>
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
