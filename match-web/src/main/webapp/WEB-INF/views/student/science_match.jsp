
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
        <c:if test="${items == null || fn:length(items) == 0}">
            当前没有参加比赛
        </c:if>
        <c:if test="${items != null && fn:length(items) > 0}">
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th class="col-sm-2">比赛名称</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${items}" var="item">
                    <tr>
                        <td>${item.title}</td>
                        <td>
                            <span class="label label-info addLog" data="${item.id}" style="cursor: pointer;"><i class="glyphicon glyphicon-plus"></i>添加科研日志</span>
                            <a style="cursor: pointer" href="/student/science/all/${item.id}"><span class="label label-success"><i class="glyphicon glyphicon-eye-open"></i>查看当前科研日志</span></a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>

    <!--模态框，显示详细信息 不用的时候删除-->
    <div class="addLogModal modal fade" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document" style="width:800px">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">科研日志添加</h4>
                </div>
                <div class="modal-body">
                    <div class="input-group">
                        <div class="input-group-addon">日志标题</div>
                        <input type="text" id="title" class="form-control" placeholder="请输入标题"/>
                    </div>

                    <div class="input-group" >
                        <div  class="input-group-addon">日志内容</div>
                        <div id="editor" >
                        </div>
                    </div>

                    <div class="input-group">
                        <div class="input-group-addon">选择日期</div>
                        <input type="text" readonly id="time"  placeholder="请输入日期" class="form_datetime form-control"/>
                    </div>

                    <div class="inline">
                        <input type="number" id="startTime" class="form-control" style="display:inline;float:left;width:50%" placeholder="请输入几点开始"/>
                        <input type="number" id="endTime" class="form-control" style="display:inline;width:50%;"   placeholder="请输入几点结束"/>
                    </div>

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
    <script type="text/javascript" src="/js/wangEditor.min.js"></script>
    <script>
        $(document).ready(function(){

            var E = window.wangEditor
            var editor = new E('#editor')
            editor.customConfig.menus = [
                'head',  // 标题
                'bold',  // 粗体
                'italic',  // 斜体
                'underline',  // 下划线
                'strikeThrough',  // 删除线
                'foreColor',  // 文字颜色
                'backColor',  // 背景颜色
                'link',  // 插入链接
                'list',  // 列表
                'justify',  // 对齐方式
                'quote',  // 引用
                'table',  // 表格
                'code',  // 插入代码
                'undo',  // 撤销
                'redo'  // 重复
            ]
            // 或者 var editor = new E( document.getElementById('#editor') )
            editor.create()

            $(".form_datetime").datetimepicker({
                language:  'zh-CN',
                format: "yyyy-MM-dd",
                autoclose: true,
                todayBtn: true,
                pickerPosition: "bottom-left",
                minView:2     //仅仅显示日期
            });

            var nowAddId = "";
            $(".addLog").click(function(){
                nowAddId = $(this).attr("data");
                $('.addLogModal').modal({backdrop: 'static', keyboard: false});
            });

            $("#add").click(function(){
                //添加日志
                if(nowAddId == "")return;

                var title = $("#title").val();
                var content = editor.txt.html();
                var time = $("#time").val();
                var startTime = $("#startTime").val();
                var endTime = $("#endTime").val();

                if(checkIsNull(title) ||
                checkIsNull(content) ||
                checkIsNull(time) ||
                checkIsNull(startTime) ||
                checkIsNull(endTime)){
                    showMsg("内容不得未空！");
                    return;
                }

                var data = {
                    title:title,
                    content:content,
                    time:time,
                    startTime:startTime,
                    endTime:endTime
                }



                $.ajax({
                    url:"/student/science/log/add/" + nowAddId,
                    data:data,
                    type:'post',
                    success:function(res){
                        var json = JSON.parse(res);
                        if(json["success"] == 'true'){
                            showMsg("添加成功");
                        }
                    }
                });
            });

            function checkIsNull(A){
                if(A==null||A!=undefined||A==""){
                    return true;
                }
                return false;
            }
        });
    </script>
</myscript>
</body>
</html>
