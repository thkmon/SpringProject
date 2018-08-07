var HttpUtil = {};


HttpUtil.getInstance = function() {
	return new BBHttpObj();
}


BBHttpObj = function(){
	this.url = "";
	this.callbackFunc = null;
	this.keyArr = null;
	this.valueArr = null;
}


BBHttpObj.prototype.setUrl = function(_url) {
	if (_url == null) {
		_url = "";
	}
	
	this.url = _url;
}


BBHttpObj.prototype.setCallback = function(_func) {
	if (_func != null && typeof(_func) == "function") {
		this.callbackFunc = _func;
	}
}


BBHttpObj.prototype.addParam = function(_key, _value) {
	if (this.keyArr == null) {
		this.keyArr = [];
	}
	
	if (this.valueArr == null) {
		this.valueArr = [];
	}
	
	this.keyArr[this.keyArr.length] = _key;
	this.valueArr[this.valueArr.length] = _value;
}


BBHttpObj.prototype.addInputParam = function(_id) {
	var inputObj = document.getElementById(_id);
	if (inputObj != null) {
		this.addParam(_id, inputObj.value);
	}
}


BBHttpObj.prototype.post = function(_callbackFunc) {
	if (_callbackFunc != null && typeof(_callbackFunc) == "function") {
		this.callbackFunc = _callbackFunc;
	}

	var url = this.url;
	var params = "";
	
	var oneKey = null;
	var oneValue = null;
	
	var keyCount = 0;
	if (this.keyArr != null) {
		keyCount = this.keyArr.length;
	}
	
	for (var i=0; i<keyCount; i++) {
		oneKey = this.keyArr[i];
		oneValue = this.valueArr[i];
		
		if (oneKey == null || oneKey.length == 0) {
			continue;
		}
		
		if (oneValue == null) {
			oneValue = "";
		}
		
		if (params.length > 0) {
			params += "&";
		}

		// alert(oneKey + " " + oneValue);
		params += encodeURIComponent(oneKey) + "=" + encodeURIComponent(oneValue);
	}
	
	var _this = this;
	
	$.ajax({
		type : "POST", 
		url : url,
		data : params, 
		success : function(_result) {
			if (_this.callbackFunc != null && typeof(_this.callbackFunc) == "function") {
				if (_result != null && _result.length > 0) {
					_result = HttpUtil.decode(_result);
					
					if (HttpUtil.startsWith(_result, "{") && HttpUtil.endsWith(_result, "}")) {
						_result = JSON.parse(_result);
						if (_result.result == 1) {
							if (_result.info != null) {
								_this.callbackFunc(_result.info);
							} else {
								_this.callbackFunc(true);
							}
							return true;
							
						} else {
							var msg = _result.message;
							if (msg == null || msg.length == 0) {
								msg = "오류가 발생하였습니다.";
							}
							
							alert(msg);
							
							_this.callbackFunc(null);
							return null;
						}
					}
				}
				
				_this.callbackFunc(null);
				return null;
			}
		}, 
		// beforeSend : showRequest, 
		error : function(e){ 
			alert(e.responseText); 
		} 
	 });
}


HttpUtil.decode = function(_s) {
	return decodeURIComponent(_s.replace(/\+/g, ' '));
}


HttpUtil.startsWith = function(_s, _strToFind) {
	if (_s == null || _s.length == 0) {
		return false;
	}
	
	var len = _s.length;
	var lenToFind = _strToFind.length;
	if (len < lenToFind) {
		return false;
	}
	
	if (_s.substring(0, lenToFind) == _strToFind) {
		return true;
	}
	
	return false;
}


HttpUtil.endsWith = function(_s, _strToFind) {
	if (_s == null || _s.length == 0) {
		return false;
	}
	
	var len = _s.length;
	var lenToFind = _strToFind.length;
	if (len < lenToFind) {
		return false;
	}
	
	if (_s.substring(len - lenToFind, len) == _strToFind) {
		return true;
	}
	
	return false;
}