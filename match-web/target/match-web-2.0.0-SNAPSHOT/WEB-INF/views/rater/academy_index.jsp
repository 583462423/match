
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

<c:if test="${notDoneMatchItems == null || fn:length(notDoneMatchItems) == 0}">
    已经没有需要评分的比赛了
</c:if>
<c:if test="${notDoneMatchItems != null && fn:length(notDoneMatchItems) > 0}">
    <table class="table table-bordered">
        <h1>未评分的比赛</h1>
        <thead>
        <tr>
            <th class="col-sm-2">比赛名称</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${notDoneMatchItems}" var="item">
            <tr>
                <td>${item.title}</td>
                <td><a href="/rater/academy/grade/${item.id}">评分</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>
<br>
<a class="btn btn-danger" style="display:inline-block;" href="/logout">退出</a>

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
</body>
</html>
