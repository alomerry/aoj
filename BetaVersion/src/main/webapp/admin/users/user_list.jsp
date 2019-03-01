<%--
  Created by IntelliJ IDEA.
  User: wu1ji
  Date: 2019/1/29
  Time: 17:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="content-type" content="text/html;charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta charset="UTF-8">
    <title>用户列表</title>
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
    <style>
        .table-header {
            text-align: center;
        }
    </style>
</head>
<body>
<div id="container">
    <div id="output">
        <div class="containerT">

            <div class="container" align="center">
                <jsp:include page="../../ico.jsp"/>

                <div align=center class="input-append">
                    <form class="form-inline" action="" method="get" style="position: unset">
                        <input class="form-control" type=text size=10 placeholder="用户名">

                        <label>
                            <select tabindex="5" class="form-control" data-settings='{"cutOff": 12}' name="language">
                                <option value="-1">注册日期</option>
                                <option value=0>C</option>
                                <option value=1>C++</option>
                            </select>
                        </label>

                        <label>
                            <select tabindex="5" class="form-control  easy-dropdowns" data-settings='{"cutOff": 12}'>
                                <option value='-1' selected>权限级别</option>
                                <option value='4'>系统管理员</option>
                                <option value='5'>普通管理员</option>
                                <option value='6'>用户管理员</option>
                            </select>
                        </label>
                        <input type=submit class='form-control' value='查找'>
                    </form>
                </div>

                <div class="scroll-item my_circle">
                    <table class="sticky-enabled" style="border-collapse: unset;border-spacing: 3px 5px;">
                        <thead>
                        <tr class="first-tr">
                            <th>
                                <i id="i_father" class="fa fa-circle-o" onclick="selectAll(this)"></i>
                            </th>
                            <th class="table-header">用户Id</th>
                            <th class="table-header">用户名</th>
                            <th class="table-header">昵称</th>
                            <th class="table-header">学校</th>
                            <th class="table-header">注册时间</th>
                            <th class="table-header">权限</th>
                            <th class="table-header">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${userLinks}" var="userLink" varStatus="i">
                            <tr id="tr_del_need_${userLink.user.user_id}" class="tr-table-data">
                                <td>
                                    <span class="underSel">
                                        <i class="fa fa-circle-o" onclick="selectItem(this)"></i>
                                    </span>
                                </td>
                                <td>${userLink.user.user_id}</td>
                                <td><a href=''>${userLink.user.username}</a></td>
                                <td>
                                    <div class=center><a href=''>${userLink.user.nickname}</a></div>

                                </td>
                                <td class='hidden-xs'>
                                        ${userLink.user.school}
                                </td>
                                <td class='hidden-xs'>
                                    <div class=red>${userLink.user.access_time.toString()}</div>
                                </td>
                                <td class='hidden-xs'>${userLink.privilege.rightstr}</td>
                                <td>
                                    <span class='btn btn-info btn-xs' style="margin-left: 15px">查看</span>
                                    <span class='btn btn-warning btn-xs' onclick="admin_go_to_editUser(${userLink.user.user_id})">修改</span>
                                    <input type="hidden" value="${userLink.user.user_id}"/>
                                    <a data-dialog="somedialog${userLink.user.user_id}" class="trigger btn btn-danger btn-xs">删除</a>
                                    <div id="somedialog${userLink.user.user_id}" class="dialog">
                                        <div class="dialog__overlay"></div>
                                        <div class="dialog__content">
                                            <div class="morph-shape">
                                                <svg xmlns="http://www.w3.org/2000/svg" width="100%" height="100%" viewBox="0 0 560 280"
                                                     preserveAspectRatio="none">
                                                    <rect x="3" y="3" fill="none" width="556" height="276"></rect>
                                                </svg>
                                            </div>
                                            <div class="dialog-inner">
                                                <h3><strong>WRANING</strong>&nbsp;<br>
                                                    <span>此操作不可逆!</span>
                                                    <p>确认删除？</p>
                                                </h3>
                                                <div>
                                                    <button class="btn btn-default" data-dialog-close>关闭</button>
                                                    <button class="btn btn-danger" onclick="doUserDel(${userLink.user.user_id})">确定</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="page">
                    <ul class="pagination">
                        <li class="page-item"><a class="page-link" href="#"><<</a></li>
                        <li class="page-item"><a class="page-link" href="#"><</a></li>
                        <li class="page-item"><a class="page-link" href="#">1</a></li>
                        <li class="page-item active"><a class="page-link" href="#">2</a></li>
                        <li class="page-item"><a class="page-link" href="#">3</a></li>
                        <li class="page-item"><a class="page-link" href="#">></a></li>
                        <li class="page-item"><a class="page-link" href="#">>></a></li>
                    </ul>
                </div>

                <jsp:include page="../../footer.jsp"/>
            </div>

        </div>
    </div>
</div>
<%--列表滚动js--%>
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
    });
</script>
</body>
</html>
