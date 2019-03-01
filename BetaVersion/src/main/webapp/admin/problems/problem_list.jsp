<%--
  Created by IntelliJ IDEA.
  User: wu1ji
  Date: 2019/1/28
  Time: 21:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="content-type" content="text/html;charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta charset="UTF-8">
    <title>Problem List</title>
    <%--背景样式--%>
    <link rel="stylesheet" type="text/css" href="../../assets/css/backgroud-style.css">
    <%--jquery 3.2.1--%>
    <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/jquery@3.2.1/dist/jquery.min.js"></script>
    <%--背景js--%>
    <script type="text/javascript" src="../../assets/js/vector.js"></script>
    <script type="text/javascript" src="../../assets/js/func.mo.js"></script>

    <%--正上方信息弹出框--%>
    <link rel="stylesheet" type="text/css" href="../../assets/css/l-message/message.css"/>
    <%--弹出框js--%>
    <script type="text/javascript" src="../../assets/js/wilma/modernizr.custom.js"></script>
    <script type="text/javascript" src="../../assets/js/wilma/classie.js"></script>
    <script type="text/javascript" src="../../assets/js/wilma/dialogFx.js"></script>
    <%--弹出框--%>
    <link rel="stylesheet" type="text/css" href="../../assets/css/wilma/dialog.css"/>
    <link rel="stylesheet" type="text/css" href="../../assets/css/wilma/dialog-wilma.css"/>
    <%--列表--%>
    <link rel="stylesheet" type="text/css" href="../../assets/css/component.css"/>
    <link rel="stylesheet" href="../../assets/css/styles.css">

    <%--甜蜜弹框--%>
    <script src="../../assets/js/sweetalert.min.js"></script>
    <script src="../../assets/js/sort.js"></script>
</head>
<body>
<div id="container">
    <div id="output">
        <div class="containerT">
            <jsp:include page="../../ico.jsp" flush="true"/>
            <div class="container">
                <div align=center class="input-append">
                    <form id=simform class=form-inline action="status.php" method="get">
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

                <%--@elvariable id="orderByType" type="java.lang.String"--%>
                <%--@elvariable id="desc" type="java.lang.String"--%>
                <%--@elvariable id="sort" type="java.lang.String"--%>
                <div class="scroll-item my_circle">
                    <table class="sticky-enabled" style="border-collapse: unset;border-spacing: 3px 5px;">
                        <thead>
                        <input id="sort" type="hidden" value="${empty sort? desc:sort}"/>
                        <input id="orderByType" type="hidden" value="${empty orderByType? 'create_time':orderByType}"/>
                        <tr class='toprow'>
                            <th>
                                <input id="checkbox_father" type="checkbox" class="checkbox-right" onclick="selectAll(this)" title="">
                            </th>
                            <th data-type="problem_id" width="10%">题目编号<a class="caret" onclick="adminProblemListOrderBy(this)"><i class="fa fa-caret-down"></i></a>
                            </th>
                            <th data-type="title">标题<a class="caret" onclick="adminProblemListOrderBy(this)"><i class="fa fa-caret-down"></i></a>
                            </th>
                            <th data-type="source" width="10%">来源/分类</th>
                            <th data-type="accepted">正确<a class="caret" onclick="adminProblemListOrderBy(this)"><i class="fa fa-caret-down"></i></a>
                            </th>
                            <th data-type="submit">提交<a class="caret" onclick="adminProblemListOrderBy(this)"><i class="fa fa-caret-down"></i></a>
                            </th>
                            <th data-type="defunct" width="10%">公开级别<a class="caret" onclick="adminProblemListOrderBy(this)"><i class="fa fa-caret-down"></i></a>
                            </th>
                            <th width="15%">操作</th>

                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${problems}" var="problem" varStatus="i">
                            <c:choose>
                                <c:when test="${i.index % 2 == 0}">
                                    <tr class='evenrow'>
                                </c:when>
                                <c:otherwise>
                                    <tr class='oddrow'>
                                </c:otherwise>
                            </c:choose>
                            <td><input type="checkbox" class="checkbox-right" onclick="selectItem(this)" title=""></td>
                            <td type="upper"><span type="under_${problem.problem_id}">${problem.problem_id}</span></td>
                            <td>
                                <div class=center><a href='problem.php?cid=100000634&pid=0'>${problem.title}</a></div>
                            </td>
                            <td><span>${problem.source}</span></td>
                            <td class='hidden-xs'>
                                <div class=red>${problem.accepted}</div>
                            </td>
                            <td class='hidden-xs'>
                                <div class=red>${problem.submit}</div>
                            </td>
                            <td class='hidden-xs'><span>${problem.defunct}</span></td>
                            <td>
                                <span><a style="color: white" class='btn btn-info btn-xs' href="/admin_problem_view?contest_id=${problem.problem_id}">查看</a></span>
                                <span><a style="color: white" class='btn btn-warning btn-xs' href="/admin_contest_edit?contest_id=${problem.problem_id}">修改</a></span>
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
                                    确定删除该题目吗？该操作不可逆！
                                    <p></p>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-default" data-dismiss="modal" onclick="disIdForDelete()">关闭
                                    </button>
                                    <button type="button" class="btn btn-danger" data-dismiss="modal" onclick="ProblemListDoDelete(this)">
                                        确定
                                    </button>
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
<script type="text/javascript" src="../../assets/js/message.min.js"></script>
<script type="text/javascript">
    $(function () {
        Victor("container", "output"); //登陆背景函数
        $(document).keydown(function (event) {
            if (event.keyCode === 13) {
                $("#entry_btn").click();
            }
        });
        //弹出框特效
        for (var i = 0; i < document.querySelectorAll("[data-dialog]").length; i++) {
            var dlgtrigger = document.querySelectorAll("[data-dialog]")[i];
            var somedialog = document.getElementById(dlgtrigger.getAttribute('data-dialog')),
                dlg = new DialogFx(somedialog);
            dlgtrigger.addEventListener('click', dlg.toggle.bind(dlg));
            // console.log("success");
        }
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
