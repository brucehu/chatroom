var isLogin=false;//是否已经登录
var userID="";//登录成功以后用户的会话ID
var techer;
var techerMap = {};
var showTime=0;
var messageSound=new Audio;
var message={   
        time: 0,   
        title: document.title,   
        timer: null,   
  
        // 显示新消息提示   
        show:function(){   
            var title = message.title.replace("【　　　】", "").replace("【新消息】", "");  
            // 定时器，设置消息切换频率闪烁效果就此产生   
            message.timer = setTimeout(  
                function() {   
                    message.time++;  
                    message.show();   
  
                    if (message.time % 2 == 0) {   
                        document.title = "【新消息】" + title;   
                    }  
                    else{   
                        document.title = "【　　　】" + title;   
                    };   
                },   
                600 // 闪烁时间差  
            );   
            return [message.timer, message.title];   
        },   
  
        // 取消新消息提示   
        clear: function(){   
            clearTimeout(message.timer);   
            document.title = message.title;   
        }   
    };   


jsSocket.pollCallBack=function(msg){
	var messageType=msg.messageType;
	if(messageType==1){//聊天消息
		var status=msg.status;
		
		if(status==1){
			message.show();
			messageSound.play();
			chatMessageUpdate("<div class='pop'><p>"+msg.sendTime+"</p>"+msg.message+"</div>");
		}else if(status==2){
//			chatMessageUpdate("<div class='pop'>服务器相应。</div>");
		}else if(status==0){
			jsSocket.closeSocket();
			chatMessageUpdate("<div class='pop'>"+msg.message+"</div>");
		}
	}else if(messageType==2){//老师上线
		
	}else if(messageType==3){//老师退出
		$("#toUserText").html("当前未连接老师！");
//		$("#icon_"+techer.techerID).attr("class","gray_img");
		chatMessageUpdate("<div class='pop'>"+techer.techerName+"下线，请更换老师</div>");
//		techer.techerID="";
//		techer.online=false;
		
	}
	
	//console.log('pollCallBack，'+status);
};

function chatMessageUpdate(message){
	$("#pollMessage").append(message);
	var top=Math.max(0,$("#pollMessage")[0].scrollHeight-$("#pollMessage")[0].offsetHeight);
	$("#pollMessage").scrollTop(top);
}

function login(){
	$("#btn_login").attr("disabled","true");
	$("#btn_login").val("登录中...");
	var userName=$("#text_userID").val();
	var passWord=$("#text_password").val();
	var data={userName:userName,passWord:passWord};
	jsSocket.url="login.do";
	jsSocket.http(data,function(msg){
		if(msg.status==1){
			userID=msg.userID;
			alert("登录成功,点击老师头像开始对话。");
			isLogin=true;
			$("#btn_login").removeAttr("disabled");
			$("#login_window").hide();
		}else if(msg.status==0){
			$("#btn_login").removeAttr("disabled");
			$("#btn_login").val("登录");
			alert(msg.message);
		}
		
	});
}
function logout(){
	var data={userID:userID};
	jsSocket.url="logout.do";
	jsSocket.http(data,function(msg){
		jsSocket.closeSocket();
	});
}
function chatToTecher(){
//	alert(techer.techerID+","+techer.techerName);
	var data={userID:userID,techerID:techer.techerID};
	jsSocket.url="chatToTecher.do";
	jsSocket.http(data,function(msg){
		var status=msg.status;
		if(status==1){
			$("#toUserText").html(techer.techerName);
			chatMessageUpdate("<div class='pop2'>与"+techer.techerName+"对话中</div>");
			$("#chat_window").show();
		}else if(status==0){
//			alert(msg.message);
//			logout();
			$("#toUserText").html("当前未连接老师！");
			chatMessageUpdate("<div class='pop2'>"+techer.techerName+msg.message+"</div>");
//			$("#chat_window").hidden();
		}
	});
	
}

function sendChatMessage(message){
	message=message.replace(/\r\n/g,  "<br/>");
//	alert(techerID+","+techerName);
	$("#toast").html("发送...");
	var data={userID:userID,techerID:techer.techerID,message:message};
	jsSocket.url="sendChat.do";
	jsSocket.http(data,function(msg){
		if(msg.status==1){
			chatMessageUpdate("<div class='pop2'><p>"+msg.sendTime+"</p>"+message+"</div>");
			$("#message").val("");
			$("#toast").html("发送成功");
		}
	});
}
var appArray={
        "app1":{w:800,h:500,name:"app1",url:"admin/webAppIndex.do?appid=app1",title:"投资日报",id:"app1_div"},
        "app2":{w:800,h:500,name:"app2",url:"admin/webAppIndex.do?appid=app2",title:"操作建议",id:"app2_div"},
        "app3":{w:800,h:500,name:"app3",url:"admin/webAppIndex.do?appid=app3",title:"经典战绩",id:"app3_div"},
        "app4":{w:800,h:500,name:"app4",url:"admin/webAppIndex.do?appid=app4",title:"收盘点评",id:"app4_div"},
        "app5":{w:800,h:500,name:"app5",url:"admin/webAppIndex.do?appid=app5",title:"盘中提示",id:"app5_div"},
        "app6":{w:800,h:500,name:"app6",url:"admin/webAppIndex.do?appid=app6",title:"操盘日记",id:"app6_div"},
        "app7":{w:1000,h:500,name:"app7",url:"http://www.4000080676.com/uploadfile/2013/1205/20131205093936771.jpg",title:"实战体验",id:"app7_div"}
};
/*
 * 桌面上的webApp初始化
 */
