<%@ page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html>
<html>
<head>
	<title>글쓰기</title>
	<meta name="viewport" content="width=device-width, user-scalable=no" />
	<link rel="stylesheet" type="text/css" href="/ddoc/write/bbjseditor_base.css">
	<link rel="stylesheet" type="text/css" href="/ddoc/write/bbjseditor.css">
	<script type="text/javascript" src="/lib/jquery/jquery-2.x-git.min.js"></script>
	<script type="text/javascript" src="/ddoc/write/bbjseditor.js"></script>
	<script type="text/javascript" src="/upload/upload_util.js"></script>
	<script type="text/javascript">
		// @implement
		bbjseditor.setPicture = function() {
			uploadUtil.doUpload();
			bbjseditor.focus();
		}
		
		window.onload = function() {
			bbjseditor.createEditor("editorDiv", "500px");
		}
		
		window.onresize = function() {
			bbjseditor.resizeEditor();
		}
		
		
	</script>
</head>
<body>
	<div id="editorDiv" style="width: 500px; font-family: D2Coding;">
	</div>
</body>
</html>