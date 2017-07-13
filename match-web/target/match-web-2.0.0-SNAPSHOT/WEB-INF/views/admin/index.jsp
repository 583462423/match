<%--
  Created by IntelliJ IDEA.
  User: qxg
  Date: 17-6-29
  Time: 下午8:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/base/base.jsp"%>
<html>
<head>
    <title>欢迎</title>
</head>
<body>
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
                                    <li><a href="matchs">比赛管理</a></li>
                                    <li><a href="createMatch">创建比赛</a></li>
                                    <li><a href="alterMatch">修改比赛</a></li>
                                </ul>
                            </li>

                            <li><a><i class="fa fa-home"></i> 视频<span class="fa fa-chevron-down"></span></a>
                                <ul class="nav child_menu">
                                    <li><a href="video.html">视频管理</a></li>
                                    <li><a href="videoUp.html">视频上传</a></li>
                                </ul>
                            </li>

                            <li><a><i class="fa fa-home"></i> BP<span class="fa fa-chevron-down"></span></a>
                                <ul class="nav child_menu">
                                    <li><a href="BP.html">BP管理</a></li>
                                </ul>
                            </li>

                            <li><a><i class="fa fa-home"></i> VC<span class="fa fa-chevron-down"></span></a>
                                <ul class="nav child_menu">
                                    <li><a href="VC.html">VC管理</a></li>
                                    <li><a href="VCadd.html">VC增加</a></li>
                                </ul>
                            </li>

                            <li><a><i class="fa fa-home"></i> Banner<span class="fa fa-chevron-down"></span></a>
                                <ul class="nav child_menu">
                                    <li><a href="banner.html">Banner管理</a></li>
                                    <li><a href="bannerAdd.html">Banner增加</a></li>
                                </ul>
                            </li>

                            <li><a><i class="fa fa-home"></i> 标签<span class="fa fa-chevron-down"></span></a>
                                <ul class="nav child_menu">
                                    <li><a href="tag.html">标签管理</a></li>
                                    <li><a href="tagAdd.html">标签增加</a></li>
                                </ul>
                            </li>

                            <li><a><i class="fa fa-home"></i> 分类<span class="fa fa-chevron-down"></span></a>
                                <ul class="nav child_menu">
                                    <li><a href="catalog.html">分类管理</a></li>
                                    <li><a href="catalogAdd.html">分类增加</a></li>
                                </ul>
                            </li>

                        </ul>
                    </div>

                </div>
                <!-- /sidebar menu -->

                <!-- /menu footer buttons -->
                <div class="sidebar-footer hidden-small">
                    <a data-toggle="tooltip" data-placement="top" style="width:100%" title="退出登录">
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
                            <a href="javascript:;" class="user-profile dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
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
</body>
</html>
