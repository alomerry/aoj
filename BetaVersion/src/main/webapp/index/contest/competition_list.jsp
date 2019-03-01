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
        </center>
    </div>

</div>
<jsp:include page="../../footer.jsp"/>
</body>
</html>
