var $b = {};
$b.empty = function(_obj) {
	if (_obj == null || _obj.length < 1) {
		return true;
	} else {
		return false;
	}
}

$b.getElem = function(_str) {
	if ($b.empty(_str)) {
		return null;
	}
	
	return document.getElementById(_str);
}

$b.parseInt = function(_str, _default) {
	if ($b.empty(_str)) {
		if ($b.empty(_default)) {
			_str = "0";
		} else {
			_str = _default;
		}
	}
	
	return parseInt(_str, 10);
}