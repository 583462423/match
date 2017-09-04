<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: qxg
  Date: 17-6-30
  Time: 下午5:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<mydiv>
<form id="myForm">
    <div class="input-group">
        <div class="input-group-addon">比赛名称</div>
        <input class="form-control"  type="text" id="name" name="name" />
    </div>

    <div class="input-group">
        <div class="input-group-addon">比赛类别</div>
        <select id="type1" class="form-control" name="type1"></select>
        <div class="input-group-addon">类别分类</div>
        <select id="type2" class="form-control" name="type2"></select>
        <div class="input-group-addon">比赛级别</div>
        <select id="level" class="form-control" name="level">
            <option value="1">国赛</option>
            <option value="2">省级</option>
            <option value="3">校级</option>
            <option value="4">院级</option>
        </select>
    </div>

    <div class="input-group">
        <div class="input-group-addon">队长个数</div>
        <input class="form-control" id="leaderNum" type="number" name="leaderNum" />
        <div class="input-group-addon">队长可参与个数</div>
        <input class="form-control" id="leaderInNum" type="number" name="leaderInNum" />
    </div>

    <div class="input-group">
        <div class="input-group-addon">成员个数</div>
        <input class="form-control" id="memberNum" type="number" name="memberNum" />
        <div class="input-group-addon">成员可参与个数</div>
        <input class="form-control" id="memberInNum" type="number" name="memberInNum" />
    </div>
    <div class="input-group">
        <div class="input-group-addon">指导老师个数</div>
        <input class="form-control" id="teacherNum" type="number" name="teacherNum" />
        <div class="input-group-addon">指导老师指导个数</div>
        <input class="form-control" id="teacherInNum" type="number" name="teacherInNum" />
    </div>
    <div class="input-group">
        <div class="input-group-addon">项目开始时间</div>
        <input type="text" readonly id="startTime" class="form_datetime form-control"/>
        <div class="input-group-addon">项目结束时间</div>
        <input type="text" readonly id="endTime" class="form_datetime form-control"/>
    </div>

    <hr>
    <table id="allStage" class="table table-bordered">
        <thead>
            <tr>
                <th>选择</th>
                <th>阶段名称</th>
                <th>开始时间</th>
                <th>结束时间</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${stages}" var="item">
                <tr>
                    <th><input type="checkbox" class="isChoose" value="${item.id}" checked/> </th>
                    <th> ${item.stageName}<input type="hidden" id="name${item.id}" value="${item.stageName}"/> </th>
                    <th><input type="text" readonly id="startTime${item.id}" class="form_datetime form-control"/></th>
                    <th><input type="text" readonly id="endTime${item.id}" class="form_datetime form-control"/></th>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <input type="button" id="submit" class="btn btn-danger" value="提交"/>
</form>
</mydiv>
<myscript>
    <script>
        $(document).ready(function(){

            $(".form_datetime").datetimepicker({
                language:  'zh-CN',
                format: "yyyy-MM-dd hh:ii",
                autoclose: true,
                todayBtn: true,
                pickerPosition: "bottom-left"
            });

            $("#submit").click(function(){
                //TODO 在点击的时候，要把这个按钮变成不可点击状态，防止多次提交
                var name = $("#name").val();
                var type1 = $("#type1").val();
                var type2 = $("#type2").val();
                var level = $("#level").val();
                var leaderNum    = $("#leaderNum").val();
                var leaderInNum  = $("#leaderInNum").val();
                var memberNum    = $("#memberNum").val();
                var memberInNum  = $("#memberInNum").val();
                var teacherNum   = $("#teacherNum").val();
                var teacherInNum = $("#teacherInNum").val();
                var isChoose     = new Array(11);
                var type         = new Array(11);
                var startTime    = new Array(11);
                var endTime      = new Array(11);
                var stageName    = new Array(11);
                var isChooseCheckBox = $(".isChoose");
                var infoStartTime = $("#startTime").val();
                var infoEndTime = $("#endTime").val();
                //var typeTmp = $(".type");

                for(var i=0; i<isChooseCheckBox.length; i++){
                    var value = isChooseCheckBox[i].value;
                    //type[i] = typeTmp[i].value;
                    if(isChooseCheckBox[i].checked){
                        isChoose[i] = value;
                        startTime[i] = $("#startTime"+value).val();
                        endTime[i] = $("#endTime"+value).val();
                        stageName[i] = $("#name"+value).val();
//                        alert(stageName[i]);
//                        alert(startTime[i]);
//                        alert(endTime[i]);
                        if(startTime[i] == ""){
                            alert("请勾选开始时间");
                            return;
                        }

                        if(endTime[i] == ""){
                            alert("请勾选结束时间");
                            return;
                        }
                    }
                    else{
                        isChoose[i] = 0;
                        startTime[i] = 0;
                        endTime[i] = 0;
                        stageName[i] = null;
                    }
                }

                var data = {
                    "name":name,
                    "type1":type1,
                    "type2":type2,
                    "level":level,
                    "stageName":stageName,
                    "leaderNum":leaderNum,
                    "leaderInNum":leaderInNum,
                    "memberNum":memberNum,
                    "memberInNum":memberInNum,
                    "teacherNum":teacherNum,
                    "teacherInNum":teacherInNum,
                    "isChoose":isChoose,
                    "startTime":startTime,
                    "endTime":endTime,
                    "infoStartTime":infoStartTime,
                    "infoEndTime":infoEndTime
                };
                
                $.ajax({
                    url:"/admin/match/create",
                    data:data,
                    type:'post',
                    success:function(json){
                        if(json["success"] == true){
                            //TODO 这个地方后期要换成非alert形式，提升用户体验
                            alert("添加成功");
                            //TODO 自己考虑添加成功是否跳转到其他页面
                        }else{
                            alert("创建失败");
                        }

                        //TODO 不论成功与否，都要把按钮的disable换成可点击
                    },
                    error:function(){
                        alert("未知错误");
                    }
                });
            });

            $.ajax({
                url:"/type/type1",
                success:function(json){
                    var flag = true;
                    json.forEach(function(item){
                        //每个item都是一个json:{"id":2,"name":"省赛"}
                        $("#type1").append('<option value="'+item["id"]+'">' + item["name"] + '</option>');
                        if(flag == true){
                            changeType1(item["id"]);
                            flag = false;
                        }
                    });
                }
            });

            $("#type1").change(function(){
                var id = $(this).val();
                changeType1(id);
            });

            /** 以下是function的定义*/
            /** type1改变的时候更新type2的option所有值*/
            function changeType1(id){
                //首先要清楚数据
                $("#type2").empty();
                $.ajax({
                    url:"/type/type1/type2/" + id,
                    success:function(json){
                        json.forEach(function(item){
                            //每个item都是一个json:{"id":2,"name":"省赛"}
                            $("#type2").append('<option value="'+item["id"]+'">' + item["name"] + '</option>');
                        });
                    }
                });
            }
        });
    </script>
</myscript>
</body>
</html>
