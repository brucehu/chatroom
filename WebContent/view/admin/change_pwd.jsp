<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改密码</title>
<LINK href="css/admin.css" type="text/css" rel="stylesheet">
</head>
<body>
<TABLE cellSpacing=0 cellPadding=0 width="100%" align=center border=0>
  <TR height=28>
    <TD background=images/title_bg1.jpg>当前位置: 修改密码</TD></TR>
  <TR>
    <TD bgColor=#b1ceef height=1></TD></TR>
  <TR height=20>
    <TD background=images/shadow_bg.jpg></TD></TR></TABLE>
    <p>${message }</p>
<div style="PADDING-LEFT: 20px;">
<form action="changePwdDB.do" method="post" name="form1">
<input type="hidden" name="id" value="${group.groupId}" />
<table>
<tr><td>旧密码：</td>
<td><input type="password" name="oldPwd" value="" /></td></tr>
<tr><td>新密码：</td>
<td><input type="password" name="newPwd" value="" /></td></tr>
<tr><td></td><td><input type="submit" value="修改"/></td></tr>
</table>
</form>
</div>
</body>
</html>