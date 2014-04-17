<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>后台管理</title>
<link rel="stylesheet"
	href="../jquery-ui-1.10.3.custom/css/redmond/jquery-ui.min.css">
<link rel="stylesheet"
	href="../jquery-ui-1.10.3.custom/css/redmond/jquery.ui.theme.css">
<link rel="stylesheet"
	href="css/admin.css">
<script src="../jquery-ui-1.10.3.custom/js/jquery-1.9.1.js"></script>
<script src="../jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.js"></script>

<script>

	function expand(el) {
		childObj = document.getElementById("child" + el);

		if (childObj.style.display == 'none') {
			childObj.style.display = 'block';
		} else {
			childObj.style.display = 'none';
		}
		return;
	}

	function addTab(title, id, url) {
		//var tabsDiv= $("#mainFrame",parent.document.body).contents().find("#tabs");//$(window.frames["mainFrame"].document).find("#tabs");
		var tempIdx = tabsHasOpened[id];
		if (tempIdx != null) {
			//alert("have "+id+","+tempIdx);
			tabs.tabs("option", "active", tempIdx);
			return;
		}
		var li = "<li><a href='#"+id+"'>"
				+ title
				+ "</a> <span class='ui-icon ui-icon-close' role='presentation'>Remove Tab</span></li>";

		var tabContentHtml = "<iframe height='100%' width='100%' border='0' frameborder='0' src='"
				+ url + "'>浏览器不支持嵌入式框架，或被配置为不显示嵌入式框架。</iframe>  ";

		tabs = $("#tabs").tabs();
		tabs.find(".ui-tabs-nav").append(li);
		tabs.append("<div style='height:90%' id='" + id + "'>" + tabContentHtml
				+ "</div>");
		tabsHasOpened[id] = tabCount++;
		tabs.tabs("refresh");
		tabs.tabs("option", "active", tabsHasOpened[id]);

	}
	var tabsHasOpened = {
		"tabs-1" : 0
	};
	var tabCount = 1;
	var tabs;

	$(function() {
		
		$( "#accordion" ).accordion();
		
		$("#switchBar").click(function(){
			var ssrc = $("img[name='img1']").attr("src");
			//alert(ssrc);
			if (ssrc.indexOf("main_55.gif") > 0) {
				$("img[name='img1']").attr("src","images/main_55_1.gif");
				$("#menu").hide();
				//$("#menu").animate({left:"-170px"},"slow");
				//$("#content").animate({width:"100%",left:"10px"},"slow");
				$("#content").css("width","100%").css("left","10px");
			} else {
				$("img[name='img1']").attr("src","images/main_55.gif");
				$("#menu").show();
				$("#content").css("width",($(window).width()-190)+"px").css("left","180px");
			}
		});
		
		//$("#center").css("height",($(window).height()-120)+"px");
		//$("#content").css("height",($(window).height()-220)+"px");
		$("#content").css("width",($(window).width()-190)+"px");
		tabs = $("#tabs").tabs();

		tabs.delegate("span.ui-icon-close", "click", function() {
			var panelId = $(this).closest("li").remove().attr("aria-controls");
			$("#" + panelId).remove();
			tabs.tabs("refresh");
			delete tabsHasOpened[panelId];
			var tempIdx = 0;
			for (key in tabsHasOpened) {
				tabsHasOpened[key] = tempIdx++;
			}
			tabCount--;
		});
	});
</script>
<style>
.navPoint {
	COLOR: white;
	CURSOR: hand;
	FONT-FAMILY: Webdings;
	FONT-SIZE: 9pt
}

/* #header ,#centers ,#footer{ width:100%; margin:0 auto; clear:both;font-size:18px; line-height:68px; font-weight:bold;} 
#header{ height:56px;} 
#center{margin-bottom:60px;} 
#footer{background:#F2F2F2;height: 60px;} 

#center .c_left{ float:left; width:170px;background:#F7F7F7;} 
#center .c_right{ float:left; margin:0 auto;background:#F7F7F7} */

#header {
	height: 56px;
	background-image:url(images/header_bg.jpg);
	background-repeat: repeat repeat;
}
#header .left {
	background-image:url(images/header_left.jpg);
}

#center {
	height:100%;
}

#footer {
	height: 60px;
}

