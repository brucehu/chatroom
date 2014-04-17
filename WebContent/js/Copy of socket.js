var jsSocket={
		url:"",
		dataType:"",
		isConnected:false,
		sessionID:null,
		http:function(data,callback){
			$.ajax({
				type : "get", 
				dataType : this.dataType,
				url : this.url,
				data :data, //要发送的数据
				complete : function() {
					console.log('http complete');
				}, //AJAX请求完成时
				error : function() {
					console.log('http error');
				},
				success : function(msg) {
					console.log(msg);
					callback(msg);
//					var method={"methodName":callback,"methodParam":msg};
//					jsSocket.callMethod(method);
					//console.log('poll success');
				}
			});
		},
		connect:function(){
			$.ajax({
				type : "get", 
				dataType : this.dataType,
				url : this.url,
				data : {
				}, //要发送的数据
				complete : function() {
					console.log('connect complete');
				}, //AJAX请求完成时
				error : function() {
					console.log('connect error');
					jsSocket.isConnected=false;
				},
				success : function(msg) {
					console.log(msg);
					jsSocket.callMethod(msg);
					//console.log('poll success');
				}
			});
		},
		connetedSuccess : function(obj){
			this.isConnected=true;
			this.sessionID=obj.sessionID;
			console.log("conneted ok.sessionID="+this.sessionID);
			this.poll();
		},
		connetedFailed : function(obj){
			this.isConnected=false;
			alert("连接服务器失败！");
		},
		poll:function (){
			console.log('this.isConneted='+this.isConnected);
			this.url="poll.do";
			$.ajax({
				type : "get", 
				dataType : this.dataType,
				url : this.url,
				data : {
					userID:this.userID
				}, //要发送的数据
				complete : function() {
					console.log('poll complete');
					jsSocket.poll();
				}, //AJAX请求完成时
				error : function(data) {
					jsSocket.isConnected=false;
					console.log('poll error，'+data.responseText);
				},
				success : function(msg) { //msg为返回的数据，在这里做数据绑定
					console.log('poll success'+msg);
					jsSocket.callMethod(msg);
					//console.log('poll success');
				}
			});
		},
		pollTimeOut:function (obj){
			console.log('pollTimeOut '+obj.userID);
		},
		callMethod : function(msg){
			var methodName=msg.methodName;
			var methodParam=msg.methodParam;
			if(methodName!=null&&methodParam!=null){
				eval("jsSocket."+methodName+"(methodParam)");
			}
		},
		sendMessage:function(toSessionID,message){
			this.url="send.do";
			$.ajax({
				type : "get", 
				dataType : this.dataType,
				url : this.url,
				data : {
					sessionID:this.sessionID,
					toSessionID:toSessionID,
					message:message
				}, //要发送的数据
				complete : function() {
					console.log('this.url="poll.do"; complete');
				}, //AJAX请求完成时
				error : function() {
					console.log('this.url="poll.do"; error');
					jsSocket.isConnected=false;
				},
				success : function(msg) {
					console.log(msg);
					jsSocket.callMethod(msg);
					//console.log('poll success');
				}
			});
		},
		inRoom:function(){
			this.url="inRoom.do";
			$.ajax({
				type : "get", 
				dataType : this.dataType,
				url : this.url,
				data : {
					sessionID:this.sessionID,
					toSessionID:toSessionID,
					message:message
				}, //要发送的数据
				complete : function() {
					console.log('this.url="poll.do"; complete');
				}, //AJAX请求完成时
				error : function() {
					console.log('this.url="poll.do"; error');
					jsSocket.isConnected=false;
				},
				success : function(msg) {
					console.log(msg);
					jsSocket.callMethod(msg);
					//console.log('poll success');
				}
			});
		}
};
