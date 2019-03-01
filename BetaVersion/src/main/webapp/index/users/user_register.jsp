<%--
  Created by IntelliJ IDEA.
  User: wu1ji
  Date: 2019/1/29
  Time: 5:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta http-equiv="content-type" content="text/html;charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <%--蓝色背景--%>
    <link rel="stylesheet" type="text/css" href="../../assets/css/backgroud-style.css">
    <script type="text/javascript" src="../../assets/js/vector.js"></script>
    <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/jquery@3.2.1/dist/jquery.min.js"></script>
    <%--甜蜜弹窗--%>
    <script type="text/javascript" src="../../assets/js/sweetalert.min.js"></script>
    <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js"></script>

    <title>Register</title>
</head>
<body>
<div id="container">
    <div id="output">
        <div class="containerT">
            <div class="container">
                <jsp:include page="../../header.jsp"/>
                <h1>用户注册</h1>
                <form>
                    <center>
                        <table>
                            <tr style="margin: 50px">
                                <th style="padding-right: 10px">
                                    <div class="form-group">
                                        <input type="text" placeholder="用户名" size=20 name="username" maxlength="16" class="own-popover" data-container="body" data-toggle="popover" data-content="">
                                    </div>
                                    <div class="form-group">
                                        <input name="passwd" size=20 type=password placeholder="请输入密码" maxlength="16" class="own-popover" data-container="body" data-toggle="popover" data-content="">
                                    </div>
                                    <div class="form-group">
                                        <input name="rptPwd" size=20 type=password placeholder="请再次输入密码" maxlength="16" class="own-popover" data-container="body" data-toggle="popover" data-content="">
                                    </div>
                                    <div class="form-group">
                                        <input type="text" name="nickname" size=50 id="nick" placeholder="请输入昵称" maxlength="10"/>
                                    </div>
                                </th>
                                <th style="padding-left: 10px">

                                    <div class="form-group">
                                        <input name="school" size=30 type=text placeholder="请输入学校" maxlength="10">
                                    </div>
                                    <div class="form-group">
                                        <input name="email" size=30 type=text placeholder="请输入电子邮箱">
                                    </div>
                                    <div class="form-group">
                                        <input type="text" placeholder="验证码" id="verifyCode" maxlength="4" onclick="getVerify(this)"
                                               class="popover-options" data-container="body" data-toggle="popover" data-content='<img id="verifyImage" alt="点击获取验证码" src="" onclick="getVerify(this)" height="">'>
                                        <a class="own-popover" style="float:left;" data-container="body" data-toggle="popover" data-content="验证码错误"></a>
                                        <input id="webkey" class="form-control" type="hidden" value="onlinejudge">
                                    </div>
                                    <div class="form-group">
                                        <input value="重置" name="reset" type="reset" class="btn">
                                    </div>
                                </th>
                            </tr>
                        </table>
                        <button type="button" class="btn btn-success" onclick="ChechVerifyAndjsMd5()" href="">提交</button>
                    </center>


                </form>
            </div>
        </div>
    </div>
