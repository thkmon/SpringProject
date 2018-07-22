window.onload = function() {
	resizeThisWindow();
	
	$bb.getElem("subject").focus();
	
	createUploadForm();
	createFileObject();
	
	$("#publish_button").click(publishDoc);
	$("#attach_button").click(addAttachFile);
}


window.onresize = function() {
	resizeThisWindow();
}


function resizeThisWindow() {
	var elem = $bb.getElem("content");
	var newHeight = ($bb.parseInt(window.innerHeight) - 200);
	if (newHeight < 200) {
		newHeight = 200;
	}
	elem.style.height = newHeight + "px";
}


function createUploadForm() {
	// <form id="writeForm" action="/ddoc/write/writeDoc.jsp" method="post" enctype="multipart/form-data" target="hiddenFrame">
		
	// var i = document.createElement("input");
	// i.setAttribute("type", "text");
	// i.setAttribute("name", "username");

	// var s = document.createElement("input");
	// s.setAttribute("type", "submit");
	// s.setAttribute("value", "Submit");
	
	// f.appendChild(i);
	// f.appendChild(s);
	
	var uploadForm = document.createElement("form");
	uploadForm.setAttribute("id", "uploadForm");
	uploadForm.setAttribute("method", "post");
	uploadForm.setAttribute("action", "");
	uploadForm.setAttribute("enctype", "multipart/form-data");
	uploadForm.setAttribute("style", "border:0px; width:1px; height:1px;");
	uploadForm.setAttribute("target", "hiddenForm");
	document.getElementsByTagName("body")[0].appendChild(uploadForm);
	
	var hiddenForm = document.createElement("form");
	hiddenForm.setAttribute("id", "hiddenForm");
	hiddenForm.setAttribute("method", "post");
	hiddenForm.setAttribute("action", "");
	hiddenForm.setAttribute("enctype", "multipart/form-data");
	hiddenForm.setAttribute("style", "border:0px; width:1px; height:1px;");
	hiddenForm.setAttribute("target", "hiddenForm");
	document.getElementsByTagName("body")[0].appendChild(hiddenForm);
	
	return uploadForm;
}


function createFileObject() {
	var hiddenForm = document.createElement("input");
	hiddenForm.setAttribute("id", "file01");
	hiddenForm.setAttribute("type", "file");
	hiddenForm.setAttribute("style", "display:");
	document.getElementsByTagName("body")[0].appendChild(hiddenForm);
}


function publishDoc() {
	var subject = $("#subject").val();
	var content = $("#content").text();
	
	alert(subject);
	alert(content);
}


function addAttachFile() {
//	alert("addAttachFile");
	
	$("#file01").click();
	
}

