<%--
  Created by IntelliJ IDEA.
  User: wu1ji
  Date: 2019/1/31
  Time: 8:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="assets/css/header/bootsnav.css"/>
    <link rel="stylesheet" type="text/css" href="assets/css/header/htmleaf-demo.css">
    <link rel="stylesheet" type="text/css" href="assets/css/font-controller.css">
    <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/font-awesome@4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="assets/css/header/header-style.css">

    <script type="text/javascript" src="assets/js/header/bootsnav.js"></script>
    <style>
        nav.navbar.bootsnav li.dropdown ul.dropdown-menu > li > a {
            padding: 10px 18px;
            border-bottom: solid 1px #eee;
            color: #6f6f6f;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="demo" style="/*min-height:350px;*/">
        <div class="container">
            <div class="row">
                <nav class="navbar navbar-default navbar-mobile bootsnav">
                    <div class="navbar-header">
                        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navbar-menu">
                            <i class="fa fa-bars"></i>
                        </button>
                    </div>
                    <div class="collapse navbar-collapse" id="navbar-menu">
                        <ul class="nav navbar-nav" data-in="fadeInDown" data-out="fadeOutUp">
                            <li>
                                <a href="${pageContext.request.contextPath}/home" data-hover="Home" style="font-size: 19px">Online Judge<span data-hover="Home"></span></a>
                            </li>
                            <li><a href="" data-hover="About">常见问题 <span data-hover="About"></span></a></li>
                            <li><a href="${pageContext.request.contextPath}/problem_list">问题<span></span></a></li>
                            <li><a href="${pageContext.request.contextPath}/contest_list">比赛列表<span></span></a></li>
                            <li><a href="">状态<span></span></a></li>
                            <li><a href="">排名<span></span></a></li>


                            <li class="dropdown">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown" data-hover="Pages">比赛列表
                                    <span data-hover="Pages"></span></a>
                                <ul class="dropdown-menu">
                                    <li><a href="#">Custom Menu</a></li>
                                    <li><a href="#">Custom Menu</a></li>
                                    <li class="dropdown">
                                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">Sub Menu</a>
                                        <ul class="dropdown-menu">
                                            <li><a href="#">Custom Menu</a></li>
                                            <li><a href="#">Custom Menu</a></li>
                                            <li class="dropdown">
                                                <a href="#" class="dropdown-toggle" data-toggle="dropdown">Sub
                                                    Menu</a>
                                                <ul class="dropdown-menu multi-dropdown">
                                                    <li><a href="#">Custom Menu</a></li>
                                                    <li><a href="#">Custom Menu</a></li>
                                                    <li><a href="#">Custom Menu</a></li>
                                                    <li><a href="#">Custom Menu</a></li>
                                                </ul>
                                            </li>
                                            <li><a href="#">Custom Menu</a></li>
                                        </ul>
                                    </li>
                                    <li><a href="#">Custom Menu</a></li>
                                    <li><a href="#">Custom Menu</a></li>
                                    <li><a href="#">Custom Menu</a></li>
                                    <li><a href="#">Custom Menu</a></li>
                                </ul>
                            </li>
                            <li class="dropdown megamenu-fw">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown" data-hover="Megamenu">***
                                    <span data-hover="Megamenu"></span></a>
                                <ul class="dropdown-menu megamenu-content" role="menu">
                                    <li>
                                        <div class="row">
                                            <div class="col-menu col-md-3">
                                                <h6 class="title">Title Menu One</h6>
                                                <div class="content">
                                                    <ul class="menu-col">
                                                        <li><a href="#">Custom Menu</a></li>
                                                        <li><a href="#">Custom Menu</a></li>
                                                        <li><a href="#">Custom Menu</a></li>
                                                        <li><a href="#">Custom Menu</a></li>
                                                        <li><a href="#">Custom Menu</a></li>
                                                    </ul>
                                                </div>
                                            </div><!-- end col-3 -->
                                            <div class="col-menu col-md-3">
                                                <h6 class="title">Title Menu Two</h6>
                                                <div class="content">
                                                    <ul class="menu-col">
                                                        <li><a href="#">Custom Menu</a></li>
                                                        <li><a href="#">Custom Menu</a></li>
                                                        <li><a href="#">Custom Menu</a></li>
                                                        <li><a href="#">Custom Menu</a></li>
                                                        <li><a href="#">Custom Menu</a></li>
                                                    </ul>
                                                </div>
                                            </div><!-- end col-3 -->
                                            <div class="col-menu col-md-3">
                                                <h6 class="title">Title Menu Three</h6>
                                                <div class="content">
                                                    <ul class="menu-col">
                                                        <li><a href="#">Custom Menu</a></li>
                                                        <li><a href="#">Custom Menu</a></li>
                                                        <li><a href="#">Custom Menu</a></li>
                                                        <li><a href="#">Custom Menu</a></li>
                                                        <li><a href="#">Custom Menu</a></li>
                                                    </ul>
                                                </div>
                                            </div>
                                            <div class="col-menu col-md-3">
                                                <h6 class="title">Title Menu Four</h6>
                                                <div class="content">
                                                    <ul class="menu-col">
                                                        <li><a href="#">Custom Menu</a></li>
                                                        <li><a href="#">Custom Menu</a></li>
                                                        <li><a href="#">Custom Menu</a></li>
                                                        <li><a href="#">Custom Menu</a></li>
                                                        <li><a href="#">Custom Menu</a></li>
                                                    </ul>
                                                </div>
                                            </div><!-- end col-3 -->
                                        </div><!-- end row -->
                                    </li>
                                </ul>
                            </li>
                        </ul>

                        <c:choose>
                        <c:when test="${empty online_judge_locuser}">
                        <ul class="nav navbar-nav navbar-right">
                            <li class="dropdown">
                                <a href="" class="dropdown-toggle" data-toggle="dropdown" data-hover="Shortcodes">登录
                                    <i class="fa fa-angle-double-down"></i>
                                    <span data-hover="Shortcodes"></span></a>

                                <ul class="dropdown-menu">
                                    <li><a href="${pageContext.request.contextPath}/user_login">登录</a></li>
                                    <li><a href="${pageContext.request.contextPath}/user_register">注册</a></li>
                                </ul>
                            </li>
                            </c:when>
                            <c:otherwise>
                            <ul class="nav navbar-nav navbar-right">
                                <li class="dropdown">
                                    <a class="dropdown-toggle" data-toggle="dropdown" data-hover="Shortcodes">
                                            ${online_judge_locuser.nickname}
                                        <i class="fa fa-angle-double-down"></i>
                                        <span data-hover="Shortcodes"></span></a>
                                    <ul class="dropdown-menu">
                                        <li class="dropdown">
                                            <a class="dropdown-toggle" data-toggle="dropdown" style="padding-left: 5px">
                                                <i class="fa fa-caret-left"></i>&nbsp;帐号管理
                                            </a>

                                            <ul class="dropdown-menu">
                                                <li><a href="">个人中心</a></li>
                                                <li><a href="">修改密码</a></li>
                                            </ul>
                                        </li>
                                        <li><a href="${pageContext.request.contextPath}/solution_status">我的提交</a></li>
                                        <li><a href="${pageContext.request.contextPath}/own_competition">参加的比赛</a></li>
                                        <c:if test="${online_judge_locgroup.rightstr.startsWith('admin')}">
                                            <li><a href="${pageContext.request.contextPath}/admin_home">后台管理</a></li>
                                        </c:if>
                                        <li><a href="${pageContext.request.contextPath}/loginout">注销</a></li>
                                    </ul>

                                </li>
                                </c:otherwise>
                                </c:choose>
                            </ul>
                    </div>
                </nav>
            </div>
        </div>
    </div>

</div>
</body>
</html>