#menu {
background-image: url(images/menu_bg.jpg);
	background-repeat: repeat repeat;
/*
	left:0px;
	top:57px;
	position:absolute;
	*/
	padding:2px;
	width: 170px;
	 float: left;
	
}
.menuChild{
margin:10px;
}
.menuItem{
margin-left:0px;
}
.menuItem p{
margin-left:0px;
	margin-top:10px;
}
#content {
	width:100%;
	position:absolute;
	top:57px;
	left:180px;
	margin-left:5px;
	margin:auto 0;
	BACKGROUND-COLOR: #1873aa;
}

#switchBar {
	height:100%; 
	margin:auto 0;
	BACKGROUND-COLOR: #1873aa;
	width: 6px;
	float: left;
} 
#switchBar img{
	margin:auto 0;
}

</style>
</head>

<body style="overflow: hidden;margin:0 auto;">
	<div id="header">
	<img src="images/header_left.jpg" />
	<!-- 
		<TABLE cellSpacing=0 cellPadding=0 width="100%"
			background="images/header_bg.jpg" border=0>
			<TR height=56>
				<TD width=260><IMG height=56 src="images/header_left.jpg"
					width=260></TD>
				<TD style="FONT-WEIGHT: bold; COLOR: #fff; PADDING-TOP: 20px"
					align=middle><A style="COLOR: #fff"
					href="http://115.28.242.231:8080/chatroom/" target="_blank">网站首页</A>
					&nbsp;&nbsp;<A style="COLOR: #fff"
					href="http://115.28.242.231:8080/chatroom/techer.html"
					target="_blank">讲师登陆</A> &nbsp;&nbsp;当前用户：${user.userName }
					&nbsp;&nbsp; <A style="COLOR: #fff" href="changePwd.do" >修改口令</A>
					&nbsp;&nbsp; <A style="COLOR: #fff"
					onclick="if (confirm('确定要退出吗？')) return true; else return false;"
					href="logout.do" target=_top>退出系统</A></TD>
				<TD align=right width=268><IMG height=56
					src="images/header_right.jpg" width=268></TD>
			</TR>
		</TABLE>
		 -->
	</div>


	<div id="center">
		<div id="menu" >
				<!-- begin menu -->
			<TABLE height="100%" cellSpacing=0 cellPadding=0 width=170
				background=images/menu_bg.jpg border=0>
				<TR>
					<TD vAlign=top align=middle>
						<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>

							<TR>
								<TD height=10></TD>
							</TR>
						</TABLE>
						<TABLE cellSpacing=0 cellPadding=0 width=150 border=0>

							<TR height=22>
								<TD style="PADDING-LEFT: 30px" background=images/menu_bt.jpg><A
									class=menuParent onclick=expand(1) href="javascript:void(0);">WebAPP管理</A></TD>
							</TR>
							<TR height=4>
								<TD></TD>
							</TR>
						</TABLE>
						<TABLE id=child1 cellSpacing=0 cellPadding=0 width=150 border=0>
							<TR height=20>
								<TD align=middle width=30><IMG height=9
									src="images/menu_icon.gif" width=9></TD>
								<TD><A class=menuChild
									onclick="addTab('投资日报','tab_item_1','../editWebApp.do?appid=app1')"
									href="#">投资日报</A></TD>
							</TR>
							<TR height=20>
								<TD align=middle width=30><IMG height=9
									src="images/menu_icon.gif" width=9></TD>
								<TD><A class=menuChild
									onclick="addTab('操作建议','tab_item_2','../editWebApp.do?appid=app2')"
									href="#">操作建议</A></TD>
							</TR>
							<TR height=20>
								<TD align=middle width=30><IMG height=9
									src="images/menu_icon.gif" width=9></TD>
								<TD><A class=menuChild
									onclick="addTab('经典战绩','tab_item_3','../editWebApp.do?appid=app3')"
									href="#" >经典战绩</A></TD>
							</TR>
							<TR height=20>
								<TD align=middle width=30><IMG height=9
									src="images/menu_icon.gif" width=9></TD>
								<TD><A class=menuChild
									onclick="addTab('收盘点评','tab_item_4','../editWebApp.do?appid=app4')"
									href="#" >收盘点评</A></TD>
							</TR>
							<TR height=20>
								<TD align=middle width=30><IMG height=9
									src="images/menu_icon.gif" width=9></TD>
								<TD><A class=menuChild
									onclick="addTab('盘中提示','tab_item_5','../editWebApp.do?appid=app5')"
									href="#" >盘中提示</A></TD>
							</TR>
							<TR height=20>
								<TD align=middle width=30><IMG height=9
									src="images/menu_icon.gif" width=9></TD>
								<TD><A class=menuChild
									onclick="addTab('操盘日记','tab_item_6','../editWebApp.do?appid=app6')"
									href="#" >操盘日记</A></TD>
							</TR>
							<TR height=4>
								<TD colSpan=2></TD>
							</TR>
						</TABLE>

						<TABLE cellSpacing=0 cellPadding=0 width=150 border=0>
							<TR height=22>
								<TD style="PADDING-LEFT: 30px" background=images/menu_bt.jpg><A
									class=menuParent onclick=expand(7) href="javascript:void(0);">系统管理</A></TD>
							</TR>
							<TR height=4>
								<TD></TD>
							</TR>
						</TABLE>
						<TABLE id=child7 style="" cellSpacing=0 cellPadding=0
							width=150 border=0>

							<TR height=20>
								<TD align=middle width=30><IMG height=9
									src="images/menu_icon.gif" width=9></TD>
								<TD><A class=menuChild
									onclick="addTab('分组管理','tab_item_7','techer_group.do')"
									href="#" >分组管理</A></TD>
							</TR>
							<TR height=20>
								<TD align=middle width=30><IMG height=9
									src="images/menu_icon.gif" width=9></TD>
								<TD><A class=menuChild
									onclick="addTab('老师管理','tab_item_8','techer_list.do')" href="#"
									>老师管理</A></TD>
							</TR>
							<TR height=20>
								<TD align=middle width=30><IMG height=9
									src="images/menu_icon.gif" width=9></TD>
								<TD><A class=menuChild
									onclick="addTab('客户管理','tab_item_9','user_list.do')" href="#"
									>客户管理</A></TD>
							</TR>
						</TABLE>
						<TABLE cellSpacing=0 cellPadding=0 width=150 border=0>

							<TR height=22>
								<TD style="PADDING-LEFT: 30px" background=images/menu_bt.jpg><A
									class=menuParent onclick=expand(0) href="javascript:void(0);">个人管理</A></TD>
							</TR>
							<TR height=4>
								<TD></TD>
							</TR>
						</TABLE>
						<TABLE id=child0 style="DISPLAY: none" cellSpacing=0 cellPadding=0
							width=150 border=0>
							<TR height=20>
								<TD align=middle width=30><IMG height=9
									src="images/menu_icon.gif" width=9></TD>
								<TD><A class=menuChild
									onclick="addTab('qq','tab_item_11','qq_list.do')" href="#"
									>qq</A></TD>
							</TR>
							<TR height=20>
								<TD align=middle width=30><IMG height=9
									src="images/menu_icon.gif" width=9></TD>
								<TD><A class=menuChild
									onclick="addTab('修改口令','tab_item_10','changePwd.do')" href="#"
									>修改口令</A></TD>
							</TR>
							<TR height=20>
								<TD align=middle width=30><IMG height=9
									src="images/menu_icon.gif" width=9></TD>
								<TD><A class=menuChild
									onclick="if (confirm('确定要退出吗？')) return true; else return false;"
									href="logout.do" target=_top>退出系统</A></TD>
							</TR>
						</TABLE>
					</TD>
					<TD width=1 bgColor=#d1e6f7></TD>
				</TR>
			</TABLE>
			<!-- end menu -->
		</div>
		<div id="switchBar" class=navPoint title=关闭/打开左栏>
			<img src="images/main_55.gif" name="img1" width=6 height=40 id=img1 />
		</div>
		<div id="content">
			<div>
					<div id="tabs">
						<ul>
							<li><a href="#tabs-1">首页</a></li>
						</ul>
						<div id="tabs-1" >
							<iframe height="100%"  width="100%" border="0" frameborder="0"
								src="">浏览器不支持嵌入式框架，或被配置为不显示嵌入式框架。</iframe>
						</div>
					</div>
			</div>
			

			
		</div>
	</div>
	<div id="footer"></div>

</body>
</html>
