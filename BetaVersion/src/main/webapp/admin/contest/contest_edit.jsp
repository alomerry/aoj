<%--
  Created by IntelliJ IDEA.
  User: wu1ji
  Date: 2019/1/28
  Time: 1:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="content-type" content="text/html;charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta charset="UTF-8">
    <title>Contest Eidt</title>

    <link rel="stylesheet" type="text/css" href="../../assets/css/backgroud-style.css">

    <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/jquery@3.2.1/dist/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="../../assets/js/vector.js"></script>
    <script type="text/javascript" src="../../assets/js/func.mo.js"></script>

    <%--弹出框js--%>
    <script type="text/javascript" src="../../assets/js/wilma/modernizr.custom.js"></script>
    <script type="text/javascript" src="../../assets/js/wilma/classie.js"></script>
    <script type="text/javascript" src="../../assets/js/wilma/dialogFx.js"></script>
    <%--弹出框--%>
    <link rel="stylesheet" type="text/css" href="../../assets/css/wilma/dialog.css"/>
    <link rel="stylesheet" type="text/css" href="../../assets/css/wilma/dialog-wilma.css"/>

    <%--alert插件--%>
    <link rel="stylesheet" href="../../assets/css/alert/alert.css">
    <script src='../../assets/js/alert/alert.js'></script>


    <link rel="stylesheet" href="../../assets/css/styles.css">
    <script src="../../assets/js/sweetalert.min.js"></script>
    <style type="text/css">
        input.thinner {
            /*width: 60%;*/
        }

        textarea.thinner {
            /*width: 60%;*/
        }

        div.thinner {
            /*width: 60%;*/
            /*margin-left: 45px;*/
        }

        .alert-title, .alert-btn-box, .alert-content {
            background: linear-gradient(to right, #ffecd2 0%, #fcb69f 100%);
        }

        .alert-title {
            font-size: 19px;
        }
    </style>
</head>
<body>
<div id="container">
    <div id="output">
        <div class="containerT">
            <jsp:include page="../../ico.jsp" flush="true"/>
            <%--TODO 竞赛问题编辑--%>
            <div class="container">
                <div class="widget-content nopadding">
                    <c:if test="${!empty msg}">
                        <div class="thinner alert alert-dismissable ${msgType == 1?'alert-success':'alert-warning'}">
                            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                            <strong>${msgType == 1?修改比赛成功:修改比赛失败}</strong>${msg}
                        </div>
                    </c:if>
                    <form style="margin-left:50px" method="post" id="contest-do" action="/admin_contest_doadd">
                        <div class="row clearfix">
                            <div class="col-md-6 column">
                                <table>
                                    <tr>
                                        <th><label for="contest_title">竞赛标题</label></th>
                                        <th>
                                            <input type="text" class="thinner" id="contest_title" placeholder="竞赛标题" name="title" value="${contest.title}">
                                        </th>
                                    </tr>
                                    <tr>
                                        <th><label for="contest_organizer">主办方</label></th>
                                        <th>
                                            <input type="text" class="thinner" id="contest_organizer" placeholder="主办方" name="organizer" value="${contest.organizer}">
                                        </th>
                                    </tr>
                                    <tr>
                                        <th><label for="contest_discribe">竞赛描述</label></th>
                                        <th>
                                            <input class="thinner" id="contest_discribe" placeholder="竞赛描述" name="describes" value="${contest.describes}"/>
                                        </th>
                                    </tr>
                                    <tr>
                                        <th><label for="contest_max">参赛人数</label></th>
                                        <th>
                                            <input type="text" class="thinner" id="contest_max" placeholder="参赛人数" name="max" value="${contest.max}">
                                        </th>
                                    </tr>
                                </table>
                            </div>
                            <div class="col-md-6 column">
                                <table>
                                    <tr>
                                        <th><label for="content_start">开始时间</label></th>
                                        <th>
                                            <input type="datetime-local" class="thinner" id="content_start">
                                            <input type="hidden" class="thinner" id="content_start_v" name="start_time">
                                        </th>
                                    </tr>
                                    <tr>
                                        <th><label for="content_end">结束时间</label></th>
                                        <th>
                                            <input type="datetime-local" class="thinner" id="content_end">
                                            <%--<input type="date" class="form-control thinner" id=>--%>
                                            <%--<input type="time" class="form-control thinner" id=>--%>
                                            <input type="hidden" class="form-control thinner" id="content_end_v" name="end_time">
                                        </th>
                                    </tr>
                                    <tr>
                                        <th><label>是否可报名</label></th>
                                        <th>
                                            <i class="fa fa-${admin_level.contains("b")?'check-':''}square-o fa-fw" onclick="awesome_checkbox_click(this)" data-checked="false"></i>
                                            <%--<input type="date" class="form-control thinner" id=>--%>
                                            <%--<input type="time" class="form-control thinner" id=>--%>
                                            <input type="hidden" class="form-control thinner" id="content_end_v" name="end_time">
                                        </th>
                                    </tr>
                                    <tr>
                                        <th><label>是否公开</label></th>
                                        <th>
                                            <i class="fa fa-${admin_level.contains("b")?'check-':''}square-o fa-fw" onclick="awesome_checkbox_click(this)" data-checked="false"></i>&nbsp;公开
                                            <%--<input type="date" class="form-control thinner" id=>--%>
                                            <%--<input type="time" class="form-control thinner" id=>--%>
                                            <i class="fa fa-${admin_level.contains("b")?'check-':''}square-o fa-fw" onclick="awesome_checkbox_click(this)" data-checked="false"></i>&nbsp;内部
                                        </th>
                                    </tr>
                                    <tr>
                                        <th><label for="verifyCode">验证码</label></th>
                                        <th>
                                            <input type="text" placeholder="验证码" id="verifyCode" maxlength="4" onclick="getVerify(this)"
                                                   class="popover-options" data-container="body" data-toggle="popover" data-content='<img id="verifyImage" alt="点击获取验证码" src="" onclick="getVerify(this)" height="">'>
                                            <img style="display: none" alt="" src="" onclick="" height="" id="updateCodeHidden">
                                            <input id="webkey" class="form-control" type="hidden" value="onlinejudge">
                                            <label id="msg" class="alert-mine"></label>
                                        </th>
                                    </tr>
                                </table>
                            </div>
                            <a style="margin-top:50px;margin-left: 50px" class="btn btn-primary btn-lg" onclick="ChechVerifyAndjsMd5()">保存</a>
                        </div>
                    </form>
                    <div class="row clearfix">
                        <div class="col-md-12 column">


                            <%--添加比赛的问题--%>
                            <a class="trigger btn btn-danger btn-xs" onclick="postAjax('1')">添加问题</a>
                            <div style="display: none" id="blindDialog">
                                <form class="form-horizontal" role="form">
                                    <div class="form-group">
                                        <label for="select_from" style="margin-left: 30px;">问题来源</label>
                                        <div class="col-sm-8" style="float: right;margin-right: 20px">
                                            <select class="form-control" size="1" name="" id="select_from" onchange="changeProblems()">
                                                <option value='-1' selected>All</option>
                                                <option value='1'>公开</option>
                                                <option value='2'>私有</option>
                                                <option value='3'>已创建</option>
                                                <%--<option value='7'>进行中</option>--%>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="select_pro" class="col-sm-2 control-label">问题</label>
                                        <div class="col-sm-8">
                                            <select class="form-control" size="1" name="" id="select_pro" onchange="getSelect()">
                                                <option value='-1' selected>请选择</option>
                                                <%--<option value='4'>可报名</option>--%>
                                            </select>
                                        </div>
                                    </div>
                                </form>
                                <div class="col-md-12 column">
                                    <table class="table table-bordered" id="problem_detail_show" style="display: none">
                                        <input type="hidden" id="addingId" value="">
                                        <thead>
                                        <tr>
                                            <th width="20%">题目</th>
                                            <th class="s666"></th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <tr class="info">
                                            <td>作者</td>
                                            <td class="s666"></td>
                                        </tr>
                                        <tr class="success">
                                            <td>描述</td>
                                            <td class="s666"></td>
                                        </tr>
                                        <tr class="error">
                                            <td>时间限制</td>
                                            <td class="s666"></td>
                                        </tr>
                                        <tr class="warning">
                                            <td>内存限制</td>
                                            <td class="s666"></td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-default" onclick="$('#problem_detail_show').attr('style','display:none')">关闭</button>
                                    <button type="button" class="btn btn-primary" onclick="addProblemToContest(${contest.contest_id})">保存</button>
                                </div>
                            </div>


                            <table class="table table-striped" align="center" style="width:90%;">
                                <thead>
                                <tr class='evenrow'>
                                    <th width="17%">题目编号</th>
                                    <th width="25%">标题</th>
                                    <th>状态</th>
                                    <th width="10%">正确</th>
                                    <th width="25%">操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${competition}" var="s" varStatus="i">
                                    <tr>
                                        <form>
                                            <td>
                                                <span id="span_problem_num_${s.problem.problem_id}">${s.num}</span>
                                                <input type="text" id="input_problem_num_${s.problem.problem_id}" title="" style="display: none;" value=""/>
                                            </td>
                                        </form>
                                        <td><a href="/problem_detail?problem_id=${s.problem.problem_id}">${s.title}</a>
                                        </td>
                                        <td>未答题</td>
                                        <td>${s.problem.accepted}</td>
                                        <td>
                                            <a id="a_change_${s.problem.problem_id}" class='btn btn-warning btn-xs' style="color: white"
                                               onblur="changeNumFinish(${s.problem.problem_id})"
                                               onclick="changeNumStart(${s.problem.problem_id},${contest.contest_id})" type="off">修改</a>
                                            <a style="color: white" class="btn btn-danger btn-xs" onclick="delProblemFromContest(${s.problem.problem_id},${contest.contest_id})">删除</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
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

        $("#updateCodeHidden").attr("src", "/getVerify?" + Math.random());
        $(document).keydown(function (event) {
            if (event.keyCode === 13) {
                $("#entry_btn").click();
            }
        });
        $('.scroll-item').perfectScrollbar();
        $(".popover-options").popover({html: true});

        for (var i = 0; i < document.querySelectorAll("[data-dialog]").length; i++) {
            var dlgtrigger = document.querySelectorAll("[data-dialog]")[i];
            var somedialog = document.getElementById(dlgtrigger.getAttribute('data-dialog')),
                dlg = new DialogFx(somedialog);
            dlgtrigger.addEventListener('click', dlg.toggle.bind(dlg));
            // console.log("success");
        }
        init_Awesome_checkbox();
    });

    var list;


    function ChechVerifyAndjsMd5() {
        var completeFlag = $.ajax({
                url: "/checkVerify",
                type: "post",
                data: '{"code":"' + $("#verifyCode").val() + '"}',
                contentType: "application/json",
                success: function (result) {
                    console.log(result);
                    if (result == false) {
                        $("#msg").html("验证码错误");
                        console.log("verifyCode is wrong...");
                        return false;
                    } else if ($("#md5Pwd").val() == "") {
                        console.log("passwd is missing...");
                        return false;
                    } else {
                        console.log("verifyCode is correct...");
                        $("input[name=passwd]").val(hex_md5(encodeURIComponent($("#md5Pwd").val() + $("#webkey").val())));
                        $("#login").submit();
                    }
                }
            }
            )
        ;
    }

    function getSelect() {
        var res = document.getElementById("select_pro").selectedIndex;
        var selectItem = $("#select_pro").children().eq(res);

        console.log("index-" + res + ":value-" + selectItem.attr("value") + ":title-" + selectItem.html());
        var item = list[res - 1];
        var insert = new Array();
        insert.push(item.title);
        insert.push(item.user_id);
        insert.push(item.description);
        insert.push(item.time_limit);
        insert.push(item.memory_limit);
        var i = 0;
        $("#addingId").attr("value", item.problem_id);
        var table = $("#problem_detail_show");
        console.log("table:" + table);
        table.find("tr").each(function () {
            $(this).find(".s666").html(insert[i]);
            ++i;
        });
        table.removeAttr("style");
    }

    function changeProblems() {
        var res = document.getElementById("select_from").selectedIndex;
        var selectItem = $("#select_from").children().eq(res);
        // console.log("changeProblems-value:"+selectItem.attr("value"));
        var type = selectItem.attr("value");
        console.log("type:" + type);
        switch (type) {
            case "3": {//3个人创建//空
                console.log("请求个人创建的比赛");
                $.ajax({
                    url: "/admin_get_problems",
                    type: "post",
                    data: '{"ask":"666"}',
                    contentType: "application/json",
                    success: function (res) {
                        $("#select_pro").html(" ");
                        $("#select_pro").html("<option value='-1' selected>Select</option>");
                        console.log(res.problems);
                        $.each(res.problems, function (index, item) {
                            $("<option value='" + item.problem_id + "'>" + item.title + "</option>").appendTo("#select_pro");
                            // console.log(item.title+":"+item.create_time);
                        })
                    }
                });
                break;
            }
            case "2": {//管理员比赛
                console.log("请求管理员比赛");
                postAjax(2);
                break;
            }
            case "1": {//公开比赛
                console.log("请求公开比赛");
                postAjax(1);
                break;
            }
        }
    }

    function postAjax(dat) {
        if (bindDialog) {
            bindDialog.show();
        }
        var bindDialog = jqueryAlert({
            'style': 'pc',
            'title': '添加题目',
            'content': $("#blindDialog").html(),
            'modal': true,
            'contentTextAlign': 'left',
            'width': '500',
            'className': "bindDialog",
            'buttons': {
                "确定": function () {
                    bindDialog.close();
                },
                "我不是": function () {
                    alert(11);
                }
            }
        });
        $.ajax({
            url: "/admin_get_problems",
            type: "post",
            data: '{"defunct":"' + dat + '"}',
            contentType: "application/json",
            success: function (res) {
                $("#select_pro").html(" ");
                $("#select_pro").html("<option value='-1' selected>Select</option>");
                list = res.problems;
                console.log(list.length);
                $.each(res.problems, function (index, item) {
                    $("<option value='" + item.problem_id + "'>" + item.title + "</option>").appendTo("#select_pro");
                    // console.log(item.title+":"+item.create_time);
                })
            }
        });
    }

    function addProblemToContest(contest_id) {
        var problemId = $("#addingId").attr("value");
        $('#problem_detail_show').attr('style', 'display:none');
        $.ajax({
            url: "/admin_problem_addToContest",
            data: '{"contest_id":"' + contest_id + '","problem_id":"' + problemId + '"}',
            type: "post",
            contentType: "application/json",
            success: function (res) {
                var type = res.msgType;
                var msg = res.msg;
                if (type == "1") {
                    swal({
                        icon: "success",
                        time: 600,
                        title: "操作成功",
                        text: "添加问题成功",
                        buttons: false,
                    });
                } else {
                    swal({
                        icon: "error",
                        time: 600,
                        title: "操作失败",
                        text: "添加题目失败！",
                        buttons: false,
                    });
                }
                setTimeout(function () {
                    window.location.reload();
                }, 800);
            }
        });

    }

    function delProblemFromContest(problemId, contestId) {
        console.log("problemId:" + problemId + "," + "contestId:" + contestId);
        $.ajax({
            url: "admin_problem_delFromContest",
            type: "post",
            data: '{"contest_id":"' + contestId + '","problem_id":"' + problemId + '"}',
            contentType: "application/json",
            success: function (res) {
                if (res.msgType == "1") {
                    swal({
                        icon: "success",
                        title: "操作成功",
                        text: "删除成功！",
                        buttons: false,
                        time: 600,
                    })
                } else {
                    swal({
                        icon: "error",
                        title: "操作失败",
                        text: "删除失败！",
                        buttons: false,
                        time: 600,
                    })
                }
                setTimeout(function () {
                    window.location.reload();
                }, 800);
            }
        })
    }

    function changeNumFinish(problemId) {//恢复
        var tag_a = $("#a_change_" + problemId);
        var tag_span = $("#span_problem_num_" + problemId);
        var tag_input = $("#input_problem_num_" + problemId);

        tag_span.removeAttr("style");
        tag_input.attr("style", "display:none");
        tag_input.val("");
        tag_a.attr("style", 'color: white');
        tag_a.attr("type", "off");
        tag_a.attr("class", "btn btn-warning btn-xs");
        tag_a.html("修改");
    }

    function changeNumStart(problemId, contestId) {
        console.log("problemId:" + problemId + "," + "contestId:" + contestId);
        var tag_a = $("#a_change_" + problemId);
        var tag_span = $("#span_problem_num_" + problemId);
        var tag_input = $("#input_problem_num_" + problemId);
        if (tag_a.attr("type") == "off") {//开始修改
            tag_span.attr("style", "display:none");
            tag_input.removeAttr("style");
            tag_input.attr("style", "width:30%;height:70%");
            tag_a.attr("type", "on");
            tag_a.attr("class", "btn btn-primary btn-xs");
            tag_a.html("保存");
        } else {//保存修改
            if (tag_input.val() != "" && tag_input.val() != tag_span.val()) {
                $.ajax({
                    url: "/admin_contest_changProblemNum",
                    type: "post",
                    data: '{"contest_id":"' + contestId + '","problem_id":"' + problemId + '","num":"' + tag_input.val() + '"}',
                    contentType: "application/json",
                    success: function (res) {
                        if ("1" == res.msgType) {
                            swal({
                                icon: "success",
                                title: "操作成功！",
                                text: "修改成功！",
                                buttons: false,
                                time: 650,
                            });
                        } else {
                            swal({
                                icon: "error",
                                title: "操作失败！",
                                text: "" + res.msg,
                                buttons: false,
                                time: 650,
                            });
                        }
                        setTimeout(function () {
                            window.location.reload()
                        }, 700);
                    }
                })
            }
            changeNumFinish(problemId);
        }
    }
</script>
</body>
</html>
