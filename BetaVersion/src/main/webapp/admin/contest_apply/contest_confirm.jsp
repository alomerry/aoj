<%--
  Created by IntelliJ IDEA.
  User: wu1ji
  Date: 2018/11/28
  Time: 7:56
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
    <script src="../../assets/js/sort.js"></script>
    <script src="../../assets/js/sweetalert.min.js"></script>
    <style type="text/css">
        input[type=checkbox].checkbox-right {
            margin-left: 30px;
        }
    </style>
</head>
<body>
<jsp:include page="../../ico.jsp"/>

<div class="container">
    <div class="jumbotron">
        <div align=center class="input-append">
            <form id=simform class=form-inline action="" method="get">
                <label>竞赛名:</label>
                <input class="form-control" type=text size=4 name=user_id value='' title="">
                开始日期:
                <select class="form-control" size="1" name="language" title="">
                    <option value="-1">All</option>
                    <option value=0>
                        2019-09-21 16:33:26
                    </option>
                    <option value=1>
                        2019-09-21 16:33:26
                    </option>
                    <option value=2>
                        2019-09-21 16:33:26
                    </option>
                    <option value=3>
                        2019-09-21 16:33:26
                    </option>
                    <option value=4>
                        2019-09-21 16:33:26
                    </option>
                    <option value=5>
                        2019-09-21 16:33:26
                    </option>
                    <option value=6>
                        2019-09-21 16:33:26
                    </option>
                </select>
                状态:<select class="form-control" size="1" name="jresult" title="">
                <option value='-1' selected>All</option>
                <option value='4'>可报名</option>
                <option value='5'>已结束</option>
                <option value='6'>待开始</option>
                <option value='7'>进行中</option>
            </select>
                <input type=submit class='form-control' value='查找'>
            </form>
        </div>
        <div>
            <table id=result-tab class="table table-striped content-box-header" align=center width=90%>
                <thead>
                <input id="sort" type="hidden" value="${empty sort? desc:sort}"/>
                <input id="orderByType" type="hidden" value="${empty orderByType? contest_id:orderByType}"/>
                <tr class='toprow'>
                    <th width="13%">
                        <input id="checkbox_father" type="checkbox" class="checkbox-right" onclick="selectAll(this)" title="">&nbsp;&nbsp;&nbsp;全选
                    </th>
                    <th type="contest_id">竞赛Id<a class="caret" onclick="adminContestApplyListOrderBy(this)"></a></th>
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
                    <c:choose>
                        <c:when test="${i.index % 2 == 0}">
                            <tr class='evenrow'>
                        </c:when>
                        <c:otherwise>
                            <tr class='oddrow'>
                        </c:otherwise>
                    </c:choose>
                    <td><input type="checkbox" class="checkbox-right" onclick="selectItem(this)" title=""></td>
                    <td>
                        <div class=center style="margin-left: 10px">
                            <a><span id="span_${item.contest.contest_id}">${item.contest.contest_id}</span></a>
                        </div>
                    </td>
                    <td>${item.contest.title}</td>
                    <td class='hidden-xs'>
                        <div class=red style="margin-left: 10px">
                            <a><span id="span_${item.user.user_id}">${item.user.user_id}</span></a></div>
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
                <c:if test="${applies.size() != 0}">
                    <tr>
                        <td></td>
                        <td></td>
                        <td style="text-align:right"><span class='btn btn-success'>同意</span></td>
                        <td style="text-align:left"><span><a class="btn btn-danger">拒绝</a></span></td>
                        <td></td>
                        <td></td>
                    </tr>
                </c:if>
                </tbody>
            </table>
        </div>
    </div>
</div>

<jsp:include page="../../footer.jsp"/>
<script type="text/javascript">
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

    function adminConfirmApply(contest_id, user_id) {
        var c_id = "#span_" + contest_id;
        var u_id = "#span_" + user_id;
        var res = $.ajax({
            url: "/admin_contest_doconfirm",
            type: "post",
            data: '{"contest_id":"' + $(c_id).html() + '","user_id":"' + $(u_id).html() + '"}',
            contentType: "application/json",
            success: function (result) {
                var type = result.msgType;
                var msg = result.msg;
                console.log(type + ":" + msg);
                if (type == 1) {
                    swal({
                        title: "Success",
                        text: "操作成功！",
                        icon: "success",
                        timer: 600,
                        buttons: false
                    });
                    setTimeout(function () {
                        window.location.reload();//刷新当前页面.
                    }, 650);
                } else {
                    swal({
                        title: "Failed",
                        text: "操作失败！",
                        icon: "error",
                        timer: 600,
                        buttons: false
                    });
                }
                return true;
            }
        });
    }

    function adminDeleteApply(contest_id, user_id) {
        var c_id = "#span_" + contest_id;
        var u_id = "#span_" + user_id;
        var res = $.ajax({
            url: "/admin_contest_dodelete",
            type: 'POST',
            data: '{"contest_id":"' + $(c_id).html() + '","user_id":"' + $(u_id).html() + '"}',
            contentType: "application/json",
            success: function (result) {
                var type = result.msgType;
                var msg = result.msg;
                if (type == 1) {
                    swal({
                        title: "Success",
                        text: "操作成功！",
                        icon: "success",
                        timer: 600,
                        buttons: false
                    });
                } else {
                    swal({
                        title: "Failed",
                        text: "操作失败！",
                        icon: "error",
                        timer: 600,
                        buttons: false
                    });
                }
                setTimeout(function () {
                    console.log("操作完毕刷新页面");
                    window.location.reload();//刷新当前页面.
                }, 650);
                return true;
            }
        });
    }

</script>
</body>
</html>