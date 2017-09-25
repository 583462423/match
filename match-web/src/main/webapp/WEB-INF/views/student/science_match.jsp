
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
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${items}" var="item">
                <tr>
                    <td>${item.title}</td>
                    <td><button class="addLog" data="${item.id}">添加科研日志</button>  // <a href="/student/science/all/${item.id}">查看当前科研日志</a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <!--模态框，显示详细信息 不用的时候删除-->
    <div class="addLogModal modal fade" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">类别添加</h4>
                </div>
                <div class="modal-body">
                    <input type="text" id="title" class="form-control" placeholder="请输入标题"/>
                    <textarea id="content" placeholder="请输入日志内容"></textarea>
                    <input type="text" readonly id="time" placeholder="请输入日期" class="form_datetime form-control"/>
                    <input type="number" id="startTime" placeholder="请输入几点开始"/>
                    <input type="number" id="endTime" placeholder="请输入几点结束"/>
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

            $(".form_datetime").datetimepicker({
                language:  'zh-CN',
                format: "yyyy-MM-dd",
                autoclose: true,
                todayBtn: true,
                pickerPosition: "bottom-left",
                minView:2     //仅仅显示日期
            });

            var nowAddId = "";
            $(".addLog").click(function(){
                nowAddId = $(this).attr("data");
                $(".addLogModal").modal("show");
            });

            $("#add").click(function(){
                //添加日志
                if(nowAddId == "")return;

                var title = $("#title").val();
                var content = $("#content").val();
                var time = $("#time").val();
                var startTime = $("#startTime").val();
                var endTime = $("#endTime").val();

                var data = {
                    title:title,
                    content:content,
                    time:time,
                    startTime:startTime,
                    endTime:endTime
                }

                $.ajax({
                    url:"/student/science/log/add/" + nowAddId,
                    data:data,
                    type:'post',
                    success:function(res){
                        var json = JSON.parse(res);
                        if(json["success"] == 'true'){
                            alert("添加成功");
                        }
                    }
                });
            });
        });
    </script>
</myscript>
</body>
</html>
