(function($) {

	$.fn.table = function(){
		var that = this;
		
		// The user has the possibility to sort the columns
		this.tablesorter({
	    	// It should not sort the first column
			 headers: {
				 1: { sorter: false, parser: false },
			 }
	    });
//		this.tablesorter();
		
		// Change the color of the row when a checkbox is selected
		this.find('.table-checkbox').change(function(event, param) {
	    	if($(this).is(":checked")) {
	        	$(this).parent().parent().css("background-color","#acbad4")
	        }else{
	        	$(this).parent().parent().css("background-color","white")
	        }
	    	
	    	that.find('.table-checkbox-all').prop('checked', param || false);
	    });
		
		this.find('.table-checkbox-all').change(function() {
			that.find('.table-checkbox').prop('checked', $(this).is(":checked"));
			that.find(".table-checkbox").trigger("change", [$(this).is(":checked")]);        
	    });	
		
	};
	
	$.fn.getSelectedRows = function(){
		var ids = [];
    	
		this.find('.table-checkbox:checked').each(function() {
    	    ids.push($(this).data("id"));
    	});
    	
    	return ids;	
	}
})(jQuery);