</div>
<script src="../../assets/js/md5.js"></script>
<script type="text/javascript">
    $(function () {
        Victor("container", "output"); //登陆背景函数
        $("#entry_name").focus();

        // var verifyImage = document.getElementById("verifyImage");
        // verifyImage.src = "/getVerify?" + Math.random();
        // $("#verifyImage").attr("src", "/getVerify?" + Math.random());
        $("#updateCodeHidden").attr("src", "/getVerify?" + Math.random());
        $(document).keydown(function (event) {
            if (event.keyCode === 13) {
                $("#entry_btn").click();
            }
        });
        $(".popover-options").popover({html: true});
    });

    // $('body').on('click', function (event) {
    //
    // };

    $(document).ready(function () {
        var verifyImage = document.getElementById("verifyImage");
        verifyImage.src = "/getVerify?" + Math.random();
    });

    function getVerify(obj) {
        var tmp = "/getVerify?" + Math.random();
        if (obj != null) {
            obj.src = "/getVerify?" + Math.random();
        } else {
            tmp = "";
            console.log(tmp);
        }
        console.log("verifyCode update...");
    }

    function ChechVerifyAndjsMd5() {
        $.ajax({
                url: "/checkVerify",
                type: "post",
                data: '{"code":"' + $("#verifyCode").val() + '"}',
                contentType: "application/json",
                success: function (result) {
                    console.log(result);
                    if (result === false) {
                        swal({
                            icon: "error",
                            title: "验证码错误!",
                            text: "请输入正确的验证码!",
                            time: 800
                        });
                        console.log("verifyCode is wrong...");
                        return false;
                    } else {
                        if (checkForm()) {
                            var webkey = $("#webkey");
                            var dat = getFromData() + '"passwd":"' + hex_md5(encodeURIComponent($("input[name=passwd]").val() + webkey.val())) +
                                '","rptPwd":"' + hex_md5(encodeURIComponent($("input[name='rptPwd']").val() + webkey.val())) + '"}';
                            $.ajax({
                                url: "/registerDo",
                                type: "post",
                                data: dat,
                                contentType: "application/json",
                                success: function (res) {
                                    //注册成功4    //用户名已存在5   //两次密码不一致6
                                    if (res.msgType === 4) {
                                        swal({
                                            icon: "success",
                                            title: "注册成功!",
                                            text: "欢迎!",
                                            time: 800
                                        });
                                        setTimeout(function () {
                                            window.location.href = "/home";
                                        }, 850);
                                    } else {
                                        swal({
                                            icon: "error",
                                            title: "注册失败!",
                                            text: res.msg,
                                            time: 800
                                        });
                                    }
                                }
                            });
                        }
                    }
                }
            }
        )
        ;
    }

    function getFromData() {
        var user_username = $("input[name='username']"), user_email = $("input[name='email']"),
            user_nickname = $("input[name='nickname']"), user_school = $("input[name='school']");
        return '{"username": "' + user_username.val() + '", "nickname": "' + user_nickname.val() + '", "school": "' +
            user_school.val() + '", "email": "' + user_email.val() + '", ';
    }

    function checkForm() {
        var flag = true;
        var user_username = $("input[name='username']"), user_pwd = $("input[name='passwd']"),
            user_nickname = $("input[name='nickname']");

        if (user_nickname.val() === "") {
            user_nickname.attr("data-content", "请输入昵称").popover("show");
            setTimeout(function () {
                user_nickname.popover('hide');
            }, 2000);
            flag = false;
        }
        if (user_username.val() === "") {
            user_username.attr("data-content", "请输入用户名").popover("show");
            setTimeout(function () {
                user_username.popover('hide');
            }, 2000);
            flag = false;
        } else if (user_username.val().length < 5) {
            user_username.attr("data-content", "用户名太短").popover("show");
            setTimeout(function () {
                user_username.popover('hide');
            }, 2000);

            console.log("用户名太短...");
            flag = false;
        }
        if (user_pwd.val() === "") {
            user_pwd.attr("data-content", "请输入密码").popover("show");
            setTimeout(function () {
                user_pwd.popover('hide');
            }, 2000);

            flag = false;
        } else if (user_pwd.val().length < 6) {
            user_pwd.attr("data-content", "密码太短").popover("show");
            setTimeout(function () {
                user_pwd.popover('hide');
            }, 2000);
            flag = false;
        }
        if ($("input[name='rptPwd']").val() !== user_pwd.val()) {
            user_pwd.attr("data-content", "两次密码不一致").popover("show");
            setTimeout(function () {
                user_pwd.popover('hide');
            }, 2000);
            flag = false;
        }
        console.log("核查表单" + flag ? "无问题" : "有错误");
        return flag;
    }
</script>
</body>
</html>
