function newWidget(parmeters) {
	var windowDiv = "<div id='"+parmeters.id+"' class='widget' style='position:absolute;top:"
			+ parmeters.y
			+ "px;left:"
			+ parmeters.x
			+ "px;z-index:1;width:"
			+ parmeters.w
			+ "px;height:"
			+ parmeters.h
			+ "px'><div style='-moz-border-radius: 5px;-webkit-border-radius: 5px;border-radius:5px;background-color: #c8c8c8;width:98%;height=30px'><b>&nbsp;</b></div><iframe width='100%' height='100%' frameborder='0' marginwidth='0' marginheight='0' src='"
		+ parmeters.src + "'></iframe></div>";
	$("body").append(windowDiv);
	$("#" + parmeters.id).draggable();
}
function newWidgetNoTitle(parmeters) {
	var windowDiv = "<div id='"+parmeters.id+"' class='widget' style='position:absolute;top:"
			+ parmeters.y
			+ "px;left:"
			+ parmeters.x
			+ "px;z-index:1;width:"
			+ parmeters.w
			+ "px;height:"
			+ parmeters.h
			+ "px'><iframe width='100%' height='100%' frameborder='0' marginwidth='0' marginheight='0' src='"
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
			+ "px'><iframe width='100%' height='100%' frameborder='0' marginwidth='10' marginheight='0' scrolling='yes' src='"
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
			$("#clock-widget").css("z-index", 2);
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