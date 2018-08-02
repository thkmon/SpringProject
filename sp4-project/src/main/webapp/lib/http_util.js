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


BBHttpObj.prototype.addKeyValue = function(_key, _value) {
	if (this.keyArr == null) {
		this.keyArr = [];
	}
	
	if (this.valueArr == null) {
		this.valueArr = [];
	}
	
	this.keyArr[this.keyArr.length] = _key;
	this.valueArr[this.valueArr.length] = _value;
}


BBHttpObj.prototype.addInputValue = function(_id) {
	var inputObj = document.getElementById(_id);
	if (inputObj != null) {
		this.addKeyValue(_id, inputObj.value);
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

		alert(oneKey + " " + oneValue)
		params += encodeURIComponent(oneKey) + "=" + encodeURIComponent(oneValue);
	}
	
	var _this = this;
	
	$.ajax({
		type : "POST", 
		url : url,
		data : params, 
		success : function(_result) {
			if (_this.callbackFunc != null && typeof(_this.callbackFunc) == "function") {
				_this.callbackFunc(_result);
			}
		}, 
		// beforeSend : showRequest, 
		error : function(e){ 
			alert(e.responseText); 
		} 
	 });
}