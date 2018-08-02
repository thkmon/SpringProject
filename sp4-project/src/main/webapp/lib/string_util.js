var StringUtil = {};


StringUtil.checkRegex = function(_str, _oneRegex) {
	if (_str == null || _str.length == 0) {
		return false;
	}
	
	var regex = new RegExp(_oneRegex);
	
	var len = _str.length;
	for (var i=0; i<len; i++) {
		if (!regex.exec(_str.substring(i, i+1))) {
			return false;
		}
	}
	
	return true;
}


StringUtil.checkEmptyType = function(_str) {
	if (_str == null || _str.length == 0 || StringUtil.trim(_str).length == 0) {
		return true;
	}

	return false;
}


StringUtil.checkNameType = function(_str) {
	if (_str == null || _str.length == 0) {
		return false;
	}
	
	return StringUtil.checkRegex(_str, "[가-힣a-zA-Z0-9]");
}


StringUtil.checkEmailType = function(_str) {
	if (_str == null || _str.length == 0) {
		return false;
	}
	
	var atCount = StringUtil.getCount(_str, "@");
	if (atCount != 1) {
		return false;
	}
	
	var atIndex = _str.indexOf("@");
	if (atIndex == 0 || atIndex == (_str.length - 1)) {
		return false;
	}
	
	return StringUtil.checkRegex(_str, "[a-zA-Z0-9@]");
}


StringUtil.getCount = function(_str, _strToFind) {
	if (_str == null || _str.length == 0) {
		return 0;
	}
	
	if (_strToFind == null || _strToFind.length == 0) {
		return 0;
	}
	
	var count = 0;
	
	var fl = _strToFind.length;
	var len = _str.length;
	for (var i=0; i<len; i++) {
		if (StringUtil.substring(_str, i, i+fl) == _strToFind) {
			count++;
			i = i + (fl - 1);
		}
	}
	
	return count;
}


StringUtil.substring = function(_str, _b, _e) {
	if (_str == null || _str.length == 0) {
		return "";
	}
	
	if (_b < 0) {
		_b = 0;
	}
	
	var len = _str.length;
	if (_e > len) {
		_e = len;
	}
	
	return _str.substring(_b, _e);
}


StringUtil.replaceAll = function(_str, _org, _dest) {
    return _str.split(_org).join(_dest);
}


StringUtil.trim = function(_str) {
    return _str.replace(/(^\s*)|(\s*$)/g, "");
}