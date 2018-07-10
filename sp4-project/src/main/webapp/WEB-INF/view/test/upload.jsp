<%@ page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html>
<html>
<head>
<title>업로드 테스트</title>
</head>
<body>
<!-- 기동후 http://localhost:8080/test/upload 로 접속가능 -->
업로드 테스트

<form action="/file/upload" id="fileUploader" name="fileUploader" method="post" enctype="multipart/form-data">
	<input type="file" name="file"><br>
	<input type="submit" name="업로드" value="제출"><br>
</form>
</body>
</html>