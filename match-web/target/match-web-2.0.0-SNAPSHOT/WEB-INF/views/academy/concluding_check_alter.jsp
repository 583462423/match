
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
        <form action="/academy/match/concluding/report/update" method="post" id="updateForm">
            <!-- 上传过检查表-->
            <input name="matchItemId" type="hidden" value="${concludingStatement.matchItemId}"/>
            <input name="completionSituation" placeholder="完成情况" value="${concludingStatement.completionSituation}" />
            <input name="researchResult" placeholder="研究结果"  value="${concludingStatement.researchResult}" />
            <input name="selfJudge" placeholder="个人评价" value="${concludingStatement.selfJudge}" />
            <input name="priceSituation" placeholder="经费情况" value="${concludingStatement.priceSituation}" />
            <input name="viewOfTeacher" placeholder="教师评价" value="${concludingStatement.viewOfTeacher}"/>
            <input name="viewOfAcademy" placeholder="学院评价" value="${concludingStatement.viewOfAcademy}"/>
            <input type="submit" value="保存">
        </form>
        <!-- 显示成员变更表-->
        <c:if test="${isChange == '0'}">
            <!-- 未进行变更 -->
        </c:if>
        <c:if test="${isChange == '1'}">
            <!--进行变更-->
            原先队员:
            <c:forEach items="${fromMembers}" var="item">
                <span class="label label-info"  style="cursor:pointer" >${item.username}&nbsp;${item.name}</span>
            </c:forEach>
            变更队员:
            <c:forEach items="${toMembers}" var="item">
                <span class="label label-info"  style="cursor:pointer" >${item.username}&nbsp;${item.name}</span>
            </c:forEach>
            <button id="passChange" class="btn btn-danger" data="${transferMemberId}">允许变更</button>
            <br>
        </c:if>

        <button id="submitToNext" class="btn btn-info" data="${matchItemId}">提交</button>
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
            $("#updateForm").ajaxForm(function(res){
                var json = JSON.parse(res);
                if(json["success"] == "true"){
                    alert("保存成功");
                }else{
                    alert(json["error"]);
                }
            });

            $("#submitToNext").click(function(){
                //提交到下一个阶段
                var data = {
                    matchItemId:${concludingStatement.matchItemId}
                };
                $.ajax({
                    url:"/academy/match/concluding/report/submit",
                    data:data,
                    type:'post',
                    success:function(res){
                        var json = JSON.parse(res);
                        if(json["success"] == "true"){
                            alert("提交成功,请耐心等待审核");
                        }else{
                            alert(json["error"]);
                        }
                    }
                });
            });

            $("#passChange").click(function(res){
                var transferMemberId = $(this).attr("data");
                var data = {
                    transferMemberId:'${transferMemberId}'
                };
                $.ajax({
                    url:'/academy/match/concluding/change/submit',
                    data:data,
                    type:'post',
                    success:function(res){
                        var json = JSON.parse(res);
                        if(json["success"] == "true"){
                            alert("成功");
                        }
                    }
                });
            });
        });
    </script>
</myscript>
</body>
</html>
