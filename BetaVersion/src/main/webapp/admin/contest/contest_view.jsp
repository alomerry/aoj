<%--
  Created by IntelliJ IDEA.
  User: wu1ji
  Date: 2019/1/28
  Time: 1:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="content-type" content="text/html;charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta charset="UTF-8">
    <title>Contest View</title>
    <link rel="stylesheet" type="text/css" href="../../assets/css/backgroud-style.css">

    <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/jquery@3.2.1/dist/jquery.min.js"></script>

    <script type="text/javascript" src="../../assets/js/vector.js"></script>
    <script type="text/javascript" src="../../assets/js/func.mo.js"></script>
    <link rel="stylesheet" href="../../assets/css/styles.css">

    <%--列表--%>
    <link rel="stylesheet" type="text/css" href="../../assets/css/component.css"/>
</head>
<body>
<div id="container">
    <div id="output">
        <div class="containerT">
            <jsp:include page="../../ico.jsp" flush="true"/>
            <div class="container">
                <div class="row clearfix">
                    <div class="col-md-12 column">
                        <div class="scroll-item my_circle">
                            <table class="sticky-enabled" style="border-collapse: unset;border-spacing: 3px 5px;">
                                <thead>
                                <input id="sort" type="hidden" value="${empty sort? desc:sort}"/>
                                <input id="orderByType" type="hidden" value="${empty orderByType? contest_id:orderByType}"/>
                                <tr class='toprow'>
                                    <th>
                                        <i id="i_father" class="fa fa-circle-o" onclick="selectAll(this)"></i>
                                    </th>
                                    <th type="contest_id">竞赛Id<a class="caret" onclick="adminContestApplyListOrderBy(this)"></a>
                                    </th>
                                    <th type="title">标题
                                        <%--<a class="caret" onclick="adminContestApplyListOrderBy(this)"></a>--%>
                                    </th>
                                    <th type="user_id" width="10%">用户Id<a class="caret" onclick="adminContestApplyListOrderBy(this)"></a>
                                    </th>
                                    <th type="username">用户名
                                        <%--<a class="caret" onclick="adminContestApplyListOrderBy(this)"></a>--%>
                                    </th>
                                    <th>操作<a class="arrow-top"></a></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${applies}" var="item" varStatus="i">
                                    <tr>
                                        <td>
                                            <span class="underSel">
                                                <i class="fa fa-circle-o" onclick="selectItem(this)"></i>
                                             </span>
                                        </td>
                                        <td>
                                            <div class=center style="margin-left: 10px">
                                                <a><span id="span_${item.contest.contest_id}">${item.contest.contest_id}</span></a>
                                            </div>
                                        </td>
                                        <td>${item.contest.title}</td>
                                        <td class='hidden-xs'>
                                            <div class=red style="margin-left: 10px">
                                                <a><span id="span_${item.user.user_id}">${item.user.user_id}</span></a>
                                            </div>
                                        </td>
                                        <td class='hidden-xs'>
                                            <div class=red>${item.user.username}</div>
                                        </td>
                                        <td>
                                            <a class='btn btn-info btn-xs' onclick="adminConfirmApply(${item.contest.contest_id},${item.user.user_id})">同意</a>
                                            <a class="btn btn-danger btn-xs" onclick="adminDeleteApply(${item.contest.contest_id},${item.user.user_id})">拒绝</a>
                                        </td>
                                    </tr>
                                </c:forEach>

                                </tbody>
                            </table>
                            <c:if test="${applies.size() != 0}">
                                <span class='btn btn-success'>同意</span>
                                <span><a class="btn btn-danger">拒绝</a></span>
                            </c:if>
                        </div>
                    </div>
                </div>
                <div class="row clearfix">
                    <div class="col-md-12 column">
                        <table class="table table-striped" align="center" style="width:90%;">
                            <thead>
                            <tr class='evenrow'>
                                <th width="10%">题目编号</th>
                                <th width="25%">标题</th>
                                <th>状态</th>
                                <th width="10%">正确</th>
                                <th width="10%">提交</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${competition}" var="s" varStatus="i">
                                <c:choose>
                                    <c:when test="${i.index % 2 == 0}">
                                        <tr class='oddrow'>
                                    </c:when>
                                    <c:otherwise>
                                        <tr class='evenrow'>
                                    </c:otherwise>
                                </c:choose>
                                <td>${s.num}</td>
                                <td><a href="/problem_detail?problem_id=${s.problem.problem_id}">${s.title}</a></td>
                                <td>未答题</td>
                                <td>${s.problem.accepted}</td>
                                <td>${s.problem.submit}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <jsp:include page="../../footer.jsp" flush="true"/>
        </div>
    </div>
</div>
<script type="text/javascript" src="../../assets/js/divscroll.js"></script>
<script type="text/javascript">
    $(function () {
        Victor("container", "output"); //登陆背景函数
        // $("#entry_name").focus();
        $(document).keydown(function (event) {
            if (event.keyCode === 13) {
                $("#entry_btn").click();
            }
        });
        $('.scroll-item').perfectScrollbar();
    });

    $(document).ready(function () {
        var input = $("#orderByType");
        //读取分类的值
        var type = input.val();
        input.next("tr").children("th").each(function () {
            // console.log("---" + $(this).attr("type"));
            if ($(this).attr("type") == type) {
                //选中的排序
                console.log("find!");
                $(this).attr("style", "font-family:YouYuan,bold ;font-size:16px;color:#993399;font-weight:900");
                //
                console.log("sort:" + $("#sort").val());
                console.log("class will be:" + $("#sort").val() == 'desc' ? "caret" : "arrow-top");
                $(this).children("a").attr("class", $("#sort").val() == 'desc' ? "caret" : "arrow-top");
                return false;
            }
        });
    })
</script>
</body>
</html>
