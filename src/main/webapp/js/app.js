$(document).ready(function(event){
	 
$('#countryId').change(function() {
	
	$("#stateId").find('option').remove();
	$('<option>').val('').text('-Select-').appendTo("#stateId");
	
	$("#cityId").find('option').remove();
	$('<option>').val('').text('-Select-').appendTo("#cityId");
		
	var countryId = $("#countryId").val();
    $.ajax({
        type:"GET",
        url : "getStates?cid="+countryId,
        success : function(res) {
        	alert("res");
        	 $.each(res, function(stateId, stateName) {          
        		 $('<option>').val(stateId).text(stateName).appendTo("#stateId");
				});
        }
        });
        });

$('#stateId').change(function() {
	alert("success");
	$("#cityId").find('option').remove();
	$('<option>').val('').text('-Select-').appendTo("#cityId");
	var stateId = $("#stateId").val();
    $.ajax({
        type:"GET",
        url : "getCities?sid="+stateId,
        success : function(res) {
        	 $.each(res, function(cityId, cityName) {          
        		 $('<option>').val(cityId).text(cityName).appendTo("#cityId");
				});
        }
        });
        });

});