<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML><HEAD>
<META http-equiv=Content-Type content="text/html; charset=utf-8">
<LINK href="admin/css/admin.css" type="text/css" rel="stylesheet">
<script src="jquery-ui-1.10.3.custom/js/jquery-1.9.1.js"></script>
<script src="jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.js"></script>
<SCRIPT language=javascript>
	function expand(el)
	{
		childObj = document.getElementById("child" + el);

		if (childObj.style.display == 'none')
		{
			childObj.style.display = 'block';
		}
		else
		{
			childObj.style.display = 'none';
		}
		return;
	}
	var tabCounter=1;
	var tabTemplate = "";
    
	function addTab(title,id,url){
		var tabsDiv= $("#mainFrame",parent.document.body).contents().find("#tabs");//$(window.frames["mainFrame"].document).find("#tabs");
		
		var li ="<li><a href='#"+id+"'>"+title+"</a> <span class='ui-icon ui-icon-close' role='presentation'>Remove Tab</span></li>";
        
        var tabContentHtml = "<iframe height='100%' width='100%' border='0' frameborder='0' src='"+url+"'>浏览器不支持嵌入式框架，或被配置为不显示嵌入式框架。</iframe>  ";
 
        tabsDiv.find( ".ui-tabs-nav" ).append( li );
        tabsDiv.append( "<div id='" + id + "'>" + tabContentHtml + "</div>" );
        var tabs=tabsDiv.tabs();
        tabs.tabs("refresh");
      	tabCounter++;
	}
</SCRIPT>
</HEAD>
<BODY>
<TABLE height="100%" cellSpacing=0 cellPadding=0 width=170 
background=admin/images/menu_bg.jpg border=0>
  <TR>
    <TD vAlign=top align=middle>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        
        <TR>
          <TD height=10></TD></TR></TABLE>
      <TABLE cellSpacing=0 cellPadding=0 width=150 border=0>
        
        <TR height=22>
          <TD style="PADDING-LEFT: 30px" background=admin/images/menu_bt.jpg><A 
            class=menuParent onclick=expand(1) 
            href="javascript:void(0);">WebAPP</A></TD></TR>
        <TR height=4>
          <TD></TD></TR></TABLE>
      <TABLE id=child1 cellSpacing=0 cellPadding=0 
      width=150 border=0>
      	<TR height=20>
          <TD align=middle width=30><IMG height=9 
            src="admin/images/menu_icon.gif" width=9></TD>
          <TD><A class=menuChild 
          onclick="addTab('投资日报','tab_item_1','admin/techer_list.do')"
            href="#" 
            >投资日报</A></TD></TR>
        <TR height=20>
          <TD align=middle width=30><IMG height=9 
            src="admin/images/menu_icon.gif" width=9></TD>
          <TD><A class=menuChild 
          onclick="addTab('操作建议','tab_item_2','admin/user_list.do')"
            href="#" 
            >操作建议</A></TD></TR>
        <TR height=4>
          <TD colSpan=2></TD></TR></TABLE>
          
      </TD>
    <TD width=1 bgColor=#d1e6f7></TD></TR></TABLE></BODY></HTML>
