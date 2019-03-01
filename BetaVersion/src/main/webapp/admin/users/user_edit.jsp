<%--@elvariable id="admin_level" type="com"--%>
<%--@elvariable id="user" type="com"--%>
<%--
  Created by IntelliJ IDEA.
  User: wu1ji
  Date: 2019/1/19
  Time: 9:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>用户信息修改</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="content-type" content="text/html;charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta charset="UTF-8">
    <%--背景样式--%>
    <link rel="stylesheet" type="text/css" href="../../assets/css/backgroud-style.css">
    <%--jquery 3.2.1--%>
    <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/jquery@3.2.1/dist/jquery.min.js"></script>
    <%--背景js--%>
    <script type="text/javascript" src="../../assets/js/vector.js"></script>
    <script type="text/javascript" src="../../assets/js/func.mo.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js"></script>

    <%--正上方信息弹出框--%>
    <link rel="stylesheet" type="text/css" href="../../assets/css/l-message/message.css"/>
    <script type="text/javascript" src="../../assets/js/message.min.js"></script>

    <%--sweet弹出框--%>
    <script src="../../assets/js/sweetalert.min.js"></script>
    <style>
        form input {
            -webkit-appearance: none;
            -moz-appearance: none;
            appearance: none;
            outline: 0;
            border: 1px solid rgba(255, 255, 255, .4);
            background-color: rgba(255, 255, 255, .2);
            width: 200px;
            border-radius: 3px;
            padding: 8px 15px;
            margin: 0 auto 10px 0;
            display: block;
            text-align: center;
            font-size: 15px;
            color: #fff;
            -webkit-transition-duration: .25s;
            transition-duration: .25s;
            font-weight: 300
        }
    </style>
</head>
<body>


<div id="container">
    <div id="output">
        <div class="containerT">
            <div class="container">
                <jsp:include page="../../ico.jsp"/>

                <h3>
                    <strong>用户信息</strong>
                </h3>
                <form id="entry_form">
                    <style type="text/css">

                    </style>
                    <c:if test="${!empty doEdit_msg}">
                        <span class="label label-danger">修改失败，该账户无法修改</span>
                    </c:if>
                    <center>
                        <table style="border-collapse: unset;border-spacing: 3px 5px;">
                            <thead>
                            <tr>
                                <td width="40%"></td>
                                <td></td>
                            </tr>
                            </thead>
                            <tr>
                                <th style="font-weight: 700;">用户Id:</th>
                                <th style="padding-left: 30px"><label>&nbsp;&nbsp;${user.user_id}</label><br></th>
                            </tr>
                            <tr>
                                <th><label for="input-label12345">用户名</label></th>
                                <th>
                                    <input name="user_id" type=text value="${user.username}" title="用户名" id="input-label12345">
                                </th>
                            </tr>
                            <tr>
                                <th><label for="input-label123456">昵称</label></th>
                                <th>
                                    <input style="display: inline-block" name="nick" type=text title="用户昵称" value="${user.nickname}" id="input-label123456">
                                </th>
                            </tr>
                            <tr>
                                <th><label for="input-label1234ds">密码</label></th>
                                <th>
                                    <input style="display: inline-block" type="password" placeholder="密码" id="input-label1234ds">
                                </th>
                            </tr>
                            <tr>
                                <th><label for="input-label12345sadasd">学校</label></th>
                                <th>
                                    <input style="display: inline-block" name="school" type=text title="" value="${user.school}" id="input-label12345sadasd">
                                </th>
                            </tr>
                            <tr>
                                <th><label for="input-label12dsf5">电子邮件</label></th>
                                <th>
                                    <input name="email" type=text title="" value="${user.email}" id="input-label12dsf5">
                                </th>
                            </tr>
                            <tr>
                                <th><label for="input-label1dsfads">备注</label></th>
                                <th>
                                    <input name="email" type=text title="" value="${user.remark}" id="input-label1dsfads">
                                </th>
                            </tr>
                            <tr>
                                <th>权限</th>
                                <th>
                                    <div style="display: inline">
                                        <i class="fa fa-${admin_level.contains("b")?'check-':''}square-o fa-fw" onclick="awesome_checkbox_click(this)" data-checked="false"></i>&nbsp;权限管理
                                        <i class="fa fa-${admin_level.contains("c")?'check-':''}square-o fa-fw" onclick="awesome_checkbox_click(this)" data-checked="false"></i>&nbsp;题目添加
                                        <i class="fa fa-${admin_level.contains("g")?'check-':''}square-o fa-fw" onclick="awesome_checkbox_click(this)" data-checked="false"></i>&nbsp;比赛组织
                                        <i class="fa fa-${admin_level.contains("h")?'check-':''}square-o fa-fw" onclick="awesome_checkbox_click(this)" data-checked="false"></i>&nbsp;代码查看
                                    </div>
                                    <br>
                                    <div style="display: inline">
                                        <i class="fa fa-${admin_level.contains("j")?'check-':''}square-o fa-fw" onclick="awesome_checkbox_click(this)" data-checked="false"></i>&nbsp;手动判题
                                        <i class="fa fa-${admin_level.contains("k")?'check-':''}square-o fa-fw" onclick="awesome_checkbox_click(this)" data-checked="false"></i>&nbsp;远程判题
                                        <i class="fa fa-${admin_level.contains("l")?'check-':''}square-o fa-fw" onclick="awesome_checkbox_click(this)" data-checked="false"></i>&nbsp;公告管理
                                        <i class="fa fa-${admin_level.contains("m")?'check-':''}square-o fa-fw" onclick="awesome_checkbox_click(this)" data-checked="false"></i>&nbsp;用户管理
                                    </div>
                                </th>
                            </tr>
                            <tr>
                                <th>验证码</th>
                                <th>
                                    <input type="text" placeholder="验证码" id="verifyCode" maxlength="4" onclick="getVerify(this)"
                                           class="popover-options" data-container="body" data-toggle="popover"
                                           data-content='<img id="verifyImage" alt="点击获取验证码" src="" onclick="getVerify(this)" height="">'>
                                </th>

                                <img style="display: none" alt="" src="" onclick="" height="" id="updateCodeHidden">
                                <input id="webkey" class="form-control" type="hidden" value="onlinejudge">
                            </tr>
                            <tr>
                                <th></th>
                                <th>
                                    <a class="btn btn-success btn-lg" onclick="CheckVerifyAndjsMd5_admin_user_edit();">&nbsp;&nbsp;修改&nbsp;&nbsp;</a>
                                </th>
                            </tr>
                        </table>

                    </center>

                </form>


                <jsp:include page="../../footer.jsp"/>
            </div>
        </div>
    </div>
