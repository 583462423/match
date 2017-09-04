
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
    <title>评委管理</title>
</head>
<body>
<mydiv>
    <div style="width: 100%;height: 100%;">
        请选择比赛类别
        <select id="info" class="form-control">
            <option disabled selected value>请选择</option>
            <c:forEach items="${info}" var="item">
                <option value="${item.id}">${item.name}</option>
            </c:forEach>
        </select>
        <table class="table table-bordered">
            <thead>
                <tr>
                    <th class="col-sm-6"><input type="checkbox" id="allCheck" />全选</th>
                    <th class="col-sm-6">比赛名称</th>
                </tr>
            </thead>
            <tbody id="tbody">

            </tbody>
        </table>

        <input type="text" readonly id="startTime" style="width:200px;display:inline" placeholder="请输入开始时间" class="form_datetime form-control"/>
        <input type="text" readonly id="endTime" style="width:200px;display:inline" placeholder="请输入结束时间" class="form_datetime form-control"/>
        <input type="number" id="cnt" style="width:200px;display:inline" placeholder="请输入评委个数" class="form-control"/>

        <button id="startGen" class="btn btn-danger">开始生成</button>
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

            $("#info").change(function(){
                var infoId = $(this).val();
                $.ajax({
                    url:"/item/"+infoId,
                    success:function(json){
                        if(json["success"]==true){
                            $("#tbody").empty();
                            json["data"].forEach(function(item){
                                var appendHtml = '<tr><td><input value="'+ item["id"] +'" name="itemId"  type="checkbox"></input></td>' + '<td>'+item["title"]+'</td></tr>'
                                $("#tbody").append(appendHtml)
                            });
                        }
                    }
                });
                //TODO 其他过滤项目以后再说
                //TODO 注意item显示方式按照最近日期先显示原则
            });

            $("#startGen").click(function(){
                //开始生成评委，生成前，调用模态框显示所有要生成的项目，并点击确认后开始生成
                //首先遍历选中的item，然后显示
                var text = $("input:checkbox[name='itemId']:checked").map(function(index,elem) {
                    return $(elem).val();
                }).get().join(',');
                if(text == ""){
                    alert("请选择比赛条目");
                    return;
                }
                var startTime = $("#startTime").val();
                var endTime = $("#endTime").val();
                var cnt = $("#cnt").val();
                //alert("选中的checkbox的值为："+text);
                //text为:2,3
                //接着发送数据,后续有需求的话可调用模态框进行提醒
                var data = {
                    matchItem:text,
                    startTime:startTime,
                    endTime:endTime,
                    cnt:cnt
                }
                $.ajax({
                    url:"/admin/rater/gen",
                    data:data,
                    type:'post',
                    success:function(json){
                        if(json["success"] == true){
                            alert("创建成功");
                            //TODO 有需求的话，调用模态狂显示添加成功的人
                        }
                    }
                });
            })
        });
    </script>
</myscript>
</body>
</html>
