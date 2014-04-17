var userID;
//var roomID;
//var roomText;
var isLogin=false;
var toUserID;
/*
 * 登录
 */
function login(){
	isLogin=true;
	$("#chat_window").show();
	$("#pollMessage").append("<p>正在连接"+toUserID+"老师，</p>");
	$.ajax({
		type : "get", //使用get方法访问后台
		dataType : "json", //返回json格式的数据
		url : "ChatMainServlet", //要访问的后台地址
		data : {
			userID : userID,
			toUserID : toUserID,
			messageType:"login"
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
			if (status == 0) {//login success
				$("#toUserText").html("与"+toUserID+"对话中");
				pollMessageUpdate("<p>连接"+toUserID+"成功!</p>");
				poll();
			} else if (status == 1) {
				pollMessageUpdate("<p>连接错误："+msg.message+"</p>");
			}
		}
	});
}

function logout(){
	isLogin=false;
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
				//alert(msg.message);
				//pollMessageUpdate("<p>"+msg.message+"</p>");
			} else if (status == 1) {
				pollMessageUpdate("<p>"+msg.message+"</p>");
			}
		}
	});
}

function send(message){

	$("#resMessage").html("发送中.....");
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
				$("#toast").html(msg.message);
				pollMessageUpdate("<p>你说："+message+"</p>");
			} else if (status == 1) {
				//alert(msg.message);
				$("#toast").html(msg.message);
			}
		}
	});
}

function poll(){
	//console.log('begin poll...');
	if(!isLogin){
		return;
	}
	$.ajax({
		type : "get", //使用get方法访问后台
		dataType : "json", //返回json格式的数据
		url : "ChatMainServlet", //要访问的后台地址
		data : {
			userID : userID,
			messageType:"poll",
			toUserID:toUserID
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
				pollMessageUpdate("<p>"+msg.userID+"说："+msg.message+"</p>");
			} else if (msg.status == 1) {
				isLogin=false;
				alert(msg.message);
			}
			//console.log('poll success');
		}
	});
}
function changeTecher(old_techer,new_techer){
	toUserID=new_techer;
	$.ajax({
		type : "get", //使用get方法访问后台
		dataType : "json", //返回json格式的数据
		url : "ChatMainServlet", //要访问的后台地址
		data : {
			userID : userID,
			toUserID:toUserID,
			messageType:"changeTecher"
		}, //要发送的数据
		complete : function() {
			//alert("complete");
			//console.log('poll complete');
			//poll();
		}, //AJAX请求完成时
		error : function() {
			//alert("error");
			//console.log('poll error');
		},
		success : function(msg) { //msg为返回的数据，在这里做数据绑定

			if(msg.status==0){
				$("#toUserText").html("与"+toUserID+"对话中");
				pollMessageUpdate("<p>连接"+toUserID+"成功</p>");
			} else if (msg.status == 1) {
				toUserID=old_techer;
				pollMessageUpdate("<p>"+msg.message+"</p>");
				//pollMessageUpdate("<p>继续跟"+old_techer+"老师对话。</p>");
			}
			$("#toUserText").html("与"+toUserID+"对话中");
			//console.log('poll success');
		}
	});
}

