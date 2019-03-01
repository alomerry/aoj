<%--
  Created by IntelliJ IDEA.
  User: wu1ji
  Date: 2019/1/29
  Time: 4:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta http-equiv="content-type" content="text/html;charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <style>
        p.copyright, #online_numer {
            font-size: 15px;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="footer-basic">
        <footer>
            <div class="social"></div>
            <p class="copyright">就当一次路过丶 © 2019</p>
            <p class="copyright">当前在线人数：<span id="online_numer"></span></p>
        </footer>
    </div>
</div>
<script>
    $(document).ready(function () {
        $.ajax({
                url: "/online_user_num",
                type: "get",
                contentType: "applicaiton/json",
                success: function (result) {
                    console.log("当前在线人数：" + result);
                    $("#online_numer").html(result);
                }
            }
        )
    });
</script>
</body>
</html>