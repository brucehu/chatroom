
var userID;
var roomID;
var roomText;
var isLogin=false;


	
function login(){
	userID=$("#techerID").val();
	
	$.ajax({
		type : "get", //使用get方法访问后台
		dataType : "json", //返回json格式的数据
		url : "ChatMainServlet", //要访问的后台地址
		data : {
			techerID : userID,
			messageType:"techer_login"
		}, //要发送的数据
		complete : function() {
			//console.log('login complete');
		}, //AJAX请求完成时
		error : function() {
			alert("error");
			////console.log('login error');
		},
		success : function(msg) { //msg为返回的数据，在这里做数据绑定
			//alert(msg);
			var status = msg.status;
			if (status == 0) {
				isLogin=true;
				alert(msg.message);
				$("#loginForm").hide();
				$("#chatForm").show();
				$("#tempuserID").val(userID);
				//$("#pollMessage").append("<p>欢迎用户"+userID+"进入聊天室</p>");
				poll();
			} else if (status == 1) {
				alert(msg.message);
			}
		}
	});
}

function logout(){
	isLogin=false;
	//console.log('logout...'+isLogin);
	$.ajax({
		type : "get", //使用get方法访问后台
		dataType : "json", //返回json格式的数据
		url : "ChatMainServlet", //要访问的后台地址
		data : {
			userID : userID,
			messageType:"logout"
		}, //要发送的数据
		complete : function() {
			//console.log('login complete');
		}, //AJAX请求完成时
		error : function() {
			alert("error");
			////console.log('login error');
		},
		success : function(msg) { //msg为返回的数据，在这里做数据绑定
			//alert(msg);
			var status = msg.status;
			if (status == 0) {
				alert(msg.message);
				$("#loginForm").show();
				//$("#chatForm").hide();
				$("#pollMessage").html("");
			} else if (status == 1) {
				window.location.href = msg.redirect;
			}
		}
	});
}

function send(){
	//alert("send");
	var message=$("#message").val();
	var toUserID=$("#toUserID").val();
	$("#resMessage").html("");
	$.ajax({
		type : "get", //使用get方法访问后台
		dataType : "json", //返回json格式的数据
		url : "ChatMainServlet", //要访问的后台地址
		data : {
			userID : userID,
			toUserID:toUserID,
			message : message,
			messageType:"push"
		}, //要发送的数据
		complete : function() {
			//console.log('send complete');
			//alert("complete");
		}, //AJAX请求完成时
		error : function() {
			alert("error");
			//console.log('send error');
		},
		success : function(msg) { //msg为返回的数据，在这里做数据绑定
			var status = msg.status;
			if (status == 0) {
				//alert(msg.message);
				$("#message").val("");
				$("#resMessage").html(msg.message);
			} else if (status == 1) {
				//alert(msg.message);
				$("#resMessage").html(msg.message);
			}
		}
	});
}

function poll(){
	//console.log('begin poll...'+isLogin);
	if(!isLogin){
		return;
	}
	$.ajax({
		type : "get", //使用get方法访问后台
		dataType : "json", //返回json格式的数据
		url : "ChatMainServlet", //要访问的后台地址
		data : {
			userID : userID,
			messageType:"poll"
		}, //要发送的数据
		complete : function() {
			//alert("complete");
			//console.log('poll complete');
			poll();
		}, //AJAX请求完成时
		error : function() {
			//alert("error");
			//console.log('poll error');
		},
		success : function(msg) { //msg为返回的数据，在这里做数据绑定
			if(msg.status==0){
				if(msg.messageType==1){
					$("#pollMessage").append("<p><a href='#' onclick=\"clickUserID('"+msg.userID+"')\">"+msg.userID+"</a>"+"说："+msg.message+"</p>");
					var top=Math.max(0,$("#pollMessage")[0].scrollHeight-$("#pollMessage")[0].offsetHeight);
					//alert(top);
					$("#pollMessage").scrollTop(top);
				}else if(msg.messageType==2){//有客户进入
					//alert("test");
					$("#userlist").append("<p id='userlist_"+msg.userID+"'><a href='#' onclick=\"clickUserID('"+msg.userID+"')\">"+msg.userID+"</a></p>");
				}else if(msg.messageType==3){//有客户退出
					//alert("test");
					$("#userlist_"+msg.userID).remove();
					//$("#userlist").append("<p><a href='#' onclick=\"clickUserID('"+msg.userID+"')\">"+msg.userID+"</a></p>");
				}
			} else if (msg.status == 1) {
				isLogin=false;
				alert(msg.message);
			}
			//console.log('poll success');
		}
	});
}



window.onbeforeunload = function() { 
	//alert("onbeforeunload");
	if(isLogin){
		logout();
	}
};
window.onunload = function() { 
	//alert("onunload");
	if(isLogin){
		logout();
	}
};

function clickUserID(toUserID){
	$("#toUserID").val(toUserID);
}
$(document).ready(function() {
	$.ajaxSetup({cache: false });
	//$("#chatForm").hide();
	//alert($("#pollMessage")[0].offsetHeight);
	var top=Math.max(0,$("#pollMessage")[0].scrollHeight-$("#pollMessage")[0].offsetHeight);
	//alert(top);
	$("#pollMessage").scrollTop(top);
	$("#login").click(function(){
		login();
	});
	$("#logout").click(function(){
		logout();
	});
	$("#send").click(function(){
		send();
	});
	
});