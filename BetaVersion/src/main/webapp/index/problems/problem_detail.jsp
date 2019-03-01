<%--
  Created by IntelliJ IDEA.
  User: wu1ji
  Date: 2018/11/30
  Time: 9:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Detail</title>
</head>
<body>
<jsp:include page="../../header.jsp"/>
<div class="container">
    <div class="jumbotron">
        <center>
            <h2>问题 ${problem.problem_id} : ${problem.title}</h2>
            <span class=green>时间限制: </span>${problem.time_limit} ms&nbsp;&nbsp;<span class=green>内存限制: </span>${problem.memory_limit}
            MB<br><span class=green>提交: </span>${problem.submit}&nbsp;&nbsp;<span class=green>解决: </span>${problem.accepted}<br>[<a href='submitpage.php?id=1000'>提交</a>][<a
                href='problemstatus.php?id=1000'>状态</a>][<a href='bbs.php?pid=1000'>讨论版</a>][命题人:<span id='creator'></span>]
        </center>
        <!--StartMarkForVirtualJudge-->
        <h2>题目描述</h2>
        <div class=content>
            <p>${problem.description}<span><span style="font-size:14px;"><br/>
                    </span></span></p>
        </div>
        <h2>输入</h2>
        <div class=content>
            <p>${problem.input}
                <span>
                <span style="font-size:14px;"><br/></span>
            </span>
            </p>
        </div>
        <h2>输出</h2>
        <div class=content>
            ${problem.output}
            <span style="font-size:14px;"><br/></span>
        </div>
        <h2>样例输入</h2>
        ${problem.sample_input}
        <h2>样例输出</h2>
        ${problem.sample_output}
        <h2>来源</h2>
        <div class=content>
            <p><a href='problemset.php?search='></a>&nbsp;</p>
        </div>
        <center>
            <!--EndMarkForVirtualJudge-->[<a href='/problem_submit?problem_id=${problem.problem_id}'>提交</a>][<a href='problemstatus.php?id=1000'>状态</a>]
        </center>
    </div>
</div>

<jsp:include page="../../footer.jsp"/>
</body>
</html>
