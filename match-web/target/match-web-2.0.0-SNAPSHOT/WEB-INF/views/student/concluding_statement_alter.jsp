
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

            .addMembers{
                border:1px solid #ccc;
                padding:8px;
                border-radius:5px;
                margin-bottom:8px;
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
        <form action="/student/match/concluding/report/update" method="post" id="updateForm">
        <c:if test="${isNull == 'true'}">
            <!-- 说明之前没有上传过中期检查表-->

            <input name="matchItemId" type="hidden" value="${matchItemId}"/>
            <div class="input-group">
                <div class="input-group-addon">完成情况</div>
                <textarea style="height: 150px;" name="completionSituation" class="form-control"></textarea>
            </div>
            <div class="input-group">
                <div class="input-group-addon">研究结果</div>
                <textarea style="height: 150px;" name="researchResult" class="form-control"></textarea>
            </div>
            <div class="input-group">
                <div class="input-group-addon">个人评价</div>
                <textarea style="height: 150px;" name="selfJudge" class="form-control"></textarea>
            </div>
            <div class="input-group">
                <div class="input-group-addon">经费情况</div>
                <textarea style="height: 150px;" name="priceSituation" class="form-control"></textarea>
            </div>
            <%--<input name="viewOfTeacher" placeholder="教师评价" />--%>
            <%--<input name="levelByTeacher" placeholder="教师评级" />--%>
            <%--<input name="viewOfAcademy" placeholder="学院评价" />--%>
            <%--<input name="viewOfSuper" placeholder="学校评价" />--%>
        </c:if>
        <c:if test="${isNull != 'true'}">
            <!-- 上传过检查表-->
            <input name="matchItemId" type="hidden" value="${matchItemId}"/>

            <div class="input-group">
                <div class="input-group-addon">完成情况</div>
                <textarea style="height: 150px;" name="completionSituation" class="form-control">${concludingStatement.completionSituation}</textarea>
            </div>
            <div class="input-group">
                <div class="input-group-addon">研究结果</div>
                <textarea style="height: 150px;" name="researchResult" class="form-control">${concludingStatement.researchResult}</textarea>
            </div>
            <div class="input-group">
                <div class="input-group-addon">个人评价</div>
                <textarea style="height: 150px;" name="selfJudge" class="form-control">${concludingStatement.selfJudge}</textarea>
            </div>
            <div class="input-group">
                <div class="input-group-addon">经费情况</div>
                <textarea style="height: 150px;" name="priceSituation" class="form-control">${concludingStatement.priceSituation}</textarea>
            </div>
            <%--<input name="viewOfTeacher" placeholder="教师评价" value="${middleCheck.viewOfTeacher}"/>--%>
            <%--<input name="levelByTeacher" placeholder="教师评级" value="${middleCheck.levelByTeacher}"/>--%>
            <%--<input name="viewOfAcademy" placeholder="学院评价" value="${middleCheck.viewOfAcademy}"/>--%>
            <%--<input name="viewOfSuper" placeholder="学校评价" value="${middleCheck.viewOfSuper}"/>--%>
        </c:if>
        <input type="submit" class="btn btn-success" value="保存">
        </form>

        <hr>
        <div class="changeDiv">
            <div class="alert alert-danger" role="alert">是否变更成员？（变更成员后，点击保存按钮不会保存成功，只有提交后审核成功才会变更成功）</div>
            <button id="changeMemberBtn" class="btn btn-danger">是</button>

            <div id="hiddenDiv" style="display:none">
                <button id="cancel" class="btn btn-danger">取消变更</button>
                <input type="hidden" id="isChange" value="0"/> <!-- 0 表示不变更，1表示变更 -->
                <!-- 显示以前的成员,需要给对应的matchInfo信息 -->
                <div class="addMembers">
                    <div id="members" max="${info.memberNum}">
                        <!--添加的成员，关键是如何检索成员-->
                        <!--通过用户名检索-->


                        <!--通过用户名检索-->
                        <div class="input-group" style="width:300px;">
                            <div class="input-group-addon"><span class="glyphicon glyphicon-user"></span></div>
                            <input type="text" id="searchWord" class="form-control" placeholder="请输入用户id"/>
                            <div class="input-group-addon" style="cursor: pointer;" id="addPerson"><span class="glyphicon glyphicon-plus"></span></div>
                        </div>

                        成员 <span id="nowNum" class="addMembersSpan">0/${info.memberNum}:</span>
                        <c:forEach items="${members}" var="item">
                            <div>
                                <span class="label label-info nowAdd"  style="cursor:pointer" id="${item.id}">${item.username}&nbsp;${item.name}</span>
                                <input type="hidden" name="memberIds" value="${item.id}">
                            </div>
                        </c:forEach>
                    </div>
                </div>
                <br>
                <!-- 显示添加成员按钮 -->
            </div>
        </div>
        <hr>
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
            var users = [${nowMembers}];   //保存当前已选的用户
            var memberNum = ${info.memberNum};

            //给添加的成员设置点击事件，点击后删除对应的member
            $("#members").on('click',".nowAdd",function(){
                var userId = parseInt($(this).attr("id"));
                users.splice($.inArray(userId,users),1);
                $(this).parent().remove();
                $("#nowNum").text(users.length +"/" + memberNum + ":");
            });



            $("#addPerson").click(function(){
                //判断当前用户个数
                if(users.length == memberNum){
                    showMsg("用户个数已满");
                    return;
                }
                var searchWord = $("#searchWord").val();
                //检索是否存在searchWord这个人，如果存在添加成功
                $.ajax({
                    url:"/user/"+searchWord,
                    type:'get',
                    success:function(res){
                        if(res == "null"){
                            showMsg("无此账户");
                        }else{
                            //用户搜索成功，将用户添加到..处
                            var user = JSON.parse(res);
                            if(user.error != null){
                                showMsg(user.error);
                                return;
                            }
                            if(user.name == null)user.name = "未命名";
                            var inputHtml = ' <div><span class="label label-info nowAdd"  style="cursor:pointer" id="' + user.id + '">' + user.username + ' ' + user.name + '</span>  <input type="hidden" name="memberIds" value="' + user.id + '">'
                                + '</div>';
                            var id = parseInt(user.id);
                            if($.inArray(id,users) == -1){
                                $("#members").append(inputHtml);
                                users.push(id);
                                $("#nowNum").text(users.length +"/" + memberNum + ":");
                            }else{
                                showMsg("不能重复添加");
                            }
                        }
                    }
                });
            });


            $("#changeMemberBtn").click(function(){
                $(this).css("display","none");
                $("#hiddenDiv").css("display","block");
                $("#isChange").val(1);
            });

            //取消变更
            $("#cancel").click(function(){
               $("#changeMemberBtn").css("display","block");
               $("#hiddenDiv").css("display","none");
               $("#isChange").val(0);
            });


            $("#updateForm").ajaxForm(function(res){
                var json = JSON.parse(res);
                if(json["success"] == "true"){
                    showMsg("保存成功");
                    setTimeout("location.reload()",1000);
                }else{
                    showMsg(json["error"]);
                }
            })

            $("#submitToNext").click(function(){
                //提交到下一个阶段,同时提交成员变更表
                var data = {
                    matchItemId:${matchItemId},
                    memberIds:users,
                    isChange:$("#isChange").val()
                };
                $.ajax({
                    url:"/student/match/concluding/report/submit",
                    data:data,
                    type:'post',
                    success:function(res){
                        var json = JSON.parse(res);
                        if(json["success"] == "true"){
                            showMsg("提交成功,请耐心等待审核");
                            setTimeout("location.href='/student/match/concluding'",2000);
                        }else{
                            showMsg(json["error"]);
                        }
                    }
                });
            });
        });
    </script>
</myscript>
</body>
</html>
