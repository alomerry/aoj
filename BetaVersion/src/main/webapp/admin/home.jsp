<%--
  Created by IntelliJ IDEA.
  User: wu1ji
  Date: 2019/1/29
  Time: 5:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin Home</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta http-equiv="content-type" content="text/html;charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="../assets/css/backgroud-style.css">
    <script src="https://cdn.jsdelivr.net/npm/jquery@3.2.1/dist/jquery.min.js"></script>
    <script type="text/javascript" src="../assets/js/vector.js"></script>
</head>
<body>
<div id="container">
    <div id="output">
        <div class="containerT">
            <div class="container">
                <jsp:include page="../ico.jsp"/>
                <jsp:include page="../footer.jsp"/>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(function () {
        Victor("container", "output"); //登陆背景函数
        $("#entry_name").focus();
        $(document).keydown(function (event) {
            if (event.keyCode == 13) {
                $("#entry_btn").click();
            }
        });
    });
</script>
</body>
</html>