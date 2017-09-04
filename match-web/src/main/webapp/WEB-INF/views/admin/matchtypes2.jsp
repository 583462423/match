
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
    <title>比赛种类</title>
</head>
<body>
<mydiv>
    <div style="width: 100%;height: 100%;">
        <table class="table table-bordered">
            <thead>
            <tr>
                <th class="col-sm-4">类别名称</th>
                <th class="col-sm-4">类别所属</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${types}" var="item">
                <tr>
                    <td>${item.matchType2.name}</td>
                    <td>${item.matchType.name}</td>
                    <td>
                        <a style="cursor: pointer" class="delete" data-id="${item.matchType2.id}"><span class="label label-danger"><i class="glyphicon glyphicon-remove"></i>删除</span></a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <button class="btn btn-danger" id="addBtn">增加</button>
    </div>

    <!--模态框，显示详细信息-->
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

            $("#addBtn").click(function(){
                $(".modal").modal("show");
                //顺便把type1的所有种类取出来
                $.ajax({
                    url:'/type/type1',
                    success:function(json){
                        $("#type").empty();
                        json.forEach(function(item){
                            $("#type").append('<option value="'+ item["id"] +'">' + item["name"] + '</option>');
                        });
                    }
                });
            });

            /** 比赛类别添加*/
            $("#add").click(function(){
                var name = $("#name").val();
                var type1 = $("#type").val();

                $.ajax({
                    url:"/admin/matchtype2/create/"+name+"/"+type1,
                    success:function(json){
                        if(json["success"] == true){
                            alert("创建成功");
                            window.location.href = "/admin/matchtype2";
                        }else{
                            alert(json["message"]);
                        }
                    }
                });
            });

            /** 比赛类别删除*/
            $(".delete").click(function(){
                var id = $(this).attr("data-id");
                $.ajax({
                    url:"/admin/matchtype2/delete/"+id,
                    success:function(json){
                        if(json["success"] == true){
                            alert("删除成功");
                            window.location.href="/admin/matchtype2";
                        }else{
                            alert(json["message"]);
                        }
                    }
                });
            });
        });
    </script>
</myscript>
</body>
</html>
