
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
    <!-- 上传申请表 -->
    <c:if test="${apply != null}">
        <b>您已上传过申请表，再次上传将会覆盖之前的申请表: ${apply}</b>
        <input type="button" class="btn btn-info submit" value="是否提交？">
    </c:if>
    <form id="fileForm" enctype="multipart/form-data" method="post" action="/upload/match/apply/${item.id}">
        <input type="file" name="file" />
        <input type="submit" />
    </form>


    <!--模态框，显示详细信息 不用的时候删除-->
    <div class="upSuccess modal fade" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">类别添加</h4>
                </div>
                <div class="modal-body">
                    <span id="info">上传成功，是否提交，提交后不可更改。</span>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default dismiss" >取消</button>
                    <button type="button" id="submit" class="btn btn-primary">提交</button>
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
            var itemId = ${item.id};
            $("#fileForm").ajaxForm(function(res){
                var json = JSON.parse(res);
                if(json["error"] != null){
                    alert(json["error"]);
                }else{
                    $("#info").text("上传成功,是否提交？提交后不可更改");
                    $(".upSuccess").modal('show');
                }
            })

            $("#submit").click(function(){
                //点击提交后
                submit();
            });

            $(".dismiss").click(function(){
                //点击取消后，刷新当前页面
                location.reload(true);
            });

            $(".submit").click(function(){
                $("#info").text("是否提交？提交后不可更改");
                $(".upSuccess").modal('show');
            });

            function submit(){
                $.ajax({
                    url:"/student/match/apply/submit/"+itemId,
                    type:'get',
                    success:function(res){
                        var json = JSON.parse(res);
                        if(json["success"] == "true"){
                            alert("提交成功");
                            location.href = "/student/match/my";
                        }else{
                            alert(json["error"]);
                        }
                    }
                });
            }
        });
    </script>
</myscript>
</body>
</html>
