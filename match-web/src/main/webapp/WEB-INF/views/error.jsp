<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/base/base.jsp"%>

<html>
<head>
    <title>错误界面</title>
    <!--- basic page needs
   ================================================== -->
    <meta charset="utf-8">
    <title>Quatro - Particles</title>
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- mobile specific metas
    ================================================== -->
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

    <!-- CSS
  ================================================== -->
    <link rel="stylesheet" href="/css/base.css">
    <link rel="stylesheet" href="/css/main.css">
    <link rel="stylesheet" href="/css/vendor.css">

    <!-- script
    ================================================== -->
    <script src="js/modernizr.js"></script>

    <!-- favicons
     ================================================== -->
    <link rel="icon" type="image/png" href="favicon.png">

</head>

    <%--${ex.msg}--%>
<body>

<!-- header
================================================== -->

<!-- navigation
================================================== -->
    <!-- main content
    ================================================== -->
    <main id="main-404-content" class="main-content-particle-js">

        <div class="content-wrap" style="position: absolute;top:-150px;">


            <div class="main-content">
                <div class="row">
                    <div class="col-twelve">

                        <h1 class="kern-this">错误!</h1>
                        <p>
                            ${ex.msg}
                        </p>


                    </div> <!-- /twelve -->
                </div> <!-- /row -->
            </div> <!-- /main-content -->

            <footer>
                <div class="row">

                    <div class="col-five tab-full bottom-links">
                        <ul class="links">
                            <li><a href="/logout">主页</a></li>
                            <li><a id="back" href="#">返回上一页</a></li>
                        </ul>
                    </div>

                </div> <!-- /row -->
            </footer>

        </div> <!-- /content-wrap -->

    </main> <!-- /main-404-content -->

    <div id="preloader">
        <div id="loader"></div>
    </div>

<!-- Java Script
================================================== -->
<script src="/js/jquery.min.js"></script>
<script src="/js/plugins.js"></script>
<script src="/js/main.js"></script>
<script>
    $(document).ready(function(){
        $("#back").click(function(){
            history.back();
        });
    });
</script>
</body>

</html>


