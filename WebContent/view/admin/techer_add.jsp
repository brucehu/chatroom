<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加老师</title>
<LINK href="css/admin.css" type="text/css" rel="stylesheet">
</head>
<body>
<TABLE cellSpacing=0 cellPadding=0 width="100%" align=center border=0>
  <TR height=28>
    <TD background=images/title_bg1.jpg>当前位置: 添加老师 <button onclick="javascript:history.go(-1)">返回</button></TD></TR>
  <TR>
    <TD bgColor=#b1ceef height=1></TD></TR>
  <TR height=20>
    <TD background=images/shadow_bg.jpg></TD></TR></TABLE>
<div style="PADDING-LEFT: 20px;">
<form action="${action}" method="post" name="form1">
<input type="hidden" name="id" value="${techer.userID}"/>
<table>
<tr><td>分类：</td>
<td><select id="groupId" name="groupId">
<c:forEach items="${groupList}" var="group"> 
	<c:choose>
	       <c:when test="${group.groupId==techer.groupId}">
	              <option selected value="${group.groupId}">${group.groupName}</option>
	       </c:when>
	       <c:otherwise>
	              <option value="${group.groupId}">${group.groupName}</option>
	       </c:otherwise>
	</c:choose>
</c:forEach>
</select></td></tr>
<tr><td>账号：</td>
<td><input id="userName" type="text" name="userName" value="${techer.userName}" /></td>
<tr><td>密码：</td>
<td><input id="passWord" type="password" name="passWord" value="${techer.passWord}" /></td></tr>
<tr><td>昵称：</td>
<td><input type="text" name="nickName" value="${techer.nickName}" /></td></tr>
<tr><td>称谓：</td>
<td><input type="text" name="techerTitle" value="${techer.techerTitle }" /></td></tr>
<tr><td>头像：</td>
<td><input type="text" name="techerIcon" value="${techer.techerIcon }" /></td></tr>
<tr>
<td colspan="2" >
<iframe src="/chatroom/upload.html"></iframe>
</td>
</tr>
<tr><td></td><td><input type="submit" value="${buttonValue }"/></td></tr>
</table>
</form>
</div>
</body>
</html>