function webAppInit(){

	
	$(".desktop_icon").click(function(){
		var appid=$(this).attr("id");
		var webapp=appArray[appid];
		var xchar=100*Math.random();
		var ycahr=100*Math.random();
		if(appid=="app7"){
			newWindow({x:50,y:100,w:webapp.w,h:webapp.h,title:webapp.title,id:webapp.id,src:webapp.url,resizeable:true});
		}else{
			newWindow({x:100+xchar,y:50+ycahr,w:webapp.w,h:webapp.h,title:webapp.title,id:webapp.id,src:webapp.url,resizeable:true});
		}
		
	});
//	for(var idx in appArray){  
//		var webapp=appArray[idx];
////		alert("#"+webapp.name);
//		$("#"+webapp.name).click(function(title,){
//			newWindow({x:10,y:10,w:600,h:500,title:webapp.title,id:webapp.id,src:webapp.url,resizeable:true});
//		});
//	}
	
//	$("#app1").click(function(){
//		newWindow({x:0,y:0,w:600,h:500,title:'百度',id:'leshiwang_div',src:'WebApp.do?appid=1',resizeable:true});
//	});

//	$("#dubianFim").click(function(){
//		newWindow(100,100,600,400,'豆瓣FM','dubianFim_div','http://douban.fm/',true);
//	});
	
	var temp_width=300,temp_height=250;
	var x=($(window).width()-temp_width)/2;
	var y=300;
	newWidget({x:700,y:220,w:temp_width,h:temp_height,title:'行情',id:'hangqing_div',src:'http://data.stock.hexun.com/quotes/stock_1.htm'});
//	newWidgetNoTitle({x:x+350,y:20,w:130,h:130,title:'钟表',id:'clock_div',src:'javascript-clock/index.html'});
	
//	var wowClock1 = new ygClock(250,10,"/chatroom/javascript-clock/clock.png","/chatroom/javascript-clock/clock_mask.png","/chatroom/javascript-clock/hourhand.png","/chatroom/javascript-clock/minhand.png","/chatroom/javascript-clock/sechand.png",-7,-7);

	$("#yuce").css("left","400px").css("top","200px");
	$("#yuce").draggable();
//	$("#footer").css("top",($(window).height()-80)+"px");
}

/*
 * 登录窗口初始化
 * 位置放到在屏幕中间
 */
function loginWindowInit(){
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
		var userID=$("#text_userID").val();
		//alert("设置成功！\n点击老师头像与老师聊天");
		if(userID!=undefined&&userID.length>0){
			login();
		}else{
			alert("用户名不能为空！");
		}
		
	});
	
	$("#login_window").hide();
	if(isLogin){
		$("#login_window").hide();
	}
}

//var techerList=[];
var techerList_bak=[
                {
                	groupName:"股指",
                	techerArray:[
                	            {techerID:"1001",techerName:"苍老师",icon:"image/2310562641-57.jpg",title:"金牌分析师",online:true},
                	            {techerID:"1002",techerName:"王老师",icon:"image/01-020734_278.jpg",title:"金牌分析师",online:true},
                	            {techerID:"1003",techerName:"刘老师",icon:"image/20-030501_381.jpg",title:"金牌分析师",online:true}
                	            ]
                },
                {
                	groupName:"农产品",
                	techerArray:[
								{techerID:"2001",techerName:"苍老师",icon:"image/01-020734_278.jpg",title:"金牌分析师",online:true}
								]
                },
                {
                	groupName:"能源化工",
                	techerArray:[
								{techerID:"3001",techerName:"苍老师",icon:"image/01-020734_278.jpg",title:"金牌分析师",online:true}
								]
                },
                {
                	groupName:"贵金属",
                	techerArray:[
								{techerID:"4001",techerName:"苍老师",icon:"image/01-020734_278.jpg",title:"银牌分析师",online:true}
								]
                },
                {
                	groupName:"理财",
                	techerArray:[
								{techerID:"5001",techerName:"苍老师",icon:"image/01-020734_278.jpg",title:"银牌分析师",online:true}
								]
                }
                ];
/*
 * 在线讲师列表初始化
 */
