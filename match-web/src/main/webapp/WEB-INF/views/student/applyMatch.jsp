
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
<mylink>
    <style>
        .addMembers{
            border:1px solid #ccc;
            padding-top:8px;
            padding-left:8px;
            padding-right:8px;
            border-radius:5px;
            margin-bottom:8px;
        }

        #upApply{
            width:800px;
        }
        .idontknowwhatitshouldbecalled{
            display:block;
        }
        .idontknowwhatitshouldbecalled input{
            border:1px solid #ccc;
            font-size:8px;
        }
    </style>
</mylink>
<body>
<mydiv>
        <form id="upApply" action="/upload/match/apply/form" method="post">
            <!-- 申请比赛，这个地方的需求比较复杂，因为对于比赛的申请，需要限制导师的人数，学生的人数，所以要使用ajax进行一些实现，并且要提供搜索的功能 -->
            <div class="input-group">
                <div class="input-group-addon">比赛名称</div>
                <input class="form-control" name="title" type="text" value="${info.name}" readonly/>
            </div>
            <div class="input-group">
                <div class="input-group-addon">项目名称</div>
                <input class="form-control" name="title" type="text" placeholder="请输入项目名称"/>
            </div>

            <input type="hidden" name="matchInfoId" value="${info.id}"/>

            <div class="addMembers">
                <div id="teacherMembers"  max="${info.teacherNum}">
                    指导教师 <span id="nowTeaNums" class="addMembersSpan">0/${info.teacherNum}:</span>
                    <!--添加的成员，关键是如何检索成员-->
                    <!--<span class="label label-info"> 样式为这样的-->
                </div>
                <!--通过用户名检索-->
                <div class="input-group" style="width:300px;margin-top:8px;">
                    <div class="input-group-addon"><span class="glyphicon glyphicon-user"></span></div>
                    <input type="text"  class="form-control" id="searchTeaWord" placeholder="请输入教师id"/>
                    <div class="input-group-addon" style="cursor: pointer;" id="addTeacher"><span class="glyphicon glyphicon-plus"></span></div>
                </div>
            </div>

            <div class="addMembers">
                <div id="members"  max="${info.memberNum}">
                    添加成员 <span id="nowNum" class="addMembersSpan">0/${info.memberNum}:</span>
                    <!--添加的成员，关键是如何检索成员-->
                    <!--<span class="label label-info"> 样式为这样的-->
                </div>
                <!--通过用户名检索-->
                <div class="input-group" style="width:300px;margin-top:8px;">
                    <div class="input-group-addon"><span class="glyphicon glyphicon-user"></span></div>
                    <input type="text"  class="form-control" id="searchWord" placeholder="请输入用户id"/>
                    <div class="input-group-addon" style="cursor: pointer;" id="addPerson"><span class="glyphicon glyphicon-plus"></span></div>
                </div>
            </div>


            <input type="submit" class="btn btn-danger" style="float:right" value="提交">
        </form>
    <!-- 上传申请表 -->
        <%--<form id="fileForm" enctype="multipart/form-data" method="post" action="/upload/match/apply">--%>
            <%--<input type="file" name="file" />--%>
            <%--<input type="submit" />--%>
        <%--</form>--%>




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
    <!-- 控制上传 -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.form/4.2.2/jquery.form.min.js" integrity="sha384-FzT3vTVGXqf7wRfy8k4BiyzvbNfeYjK+frTVqZeNDFl8woCbF0CYG6g2fMEFFo/i" crossorigin="anonymous"></script>
    <script>
        $(document).ready(function(){
            var users = [];   //保存当前已选的用户
            var teacherUsers = [];  //保存当前已选的教师用户
            var memberNum = ${info.memberNum};
            var teaMemberNum = ${info.teacherNum};
            var teacherInNum = ${info.teacherInNum}; //教师可以参加比赛的数量
            var matchInfoId = ${info.id};
            //TODO 这里还需要判断教师参加比赛的是否符合要求，但是忙于将大体框架做出，所以放在以后做
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
                            var inputHtml = '<div class="idontknowwhatitshouldbecalled"><span class="label label-info nowAdd"  style="cursor:pointer" id="' + user.id + '">' + user.username + ' ' + user.name + '</span>  <input type="hidden" name="memberIds" value="' + user.id + '">'
                            + '<input type="text" class="" placeholder="请输入成员分工" name="memberJob" /></div>';
                            if($.inArray(user.id,users) == -1){
                                $("#members").append(inputHtml);
                                users.push(user.id);
                                $("#nowNum").text(users.length +"/" + memberNum + ":");
                            }else{
                                showMsg("不能重复添加");
                            }
                        }
                    }
                });
            });

            $("#addTeacher").click(function(){
                //判断当前用户个数
                if(teacherUsers.length == teaMemberNum){
                    showMsg("教师个数已满");
                    return;
                }
                var searchWord = $("#searchTeaWord").val();
                //检索是否存在searchWord这个人，如果存在添加成功
                $.ajax({
                    url:"/user/teacher/"+searchWord,
                    type:'get',
                    success:function(res){
                        if(res == "null"){
                            showMsg("无此账户");
                        }else{
                            //用户搜索成功，将用户添加到..处
                            user = JSON.parse(res);
                            if(user.error != null){
                                showMsg(user.error);
                                return;
                            }
                            if(user.name == null)user.name = "未命名";
                            var inputHtml = '<span class="label label-info teaNowAdd" style="cursor:pointer" id="' + user.id + '">' + user.username + ' ' + user.name + '</span> <input type="hidden" name="teacherIds" value="' + user.id + '">';
                            if($.inArray(user.id,users) == -1){
                                $("#teacherMembers").append(inputHtml);
                                teacherUsers.push(user.id);
                                $("#nowTeaNums").text(teacherUsers.length +"/" + teaMemberNum + ":");
                            }else{
                                showMsg("不能重复添加");
                            }
                        }
                    }
                });
            });


            $("#members").on('click',".nowAdd",function(){
                var userId = $(this).attr("id");
                users.splice($.inArray(userId,users),1);
                $(this).parent().remove();
                $("#nowNum").text(users.length +"/" + memberNum + ":");
            });

            $("#teacherMembers").on('click',".teaNowAdd",function(){
                var userId = $(this).attr("id");
                teacherUsers.splice($.inArray(userId,teacherUsers),1);
                $(this).remove();
                $("#nowTeaNums").text(teacherUsers.length +"/" + teaMemberNum + ":");
            });

            $("#upApply").ajaxForm(function(res){
                var json = JSON.parse(res);
                if(json["success"] == "true"){
                    //json["itemId"]
                    //跳转到itemId对应的上传申请表的页面
                    alert("申请成功，请上传申请表，仅允许.doc,.docx结尾的word文档");
                    location.href = "/student/matchs/upapply/" + json["itemId"];
                }else{
                    showMsg(json["error"]);
                }
            });


        });
    </script>
</myscript>
</body>
</html>
