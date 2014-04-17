var jsSocket={
		url:"",
		dataType:"json",
		isConnected:false,
		userID:"",
		isTecher:false,
		http:function(data,callback){
			$.ajax({
				type : "get", 
				dataType : this.dataType,
				url : this.url,
				data :data, //要发送的数据
				complete : function() {
					//console.log('http complete');
				}, //AJAX请求完成时
				error : function() {
					//console.log('http error');
				},
				success : function(msg) {
					//console.log(msg);
					callback(msg);
				}
			});
		},
		poll:function (){
//			console.log('this.isConneted='+this.isConnected);
			if(!this.isConnected){
				return;
			}
			this.url="poll.do";
			if(this.isTecher){
				this.url="techer_poll.do";
			}
			$.ajax({
				type : "get", 
				dataType : this.dataType,
				url : this.url,
				data : {
					userID:this.userID
				}, //要发送的数据
				complete : function() {
					//console.log('poll complete');
					jsSocket.poll();
				}, //AJAX请求完成时
				error : function(data) {
					jsSocket.isConnected=false;
					//console.log('poll error，'+data.responseText);
				},
				success : function(msg) {
					//console.log('poll success，'+msg);
					jsSocket.pollCallBack(msg);
				}
			});
		},
		pollCallBack:function(msg){
			
		},
		closeSocket:function(){
			this.isConnected=false;
		},
		callMethod : function(msg){
			var methodName=msg.methodName;
			var methodParam=msg.methodParam;
			if(methodName!=null&&methodParam!=null){
				eval("jsSocket."+methodName+"(methodParam)");
			}
		}
		
};
