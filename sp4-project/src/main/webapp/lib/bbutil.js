var $bb = {};
$bb.empty = function(_obj) {
	if (_obj == null || _obj.length < 1) {
		return true;
	} else {
		return false;
	}
}

$bb.getElem = function(_str) {
	if ($bb.empty(_str)) {
		return null;
	}
	
	return document.getElementById(_str);
}

$bb.parseInt = function(_str, _default) {
	if ($bb.empty(_str)) {
		if ($bb.empty(_default)) {
			_str = "0";
		} else {
			_str = _default;
		}
	}
	
	return parseInt(_str, 10);
}