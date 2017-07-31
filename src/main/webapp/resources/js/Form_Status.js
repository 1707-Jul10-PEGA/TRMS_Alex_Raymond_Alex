function generateRFRows(form_id, rowsData) {

	for (i = 0; i < rowsData.rfVRows.length; i++) {
		
		// 
		var newRow = document.createElement("tr");

		newRow.addActionListener("click", function() {
			//TODO: EDIT click function
		});
		newRow.setAttribute("class", "re_row");
		
		//store id
		var id_row = document.createElement("td");
		newColumn.append(document
				.createTextNode(rowsData.rfVRows[i].rfId));
		newRow.appendChild(id_row);

		//store first name
		var first_row = document.createElement("td");
		newColumn2.append(document
				.createTextNode(rowsData.rfVRows[i].first));
		newRow.appendChild(first_row);
		
		// store last name
		var last_row = document.createElement("td");
		newColumn2.append(document
				.createTextNode(rowsData.rfVRows[i].last));
		newRow.appendChild(last_row);

		document.getElementById(form_id).append(newRow);
	}
}

window.onload = function() {

	$.post('getOwnForms', function() {
		
	}).done(function(rowsData) {
		if (rowsData) {
			generateRFRows("own_forms",rowsData);
		}
	});

	/*$.post('getOtherForms', function() {
		generateRFRows("other_forms",rowsData);
	}).done(function() {
		alert("hi");
	})

	$.post('getRequests', function() {

	}).done(function() {
		alert("hi");
	})*/

	var tTop = document.createElement("tr");

	var headerRow = document.createElement("th");
	headerRow.append(document.createTextNode("Reimbursement ID"));

	tTop.appendChild(headerRow);

	var headerRow2 = document.createElement("th");
	headerRow2.append(document.createTextNode("First Name"));

	tTop.appendChild(headerRow2);
	

	var headerRow3 = document.createElement("th");
	headerRow3.append(document.createTextNode("Last Name"));
	
	tTop.appendChild(headerRow3);

	var tables = document.getElementsByClassName("gen_table");

	Array.prototype.forEach.call(tables, function(t) {
		t.append(tTop);
	});
}