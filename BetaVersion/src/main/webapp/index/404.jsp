<%--
  Created by IntelliJ IDEA.
  User: wu1ji
  Date: 2019/1/17
  Time: 13:04
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>404</title>
    <link rel="stylesheet" href="../assets/css/404.css">
</head>
<body>

<jsp:include page="../header.jsp" flush="true"/>

<div class="container">
    <div class="error-page">
        <div class="error-page-container">
            <div class="error-page-main">
                <h3>
                    <strong>404</strong>无法打开页面
                </h3>
                <div class="error-page-actions">
                    <div>
                        <h4>可能原因：</h4>
                        <ol>
                            <c:if test="${!(empty reason)}">
                                <li>${reason}</li>
                            </c:if>
                            <li>网络信号差</li>
                            <li>找不到请求的页面</li>
                            <li>输入的网址不正确</li>
                        </ol>
                    </div>
                    <div>
                        <h4>可以尝试：</h4>
                        <ul>
                            <li><a href="${pageContext.request.contextPath}/home">返回首页</a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../footer.jsp" flush="true"/>

</body>
</html>
