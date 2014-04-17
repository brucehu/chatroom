var techerID;
var roomID;
var roomText;
var isLogin=false;
jsSocket.isTecher=true;

jsSocket.pollCallBack=function(msg){
	var messageType=msg.messageType;
	
	if(messageType==1){//聊天消息
		var status=msg.status;
//		alert(messageType+","+status);
		if(status==1){
			var message=msg.message;
			chatMessageUpdate("<div class='pop'><p>"+msg.sendTime+"</p><a href='#' onclick=\"clickUserID('"+msg.userID+"','"+msg.nickName+"')\">"+msg.nickName+"</a>说："+message+"</div>");
		}else if(status==2){
//			chatMessageUpdate("<div class='pop'>服务器相应。</div>");
		}else if(status==0){
			jsSocket.closeSocket();
			var message=msg.message;
			chatMessageUpdate("<div class='pop'>"+message+"</div>");
		}
	}else if(messageType==2){//新用户进入
		$("#userlist").append("<p id='user"+msg.userID+"'><a href='#' onclick=\"clickUserID('"+msg.userID+"','"+msg.nickName+"')\">"+msg.nickName+"</a></p>");
	}else if(messageType==3){//用户退出
		$("#user"+msg.userID).remove();
	}
};

function chatMessageUpdate(message){
	$("#pollMessage").append(message);
	var top=Math.max(0,$("#pollMessage")[0].scrollHeight-$("#pollMessage")[0].offsetHeight);
	$("#pollMessage").scrollTop(top);
}

function clickUserID(toUserID,toUserNickName){
	$("#toUserID").val(toUserID);
	$("#toUserNickName").val(toUserNickName);
}
	
function login(){
	$("#btn_login").attr("disabled","true");
	$("#btn_login").val("登录中...");
	var userName=$("#text_userID").val();
	var passWord=$("#text_password").val();
	var data={userName:userName,passWord:passWord};
	jsSocket.url="techer_login.do";
	jsSocket.http(data,function(msg){
		if(msg.status==1){
			techerID=msg.techerID;
			chatMessageUpdate("<div class='pop2'>登陆成功</div>");
			isLogin=true;
			$("#techerName").val(msg.nickName);
			$("#btn_login").removeAttr("disabled");
			$("#btn_login").val("登录");
			$("#login_window").hide();
			jsSocket.userID=techerID;
			jsSocket.isConnected=true;
			jsSocket.poll();
		}else if(msg.status==0){
			$("#btn_login").removeAttr("disabled");
			$("#btn_login").val("登录");
			alert(msg.message);
		}
		
	});
}


function logout(flag){
	var data={userID:jsSocket.userID,flag:flag};
	jsSocket.url="techer_logout.do";
	jsSocket.http(data,function(msg){
		jsSocket.closeSocket();
	});
	$("#login_window").show();
}

function send(){
	
	$("#resMessage").html("发送...");
	var message=$("#message").val();
	var toUserID=$("#toUserID").val();
	var toUserNickName=$("#toUserNickName").val();
	message=message.replace(/\r\n/g,  "<br/>");
	var data={userID:toUserID,techerID:jsSocket.userID,message:message};
//	alert(data.userID+data.techerID+data.message);
	jsSocket.url="techer_send.do";
	jsSocket.http(data,function(msg){
		if(msg.status==1){
			chatMessageUpdate("<div class='pop2'><p>"+msg.sendTime+"</p>你对<a href='#' onclick=\"clickUserID('"+toUserID+"','"+toUserNickName+"')\">"+toUserNickName+"</a>说："+message+"</div>");
			$("#message").val("");
			$("#resMessage").html("发送成功");
		}
	});
}

window.onbeforeunload = function() { 
//	alert("onbeforeunload");
	logout("onbeforeunload");
	return "确定退出！";
};
window.onunload = function ()  {
//	alert("onunload");
	logout("onunload");
	return "确定退出！";
};

$(document).ready(function() {
	$.ajaxSetup({cache: false });
	$("#message").keypress(function(event){
	    var k = event.which; 
	    if(k==13||k==10){
	    	send();
	    }
	});
	$("#btn_login").click(function(){
		login();
	});
	$("#logout").click(function(){
		logout("button");
	});
	$("#send").click(function(){
		send();
	});
	
});