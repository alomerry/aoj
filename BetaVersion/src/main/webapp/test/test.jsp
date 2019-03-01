<%--
  Created by IntelliJ IDEA.
  User: wu1ji
  Date: 2019/1/24
  Time: 13:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta http-equiv="content-type" content="text/html;charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

    <link rel="stylesheet" type="text/css" href="../assets/css/backgroud-style.css">
    <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/jquery@3.2.1/dist/jquery.min.js"></script>
    <script type="application/javascript" src="../assets/js/sweetalert.min.js"></script>
    <script type="text/javascript" src="../assets/js/vector.js"></script>
    <title>Online Judge</title>
</head>
<body>
<div id="container">
    <div id="output">
        <div class="containerT">
            <div class="container">
                <jsp:include page="../header.jsp"/>

                <div>
                    <center>
                        <table class="table table-striped">
                            <thead>
                            <tr class='evenrow'>
                                <th style="text-align: center" width="30%">测试内容</th>
                                <th style="text-align: center">Test</th>
                                <th style="text-align: center" width="30%">测试内容</th>
                                <th style="text-align: center">Test</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <th style="text-align: center">SweetAlert+Jquery刷新</th>
                                <th style="text-align: center"><a class="btn-success btn" onclick="test()">测试</a></th>
                                <th style="text-align: center">***</th>
                                <th style="text-align: center"><a class="btn-info btn" onclick="">测试</a></th>
                            </tr>
                            </tbody>
                        </table>
                    </center>
                </div>

                <div class="row clearfix">
                    <div class="col-md-3 column">
                        <div class="row clearfix">
                            <div class="col-md-12 column">
                                <form class="form-horizontal" role="form" action="${pageContext.request.contextPath}/test_trascationA">
                                    <h4>事务测试</h4>
                                    <div class="form-group">
                                        <label style="margin-right: 80px" for="contest_id">contest_id</label>
                                        <div class="col-sm-10">
                                            <input style="width: 50%" type="text" id="contest_id" name="contest_id"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="user_id" style="margin-right: 80px">user_id</label>
                                        <div class="col-sm-10">
                                            <input style="width: 50%" type="text" id="user_id" name="user_id"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class=" col-sm-10">
                                            <button type="submit" class="btn btn-warning" style="margin-left: 20px">测试</button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3 column">
                        <form class="form-horizontal" role="form">
                            <h4>查询空数据</h4>
                            <div class="form-group">
                                <label style="margin-right: 80px" for="problem_id">problem_id</label>
                                <div class="col-sm-10">
                                    <input style="width: 50%" type="text" id="problem_id"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class=" col-sm-10">
                                    <button type="submit" class="btn btn-warning" style="margin-left: 20px;margin-top: 77px" onclick="testNum()">测试</button>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="col-md-3 column">
                        <form class="form-horizontal" role="form">
                            <h4>查询空数据</h4>
                            <div class="form-group">
                                <label style="margin-right: 80px" for="problem_id">problem_id</label>
                                <div class="col-sm-10">
                                    <%--<input style="width: 50%" type="text" id="problem_id"/>--%>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class=" col-sm-10">
                                    <button type="submit" class="btn btn-warning" style="margin-left: 20px;margin-top: 77px" onclick="testNum()">测试</button>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="col-md-3 column">
                    </div>
                </div>
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

    function test() {
        swal({
            title: "Success",
            text: "操作成功！",
            icon: "success",
            timer: 600,
            buttons: false
        }).then(function (value) {
            if (value == true) {
            }
        });
        setTimeout(function () {
            console.log("刷新！");
            window.location.reload();//刷新当前页面.
        }, 600);
        // window.location.replace("/test");
        // swal("操作成功", {
        //     buttons: false,
        //     timer: 500,
        // });
    }

    function testNum() {
        console.log("problem_id:" + $("#problem_id").val());
        $.ajax({
            url: "/test_searchNull",
            type: "post",
            data: '{"problem_id":"' + $("#problem_id").val() + '"}',
            contentType: "application/json",
            success: function (res) {
                swal({
                    title: "Success",
                    text: res,
                    icon: "success",
                    timer: 2000,
                })
            }
        });
    }
</script>
</body>
</html>