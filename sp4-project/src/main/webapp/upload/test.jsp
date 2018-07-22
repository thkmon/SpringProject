<%@ page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html>
<html>
<head>
	<title>업로드 테스트</title>
	<script type="text/javascript" src="/lib/jquery/jquery-2.x-git.min.js"></script>
	<script type="text/javascript" src="/upload/upload_util.js"></script>
</head>
<body>
	<!-- http://localhost:8080/upload/test -->
	업로드 테스트 페이지
	<input type="button" value="업로드" onclick="uploadUtil.doUpload()" />
</body>
</html>