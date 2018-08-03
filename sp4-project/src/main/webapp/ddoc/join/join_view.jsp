<%@ page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html>
<html>
<head>
<title>DDOC 가입</title>
<meta name="viewport" content="width=device-width, user-scalable=no" />
<link rel="stylesheet" href="/css/base.css">
<script type="text/javascript" src="/lib/jquery/jquery-2.x-git.min.js"></script>
<script type="text/javascript" src="/lib/string_util.js"></script>
<script type="text/javascript" src="/lib/http_util.js"></script>
<script type="text/javascript" src="/ddoc/join/join_view.js"></script>
<script type="text/javascript" src="/ddoc/join/aes_util.js"></script>
<script type="text/javascript">
var g_hint = "${hint}";

var g_keySize = 128;
var g_iterationCount = 10000;
var g_salt = "${salt}";
var g_iv = "${iv}";
var g_passPhrase = "passPhrase";
</script>
</head>
<body>
	<div class="main_plate">
		<b>DDOC 가입</b>
		<br><br>
		가입 페이지입니다.
		<br><br>
		<div id="info_div" class="w100per">
		이메일과 닉네임, 암호를 입력해주세요.
		</div>
		<br>
		<table class="w100per">
			<tr>
				<td>이메일 :</td>
				<td class="ralign"><input type="text" id="email_input" class="w100per" max="50" maxlength="50" onkeypress="email_input_onkeypress()"/></td>
			</tr>
			<tr>
				<td>닉네임 :</td>
				<td class="ralign"><input type="text" id="nick_input" class="w100per" max="50" maxlength="50" onkeypress="nick_input_onkeypress()"/></td>
			</tr>
			<tr>
				<td>암호 :</td>
				<td class="ralign"><input type="password" id="pw_input" class="w100per" max="50" maxlength="50" onkeypress="pw_input_onkeypress()"/></td>
			</tr>
			<tr>
				<td>암호확인 :</td>
				<td class="ralign"><input type="password" id="pw2_input" class="w100per" max="50" maxlength="50" onkeypress="pw2_input_onkeypress()"/></td>
			</tr>
		</table>
		<br>
		<br>
<!-- 		<div class="lfloat"> -->
			<input type="button" class="basic_button" value="가입" onclick="join_button_onclick()"/>
<!-- 		</div> -->
<!-- 		<div class="rfloat"> -->
<!-- 			<input type="button" class="basic_button" value="홈" onclick="home_button_onclick()"/> -->
<!-- 		</div> -->
	</div>
</body>
</html>