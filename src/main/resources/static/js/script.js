function showOrHideColumns(){
	
	this.$content.find('.visible-columns-item').each(function(){
		
		var id = $(this).data("column-id");
		
		if($(this).is(":checked")){
			$('.table th:nth-child('+id+')').show();
		    $('.table td:nth-child('+id+')').show();
		}else{
			$('.table th:nth-child('+id+')').hide();
		    $('.table td:nth-child('+id+')').hide();
		}
	});

	$('.table th:nth-child(3)').show();
	$('.table td:nth-child(3)').show();
}
function drawChart(entries){

    var series = [];

    for(var i = 0; i < entries.length; i++){

        var data = [];

        var solutions = entries[i].solutions;

        for(var j = 0; j < solutions.length; j++){
            data.push(solutions[j].objectives);
        }

        series.push({
            name: entries[i].name,
            data: data
        });
    }

    $("#modal-draw-chart").modal();

    Highcharts.chart('draw-chart-container', {
        chart: {
            type: 'scatter'
        },
        title:{
            text: ""
        },
        xAxis: {
            title: {
                text: 'Objective 1'
            }
        },
        yAxis: {
            title: {
                text: 'Objective 2'
            }
        },
        series: series
    });
}

$(function(){
	
	// If we have a flash message to show, we do that
    FlashMessage.show(function(data){
    	MessageBox.build(data.type, data.message);
    });

    $(".table").table();
    
    $('#btn-project-new').click(function() {
    	$("#form-project input[name='id']").val("");
        $("#form-project input[name='name']").val("");
        $("#modal-form-project").modal("show");
    	
    	return false;
    });
     
    $('.btn-project-edit').click(function() {
    	PostRequest.send('/project/get', [$(this).data("id")], function(project){
    		$("#form-project input[name='id']").val(project[0].id);
            $("#form-project input[name='name']").val(project[0].name);
            $("#modal-form-project").modal("show");
    	});
    	
    	return false;
    });
        
    $('.btn-project-remove').click(function() {
    	
    	var id = $(this).data("id");
    	var name = $(this).data("name");
    	
    	MessageBox.confirm("Are you sure you want to delete the project '"+name+"'?", function(){
    		PostRequest.send('/project/remove', [id]);
    	});
 	
    	return false;
    });
   
    $('#btn-visible-columns').click(function() {
    	
    	 $.confirm({
    	        title: 'Visible Columns',
    	        content: '' +
    	        '<div class="dialog-visible-columns">' +
    	        '<p>Show and hide columns dynamically through <br>this option</p>'+
    	        '<form action="">' +
    	        '<div class="row">' +        
    	        '<div class="col-md-6">' +
    	        '<p><strong>General Information</strong></p>' +
    	        '<div class="checkbox">' +
    	        '<label><input type="checkbox" data-column-id="4" id="column-n-solutions" class="visible-columns-item" checked /> # of Solutions </label><br>' +
    	        '<label><input type="checkbox" data-column-id="5" id="column-creation-time" class="visible-columns-item" checked /> Creation Time </label><br>' +
    	        '<label><input type="checkbox" data-column-id="6" id="column-execution-time" class="visible-columns-item" checked /> Execution Time </label><br>' +
    	        '</div>' +
    	        '</div>' +
    	        '<div class="col-md-6">' +
    	        '<p><strong>Quality Indicators</strong></p>'+
    	        '<div class="checkbox">' +
    	        '<label><input type="checkbox" data-column-id="7" id="column-hypervolume" class="visible-columns-item" checked /> Hypervolume </label><br>' +
    	        '<label><input type="checkbox" data-column-id="8" id="column-epsilon" class="visible-columns-item" /> Epsilon </label><br>' +
    	        '<label><input type="checkbox" data-column-id="9" id="column-igd" class="visible-columns-item" /> IGD </label><br>' +
    	        '<label><input type="checkbox" data-column-id="10" id="column-igd-plus" class="visible-columns-item" /> IGD+ </label><br>' +
    	        '<label><input type="checkbox" data-column-id="11" id="column-gd" class="visible-columns-item"  /> GD </label><br>' +
    	        '<label><input type="checkbox" data-column-id="12" id="column-spread" class="visible-columns-item" /> Spread</label><br>' +
    	        '</div>' +
    	        '</div>' +
    	        '</div>' +
    	        '</form>' +
    	        '</div>',
    	        buttons: {
    	            formSubmit: {
    	                text: 'Save',
    	                btnClass: 'btn-blue',
    	                action: showOrHideColumns
    	            },
    	            cancel: function () {
    	                //close
    	            },
    	        },
    	        onContentReady: function () {
    	            // bind to events
    	            var jc = this;
    	            this.$content.find('form').on('submit', function (e) {
    	                // if the user submits the form by pressing enter in the field.
    	                e.preventDefault();
    	                jc.$$formSubmit.trigger('click'); // reference the button and click it
    	            });
    	        }
    	    });
    	
 	
    	return false;
    });
    
    
    $('#btn-solution-set-remove').click(function() {

    	var ids = $(".table").getSelectedRows();
    	
    	if(ids.length == 0){
    		return MessageBox.warning('You need to select at least a row');    		
    	}
    	
    	MessageBox.confirm("Are you sure you want to delete these solution sets?", function(){
    		PostRequest.send('/solution-set/remove', ids);
    	});
 	
    	return false;
    });
    
    var $rows = $('.table tbody tr');
    
    $('#input-search').keyup(function() {
    	var term = $.trim($(this).val()).replace(/ +/g, ' ').toLowerCase();
    	
		$rows.show().filter(function() {
		    var text = $(this).text().replace(/\s+/g, ' ').toLowerCase();
		    return !~text.indexOf(term);
		}).hide();
    });

    $("#form-project").submit(function(){
    	var project = {
            id: $("#form-project input[name='id']").val(),
            name: $("#form-project input[name='name']").val(),
        }
    	
    	PostRequest.send('/project/save', project);
    	
        return false;
    });

    $("#btn-generate-pareto-front-set").click(function(){
    	
    	var id = $(this).data("id");
    	
    	MessageBox.confirm("Do you want to generate the Pareto-front set for this project?", function(){
    		PostRequest.send('/task/generate-pareto-front-set', id);
    	});
    });
    
    $("#btn-generate-quality-indicators").click(function(){
    	
    	var id = $(this).data("id");
    	
    	MessageBox.confirm("Do you want to generate the quality indicators for this project?", function(){
    		PostRequest.send('/task/generate-quality-indicators', id);
    	});
    });
    
    $("#btn-draw-chart").click(function(){

        var ids = $(".table").getSelectedRows();

        if(ids.length == 0){
    		return MessageBox.warning('You need to select at least a row');    		
    	}
        
        if(ids.length > 10){
            return MessageBox.warning("You must select up to 10 rows");
        }

        PostRequest.send('/solution-set/get', ids, function(response){
        	drawChart(response);
		});
    });
    
    
    
   
});
