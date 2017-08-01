function generateRFDetailRows(details) {

	$('#formDetails td').remove();
	$('#e_id').append('<td>' + details.eId + '</td>');
	$('#start_date').append('<td>' + details.startDate + '</td>');
	$('#start_time').append('<td>' + details.startTime + '</td>');
	$('#end_time').append('<td>' + details.endTime + '</td>');
	$('#location').append('<td>' + details.location + '</td>');
	$('#description').append('<td>' + details.description + '</td>');
	$('#cost').append('<td>' + details.cost + '</td>');
	$('#amount').append('<td>' + details.amount + '</td>');
	$('#grading_format').append('<td>' + details.gradingFormat + '</td>');
	$('#event_type').append('<td>' + details.eventType + '</td>');
	$('#work_related').append('<td>' + details.workRelated + '</td>');
	$('#status').append('<td>' + details.status + '</td>');
	$('#last_activity').append('<td>' + details.lastActivity + '</td>');
}


function generateRFRows(form_id, rowsData) {

	for (i = 0; i < rowsData.rfVRows.length; i++) {
		
		// 
		var newRow = document.createElement("tr");

		//set class and rfId attribute
		newRow.setAttribute("class", "re_row");
		newRow.setAttribute("rfId", rowsData.rfVRows[i].rfId);
		console.log(newRow.getAttribute("rfId"));
		
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
	
	
	$(form_id + ' tr').click(function() {
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