<%--
  Created by IntelliJ IDEA.
  User: wu1ji
  Date: 2018/11/28
  Time: 7:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Online Judge</title>
</head>
<body>
    <jsp:include page="../../header.jsp" flush="true"/>
        <div class="container">
            <form action="modify.php" method="post">
                <br><br>
                <center><table>
                    <tr><td colspan=2 height=40 width=500>&nbsp;&nbsp;&nbsp;Update Information</tr>
                    <tr><td width=25%>User ID:
                        <td width=75%>morizunzhu<input type=hidden name="postkey" value="CCE9FA55DF">
                    </tr>
                    <tr><td>Nick Name:
                        <td><input name="nick" size=50 type=text value="就当一次路过丶" >
                    </tr>
                    <tr><td>Old Password:
                        <td><input name="opassword" size=20 type=password>
                    </tr>
                    <tr><td>New Password:
                        <td><input name="npassword" size=20 type=password>
                    </tr>
                    <tr><td>Repeat New Password::
                        <td><input name="rptpassword" size=20 type=password>
                    </tr>
                    <tr><td>School:
                        <td><input name="school" size=30 type=text value="淮工" >
                    </tr>
                    <tr><td>Email:
                        <td><input name="email" size=30 type=text value="wu1jin2cheng3@live.cn" >
                    </tr>
                    <tr><td>
                        <td><input value="Submit" name="submit" type="submit">
                            &nbsp; &nbsp;
                            <input value="Reset" name="reset" type="reset">
                    </tr>
                </table></center>
                <br>
                <a href=export_ac_code.php>Download All AC Source</a>
                <br>
            </form>
        </div>
    <jsp:include page="../../footer.jsp" flush="true"/>
</body>
</html>