<%--
  Created by IntelliJ IDEA.
  User: wu1ji
  Date: 2019/1/28
  Time: 23:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Problem Add</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../../assets/css/styles.css">
    <script src="../../assets/js/sweetalert.min.js"></script>
    <script src="../../assets/js/sort.js"></script>
    <style type="text/css">
        div.my_circle {
            /*border: 1px solid;*/
            border-top-left-radius: 0.5em;
            border-top-right-radius: 0.5em;
            border-bottom-left-radius: 0.5em;
            border-top-left-radius: 0.5em;
            border-top-right-radius: 0.5em;
            border-bottom-right-radius: 0.5em;
            border-bottom-left-radius: 0.5em;
            border-bottom-right-radius: 0.5em;
            /*background-color: white;*/
            padding: 15px;
        }
    </style>
</head>
<body>
<jsp:include page="../../ico.jsp" flush="true"/>

<div class="container">
    <div class="jumbotron">
        <div class="widget-content nopadding">
            <c:if test="${!empty msg}">
                <div class="thinner alert alert-dismissable ${msgType == 1?'alert-success':'alert-warning'}">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                    <strong>${msgType == 1?添加题目成功:添加题目失败}</strong>${msg}
                </div>
            </c:if>
            <form style="margin-left:50px" method="post" id="contest-do" action="/admin_contest_doadd">
                <div class="row clearfix">
                    <div class="col-md-6 column">
                        <div class="form-group">
                            <label for="problem_title">标题</label>
                            <input type="text" class="form-control thinner" id="problem_title" placeholder="标题" name="title">
                        </div>


                        <div class="form-group">
                            <label for="problem_discribe">题目描述</label>
                            <textarea rows="5" class="form-control thinner" id="problem_discribe" placeholder="竞赛描述" name="description"></textarea>
                        </div>
                        <div class="form-group">
                            <label for="problem_organizer">主办方</label>
                            <input type="text" class="form-control thinner" id="problem_organizer" placeholder="主办方" name="organizer">
                        </div>
                        <div class="form-group">
                            <label for="problem_max">参赛人数</label>
                            <input type="text" class="form-control thinner" id="problem_max" placeholder="参赛人数" name="max">
                        </div>
                        <div class="form-group">
                            <label for="content_start">样例输入</label><br/>
                            <input type="text" class="form-control thinner" id="content_start">
                            <input type="hidden" class="form-control thinner" id="content_start_v" name="start_time">
                        </div>
                        <div class="form-group">
                            <label for="content_end">样例输出</label><br/>
                            <input type="text" class="form-control thinner" id="content_end">
                            <input type="hidden" class="form-control thinner" id="content_end_v" name="end_time">
                        </div>

                        <label>是否可报名</label>&nbsp;&nbsp;&nbsp;<input type="checkbox" name="access">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <label>公开级别</label>
                        <label class="radio-inline">
                            <input type="radio" name="privates" value="1" checked="checked"> 公开
                        </label>
                        <label class="radio-inline">
                            <input type="radio" name="privates" value="0"> 内部
                        </label>
                        <label class="radio-inline">
                            <input type="radio" name="privates" value="-1"> 私有
                        </label>
                        <br/><br/>

                        <label for="verifyCode">验证码</label>
                        <div class="form-group">
                            <input id="verifyCode" class="form-control thinner" style="width:20%;display: inline;" type="text" maxlength="4">
                            <img id="verifyImage" alt="click to change" src="" onclick="getVerify(this)" 0 height="30px">*
                            <input id="webkey" type="hidden" value="onlinejudge">
                            <label id="msg" class="alert-mine"></label>
                        </div>
                        <a style="margin-top:50px;margin-left: 50px" class="btn btn-primary btn-lg" onclick="show()">预览</a>
                        <a style="margin-top:50px;margin-left: 50px" class="btn btn-success btn-lg" onclick="ChechVerifyAndjsMd5()">提交</a>
                    </div>
                    <div class="col-md-6 column">
                        <div id="container" class="my_circle">
                            <center>
                                <h3>问题 1 : ${problem.title}</h3>
                                <span class=green>时间限制: </span>${problem.time_limit} ms&nbsp;&nbsp;
                                <span class=green>内存限制: </span>${problem.memory_limit}MB<br>
                                <span class=green>提交: </span>${problem.submit}&nbsp;&nbsp;
                                <span class=green>解决: </span>${problem.accepted}<br>
                                <%--[<a href=''>提交</a>]--%>
                                <%--[<a href=''>状态</a>]--%>
                                <%--[<a href=''>讨论版</a>]--%>
                                <%--[命题人:<span id='creator'></span>]--%>
                            </center>
                            <h2>题目描述</h2>
                            <div class=content>
                                <p>${problem.description}
                                    <span><span style="font-size:14px;">666<br/></span></span>
                                </p>
                            </div>
                            <h2>输入</h2>
                            <div class=content>
                                <p>${problem.input}
                                    <span><span style="font-size:14px;">666<br/></span></span>
                                </p>
                            </div>
                            <h2>输出</h2>
                            <div class=content>
                                ${problem.output}
                                <span style="font-size:14px;">666<br/></span>
                            </div>
                            <h2>样例输入</h2>
                            <pre>100 200 150 140 129 134 167 198
200 111
110
</pre>
                            <h2>样例输出</h2>
                            <pre>5
</pre>
                            <h2>来源</h2>
                            <div class=content>
                                <p><a href='problemset.php?search='></a>&nbsp;</p>
                            </div>
                            <center>
                                [<a href=''>提交</a>]
                                [<a href=''>状态</a>]
                            </center>
                        </div>
                    </div>
                </div>


            </form>

        </div>
    </div>
</div>

<jsp:include page="../../footer.jsp" flush="true"/>

<script type="text/javascript">
    $(document).ready(function () {
        var verifyImage = document.getElementById("verifyImage");
        verifyImage.src = "/getVerify?" + Math.random();
    });

    function getVerify(obj) {
        obj.src = "/getVerify?" + Math.random();
        console.log("verifyCode update...");
        $("#msg").html("");//.innerText = "验证码错误";
    }

    function ChechVerifyAndjsMd5() {

        var completeFlag = $.ajax({
                url: "/checkVerify",
                type: "post",
                data: '{"code":"' + $("#verifyCode").val() + '"}',
                contentType: "application/json",
                success: function (result) {
                    console.log(result);
                    if (result == false) {
                        $("#msg").html("验证码错误");
                        console.log("verifyCode is wrong...");
                        return false;
                    } else if ($("#contest_title").val() == "") {
                        console.log("请输入标题...");
                        return false;
                    } else if ($("#content_end").val() == "") {
                        console.log("请输入结束时间...");
                        return false;
                    } else if ($("#content_start").val() == "") {
                        console.log("请输入...");
                        return false;
                    } else {
                        console.log("verifyCode is correct...");
                        $("#contest-do").submit();
                    }
                }
            }
            )
        ;
        formatDateTimelocal($("#content_start").val(), $("#content_end").val())
    }

    function show() {
        $("#contest_title")
        $("#problem_discribe")

    }

</script>
</body>
</html>
