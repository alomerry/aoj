<%--
  Created by IntelliJ IDEA.
  User: wu1ji
  Date: 2019/1/29
  Time: 3:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta http-equiv="content-type" content="text/html;charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <%--bootstrap--%>
    <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css">
    <%--背景样式--%>
    <link rel="stylesheet" type="text/css" href="../../assets/css/backgroud-style.css">
    <%--jquery 3.2.1--%>
    <script src="https://cdn.jsdelivr.net/npm/jquery@3.2.1/dist/jquery.min.js"></script>
    <%--sweet弹出框--%>
    <script src="../../assets/js/sweetalert.min.js"></script>
    <%--bootstrap js--%>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js"></script>
    <%--背景js--%>
    <script type="text/javascript" src="../../assets/js/vector.js"></script>
    <%--前端md5 js--%>
    <script src="../../assets/js/md5.js"></script>

    <script src="../../assets/js/func.mo.js"></script>
    <title>Login</title>
</head>
<body>
<div id="container">
    <div id="output">
        <div class="containerT">
            <div class="container">
                <jsp:include page="../../header.jsp"/>
                <h1>用户登录</h1>
                <form>
                    <input type="text" placeholder="用户名" name="username">
                    <input type="password" placeholder="密码" id="md5Pwd">
                    <input type="hidden" name="passwd">
                    <input type="text" placeholder="验证码" id="verifyCode" maxlength="4" onclick="getVerify(this)"
                           class="popover-options" data-container="body" data-toggle="popover"
                           data-content='<img id="verifyImage" alt="点击获取验证码" src="" onclick="getVerify(this)" height="">'>
                    <input id="webkey" class="form-control" type="hidden" value="onlinejudge">
                    <button type="button" id="entry_btn" onclick="CheckVerifyAndjsMd5_index_user_login()">登录</button>
                </form>
            </div>
            <jsp:include page="../../footer.jsp"/>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(function () {
        Victor("container", "output"); //登陆背景函数
        $("#entry_name").focus();//聚焦input

        $("#updateCodeHidden").attr("src", "/getVerify?" + Math.random());//刷新页码
        $(document).keydown(function (event) {//检测回车按键
            if (event.keyCode === 13) {
                $("#entry_btn").click();
            }
        });
        $(".popover-options").popover({html: true});//验证码点击出现
    });

    /**
     * (前台登录模块)登录信息检测
     */
    function CheckVerifyAndjsMd5_index_user_login() {
        var md5Pwd = $("#md5Pwd"), passwd = $("input[name=passwd]");
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
                    } else if (md5Pwd.val() === "") {
                        console.log("passwd is missing...");
                        swal({
                            icon: "error",
                            title: "信息错误!",
                            text: "请输入正确的信息!",
                            time: 800
                        });
                        return false;
                    } else {
                        console.log("verifyCode is correct...");
                        passwd.val(hex_md5(encodeURIComponent(md5Pwd.val() + $("#webkey").val())));
                        // $("#login").submit();
                        $.ajax({
                            url: "/loginDo",
                            type: "post",
                            data: '{"username":"' + $("input[name=username]").val() + '","passwd":"' + passwd.val() + '"}',
                            contentType: "application/json",
                            success: function (result) {
                                var texts;
                                if (result.msgType === 1) {
                                    window.location.href = "/home";
                                } else {
                                    switch (result.msgType) {
                                        case 2: {
                                            texts = "用户名不存在!";
                                            break;
                                        }
                                        case 3: {
                                            texts = "密码错误!";
                                            break;
                                        }
                                    }
                                    swal({
                                        icon: "",
                                        title: "登陆失败!",
                                        text: "" + texts,
                                        time: 800
                                    });
                                }
                            }
                        });
                    }
                }
            }
        );
    }
</script>
</body>
</html>