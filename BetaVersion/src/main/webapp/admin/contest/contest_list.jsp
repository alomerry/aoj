<%--
  Created by IntelliJ IDEA.
  User: wu1ji
  Date: 2018/11/28
  Time: 7:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="mo.utils.string.StringValue" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="content-type" content="text/html;charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta charset="UTF-8">
    <title>Online Judge</title>

    <link rel="stylesheet" type="text/css" href="../../assets/css/backgroud-style.css">

    <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/jquery@3.2.1/dist/jquery.min.js"></script>
    <%--弹出框js--%>
    <script type="text/javascript" src="../../assets/js/wilma/modernizr.custom.js"></script>
    <script type="text/javascript" src="../../assets/js/wilma/classie.js"></script>
    <script type="text/javascript" src="../../assets/js/wilma/dialogFx.js"></script>
    <%--弹出框--%>
    <link rel="stylesheet" type="text/css" href="../../assets/css/wilma/dialog.css"/>
    <link rel="stylesheet" type="text/css" href="../../assets/css/wilma/dialog-wilma.css"/>

    <script type="text/javascript" src="../../assets/js/vector.js"></script>
    <script type="text/javascript" src="../../assets/js/func.mo.js"></script>


    <link rel="stylesheet" href="../../assets/css/styles.css">
    <script src="../../assets/js/sweetalert.min.js"></script>
    <script src="../../assets/js/sort.js"></script>
    <%--列表--%>
    <link rel="stylesheet" type="text/css" href="../../assets/css/component.css"/>
    <style>
        table > thead > tr > th {
            text-align: center;
        }

        i {
            color: #46feb7;
            margin-left: 5px;
        }
    </style>
