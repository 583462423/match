<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/base/base.jsp"%>

<html>
<head>
    <title>登陆界面</title>
</head>
<link rel="stylesheet" media="screen" href="/css/style.css">
<link rel="stylesheet" media="screen" href="/css/login.css">
<link rel="stylesheet" media="screen" href="/css/bootstrap.min.css">

<body>

<div class="loginContainer">
    <div class="userLogin loginForm">用户登陆
        <form id="userForm" action="/login" method="post">
            <input name="username" type="text" placeholder="请输入用户名"/>
            <input name="password" type="password" placeholder="请输入密码"/>
            <input type="button" id="userLogin" class="btn btn-warning" value="登陆" />
        </form>
        <a class="tmpRaterBtn aLogin">临时评委登陆</a>
    </div>
    <div class="tmpRaterLogin loginForm">临时登陆
        <form action="/tmpRaterLogin" method="post">
            <input name="username" type="text" placeholder="请输入用户名"/>
            <input name="password" type="password" placeholder="请输入密码"/>
            <input type="button" class="btn btn-warning" value="临时登陆" />
        </form>
        <a class="userBtn aLogin">用户登陆</a>
    </div>

</div>

<div id="particles-js">
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
                        window.location.href="/admin/matchs";
                    }else{
                        alert("错误错误");
                    }
                    //alert(json["success"] + "," + json["message"]);
                },
                error:function(error){
                    alert("服务器开小差了，请稍后再试～")
                }
            });
        });
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
