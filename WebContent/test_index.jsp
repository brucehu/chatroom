<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.10.4/themes/redmond/jquery-ui.css">
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.10.4/themes/redmond/jquery.ui.theme.css">
<LINK href="admin/css/admin.css" type="text/css" rel="stylesheet">
<script src="jquery-ui-1.10.3.custom/js/jquery-1.9.1.js"></script>
<script src="jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.js"></script>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
<style>
.navPoint {
	COLOR: white;
	CURSOR: hand;
	FONT-FAMILY: Webdings;
	FONT-SIZE: 9pt
}
</style>
<script>
	function switchSysBar() {

		var locate = location.href.replace('test_index.jsp', '');
		var ssrc = document.all("img1").src.replace(locate, '');
		if (ssrc.indexOf("images/main_55.gif") > 0) {
			document.all("img1").src = "admin/images/main_55_1.gif";
			document.all("frmTitle").style.display = "none";
		} else {
			document.all("img1").src = "admin/images/main_55.gif";
			document.all("frmTitle").style.display = "";
		}
	}

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
		var tempIdx=tabsHasOpened[id];
		if(tempIdx!=null){
			//alert("have "+id+","+tempIdx);
			tabs.tabs("option","active",tempIdx);
			return;
		}
		var li = "<li><a href='#"+id+"'>"
				+ title
				+ "</a> <span class='ui-icon ui-icon-close' role='presentation'>Remove Tab</span></li>";

		var tabContentHtml = "<iframe height='100%' width='100%' border='0' frameborder='0' src='"
				+ url + "'>浏览器不支持嵌入式框架，或被配置为不显示嵌入式框架。</iframe>  ";

		tabs = $("#tabs").tabs();
		tabs.find(".ui-tabs-nav").append(li);
		tabs.append("<div style='height:90%' id='" + id + "'>" + tabContentHtml + "</div>");
		tabsHasOpened[id]=tabCount++;
		tabs.tabs("refresh");
		tabs.tabs("option","active",tabsHasOpened[id]);
		
	}
	var tabsHasOpened={"tabs-1":0};
	var tabCount=1;
	var tabs;

	$(function() {
		tabs = $("#tabs").tabs();

		tabs.delegate("span.ui-icon-close", "click", function() {
			var panelId = $(this).closest("li").remove().attr("aria-controls");
			$("#" + panelId).remove();
			tabs.tabs("refresh");
			delete tabsHasOpened[panelId];
			var tempIdx=0;
			for (key in tabsHasOpened) {
				tabsHasOpened[key]=tempIdx++;
            }
			tabCount--;
		});
	});
</script>

</head>

<body style="overflow: hidden">
	<table width="100%" height="100%" border="0" cellpadding="0"
		cellspacing="0" style="table-layout: fixed;">
		<tr>
			<td width="171" id=frmTitle noWrap name="fmTitle" align="center"
				valign="top"><table width="171" height="100%" border="0"
					cellpadding="0" cellspacing="0" style="table-layout: fixed;">
					<tr>
						<td bgcolor="#1873aa" style="width: 6px;">&nbsp;</td>
						<td width="165">
							<TABLE height="100%" cellSpacing=0 cellPadding=0 width=170
								background=admin/images/menu_bg.jpg border=0>
								<TR>
									<TD vAlign=top align=middle>
										<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>

											<TR>
												<TD height=10></TD>
											</TR>
										</TABLE>
										<TABLE cellSpacing=0 cellPadding=0 width=150 border=0>

											<TR height=22>
												<TD style="PADDING-LEFT: 30px"
													background=admin/images/menu_bt.jpg><A
													class=menuParent onclick=expand(1)
													href="javascript:void(0);">WebAPP</A></TD>
											</TR>
											<TR height=4>
												<TD></TD>
											</TR>
										</TABLE>
										<TABLE id=child1 cellSpacing=0 cellPadding=0 width=150
											border=0>
											<TR height=20>
												<TD align=middle width=30><IMG height=9
													src="admin/images/menu_icon.gif" width=9></TD>
												<TD><A class=menuChild
													onclick="addTab('投资日报','tab_item_1','admin/techer_list.do')"
													href="#">投资日报</A></TD>
											</TR>
											<TR height=20>
												<TD align=middle width=30><IMG height=9
													src="admin/images/menu_icon.gif" width=9></TD>
												<TD><A class=menuChild
													onclick="addTab('操作建议','tab_item_2','admin/user_list.do')"
													href="#">操作建议</A></TD>
											</TR>
											<TR height=4>
												<TD colSpan=2></TD>
											</TR>
										</TABLE>

									</TD>
									<TD width=1 bgColor=#d1e6f7></TD>
								</TR>
							</TABLE>

						</td>
					</tr>
				</table></td>
			<td width="6" style="width: 6px;" valign="middle" bgcolor="1873aa"
				onclick="switchSysBar()"><SPAN class=navPoint id=switchPoint
				title=关闭/打开左栏><img src="admin/images/main_55.gif" name="img1"
					width=6 height=40 id=img1></SPAN></td>
			<td align="center" valign="top">

				<div id="tabs" style="padding:5px;margin:5px">
					<ul>
						<li><a href="#tabs-1">首页</a></li>
					</ul>
					<div id="tabs-1" style="height: 90%">
						<iframe height="100%" width="100%" border="0" frameborder="0"
							src="admin/techer_list.do">浏览器不支持嵌入式框架，或被配置为不显示嵌入式框架。</iframe>
					</div>

				</div>

			</td>
		</tr>
	</table>
</body>
</html>
