
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
    <mylink>
        <style>
            textarea {
                resize:none;
                width:100%;
                height:150px;
            }

            .changeDiv{
                border:1px solid #ccc;
                box-shadow: 0px 0px 2px 2px #ccc;
                padding:8px;
                border-radius:5px;
                margin-bottom:8px;
            }
        </style>
    </mylink>
</head>
<body>
<mydiv>
    <div style="width: 100%;height: 100%;">
        <form action="/teacher/match/middle/report/update" method="post" id="updateForm">
            <!-- 上传过检查表-->
            <input type="hidden" name="matchItemId" value="${middleCheck.matchItemId}"/>
            <div class="input-group">
                <div class="input-group-addon">与导师交流情况</div>
                <textarea readonly>${middleCheck.comuWithTeacher}</textarea>
            </div>
            <div class="input-group">
                <div class="input-group-addon">项目结果</div>
                <textarea readonly >${middleCheck.projectResult}</textarea>
            </div>
            <div class="input-group">
                <div class="input-group-addon">与期望结果的差异</div>
                <textarea readonly>${middleCheck.isExpect}</textarea>
            </div>
            <div class="input-group">
                <div class="input-group-addon">花费情况</div>
                <textarea readonly>${middleCheck.costSituation}</textarea>
            </div>
            <div class="input-group">
                <div class="input-group-addon">学生观点</div>
                <textarea readonly>${middleCheck.pointByStudent}</textarea>
            </div>
            <div class="input-group">
                <div class="input-group-addon">教师观点</div>
                <textarea readonly>${middleCheck.pointByTeacher}</textarea>
            </div>
            <div class="input-group">
                <div class="input-group-addon">后续计划</div>
                <textarea readonly>${middleCheck.followPlan}</textarea>
            </div>
            <div class="input-group">
                <div class="input-group-addon">后续重点</div>
                <textarea readonly>${middleCheck.followPoint}</textarea>
            </div>
            <div class="input-group">
                <div class="input-group-addon">教师评价</div>
                <textarea name="viewOfTeacher" placeholder="请填写教师评价">${middleCheck.viewOfTeacher}</textarea>
            </div>
            <div class="input-group">
                <div class="input-group-addon">教师评级</div>
                <textarea name="levelByTeacher" placeholder="请填写教师评级">${middleCheck.levelByTeacher}</textarea>
            </div>

            <%--<input name="viewOfAcademy" placeholder="学院评价" value="${middleCheck.viewOfAcademy}"/>--%>
            <%--<input name="viewOfSuper" placeholder="学校评价" value="${middleCheck.viewOfSuper}"/>--%>
            <input type="submit" class="btn btn-success" value="保存">
        </form>

        <!-- 显示成员变更表-->


        <c:if test="${isChange == '1'}">
            <!--进行变更-->
            <hr>
            <div class="changeDiv">
                <div class="alert alert-danger" role="alert">在提交前，请确定是否允许成员变更，默认不允许</div>
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
            </div>
            <hr>
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
                    showMsg("保存成功");
                    setTimeout("location.reload()",1000);
                }else{
                    showMsg(json["error"]);
                }
            });

            $("#submitToNext").click(function(){
                //提交到下一个阶段
                var data = {
                    matchItemId:${middleCheck.matchItemId}
                };
                $.ajax({
                    url:"/teacher/match/middle/report/submit",
                    data:data,
                    type:'post',
                    success:function(res){
                        var json = JSON.parse(res);
                        if(json["success"] == "true"){
                            showMsg("提交成功,请耐心等待审核");
                            setTimeout("location.href='/teacher/match/middle';",2000);
                        }else{
                            showMsg(json["error"]);
                        }
                    }
                });
            });

            $("#passChange").click(function(res){
                var transferMemberId = $(this).attr("data");
                var data = {
                    transferMemberId:transferMemberId
                }
                $.ajax({
                    url:'/teacher/match/middle/change/submit',
                    data:data,
                    type:'post',
                    success:function(res){
                        var json = JSON.parse(res);
                        if(json["success"] == "true"){
                            showMsg("成功");
                            setTimeout("location.reload()",1000);
                        }
                    }
                });
            });
        });
    </script>
</myscript>
</body>
</html>
