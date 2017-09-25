
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
</head>
<body>


    ${item.id}
    <br>

    ${item.title}
    <br>

    这里到时候会有各种信息，供查看
    <br>

    <form id="submitForm" action="/rater/admin/grade/submit" method="post">
        <input name="score" type="number" placeholder="请输入分数"/>
        <input name="comment" type="text" placeholder="请输入评价"/>
        <input name="matchItemId" type="hidden" value="${item.id}"/>
        <button type="submit">提交</button>
    </form>




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
                }else{
                    alert(json["error"]);
                }
            });
        });
    </script>
</body>
</html>
