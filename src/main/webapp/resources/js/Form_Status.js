
function generateFormRows(rowData) {
	
	for ( i = 0; i < rowData.ReimbursementObj.length; i ++) {
		var newRow = document.createElement("tr");
		
		
		newRow.addActionListener("click", function() {
			
		});
		
		newRow.setAttribute("class", "re_row");
		var newColumn = document.createElement("td");
		newColumn.append(document.createTextNode(rowData.ReimbursementObj[i][0]));
		newRow.appendChild(newColumn);
			
		var newColumn2 = document.createElement("td");
		newColumn2.append(document.createTextNode(rowData.ReimbursementObj[i][1]));
		newRow.appendChild(newColumn2);
			
		document.getElementById("re_list").append(newRow);
	}
}



window.onload = function (){
	
	$.get('GetForms', function() {
		
	})
	.done (function () {
		alert("hi");
	})
	
	var tTop = document.createElement("tr");
	
	var headerRow = document.createElement("th");
	headerRow.append(document.createTextNode("Reimbursement ID"));
	
	tTop.appendChild(headerRow);
	
	var headerRow2 = document.createElement("th");
	headerRow2.append(document.createTextNode("Last Activity"));
	
	tTop.appendChild(headerRow2);		
	
	var tables = document.getElementsByClassName("gen_table")[0].append(tTop);
	
	
	
	generateFormRows({
					"ReimbursementObj" : [
						["123456","12:36:06 7/26/2017"],
						["234567","23:47:07 8/21/2017"]
						]
	});
	
	
	
}