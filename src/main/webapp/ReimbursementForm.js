$(document).ready(function() {
    $("#startDay").datepicker();
    $('.timepicker').timepicker({
    	dropdown: false
    	
    });
});

/*window.onload = function(){
	
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
			
			if(xhr.status == 200){
				
			}
			
			break;
		}
	}
	
	xhr.open("POST", "sendForm", true);
	
	xhr.setRequestHeader('Content-Type', 'application/json');
	
	var data = "person="+JSON.stringify(parseForm());
	
	xhr.send(data);
	
}

function parseForm(){
	
	var form = {};
	
	if($("type").value){
		form.type = $("type").value;
	}else{
		return null;
	}
	
	if($("format").value){
		form.format = $("format").value;
	}else{
		return null;
	}
	
	if($("startDay").value){
		form.startDay = $("startDay").value;
	}else{
		return null;
	}
	
	if($("spinner").value){
		form.time = $("spinner").value;
	}else{
		return null;
	}
	
	if($("price").value){
		form.price = $("price").value;
	}else{
		return null;
	}
	
	if($("place").value){
		form.place = $("place").value;
	}else{
		return null;
	}
	
	if($("price").value){
		form.price = $("price").value;
	}else{
		return null;
	}
	
	if($("description").value){
		form.description = $("description").value;
	}else{
		return null;
	}
	
	if($("explain").value){
		form.explain = $("explain").value;
	}else{
		return null;
	}	
	
	return form;
	
}*/