function pollMessageUpdate(message){
	$("#pollMessage").append(message);
	var top=Math.max(0,$("#pollMessage")[0].scrollHeight-$("#pollMessage")[0].offsetHeight);
	$("#pollMessage").scrollTop(top);
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
function newWidget(parmeters) {
	var windowDiv = "<div id='"+parmeters.id+"' style='position:absolute;top:"
			+ parmeters.y
			+ "px;left:"
			+ parmeters.x
			+ "px;z-index:1;width:"
			+ parmeters.w
			+ "px;height:"
			+ parmeters.h
			+ "px'><iframe width='100%' height='100%' frameborder='0' marginwidth='0' marginheight='0' scrolling='yes' src='"
		+ parmeters.src + "'></iframe></div>";
	$("body").append(windowDiv);
	$("#" + parmeters.id).draggable();
}
function newWidgetNoTitle(parmeters) {
	var windowDiv = "<div id='"+parmeters.id+"' style='position:absolute;top:"
			+ parmeters.y
			+ "px;left:"
			+ parmeters.x
			+ "px;z-index:1;width:"
			+ parmeters.w
			+ "px;height:"
			+ parmeters.h
			+ "px'><iframe width='100%' height='100%' frameborder='0' marginwidth='0' marginheight='0' scrolling='yes' src='"
		+ parmeters.src + "'></iframe></div>";
	$("body").append(windowDiv);
	$("#" + parmeters.id).draggable();
}
//x, y, w, h, title, id, src,resizeable,haveBottom;
function newWindow(parmeters) {
	if($("#"+parmeters.id).length>0){
		$(".window").css("z-index", 2);
		$("#" + parmeters.id).css("z-index",3);
		return;
	}
	var bodyHeight = parmeters.h - 50;
	var windowDiv = "<div id='"
			+ parmeters.id
			+ "' class='window' style='position:absolute;top:"
			+ parmeters.y
			+ "px;left:"
			+ parmeters.x
			+ "px;z-index:3;width:"
			+ parmeters.w
			+ "px;height:"
			+ parmeters.h
			+ "px'><div class='win_title'><b>"
			+ parmeters.title
			+ "</b><span class='win_btnblock'><a href='#' class='winClose' title='关闭'></a></span></div><div class='win_body' style='height:"
			+ bodyHeight
			+ "px'><iframe width='100%' height='100%' frameborder='0' marginwidth='0' marginheight='0' scrolling='yes' src='"
			+ parmeters.src + "'></iframe></div>";
	if(parmeters.haveBottom==undefined||parmeters.haveBottom){
		windowDiv+="<div class='win_bottom'></div></div>";
	}else{
		windowDiv+="</div>";		
	}//<div class='win_bottom'></div></div>
	$("body").append(windowDiv);

	$("#" + parmeters.id + " .winClose").click(function() {
		$("#" + parmeters.id).remove();
	});
	
	
	var window_div=$("#" + parmeters.id).draggable({
		cancle : "#" + parmeters.id + " .win_body",
		start : function() {
			$(".window").css("z-index", 2);
			$("#" + parmeters.id).css("z-index", 3);
		},
		drag : function() {

		},
		stop : function() {

		}
	});

	if(parmeters.resizeable){
		window_div.resizable({
			alsoResize : "#" + parmeters.id + " .win_body",
			resize:function(event, ui){
				$(".window").css("z-index", 2);
				$("#" + parmeters.id).css("z-index",3);
			}
		});
	}
	//alert("newWindow");
}

function chatInit(){
	
	var win_h=$( window ).height(); 
	var win_w=$( window ).width(); 
	var left=(win_w-$("#login_window").width())/2;
	var top=(win_h-$("#login_window").height())/2;
	//alert(left+","+top);
	$("#login_window").css("left",left+"px").css("top",top+"px");
	$("#login_window .winClose").click(function(){
		$("#login_window").hide();
	});
	$("#btn_login").click(function(){
		userID=$("#text_userID").val();
		//alert("设置成功！\n点击老师头像与老师聊天");
		if(userID!=undefined&&userID.length>0){
			login();
			$("#login_window").hide();
		}else{
			alert("不能设置昵称为空");
		}
		
	});
	
	$("#techer_list").draggable({
		cancel : "#accordion",
		start:function(){
			$(".window").css("z-index", 2);
			$("#techer_list").css("z-index", 3);
		}
	}).resizable({
		alsoResize: "#accordion",
	      maxWidth: 200,
	      minWidth: 200,
		resize: function() {
			$(".window").css("z-index", 2);
			$("#techer_list").css("z-index", 3);
	        $( "#accordion" ).accordion( "refresh" );
	    }
	});
	var windowWidth= $(window).width();
	$("#techer_list").css('left',(windowWidth-$("#techer_list").width()-100));
	$("#accordion").accordion({heightStyle: "fill"});
	
	
	
	$("#chat_window").draggable({
		cancel : "#win_body",
		start:function(){
			$(".window").css("z-index", 2);
			$("#chat_window").css("z-index", 3);
		}
	}).resizable({
		cancel : "textarea",
		alsoResize: "#pollMessage",
		resize: function(event, ui) {
			var width=$("#pollMessage").width()-10;
			$("#send_message textarea").css("width",width+"px");
			$(".window").css("z-index", 2);
			$("#chat_window").css("z-index", 3);
	    }
	});
	
	$("#chat_window .winClose").click(function(){
		if(confirm("要关闭与老师的对话吗？")){
			logout();
			$("#chat_window").hide();
			$("#pollMessage").html("");
		}
	});
	var width=$("#pollMessage").width();
	$("#send_message textarea").css("width",width+"px");
	
	
	
	
	$(".techer").click(function(){
		
		//alert(userID);
		
		if(userID==undefined||userID.length<=0){
			toUserID=$(this).attr("id");
			$("#login_window").show();
			return;
		}
		
		$("#chat_window").show();
		if(!isLogin){
			toUserID=$(this).attr("id");
			login();
		}else{
			if(toUserID!=undefined&&toUserID.length>0){
				var new_techer=$(this).attr("id");
				if(toUserID!=new_techer){
					changeTecher(toUserID,new_techer);
				}
				return;
			}
		}
		
	});
	
	$("#btn_send").click(function(){
		var message=$("#message").val();
		$("#toast").html("发送...");
		send(message);
	});
	
	$("#chat_window").hide();
	$("#login_window").hide();
}

function appInit(){
	//alert("appInit");
	//x, y, w, h, title, id, src,resizeable,haveBottom
	$("#Pixlr").click(function(){
		newWindow({x:0,y:0,w:600,h:500,title:'百度',id:'leshiwang_div',src:'http://www.baidu.com',resizeable:true});
	});
	$("#leshiwang").click(function(){
		newWindow({x:100,y:100,w:320,h:500,title:'投资顾问',id:'Pixlr_div',src:'http://www.91demi.com:8089/web/stock/index.jsp',resizeable:false});
	});
//	$("#dubianFim").click(function(){
//		newWindow(100,100,600,400,'豆瓣FM','dubianFim_div','http://douban.fm/',true);
//	});
	
	var temp_width=296,temp_height=247;
	var x=($(window).width()-temp_width)/2;
	var y=20;
	newWidget({x:x,y:y,w:temp_width,h:temp_height,title:'行情',id:'hangqing_div',src:'http://data.stock.hexun.com/quotes/stock_1.htm'});
	
}

function uuid(len, radix) {
    var chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
    var uuid = [], i;
    radix = radix || chars.length;
 
    if (len) {
      // Compact form
      for (i = 0; i < len; i++) uuid[i] = chars[0 | Math.random()*radix];
    } else {
      // rfc4122, version 4 form
      var r;
 
      // rfc4122 requires these characters
      uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
      uuid[14] = '4';
 
      // Fill in random data.  At i==19 set the high bits of clock sequence as
      // per rfc4122, sec. 4.1.5
      for (i = 0; i < 36; i++) {
        if (!uuid[i]) {
          r = 0 | Math.random()*16;
          uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
        }
      }
    }
 
    return uuid.join('');
}

$(document).ready(function() {
	userID=uuid(19, 16);
	alert(userID);
	alert(userID.length);
	$.ajaxSetup({cache: false });
	
	$("#login").click(function(){
		login();
	});
	$("#logout").click(function(){
		logout();
	});
	$("#send").click(function(){
		send();
	});
	chatInit();
	appInit();
});