function techerListInit(techerList){
//	alert(techerList.length);
	{//初始化列表数据
		var techerListDiv="";
		for(var idx in techerList){  
			var groupObj=techerList[idx];
	        var groupName=groupObj.groupName;
	        var techerArray=groupObj.techerArray;

	        techerListDiv+="<h3>"+groupName+"</h3>";
	        techerListDiv+="<div>";
	        for(var i=0;i<techerArray.length;i++){
	        	var tempTecher=techerArray[i];
	        	techerListDiv+="<div id='"+tempTecher.techerID+"' class='techer'>";
	        	techerListDiv+="<img id='icon_"+tempTecher.techerID+"' ";
//	        	if(!tempTecher.online){
//	        		techerListDiv+="class='gray_img'";
//	        	}
	        	techerListDiv+=" src='"+tempTecher.icon+"' width='50'/>";
	        	techerListDiv+="<p>姓名："+tempTecher.techerName+"</p><p>称谓："+tempTecher.title+"</p></div>";
	        	if(i<(techerArray.length-1)){
	        		techerListDiv+="<hr/>";
	        	}
	        	techerMap[tempTecher.techerID]=tempTecher;
	        }
	        techerListDiv+="</div>";
	    }  
		$("#techer_list #accordion").html(techerListDiv);
	}
	
	
	{//初始化UI跟位置
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
		$("#techer_list").css('left',(windowWidth-$("#techer_list").width()-20));
		$("#accordion").accordion({heightStyle: "fill"});
	}
	
	//讲师列表点击
	$(".techer").click(function(){
		var tempTecherID=$(this).attr("id");
		var newTecher=techerMap[tempTecherID];
//		if(!newTecher.online){
//			alert("该老师不在线。请换其他老师");
//			return;
//		}

		if(!isLogin){
			$("#login_window").show();
			return;
		}
		
		if(!jsSocket.isConnected){
			techer=newTecher;
//			alert(techer.techerID+","+techer.techerName);
			chatMessageUpdate("<div class='pop2'>与服务器连接。。。</div>");
			$("#chat_window").show();
			
			var data={userID:userID};
			jsSocket.url="connect.do";
			jsSocket.http(data,function(msg) {
				var status=msg.status;
				if(status==1){
					jsSocket.isConnected=true;
					jsSocket.userID=userID;
					jsSocket.poll();
					chatToTecher();
				}else if(status==0){
					chatMessageUpdate("<div class='pop2'>服务器端用户已经注销，请重新刷新网页登录。</div>");
				}
			});
		}else{
			$("#chat_window").show();
			if(newTecher.techerID!=techer.techerID){
//				alert(newTecher.techerID+","+techer.techerID);
				techer=newTecher;
				chatToTecher();
			}
		}
		
	});
}

/*
 * 聊天窗口初始化
 */
function chatWindowInit(){
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
			var data={userID:userID};
			jsSocket.url="disconnect.do";
			jsSocket.http(data,function(msg) {
				var status=msg.status;
				if(status==1){
					jsSocket.closeSocket();
				}
			});
			$("#chat_window").hide();
			$("#pollMessage").html("");
		}
	});
	var width=$("#pollMessage").width();
	$("#send_message textarea").css("width",width+"px");
	
	$("#btn_send").click(function(){
		var message=$("#message").val();
//		alert(techerID);
		sendChatMessage(message);
	});
	$("#chat_window").focus(function(){
		message.clear();
	});
	$("#message").focus(function(){
		message.clear();
	});
	$("#message").keypress(function(event){
	    var k = event.which; 
//	    alert(event.ctrlKey+","+k);
	    if(k==13||k==10){
	    	var message=$("#message").val();
			sendChatMessage(message);
	    }
	    
	});
	
	$("#chat_window").hide();
}

/*
 * 聊天系统初始化
 */
function chatInit(){
	
	loginWindowInit();
	var data={};
	jsSocket.url="getTecherListJSON.do";
	jsSocket.http(data,function(msg){
		techerListInit(msg);
	});
	
	
	chatWindowInit();	
	
}

window.onbeforeunload = function() { 
	logout();
};
window.onunload = function() { 
	logout();
};

$(document).ready(function() {
	$.ajaxSetup({cache: false });

	
	messageSound.src="image/msg.mp3";
	messageSound.load();
	webAppInit();
	chatInit();
	$("#dosubmit").click(function (){
		var qhpz=$("#qhpz").val();
		var phone=$("#phone").val();
		var partten = /^1[3,5,8]\d{9}$/;
		if(partten.test(phone)){
			var data={qhpz:qhpz,phone:phone};
			jsSocket.url="submitPhone.do";
			jsSocket.http(data,function(msg) {
				var status=msg.status;
				if(status==1){
					$("#qhpz").val("期货品种");
					$("#phone").val("接收结果的手机");
					alert("分析结果稍后会发送到您的手机上。！");
				}
			});
         }
		else{
           alert("手机号错误");
        }

		
	});
});