</div>
<script>
    $(function () {
        Victor("container", "output"); //登陆背景函数
        $("#updateCodeHidden").attr("src", "/getVerify?" + Math.random());
        $(document).keydown(function (event) {
            if (event.keyCode === 13) {
                $("#entry_btn").click();
            }
        });
        $(".popover-options").popover({html: true});
        // $('.scroll-item').perfectScrollbar();
        init_Awesome_checkbox();
    });

    /**
     * (后台用户修改模块)点击确认修改判断信息
     */
    function CheckVerifyAndjsMd5_admin_user_edit() {
        $.ajax({
            url: "/checkVerify",
            type: "post",
            data: '{"code":"' + $("#verifyCode").val() + '"}',
            contentType: "application/json",
            success: function (result) {
                console.log(result);
                if (result === false) {
                    $("#msg").html("验证码错误");
                    console.log("verifyCode is wrong...");
                    swal({
                        icon: "error",
                        title: "验证码错误!",
                        text: "请输入正确的验证码!",
                        time: 800
                    });
                    return false;
                } else if (0 === 1) {
                    console.log("核对信息");
                    swal({
                        icon: "error",
                        title: "信息错误!",
                        text: "请输入正确的信息!",
                        time: 800
                    });
                    return false;
                } else {
                    console.log("verifyCode is correct...");
                    // $("#login").submit();
                    $.ajax({
                        url: "/admin_user_doEdit",
                        type: "post",
                        data: $("form").serialize(),
                        contentType: "text",
                        success: function (result) {
                            var res = jQuery.parseJSON(result);
                            console.log("result:" + res);
                            if (res.msgType + "" === "0") {
                                $.message({
                                    message: res.msg,
                                    type: "error",
                                    time: '2000',
                                    autoClose: true,
                                    showClose: false
                                });
                            } else {
                                swal({
                                    icon: "success",
                                    title: "操作成功!",
                                    text: res.msg,
                                    time: 800
                                });
                                setTimeout(function () {
                                    // window.location.href = "/admin_user_list";
                                }, 800)
                            }
                        }
                    });
                }
            }
        });
    }

</script>

</body>
</html>
