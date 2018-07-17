window.onload = function() {
	resizeThisWindow();
}

window.onresize = function() {
	resizeThisWindow();
}

function resizeThisWindow() {
	var elem = $b.getElem("content_area");
	elem.style.height = ($b.parseInt(window.innerHeight) - 200) + "px";
}