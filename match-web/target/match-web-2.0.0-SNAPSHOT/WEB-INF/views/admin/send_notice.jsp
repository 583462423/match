
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
    <title>通告管理</title>
</head>
<body>
<mydiv>
    <div style="width: 100%;height: 100%;">
        <form id="noticeSend" action="/admin/send/notice" style="width:666px;" method="post">
            <div class="input-group">
                <div class="input-group-addon">选择级别</div>
                <select name ="level" class="form-control" >
                    <option selected>请选择成员</option>
                    <option value="0">所有成员</option>
                    <option value="1">所有学生</option>
                    <option value="2">所有教师</option>
                    <option value="3">所有管理员</option>
                </select>
            </div>
            <div class="input-group">
                <div class="input-group-addon">输入标题</div>
                <input type="text" class="form-control" name="title" placeholder="请输入标题"/>
            </div>
            <div class="input-group">
                <div class="input-group-addon">输入内容</div>
                <textarea name="content" style="height:150px"></textarea>
            </div>
            <input type="text" readonly name="startTime" style="width:200px;display:inline" placeholder="请输入开始时间" class="form_datetime form-control"/>
            <input type="text" readonly name="endTime" style="width:200px;display:inline" placeholder="请输入结束时间" class="form_datetime form-control"/>
            <input type="hidden" name="isValid" value="1"/>
            <input type="submit" class="btn btn-success" value="发送"/>
        </form>
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
    <script>
        $(document).ready(function(){
            $(".form_datetime").datetimepicker({
                language:  'zh-CN',
                format: "yyyy-MM-dd hh:ii",
                autoclose: true,
                todayBtn: true,
                pickerPosition: "bottom-left"
            });

            $("#noticeSend").ajaxForm(function(res){
                var json = JSON.parse(res);
                if(json["success"] == 'true'){
                    showMsg("成功");
                }else{
                    showMsg(json["error"]);
                }
            })
        });
    </script>
</myscript>
</body>
</html>
