<%@ page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript">
window.onload = function() {
	if (window.parent != null && window.parent.uploadUtil != null) {
		if (window.parent.uploadUtil.afterUploaded != null) {
			window.parent.uploadUtil.afterUploaded("${id}");
		}
	}
}
</script>
</head>
<body>
</body>
</html>