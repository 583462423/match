
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
    <title>评委管理</title>
</head>
<body>
<mydiv>
    <div style="width: 100%;height: 100%;">
        <input type="text" id="academyName" style="width:200px;display:inline" placeholder="请输入学院类型" class="form_datetime form-control"/>
        <select id="academyType">
            <option disabled selected value>请选择学院类型</option>
            <c:forEach items="${type}" var="item">
                <option value="${item.key}">${item.value}</option>
            </c:forEach>

        </select>
        <button id="addBtn" class="btn btn-danger">添加</button>
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
            //TODO 2017.7.20 做到这了，接下来做添加的工作
            $("#addBtn").click(function(){
                var name = $("#academyName").val();
                var type = $("#academyType").val();

                if(type == ""){
                    alert("请选择类型");
                    return;
                }
                var data = {
                    name:name,
                    type:type
                }

                $.ajax({
                    url:"/admin/academy/add",
                    data:data,
                    type:'post',
                    success:function(json){
                        alert(json["message"]);
                    }
                });
            });
        });
    </script>
</myscript>
</body>
</html>
