<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/base/base.jsp"%>

<html>

<head>
    <title>未授权!</title>
    <meta charset="utf-8">
    <link rel="stylesheet" type="text/css" href="/css/demo.css" />
    <link rel="stylesheet" type="text/css" href="/css/style2.css" />
    <script type="text/javascript" src="/js/modernizr.custom.js"></script>
    <!--[if lte IE 8]><style>.main{display:none;} .support-note .note-ie{display:block;}</style><![endif]-->
</head>
<body>
<div class="container">
    <div class="main">
        <div class="fs-slider" id="fs-slider">
            <figure onclick="returns()">
            <img src="images/1.jpg" alt="image01" />

            </figure>

            <figure>
                <img src="images/5.jpg" alt="image02" />
                <figcaption>

                    <h3 style="font-family: '宋体';color: #cfd6d1"><br>未授权！</h3>

                </figcaption>
            </figure>


            <figure  onclick="fristpage()">
                <img src="images/3.jpg" alt="image03" />

            </figure>

            <DIV>

            </DIV>
        </div><!-- /fs-slider -->
    </div>
</div>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/jquery.imgslider.js"></script>
<script type="text/javascript">

    //返回上一页

    function returns(){
        window.open("上一页地址",'_self');
    }
    //返回主页函数
    function fristpage(){
        window.open("返回主页地址",'_self');
    }

    $(function() {
        $( '#fs-slider' ).imgslider();
    });
</script>

</body>
</html>