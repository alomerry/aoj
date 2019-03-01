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
    <title>Submit</title>
    <style>
        #source {
            width: 80%;
            height: 600px;
        }
    </style>
    <script src="../../ace/ace.js" type="text/javascript" charset="utf-8"></script>
    <script src="../../ace/ext-language_tools.js" type="text/javascript" charset="utf-8"></script>
    <script src="../../ace/theme-twilight.js" type="text/javascript" charset="utf-8"></script>
</head>
<body>
<jsp:include page="../../header.jsp"/>
<div class="container">
    <center>
        <form id="source_submit_to_judge" method="post" action="/problem_dosubmit">
            <span style="font-size:16px;">Problem</span>
            <span class="alert-info"><b>${problem.problem_id}</b></span>
            <input name="problem_id" type="hidden" value="${problem.problem_id}"></br>
            <div>
                <strong>
                    <span>Language:</span>
                    <select name="language" onchange="reloadLanguage(this)">
                        <option value="0" selected>C</option>
                        <option value="1">C++</option>
                        <option value="2">Java</option>
                        <option value="3">Python</option>
                        <option value="4">JavaScript</option>
                    </select>
                    验证码<input id="verifyCode" maxlength="4" type=text><img id="verifyImage" alt="click to change" src="" onclick="getVerify(this)">*
                    <label id="msg" class="alert-mine"></label>
                </strong>
            </div>
            <div class="container-fluid" id="source"></div>
            <%--<pre style="width:80%;height:600px" cols=180 rows=20 id="source"></pre>--%>
            <br>
            <input name="user_code" type="hidden" id="hide_source"/>

        </form>
            <button id="submit_button" class="btn btn-info" onclick="ChechVerify()">提交</button>
    </center>
</div>
<jsp:include page="../../footer.jsp"/>
<script>
    function ChechVerify() {
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
                    } else {
                        console.log("verifyCode is correct...");
                        $("#hide_source").val(editor.getValue());
                        $("#source_submit_to_judge").submit();
                    }
                }
            }
            )
        ;
    }

    function getVerify(obj) {
        obj.src = "/getVerify?" + Math.random();
    }

    $(document).ready(function () {
        var verifyImage = document.getElementById("verifyImage");
        verifyImage.src = "/getVerify?" + Math.random();
    });

    function reloadLanguage(lang) {
        switchLang(lang.value);
    }

    function switchLang(lang) {
        var langnames = new Array("c_cpp", "c_cpp", "java", "python", "javascript");
        console.log("change language :" + langnames[lang]);
        editor.getSession().setMode("ace/mode/" + langnames[lang]);
    }

    function test() {
        alert(editor.getValue());
    }
</script>
<script>
    ace.require("ace/ext/language_tools");
    var editor = ace.edit('source');
    editor.setFontSize(16);
    editor.setTheme("ace/theme/twilight");
    editor.getSession().setMode("ace/mode/c_cpp");
    editor.setOptions({
        enableBasicAutocompletion: true,
        enableSnippets: true,
        enableLiveAutocompletion: true
    });
    editor.getValue();
    switchLang(0);
</script>
</body>
</html>
