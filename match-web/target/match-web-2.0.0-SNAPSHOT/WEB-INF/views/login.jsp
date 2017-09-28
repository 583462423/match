<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/base/base.jsp"%>

<html>
<head>
    <title>登陆界面</title>
</head>
<link rel="stylesheet" media="screen" href="/css/bootstrap.min.css">
<link rel="stylesheet" media="screen" href="/css/login.css">
<link rel="stylesheet" media="screen" href="/css/style.css">

<body>

<div class="header">
    <div class="headerPanel">
        <img src="/images/2.png" style="width: 150px;">
        <span class="headerTitle">山东大学(威海)比赛管理平台</span>
    </div>
    <hr />
</div>

<div class="loginContainer">
    <div class="userLogin loginDiv">
        <div class="title">用户登陆</div>
        <form id="userForm" class="loginForm" action="/login" method="post">
            <input class="form-control username" name="username" type="text" placeholder="请输入用户名"/>
            <input class="form-control password" name="password" type="password" placeholder="请输入密码"/>
            <div class="loginOther">
                <div class="checkbox">
                    <label>
                        <input type="checkbox"> 记住我
                    </label>
                    <a href="#" class="forgetPass">忘记密码?</a>
                </div>
            </div>
            <input type="button" id="userLogin" class="btn btn-warning loginBtn userLoginBtn" value="登陆" />

        </form>
        <a class="tmpRaterBtn aLogin">临时评委登陆</a>
    </div>
    <div class="tmpRaterLogin loginDiv">
        <div class="title">临时评委登陆</div>
        <form id="raterForm" class="loginForm" action="/rater/login" method="post">
            <input class="form-control username" name="username" type="text" placeholder="请输入临时评委用户名"/>
            <input class="form-control password" name="password" type="password" placeholder="请输入密码"/>
            <div class="loginOther">
                <div class="checkbox">
                    <label>
                        <input type="checkbox"> 记住我
                    </label>
                    <a href="#" class="forgetPass">忘记密码?</a>
                </div>
            </div>
            <input type="button" id="raterLogin" class="btn btn-danger loginBtn" value="临时登陆" />
        </form>
        <a class="userBtn aLogin">用户登陆</a>
    </div>


</div>

<div class="footer">
    <div class="footerPanel">
        <span class="footerText">版权所有:山东大学(威海)</span>
    </div>
</div>

<div id="particles-js" style="background: url(/images/demo-1.jpg);height:100%;width:100%;">
</div>

<!-- Modal -->
<div class="errorMsgModal modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <%--<div class="modal-header">--%>
                <%--<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>--%>
                <%--&lt;%&ndash;<h4 class="modal-title" id="myModalLabel">Modal title</h4>&ndash;%&gt;--%>
            <%--</div>--%>
            <div class="modal-body">
                <div class="errorMsg"></div>
            </div>
            <%--<div class="modal-footer">--%>
                <%--<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>--%>
                <%--<button type="button" class="btn btn-primary">Save changes</button>--%>
            <%--</div>--%>
        </div>
    </div>
</div>

</body>
<script src="/js/particles.min.js"></script>
<script src="/js/app.js"></script>
<script src="/js/jquery.min.js"></script>
<script src="/js/bootstrap.min.js"></script>
<script>
    $(document).ready(function(){
        $(".userBtn").click(function(){
            $(".userLogin").css("display","block");
            $(".tmpRaterLogin").css("display","none");
        });

        $(".tmpRaterBtn").click(function(){
            $(".tmpRaterLogin").css("display","block");
            $(".userLogin").css("display","none");
        });

        $("#userLogin").click(function(){
            var data = $("#userForm").serializeArray();
            var action = $("#userForm").attr("action");
            $.ajax({
                url:action,
                type:'post',
                data:data,
                success:function(json){
                    if(json["success"] == true){
                        window.location.href= json["message"];
                    }else{
                        showMsg(json["message"]);
                    }
                    //alert(json["success"] + "," + json["message"]);
                },
                error:function(error){
                    showMsg("服务器开小差了，请稍后再试～");
                }
            });
        });

        $("#raterLogin").click(function(){
            var data = $("#raterForm").serializeArray();
            var action = $("#raterForm").attr("action");
            $.ajax({
                url:action,
                type:'post',
                data:data,
                success:function(res){
                    var json = JSON.parse(res);
                    if(json["error"] != null){
                        showMsg(json["error"]);
                    }else{
                        window.location.href = json["url"];
                    }
                    //alert(json["success"] + "," + json["message"]);
                },
                error:function(error){
                    alert("服务器开小差了，请稍后再试～")
                }
            });
        });

        function showMsg(msg){
            $(".errorMsg").html(msg);
            $(".errorMsgModal").modal("show");
        }

    });
</script>
<script>
    var count_particles, stats, update;
    stats = new Stats;
    stats.setMode(0);
    stats.domElement.style.position = 'absolute';
    stats.domElement.style.left = '0px';
    stats.domElement.style.top = '0px';
    document.body.appendChild(stats.domElement);
    count_particles = document.querySelector('.js-count-particles');
    update = function() {
        stats.begin();
        stats.end();
        if (window.pJSDom[0].pJS.particles && window.pJSDom[0].pJS.particles.array) {
            count_particles.innerText = window.pJSDom[0].pJS.particles.array.length;
        }
        requestAnimationFrame(update);
    };
    requestAnimationFrame(update);
</script>
</html>
