
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
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>比赛名称</th>
                <th>比赛分数</th>
                <th>操作(有需求的话就操作)</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${matchItemWithScore}" var="item">
                <tr>
                    <td>${item.matchItem.title}</td>
                    <td>${item.score}</td>
                    <td>删除等，排除名单等</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <input type="number" placeholder="请输入入选个数" id="num"/>
        <button id="submit" class="btn btn-danger">提交</button>
    </div>

    <!--模态框，显示详细信息 不用的时候删除-->
    <div class="scoreModal modal fade" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">提示</h4>
                </div>
                <div class="modal-body">
                    <span id="tip"></span>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" id="confirmEndScore" class="btn btn-primary">确认</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    <!--模态框结束-->
</mydiv>

<myscript>
    <script>
        $(document).ready(function(){
            $("#submit").click(function(){
                var num = $("#num").val();
                var data = {
                    num:num,
                    matchInfoId:${matchInfoId}
                }
                $.ajax({
                    url:"/admin/awards/award",
                    type:'post',
                    data:data,
                    success:function(res){
                        var json = JSON.parse(res);
                        if(json["success"] == "true"){
                            alert("提交成功");
                        }else alert(json["error"]);
                    }
                });
            });
        });
    </script>
</myscript>
</body>
</html>
