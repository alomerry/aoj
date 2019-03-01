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
</head>
<body style="border: 10px">
<jsp:include page="../../header.jsp"/>
<div class="container">
    <div class="jumbotron">
        <center>
            <ul class="pagination">
                <li class="page-item"><a class="page-link" href="#"><<</a></li>
                <li class="page-item"><a class="page-link" href="#">1</a></li>
                <li class="page-item active"><a class="page-link" href="#">2</a></li>
                <li class="page-item"><a class="page-link" href="#">3</a></li>
                <li class="page-item"><a class="page-link" href="#">>></a></li>
            </ul>
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
            <table class="table table-striped">
                <thead>
                <tr class='oddrow'>
                    <th width="10%" style="text-align: center">题目编号</th>
                    <th>标题</th>
                    <th width="10%">来源/分类</th>
                    <th width="10%">正确</th>
                    <th width="10%">提交</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${problems}" var="p" varStatus="i">
                    <c:choose>
                        <c:when test="${i.index%2==0}">
                            <tr class='oddrow'>
                        </c:when>
                        <c:otherwise>
                            <tr class='evenrow'>
                        </c:otherwise>
                    </c:choose>

                    <td style="text-align: center">${p.problem_id}</td>
                    <td><a href="/problem_detail?problem_id=${p.problem_id}">${p.title}</a></td>
                    <td>${p.source}</td>
                    <td><a>${p.submit}</a></td>
                    <td><a>${p.accepted}</a></td>
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
