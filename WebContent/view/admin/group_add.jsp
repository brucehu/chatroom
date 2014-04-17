<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加组</title>
<LINK href="css/admin.css" type="text/css" rel="stylesheet">
</head>
<body>
<TABLE cellSpacing=0 cellPadding=0 width="100%" align=center border=0>
  <TR height=28>
    <TD background=images/title_bg1.jpg>当前位置: 添加组 <button onclick="javascript:history.go(-1)">返回</button></TD></TR>
  <TR>
    <TD bgColor=#b1ceef height=1></TD></TR>
  <TR height=20>
    <TD background=images/shadow_bg.jpg></TD></TR></TABLE>
<div style="PADDING-LEFT: 20px;">
<form action="${action}" method="post" name="form1">
<input type="hidden" name="id" value="${group.groupId}" />
<table>
<tr><td>组名称：</td>
<td><input type="text" name="groupName" value="${group.groupName}" /></td></tr>
<tr><td></td><td><input type="submit" value="${buttonValue}"/></td></tr>
</table>
</form>
</div>
</body>
</html>