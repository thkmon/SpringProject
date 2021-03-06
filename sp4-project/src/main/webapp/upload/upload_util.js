var uploadUtil = {};


uploadUtil.doUpload = function() {
	
	try {
		var iframeObj = document.getElementById("upload_frame");
		if (iframeObj != null) {
			// 기존 오브젝트 삭제
			iframeObj.parentNode.removeChild(iframeObj);
		}
		
		iframeObj = document.createElement("iframe");
			
		iframeObj.setAttribute("id", "upload_frame");
		iframeObj.setAttribute("name", "upload_frame");
		iframeObj.setAttribute("src", "/upload/uploadform");
		iframeObj.setAttribute("style", "width: 1px; height: 1px; display: none;");
		
		document.getElementsByTagName("body")[0].appendChild(iframeObj);
	} catch (e) {
		alert(e);
	}
}


uploadUtil.afterFormLoaded = function() {
	var fileInputObj = getFileInputObject();
	if (fileInputObj == null) {
		return false;
	}

	fileInputObj.click();
	
	if (fileInputObj.files == null || fileInputObj.files.length == 0) {
		return false;
	}
	
	var fileName = fileInputObj.files[0].name;
	if (fileName == null || fileName.length == 0) {
		return false;
	}
	
	var fileFormObj = getFileFormObject();
	if (fileFormObj == null) {
		return false;
	}
	
	fileFormObj.submit();
}


uploadUtil.afterUploaded = function(_id) {
	if (_id == null || _id == "") {
		return false;
	}
	
	var imgObj = document.createElement("img");
	imgObj.setAttribute("src", "/getfile?id=" + _id);
	
//	document.getElementsByTagName("body")[0].appendChild(imgObj);
	
	var selection = window.getSelection();
	if (selection == null) {
		return false;
	}
	
	var range = selection.getRangeAt(0);
	if (range == null) {
		return false;
	}
	
	range.insertNode(imgObj);
}