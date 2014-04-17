<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户咨询</title>
</head>
<body>
<table border=1 width="100%">
<c:forEach items="${list}" var="phoneQhpz"> 
<tr> 
<td>${phoneQhpz.id }</td> 
<td>${phoneQhpz.phone }</td> 
<td>${phoneQhpz.qhpz }</td> 
<td>${phoneQhpz.ctime }</td> 
</tr> 
</c:forEach> 
</table>
</body>
</html>