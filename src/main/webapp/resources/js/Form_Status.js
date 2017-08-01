function generateRFDetailRows(details) {
	
}

function generateRFRows(form_id, rowsData) {

	for (i = 0; i < rowsData.rfVRows.length; i++) {
		
		// 
		var newRow = document.createElement("tr");

		//set class and rfId attribute
		newRow.setAttribute("class", "re_row");
		newRow.setAttribute("rfId", rowsData.rfVRows[i].rfId);
		
		//store id
		var id_col = document.createElement("td");
		id_col.append(document
				.createTextNode(rowsData.rfVRows[i].rfId));
		newRow.appendChild(id_col);

		//store first name
		var first_col = document.createElement("td");
		first_col.append(document
				.createTextNode(rowsData.rfVRows[i].first));
		newRow.appendChild(first_col);
		
		// store last name
		var last_col = document.createElement("td");
		last_col.append(document
				.createTextNode(rowsData.rfVRows[i].last));
		newRow.appendChild(last_col);
		
		
		$(form_id).append(newRow);
	}
	
	
	$(form_id).click(function() {
		var rfId = $(this).attr("rfId");
		if (rfId) {
			$.post('viewRFDetails', {rfId:rfId}, function(details) {
				generateRFDetailRows(details);
			});
		}
	});
}

window.onload = function() {

	$.post('getOwnForms', function(rowsData) {
		generateRFRows('#own_forms',rowsData);
	});

	$.post('getOtherForms', function(rowsData) {
		if (rowsData) {
			generateRFRows('#other_forms',rowsData);
		}
	});

	$.post('getRequests', function(rowsData) {
		if (rowsData) {
			
		}	
	});
}