<%@ page import="mo.utils.string.StringValue" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: wu1ji
  Date: 2018/11/29
  Time: 17:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>List</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body style="border: 10px">
<jsp:include page="../../header.jsp"/>
<div class="container">
    <div class="jumbotron">
        <center>
            <div>
                <ul class="pagination">
                    <li class="page-item"><a class="page-link" href="#">
                        <<</a></li>
                    <li class="page-item"><a class="page-link" href="#">1</a></li>
                    <li class="page-item active"><a class="page-link" href="#">2</a></li>
                    <li class="page-item"><a class="page-link" href="#">3</a></li>
                    <li class="page-item"><a class="page-link" href="#">>></a></li>
                </ul>
            </div>
            <table align="center" class="search-form">
                <tr class='evenrow'>
                    <td width='5'></td>
                    <td colspan='1'>
                        <form class=form-inline action="">
                            <input class="form-control search-query" type='text' name='id' placeholder="Problem ID">
                            <button class="form-control" type='submit'>Go</button>
                        </form>
                    </td>
                    <td colspan='1'>
                        <form class="form-search form-inline">
                            <input type="text" name=search class="form-control search-query" placeholder="Keywords Title or Source">
                            <button type="submit" class="form-control">查找</button>
                        </form>
                    </td>
                </tr>
            </table>
            <!-- 预设枚举值 -->
            <c:set var="STATUS_RUNNING" value="<%=StringValue.CONTEST_STATUS.STATUS_RUNNING %>"/>
            <c:set var="STATUS_OVER" value="<%=StringValue.CONTEST_STATUS.STATUS_OVER %>"/>
            <c:set var="STATUS_READY" value="<%=StringValue.CONTEST_STATUS.STATUS_READY %>"/>
            <c:set var="STATUS_AVAILABLE" value="<%=StringValue.CONTEST_STATUS.STATUS_AVAILABLE %>"/>

            <table class="table table-striped" align="center" style="width:90%;">
                <thead>
                <tr>
                    <th width="10%">题目编号</th>
                    <th>标题</th>
                    <th width="25%">开始时间</th>
                    <th width="10%">状态</th>
                </tr>
                </thead>
                <tbody>

                <c:forEach items="${contests}" var="contest">
                    <tr>
                        <td>${contest.contest_id}</td>
                        <td><a href="/contest_detail?contestId=${contest.contest_id}">${contest.title}</a></td>
                        <td>${contest.start_time.toString()}</td>
                        <td>
                            <div class="row clearfix">
                                <div class="col-md-12 column">

                                    <c:choose>
                                        <c:when test="${contest.status eq STATUS_READY}">
                                            <span class="label label-success">待开始</span>
                                        </c:when>
                                        <c:when test="${contest.status eq STATUS_AVAILABLE}">
                                            <span class="label label-primary">可报名</span>
                                        </c:when>
                                        <c:when test="${contest.status eq STATUS_RUNNING}">
                                            <span class="label label-warning">进行中</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="label label-default">已结束</span>
                                        </c:otherwise>
                                    </c:choose>

                                </div>
                            </div>
                        </td>
                    </tr>
                </c:forEach>

                </tbody>
            </table>
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
    </div>

</div>
<jsp:include page="../../footer.jsp"/>
</body>
</html>
