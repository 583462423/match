
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
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Bootstrap -->
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="/css/font-awesome.min.css" rel="stylesheet">
    <!-- 进度条插件 -->
    <link href="/css/nprogress.css" rel="stylesheet">
    <!-- bootstrap-progressbar -->
    <link href="/css/bootstrap-progressbar-3.3.4.min.css" rel="stylesheet">
    <!-- 下拉多选框-->
    <link href="/css/bootstrap-select.min.css" rel="stylesheet"/>
    <!--table内容垂直居中-->
    <link href="/css/table.css" rel="stylesheet"/>
    <!--bootstrap-datetimepicker 怕误伤友军上个先不删除-->
    <link href="/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
    <style>
        .myPanel{
            position: absolute;
            width: 500px;
            height: 400px;
            left: 50%;
            margin-left: -250px;
            margin-top: 280px;
            background: #ffffff;
            box-shadow: 0px 1px 40px 0px rgba(140,146,163,0.2),inset 0px -1px 0px 0px #dededf !important;
            border-radius: 2px;
            padding:8px;
        }
        .input-group{
            margin:8px;
        }
    </style>
</head>
<body style="background: #E0E0E0">


<div class="myPanel">
    <form id="submitForm" action="/rater/admin/grade/submit" style="margin:8px;" method="post">
        <div class="input-group">
            <div class="input-group-addon">比赛名称</div>
            <input class="form-control" readonly value="${item.title}"/>
        </div>
        <div class="input-group">
            <div class="input-group-addon">分数</div>
            <input name="score" class="form-control" type="number" placeholder="请输入分数"/>
        </div>
        <div class="input-group">
            <div class="input-group-addon">评价</div>
            <textarea name="comment" style="resize:none;height:150px;width:100%;" placeholder="请输入评价"></textarea>
        </div>
        <input name="matchItemId" type="hidden" value="${item.id}"/>
        <button type="submit" style="margin-left: 8px;" class="btn btn-success">提交</button>
    </form>
</div>


    <!-- jQuery -->
    <script src="/js/jquery.min.js"></script>
    <!-- Bootstrap -->
    <script src="/js/bootstrap.min.js"></script>
    <!-- 减少延迟插件 -->
    <script src="/js/fastclick.js"></script>
    <!-- 进度条插件 -->
    <script src="/js/nprogress.js"></script>
    <!-- bootstrap-progressbar -->
    <script src="/js/bootstrap-progressbar.min.js"></script>
    <!-- Custom Theme Scripts -->
    <script src="/js/custom.min.js"></script>
    <!-- jQuery Form -->
    <script src="/js/jquery-form.js"></script>
    <!-- 下拉多选框 -->
    <script src="/js/bootstrap-select.min.js"></script>
    <script src="/js/variable.js"></script>

    <!-- bootstrap-datetimepicker-->
    <script type="text/javascript" src="/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
    <script type="text/javascript" src="/js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
    <script>
        $(document).ready(function(){
            $("#submitForm").ajaxForm(function(res){
                var json = JSON.parse(res);
                if(json["success"] == "true"){
                    alert("评价成功");
                    location.href= "/rater/super/index";
                }else{
                    alert(json["error"]);
                }
            });
        });
    </script>
</body>
</html>
