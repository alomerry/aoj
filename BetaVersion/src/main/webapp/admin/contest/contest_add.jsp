<%--
  Created by IntelliJ IDEA.
  User: wu1ji
  Date: 2019/1/19
  Time: 9:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Contest Add</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../../assets/css/styles.css">
    <style type="text/css">
        input.thinner {
            width: 60%;
        }

        textarea.thinner {
            width: 60%;
        }

        div.thinner {
            width: 60%;
            margin-left: 45px;
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
                    <strong>${msgType == 1?添加比赛成功:添加比赛失败}</strong>${msg}
                </div>
            </c:if>
            <form style="margin-left:50px" method="post" id="contest-do" action="/admin_contest_doadd">
                <div class="row clearfix">
                    <div class="col-md-6 column">
                        <div class="form-group">
                            <label for="contest_title">竞赛标题</label>
                            <input type="text" class="form-control thinner" id="contest_title" placeholder="竞赛标题" name="title">
                        </div>

                        <div class="form-group">
                            <label for="contest_organizer">主办方</label>
                            <input type="text" class="form-control thinner" id="contest_organizer" placeholder="主办方" name="organizer">
                        </div>

                        <div class="form-group">
                            <label for="contest_discribe">竞赛描述</label>
                            <textarea rows="5" class="form-control thinner" id="contest_discribe" placeholder="竞赛描述" name="describes"></textarea>
                        </div>
                        <div class="form-group">
                            <label for="contest_max">参赛人数</label>
                            <input type="text" class="form-control thinner" id="contest_max" placeholder="参赛人数" name="max">
                        </div>
                    </div>
                    <div class="col-md-6 column">
                        <div class="form-group">
                            <label for="content_start">开始时间</label><br/>
                            <input type="datetime-local" class="form-control thinner" id="content_start">
                            <input type="hidden" class="form-control thinner" id="content_start_v" name="start_time">
                        </div>
                        <div class="form-group">
                            <label for="content_end">结束时间</label><br/>
                            <input type="datetime-local" class="form-control thinner" id="content_end">
                            <%--<input type="date" class="form-control thinner" id=>--%>
                            <%--<input type="time" class="form-control thinner" id=>--%>
                            <input type="hidden" class="form-control thinner" id="content_end_v" name="end_time">
                        </div>

                        <label>是否可报名</label>&nbsp;&nbsp;&nbsp;<input type="checkbox" name="access">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <label>是否公开</label>
                        <label class="radio-inline">
                            <input type="radio" name="privates" value="1" checked="checked"> 公开
                        </label>
                        <label class="radio-inline">
                            <input type="radio" name="privates" value="0"> 内部
                        </label>
                        <br/><br/>

                        <label for="verifyCode">验证码</label>
                        <div class="form-group">
                            <input id="verifyCode" class="form-control thinner" style="width:20%;display: inline;" type="text" maxlength="4">
                            <img id="verifyImage" alt="click to change" src="" onclick="getVerify(this)" 0 height="30px">*
                            <input id="webkey" type="hidden" value="onlinejudge">
                            <label id="msg" class="alert-mine"></label>
                        </div>
                    </div>
                    <a style="margin-top:50px;margin-left: 50px" class="btn btn-success btn-lg" onclick="ChechVerifyAndjsMd5()">提交</a>
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

    function formatDateTimelocal(date_start, date_end) {
        //2019-01-09T03:33
        //2019-01-23 10:44:02.709

        var start = myslice(date_start);
        var end = myslice(date_end);

        $("#content_start_v").val(start);
        $("#content_end_v").val(end);

        console.log("" + start);
        console.log("" + end);
    }

    function myslice(date) {
        return date.slice(0, 10) + " " + date.slice(11) + ":00.0";
    }
</script>
</body>
</html>
