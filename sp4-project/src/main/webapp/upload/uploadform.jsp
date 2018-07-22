<%@ page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript">
window.onload = function() {
	if (window.parent != null) {
		// injection of function
		window.parent.getFileInputObject = function() {
			var fileInputObj = document.getElementById("file_input");
			return fileInputObj;
		}
		
		// injection of function
		window.parent.getFileFormObject = function() {
			var fileFormObj = document.getElementById("file_form");
			return fileFormObj;
		}
		
		if (window.parent.uploadUtil != null && window.parent.uploadUtil.afterFormLoaded != null) {
			window.parent.uploadUtil.afterFormLoaded();
		}
	}
}
</script>
</head>
<body>
<form action="/file/upload" id="file_form" name="file_form" method="post" enctype="multipart/form-data">
	<input type="file" id="file_input" name="file_input"><br>
</form>
</body>
</html>