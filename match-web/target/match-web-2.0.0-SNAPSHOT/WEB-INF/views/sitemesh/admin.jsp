<%--
  Created by IntelliJ IDEA.
  User: qxg
  Date: 17-6-29
  Time: 下午8:22
  /admin页面下的界面被这个界面所装饰
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/base/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title><sitemesh:write property='title' /></title>
    <!-- Bootstrap -->
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="/css/font-awesome.min.css" rel="stylesheet">
    <!-- 进度条插件 -->
    <link href="/css/nprogress.css" rel="stylesheet">
    <!-- bootstrap-progressbar -->
    <link href="/css/bootstrap-progressbar-3.3.4.min.css" rel="stylesheet">
    <!-- Custom Theme Style -->
    <link href="/css/custom.min.css" rel="stylesheet">
    <!-- 下拉多选框-->
    <link href="/css/bootstrap-select.min.css" rel="stylesheet"/>
    <!--table内容垂直居中-->
    <link href="/css/table.css" rel="stylesheet"/>
    <!--bootstrap-datetimepicker 怕误伤友军上个先不删除-->
    <link href="/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
    <sitemesh:write property="mylink"/>
</head>
<body class="nav-md">

<div class="container body">
    <div class="main_container">
        <div class="col-md-3 left_col">
            <div class="left_col scroll-view">
                <div class="navbar nav_title" style="border: 0;">
                    <a href="article.html" class="site_title"><i class="glyphicon glyphicon-user"></i> <span>Admin最高管理员</span></a>
                </div>

                <div class="clearfix"></div>

                <!-- menu profile quick info -->
                <div class="profile clearfix">
                    <div class="profile_pic">
                        <img src="/images/img.png" alt="..." class="img-circle profile_img">
                    </div>
                    <div class="profile_info">
                        <span>欢迎您</span>
                        <h2>管理员</h2>
                    </div>
                </div>
                <!-- /menu profile quick info -->

                <br />

                <!-- sidebar menu -->
                <div id="sidebar-menu" class="main_menu_side hidden-print main_menu">
                    <div class="menu_section">
                        <h3>管理操作</h3>
                        <ul class="nav side-menu">

                            <li><a href="index.html" ><i class="fa fa-home"></i> 主页</a></li>
                            <li><a><i class="fa fa-home"></i> 比赛<span class="fa fa-chevron-down"></span></a>
                                <ul class="nav child_menu">
                                    <li><a href="/admin/matchs">比赛管理</a></li>
                                    <li><a href="/admin/match/create">创建比赛</a></li>
                                </ul>
                            </li>

                            <li><a><i class="fa fa-home"></i>比赛分类<span class="fa fa-chevron-down"></span></a>
                                <ul class="nav child_menu">
                                    <li><a href="/admin/matchtype">比赛类别</a></li>
                                    <li><a href="/admin/matchtype2">二级类别管理</a></li>
                                </ul>
                            </li>

                            <li><a><i class="fa fa-home"></i>权限<span class="fa fa-chevron-down"></span></a>
                                <ul class="nav child_menu">
                                    <li><a href="/permission/all">权限管理</a></li>
                                </ul>
                            </li>

                            <li><a><i class="fa fa-home"></i> 临时评委<span class="fa fa-chevron-down"></span></a>
                                <ul class="nav child_menu">
                                    <li><a href="/admin/rater/all.html">评委管理</a></li>
                                    <li><a href="/admin/rater/gen.html">生成评委</a></li>
                                </ul>
                            </li>

                            <li><a><i class="fa fa-home"></i> 通知(待做)<span class="fa fa-chevron-down"></span></a>
                                <ul class="nav child_menu">
                                    <li><a href="#">通知管理</a></li>
                                    <li><a href="#">发布通知</a></li>
                                </ul>
                            </li>

                            <li><a><i class="fa fa-home"></i> 学院<span class="fa fa-chevron-down"></span></a>
                                <ul class="nav child_menu">
                                    <li><a href="/admin/academy/all">学院管理</a></li>
                                    <li><a href="/admin/academy/add">添加学院</a></li>
                                </ul>
                            </li>

                            <li><a><i class="fa fa-home"></i> 统计(待做)<span class="fa fa-chevron-down"></span></a>
                                <ul class="nav child_menu">
                                    <li><a href=".">..</a></li>
                                    <li><a href=".">..</a></li>
                                </ul>
                            </li>

                            <li><a href="/admin/password"><i class="fa"></i> 修改密码<span class="fa fa-chevron-down"></span></a>
                            </li>
                        </ul>
                    </div>

                </div>
                <!-- /sidebar menu -->

                <!-- /menu footer buttons -->
                <div class="sidebar-footer hidden-small">
                    <a data-toggle="tooltip" data-placement="top" href="/logout" style="width:100%" title="退出登录">
                        <span class="glyphicon glyphicon-off" aria-hidden="true"></span>
                    </a>
                </div>
                <!-- /menu footer buttons -->
            </div>
        </div>

        <!-- top navigation -->
        <div class="top_nav">
            <div class="nav_menu">

                <nav>
                    <div class="nav toggle">
                        <a id="menu_toggle"><i class="fa fa-bars"></i></a>
                    </div>


                    <ul class="nav navbar-nav navbar-right">
                        <li class="">
                            <a href="/logout" class="user-profile dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                                <img src="/images/logout.png" alt="">退出
                            </a>
                        </li>

                    </ul>
                </nav>
            </div>
        </div>
        <!-- /top navigation -->

        <!-- page content -->
        <div class="right_col" role="main" style="height: 100%">
            <sitemesh:write property="mydiv"/>
        </div>
        <!-- /page content -->

        <!-- footer content -->
        <footer>
            <div class="pull-right">
                © 2017 Design by sduwh
            </div>
            <div class="clearfix"></div>
        </footer>
        <!-- /footer content -->
    </div>
</div>

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
<sitemesh:write property="myscript"/>

</body>
</html>



