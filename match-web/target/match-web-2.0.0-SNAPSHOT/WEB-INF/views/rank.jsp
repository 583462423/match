
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
<body>

    <div style="width: 100%;height: 100%;">

        <h1>${matchInfo.name} 比赛获奖名单!</h1>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>比赛标题</th>
                <th>比赛分数</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${matchItemWithScores}" var="item">
                <tr>
                    <td>${item.matchItem.title}</td>
                    <td>${item.score}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

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
</body>

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


    });
</script>

</html>
