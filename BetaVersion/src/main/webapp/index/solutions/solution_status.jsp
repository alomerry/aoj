<%--
  Created by IntelliJ IDEA.
  User: wu1ji
  Date: 2019/1/16
  Time: 16:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Result List</title>
    <link rel="stylesheet" href="../../assets/css/loading2.css">
</head>
<body>
<jsp:include page="../../header.jsp"/>

<div class="container">
    <div class="jumbotron">
        <div align=center class="input-append">
            <form id=simform class=form-inline action="status.php" method="get">
                题目编号:<input class="form-control" type=text size=4 name=problem_id value=''>
                用户:<input class="form-control" type=text size=4 name=user_id value='${online_judge_locuser.nickname}'>
                语言:
                <select class="form-control" size="1" name="language">
                    <option value="-1">All</option>
                    <option value=0>
                        C
                    </option>
                    <option value=1>
                        C++
                    </option>
                    <option value=3>
                        Java
                    </option>
                </select>
                结果:
                <select class="form-control" size="1" name="jresult">
                    <option value='-1' selected>All</option>
                    <option value='4'>正确</option>
                    <option value='5'>格式错误</option>
                    <option value='6'>答案错误</option>
                    <option value='7'>时间超限</option>
                    <option value='8'>内存超限</option>
                    <option value='9'>输出超限</option>
                    <option value='10'>运行错误</option>
                    <option value='11'>编译错误</option>
                    <option value='0'>等待</option>
                    <option value='1'>等待重判</option>
                    <option value='2'>编译中</option>
                    <option value='3'>运行并评判</option>
                </select>
                <input type=submit class='form-control' value='查找'>
            </form>
            <table id=result-tab class="table table-striped content-box-header" align=center width=80%>
                <thead>
                <tr class='toprow'>
                    <th>提交编号
                    <th>用户
                    <th>问题
                    <th>结果
                    <th class='hidden-xs'>内存
                    <th class='hidden-xs'>耗时
                    <th class='hidden-xs'>语言
                    <th class='hidden-xs'>代码长度
                    <th>提交时间
                    <th class='hidden-xs'>判题机
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${solutions}" var="solution" varStatus="i">
                    <c:choose>
                        <c:when test="${i.index % 2 == 0}">
                            <tr class="evenrow">
                        </c:when>
                        <c:otherwise>
                            <tr class="oddrow">
                        </c:otherwise>
                    </c:choose>
                    <td>${solution.solution_id}</td>
                    <td>
                        <a href='contestrank.php?cid=100000634&user_id=morizunzhu#morizunzhu'>${solution.user_id}</a>
                    </td>
                    <td>
                        <div class=center>
                            <a href='/problem_detail?problem_id=${solution.problem_id}'>${solution.problem_id}</a>
                        </div>
                    </td>

                    <td>
                        <span class='hidden' style='display:none' result='${solution.result}'></span>
                        <c:choose>
                            <c:when test="${solution.result == 4}">
                                <span class='btn btn-success' title='答案正确，请再接再厉。'>正确</span>
                            </c:when>
                            <c:when test="${solution.result == 5}">
                                <span class='btn btn-danger' title=''>格式错误</span>
                            </c:when>
                            <c:when test="${solution.result == 6}">
                                <span class='btn btn-danger' title=''>答案错误33%</span>
                            </c:when>
                            <c:when test="${solution.result == 7}">
                                <span class='btn btn-warning' title=''>时间超限</span>
                            </c:when>
                            <c:when test="${solution.result == 8}">
                                <span class='btn btn-warning' title=''>内存超限</span>
                            </c:when>
                            <c:when test="${solution.result == 9}">
                                <span class='btn btn-warning' title=''>输出超限</span>
                            </c:when>
                            <c:when test="${solution.result == 10}">
                                <span class='btn btn-warning' title=''>运行错误</span>
                            </c:when>
                            <c:when test="${solution.result == 11}">
                                <span class='btn btn-warning' title=''>编译错误</span>
                            </c:when>
                            <c:when test="${solution.result == 0}">
                                <span class='btn btn-info' title=''>排队中</span>
                                <div id="loadingFlag" style="display: inline-block" class="loader">Loading...</div>
                            </c:when>
                            <c:otherwise>
                                <span class='btn btn-default' title=''>等待中</span>
                                <div id="loadingFlag" style="display: inline-block" class="loader">Loading...</div>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td class='hidden-xs'><div class=red>${solution.memory}</div></td>
                    <td class='hidden-xs'><div class=red>${solution.time.toString()}</div></td>
                    <td class='hidden-xs'><a target=_blank href=showsource.php?id=1429271>C++</a>/<a target=_self>Edit</a></td>
                    <td class='hidden-xs'>${solution.code_lenght}</td>
                    <td>${solution.in_date.toString()}</td>
                    <td class='hidden-xs'>discovery</td>
                    </tr>

                </c:forEach>

                </tbody>
            </table>
        </div>
    </div>
</div>
<jsp:include page="../../footer.jsp"/>
</body>
</html>
