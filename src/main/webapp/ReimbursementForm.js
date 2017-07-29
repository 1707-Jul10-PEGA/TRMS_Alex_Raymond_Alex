$(document).ready(function() {
    $("#startDay").datepicker();
});

window.onload = function(){
	
	document.getElementById("submit").addEventListener("click", postForm, false);
	
}

function postForm() {
	
	var head = document.getElementById("head");
	
	var xhr = new XMLHttpRequest();
	
	xhr.onreadystatechange = function(){
	
		switch (xhr.readyState) {
		
		case 0:
			
			break;
		case 1:
			
			break;
		case 2:
			
			break;
		case 3:
			
			break;
		case 4:
			
			break;
		}
	}
	
	
	
	
}