
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
        还剩 ${last} 个比赛未审核
        <table class="table table-bordered">
            <thead>
            <tr>
                <th  class="col-sm-2">勾选</th>
                <th>比赛名称</th>
                <th>已评分评委个数</th>
                <th>当前生成的评委个数</th>
                <th>当前评分平均分</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${itemTOS}" var="itemTO">
                <tr>
                    <td><input type="checkbox" name="itemId" value="${itemTO.matchItem.id}"/> </td>
                    <td>${itemTO.matchItem.title}</td>
                    <td>${itemTO.raterCnt}</td>
                    <td>${itemTO.doneRater}</td>
                    <td>${itemTO.average}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <input type="text" readonly id="startTime" style="width:200px;display:inline" placeholder="请输入开始时间" class="form_datetime form-control"/>
        <input type="text" readonly id="endTime" style="width:200px;display:inline" placeholder="请输入结束时间" class="form_datetime form-control"/>
        <input type="number" id="cnt" style="width:200px;display:inline" placeholder="请输入评委个数" class="form-control"/>

        <button id="startGen" class="btn btn-danger">开始生成</button>
        <button id="endScoreBtn" class="btn btn-danger" style="float: right">评分结束</button>

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
            if(${last} > 0){
                alert("还有${last}个比赛，未审核，请等待！");
                history.back();
            }

            $(".form_datetime").datetimepicker({
                language:  'zh-CN',
                format: "yyyy-MM-dd hh:ii",
                autoclose: true,
                todayBtn: true,
                pickerPosition: "bottom-left"
            });

            $("#startGen").click(function() {
                //开始生成评委，生成前，调用模态框显示所有要生成的项目，并点击确认后开始生成
                //首先遍历选中的item，然后显示
                var itemIds = $("input:checkbox[name='itemId']:checked").map(function (index, elem) {
                    return $(elem).val();
                }).get().join(',');
                if (itemIds == "") {
                    alert("未勾选比赛");
                    return;
                }

                var startTime = $("#startTime").val();
                var endTime = $("#endTime").val();
                var cnt = $("#cnt").val();
                //alert("选中的checkbox的值为："+text);
                //text为:2,3
                //接着发送数据,后续有需求的话可调用模态框进行提醒
                var data = {
                    matchItem: itemIds,
                    startTime: startTime,
                    endTime: endTime,
                    cnt: cnt,
                    matchInfoId:${matchInfoId}
                }
                $.ajax({
                    url: "/admin/rater/gen",
                    data: data,
                    type: 'post',
                    success: function (res) {
                        var json = JSON.parse(res);
                        if (json["success"] == "true") {
                            alert("创建成功");
                            //TODO 有需求的话，调用模态狂显示添加成功的人
                        }
                    }
                });
            });

            $("#endScoreBtn").click(function(){
                //结束评分
                var notDoneCnt = ${notDoneCnt};
                var last = ${last};
                if(notDoneCnt > 0){
                    $("#tip").text("当前还有${notDoneCnt}个比赛未被评分，确定结束评分？");
                }else{
                    $("#tip").text("确认结束比赛?");
                }
                $(".scoreModal").modal("show");
            });

            $("#confirmEndScore").click(function(){
                var data = {
                    infoId:${matchInfoId}
                }
                $.ajax({
                    url:"/admin/rater/score/end",
                    type:'post',
                    data:data,
                    success:function(res){
                        var json = JSON.parse(res);
                        if(json["success"] == "true"){
                            alert("修改成功");
                        }else alert(json["error"]);
                    }
                });
            });

        });
    </script>
</myscript>
</body>
</html>
