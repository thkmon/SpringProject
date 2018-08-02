function email_input_onkeypress() {
	printForInfoDiv();
}


function nick_input_onkeypress() {
	printForInfoDiv();
}


function pw_input_onkeypress() {
	printForInfoDiv();
}


function pw2_input_onkeypress() {
	printForInfoDiv();
}


function pringMsg(_str) {
	if (_str == null || _str.length == 0) {
		_str = " ";
	}
	
	var infoDiv = document.getElementById("info_div");
	infoDiv.innerText = _str;
}


function printForInfoDiv() {
	
	var emailInput = document.getElementById("email_input").value;
	var nickInput = document.getElementById("nick_input").value;
	var pwInput = document.getElementById("pw_input").value;
	var pw2Input = document.getElementById("pw2_input").value;
	
	if (StringUtil.checkEmptyType(emailInput)) {
		pringMsg("이메일을 입력해주세요.");
		return false;
	}
	
	if (!StringUtil.checkEmailType(emailInput)) {
		pringMsg("이메일을 형식에 맞게 입력해주세요.");
		return false;
	}
	
	if (StringUtil.checkEmptyType(nickInput)) {
		pringMsg("닉네임을 입력해주세요.");
		return false;
	}
	
	if (!StringUtil.checkNameType(nickInput)) {
		pringMsg("닉네임은 한글/영어/숫자만 입력해주세요.");
		return false;
	}
	
	if (StringUtil.checkEmptyType(pwInput)) {
		pringMsg("암호를 입력해주세요.");
		return false;
	}
	
	if (StringUtil.checkEmptyType(pw2Input)) {
		pringMsg("암호확인을 입력해주세요.");
		return false;
	}
	
	pringMsg("");
	return true;
}


function home_button_onclick() {
	location.href = "/";
}


function join_button_onclick() {
	if (!printForInfoDiv()) {
		return false;
	}
	
	var pwInput = document.getElementById("pw_input").value;
	var pw2Input = document.getElementById("pw2_input").value;
	if (pwInput != pw2Input) {
		pringMsg("암호와 암호확인이 일치하지 않습니다.");
		return false;
	} else {
		pringMsg("");
	}
	
	var encValue = "";
	
	try {
		var aesUtil = new AesUtil(g_keySize, g_iterationCount);
		encValue = aesUtil.encrypt(g_salt, g_iv, g_passPhrase, pwInput);
	
	} catch (e) {
		alert("암호화에 실패하였습니다. [" + e + "]");
		return false;
	}
	
	var hObj = HttpUtil.getInstance();
	hObj.setUrl("/reqjoin");
	hObj.addInputValue("email_input");
	hObj.addInputValue("nick_input");
	hObj.addKeyValue("encValue", encValue);
	hObj.post(function(_result){
		alert("Dfdf : " + _result);
	});
}