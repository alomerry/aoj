<%@ page import="mo.utils.string.StringValue" %><%--
  Created by IntelliJ IDEA.
  User: wu1ji
  Date: 2019/1/25
  Time: 19:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta http-equiv="content-type" content="text/html;charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta charset="UTF-8">
    <title>参加的比赛</title>
    <%--bootstrap--%>
    <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css">
    <%--背景样式--%>
    <link rel="stylesheet" type="text/css" href="../../assets/css/backgroud-style.css">
    <%--jquery 3.2.1--%>
    <script src="https://cdn.jsdelivr.net/npm/jquery@3.2.1/dist/jquery.min.js"></script>
    <%--sweet弹出框--%>
    <script src="../../assets/js/sweetalert.min.js"></script>
    <%--背景js--%>
    <script type="text/javascript" src="../../assets/js/vector.js"></script>

</head>
<body>
<div id="container">
    <div id="output">
        <div class="containerT">
            <jsp:include page="../../header.jsp"/>
            <div class="container">
                <div>
                    <table id=result-tab class="table table-striped content-box-header" align=center width=90%>
                        <thead>
                        <input id="orderByType" type="hidden" value="title"/>
                        <tr class='toprow'>
                            <th type="id">竞赛Id<!--<a class="caret" onclick="adminContestListOrderBy(this)"></a>--></th>
                            <th type="title">标题<!--<a class="caret" onclick="adminContestListOrderBy(this)"></a>--></th>
                            <th type="status" width="10%">状态<!--<a class="caret" onclick="adminContestListOrderBy(this)"></a>--></th>
                            <th type="start">开始时间<!--<a class="caret" onclick="adminContestListOrderBy(this)"></a>--></th>
                            <th>&nbsp;&nbsp;<a class="arrow-top"></a></th>
                        </tr>
                        </thead>


                        <!-- 预设枚举值 -->
                        <c:set var="STATUS_RUNNING" value="<%=StringValue.CONTEST_STATUS.STATUS_RUNNING %>"/>
                        <c:set var="STATUS_OVER" value="<%=StringValue.CONTEST_STATUS.STATUS_OVER %>"/>
                        <c:set var="STATUS_READY" value="<%=StringValue.CONTEST_STATUS.STATUS_READY %>"/>
                        <c:set var="STATUS_AVAILABLE" value="<%=StringValue.CONTEST_STATUS.STATUS_AVAILABLE %>"/>


                        <tbody>
                        <c:forEach items="${contests}" var="contestApply" varStatus="i">
                            <c:choose>
                                <c:when test="${i.index%2==0}">
                                    <tr class='oddrow'>
                                </c:when>
                                <c:otherwise>
                                    <tr class='evenrow'>
                                </c:otherwise>
                            </c:choose>
                            <th>${contestApply.contest.contest_id}</th>
                            <th>${contestApply.contest.title}</th>
                            <th>
                                <div class="row clearfix">
                                    <div class="col-md-12 column">
                                        <c:choose>
                                            <c:when test='${contestApply.status == 0}'>
                                                <span class="label label-warning" id="i_status_${contestApply.contest.contest_id}">等待确认</span>
                                            </c:when>
                                            <c:when test="${contestApply.contest.status eq STATUS_READY}">
                                                <span class="label label-success" id="i_status_${contestApply.contest.contest_id}">待开始</span>
                                            </c:when>
                                            <c:when test="${contestApply.contest.status eq STATUS_AVAILABLE}">
                                                <span class="label label-primary" id="i_status_${contestApply.contest.contest_id}">待开始</span>
                                            </c:when>
                                            <c:when test="${contestApply.contest.status eq STATUS_RUNNING}">
                                                <span class="label label-warning" id="i_status_${contestApply.contest.contest_id}">进行中</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="label label-default" id="i_status_${contestApply.contest.contest_id}">已结束</span>
                                            </c:otherwise>
                                        </c:choose>

                                    </div>
                                </div>
                            </th>
                            <th id="time_${contestApply.contest.contest_id}">${contestApply.contest.start_time.toString()}</th>
                            <th>
                                <span id="end_${contestApply.contest.contest_id}" style="display: none">${contestApply.contest.end_time.toString()}</span>
                                <span class='btn btn-info btn-xs' onclick='checkTime(${contestApply.contest.contest_id})'>开始比赛</span>
                            </th>
                            </tr>
                        </c:forEach>

                        </tbody>
                    </table>
                    <form style="display: none" action="/own_competition_list" method="get" id="form_need">
                        <input type="hidden" name="contest_id" value=""/>
                    </form>
                </div>
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

    function checkTime(id) {
        var start = new Date($("#time_" + id).html());
        var end = new Date($("#end_" + id).html());
        var now = new Date();
        console.log(start.getTime());
        console.log(end.getTime());
        console.log("开始时间：" + start.getHours() + ":" + start.getMinutes() + ":" + start.getSeconds());
        console.log("结束时间：" + end.getHours() + ":" + end.getMinutes() + ":" + end.getSeconds());
        console.log("当前时间：" + now.getHours() + ":" + now.getMinutes() + ":" + now.getSeconds() + "\n");
        if (start.getTime() <= now.getTime()) {
            if (end.getTime() > now.getTime()) {
                // swal({
                //     icon: "success",
                //     title: "比赛开始",
                //     buttons: false,
                // });
                // window.location.href=
                $.ajax({
                        url: "/own_competition_check",
                        type: "post",
                        data: '{"contest_id":"' + id + '"}',
                        contentType: "application/json",
                        success: function (res) {
                            var type = res.type;
                            console.log(typeof (type));
                            console.log(type);
                            switch (type) {
                                case "-1": {
                                    swal({
                                        icon: "error",
                                        title: "比赛未开始",
                                        closeOnClickOutside: false,
                                    });
                                    break;
                                }
                                case "0": {
                                    $("input[name=contest_id]").val(id);
                                    $("#form_need").submit();
                                }
                                case "1": {
                                    swal({
                                        icon: "error",
                                        title: "比赛已结束！",
                                        closeOnClickOutside: false,
                                    });
                                    break;
                                }
                                case "2": {
                                    swal({
                                        icon: "error",
                                        title: "您未加入比赛！",
                                        closeOnClickOutside: false,
                                    });
                                    break;
                                }
                                case "3": {
                                    console.log("致电110，联系管理员吴");
                                    swal({
                                        icon: "error",
                                        title: "申请尚未确认",
                                        text: "请联系管理员批准！",
                                        closeOnClickOutside: false,
                                    });
                                    break;
                                }
                            }
                        }
                    }
                );
            } else {
                swal({
                    icon: "error",
                    title: "比赛结束",
                    closeOnClickOutside: false,
                });
            }
        } else {
            var tmp = $("#i_status_" + id).html();
            console.log("tmp:" + tmp);
            if (tmp == ("等待确认")) {
                console.log("致电110，联系管理员吴");
                swal({
                    icon: "error",
                    title: "申请尚未确认",
                    text: "请联系管理员批准！",
                    closeOnClickOutside: false,
                });
            } else {
                swal({
                    icon: "error",
                    title: "比赛未开始",
                    closeOnClickOutside: false,
                });
            }

        }
    }
</script>
</body>
</html>
