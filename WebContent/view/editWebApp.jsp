<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${webApp.appid}</title>
<script src="ckeditor/ckeditor.js"></script>
<script>
</script>
</head>
<body style="width: 960px;margin: 0 auto;">
<font color=red>${message}</font>
<form action="updateWebApp.do" method="post">
<input type="hidden" name="appid" value="${webApp.appid}" />
<textarea class="ckeditor" id="editor1" name="content" >${webApp.content}</textarea>
<input type="submit" value="修改"/>
</form>
<script type="text/javascript"> 
CKEDITOR.replace('editor1', { 
             filebrowserImageUploadUrl:"upload.do"  //上传action 
            //fullPage: true
}); 
</script>
</body>
</html>