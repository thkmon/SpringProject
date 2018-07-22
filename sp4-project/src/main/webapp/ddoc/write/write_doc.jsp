<%@ page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html>
<html>
<head>
<title>ddoc</title>
<meta name="viewport" content="width=device-width, user-scalable=no" />
<link rel="stylesheet" href="/css/base.css">
<script type="text/javascript" src="/lib/bbutil.js"></script>
<script type="text/javascript" src="/ddoc/write/write_doc.js"></script>
<script type="text/javascript" src="/lib/jquery/jquery-2.x-git.min.js"></script>
</head>
<body>
	<div class="logo_plate">
	</div>
	<div class="main_plate">
		<input id="subject" type="text" style="height: 22px;" class="width100per font_small bd1" />
		<div class="button_plate" style="text-align: right;">
			<input type="button" id="attach_button" class="basic_button" value="첨부" />
			<input type="file" value="파일"/>
		</div>
		<textarea id="content" class="width100per font_small bd1" rows="10" cols="10"></textarea>
		<div class="button_plate" style="text-align: center;">
			<input type="button" id="publish_button" class="basic_button" value="발행" />
		</div>
	</div>
</body>
</html>