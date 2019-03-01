<%--
  Created by IntelliJ IDEA.
  User: wu1ji
  Date: 2018/11/30
  Time: 9:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Online Judge</title>
    <link rel="stylesheet" href="../../assets/css/styles.css">
    <script src="../../assets/js/sweetalert.min.js"></script>
    <style type="text/css">
        .swal-overlay {
            background-color: rgba(43, 165, 137, 0.45);
        }

        .swal-footer {
            background-color: rgb(245, 248, 250);
            margin-top: 32px;
            border-top: 1px solid #E9EEF1;
            overflow: hidden;
        }
    </style>
</head>
<body>
<jsp:include page="../../header.jsp"/>
<div class="container">
    <center>
        <h2>${contest.title}</h2>
        [<a href='problemstatus.php?id=1000'>状态</a>][<a href='bbs.php?pid=1000'>公开/内部</a>][可报名<span id='creator'></span>]
    </center>
    <!--StartMarkForVirtualJudge-->
    <h2>主办方</h2>
    <div class=content>
        <pre class=content><span class=sampledata></span>${contest.organizer}</pre>
    </div>
    <h2>开始时间</h2>
    <pre class=content><span class=sampledata></span>${contest.start_time.toString()}</pre>
    <h2>结束时间</h2>
    <pre>${contest.end_time.toString()}</pre>
    <h2>比赛描述</h2>
    <div class=content>
            <pre class=content><span style="font-size:20px">${contest.describes}</span>
            </pre>
    </div>
    <center>
        <!--EndMarkForVirtualJudge-->
        <c:choose>
            <c:when test="${empty online_judge_locuser}">
                <a class="btn btn-success btn-lg" title="请先登录" onclick="alerts();">报名</a>
            </c:when>
            <c:otherwise>
                <a class="btn btn-success btn-lg" onclick="doAdd(${contest.contest_id})">报名</a>
            </c:otherwise>
        </c:choose>
    </center>
</div>
<jsp:include page="../../footer.jsp"/>

<script type="text/javascript">
    function doAdd(contestId) {
        var con = $.ajax({
                url: "/contest_doadd",
                type: "post",
                data: '{"contest_id":"' + contestId + '"}',
                contentType: "application/json",
                success: function (result) {
                    console.log(result);
                    var type = result.msgType;
                    var msg = result.msg;
                    if (type == 0) {
                        swal({
                            icon: "error",
                            title: msg,
                        })
                    } else {
                        swal({
                            icon: "success",
                            title: msg,
                        })
                    }
                    return false;
                }

            }
        );
    }

    function alerts() {
        swal({title: "请先登录",});
        console.log("请先登录");
    }
</script>
</body>
</html>