</head>
<body>
<div id="container">
    <div id="output">
        <div class="containerT">
            <jsp:include page="../../ico.jsp" flush="true"/>
            <div class="container">
                <div align=center class="input-append">
                    <form id=simform class=form-inline action="" method="get">
                        <label>竞赛名:</label>
                        <input class="form-control" type=text size=4 name=user_id title="">
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

                <!-- 预设枚举值 -->
                <c:set var="STATUS_RUNNING" value="<%=StringValue.CONTEST_STATUS.STATUS_RUNNING %>"/>
                <c:set var="STATUS_OVER" value="<%=StringValue.CONTEST_STATUS.STATUS_OVER %>"/>
                <c:set var="STATUS_READY" value="<%=StringValue.CONTEST_STATUS.STATUS_READY %>"/>
                <c:set var="STATUS_AVAILABLE" value="<%=StringValue.CONTEST_STATUS.STATUS_AVAILABLE %>"/>

                <%--@elvariable id="contest_id" type="java.lang.Integer"--%>
                <%--@elvariable id="orderByType" type="java.lang.String"--%>
                <%--@elvariable id="desc" type="java.lang.String"--%>
                <%--@elvariable id="sort" type="java.lang.String"--%>
                <%--@elvariable id="contests" type="java.util.List"--%>
                <div>
                    <div class="scroll-item my_circle">
                        <table class="sticky-enabled" style="border-collapse: unset;border-spacing: 3px 5px;">
                            <thead>
                            <input id="sort" type="hidden" value="${empty sort? desc:sort}"/>
                            <input id="orderByType" type="hidden" value="${empty orderByType? contest_id:orderByType}"/>
                            <tr class='toprow'>
                                <th>
                                    <input id="checkbox_father" type="checkbox" class="checkbox-right" onclick="selectAll(this)" title="" style="margin-left: 10px;margin-right: 10px">
                                </th>
                                <th data-type="contest_id">竞赛Id<a class="caret-low" onclick="adminContestListOrderBy(this)"><i class="fa fa-caret-down"></i></a>
                                </th>
                                <th data-type="title" width="20%">标题<a class="caret-low" onclick="adminContestListOrderBy(this)"><i class="fa fa-caret-down"></i></a>
                                </th>
                                <th data-type="status">状态</th>
                                <th data-type="start_time">开始时间<a class="caret-low" onclick="adminContestListOrderBy(this)"><i class="fa fa-caret-down"></i></a>
                                </th>
                                <th data-type="end_time">结束时间<a class="caret-low" onclick="adminContestListOrderBy(this)"><i class="fa fa-caret-down"></i></a>
                                </th>
                                <th data-type="privates" width="10%">权限<a class="caret-low" onclick="adminContestListOrderBy(this)"><i class="fa fa-caret-down"></i></a>
                                </th>
                                <th width="15%">操作</th>

                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${contests}" var="contest" varStatus="i">
                                <tr>
                                    <td>
                                        <input type="checkbox" class="checkbox-right" onclick="selectItem(this)" title="" style="margin-left: 10px;">
                                    </td>
                                    <td type="upper">
                                        <span type="under_${contest.contest_id}">${contest.contest_id}</span>
                                    </td>
                                    <td>
                                        <div class=center>
                                            <a href='' style="text-decoration: none;">${contest.title}</a>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="row clearfix">
                                            <div class="col-md-12 column">

                                                <c:choose>
                                                    <c:when test="${contest.status eq STATUS_READY}">
                                                        <span class="label label-success" style="margin-left: 10px">待开始</span>
                                                    </c:when>
                                                    <c:when test="${contest.status eq STATUS_AVAILABLE}">
                                                        <span class="label label-primary" style="margin-left: 10px">可报名</span>
                                                    </c:when>
                                                    <c:when test="${contest.status eq STATUS_RUNNING}">
                                                        <span class="label label-warning" style="margin-left: 10px">进行中</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="label label-default" style="margin-left: 10px">已结束</span>
                                                    </c:otherwise>
                                                </c:choose>

                                            </div>
                                        </div>
                                    </td>
                                    <td class='hidden-xs'>
                                        <div class=red>${contest.start_time.toString()}</div>
                                    </td>
                                    <td class='hidden-xs'>
                                        <div class=red>${contest.end_time.toString()}</div>
                                    </td>
                                    <td class='hidden-xs' style="text-align: center">
                                        <c:choose>
                                            <c:when test="${contest.privates == 0}">
                                                公开
                                            </c:when>
                                            <c:otherwise>
                                                内部
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <span><a class='btn btn-info btn-xs' style="color: white;margin-left: 10px" href="${pageContext.request.contextPath}/admin_contest_view?contest_id=${contest.contest_id}">查看</a></span>
                                        <span><a class='btn btn-warning btn-xs' style="color: white" href="${pageContext.request.contextPath}/admin_contest_edit?contest_id=${contest.contest_id}">修改</a></span>
                                        <span><a class="btn btn-danger btn-xs">删除</a></span>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <center>
                        <div class="page">
                            <ul class="pagination">
                                <li class="page-item"><a class="page-link" href="#">
                                    <<</a></li>
                                <li class="page-item"><a class="page-link" href="#">1</a></li>
                                <li class="page-item active"><a class="page-link" href="#">2</a></li>
                                <li class="page-item"><a class="page-link" href="#">3</a></li>
                                <li class="page-item"><a class="page-link" href="#">>></a></li>
                            </ul>
                        </div>
                    </center>

                    <div>
                        <div id="item" class="modal fade" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content alert" style="background-color: #fcf8e3;">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                                            &times;
                                        </button>
                                        <h4 class="modal-title" id="myModalLabel">
                                            <strong>警告!</strong>
                                        </h4>
                                    </div>
                                    <div class="modal-body">
                                        <p></p>
                                        确定删除该比赛吗？该操作不可逆！
                                        <p></p>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-default" data-dismiss="modal" onclick="disIdForDelete()">关闭
                                        </button>
                                        <button type="button" class="btn btn-danger" data-dismiss="modal" onclick="ContestListDoDelete(this)">
                                            确定
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
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

        var input = $("#orderByType");//分类排序
        //读取分类的值
        var type = input.val();
        input.next("tr").children("th").each(function () {
            // console.log("---" + $(this).attr("type"));
            if ($(this).data("type") === type) {
                //选中的排序
                console.log("find!");
                $(this).attr("style", "font-family:YouYuan,bold ;font-size:16px;color:#993399;font-weight:900");
                var sorts = $("#sort").val();
                console.log("sort:" + sorts);
                console.log("class will be:" + sorts === 'desc' ? "caret-low" : "arrow-top");
                $(this).children("a").attr("class", sorts === 'desc' ? "caret-low" : "arrow-top");
                $(this).children("a").children("i").attr("class", sorts === 'desc' ? "fa fa-caret-down" : "fa fa-caret-up");
                return false;
            }
        });
    });

</script>
</body>
</html>