var FlashMessage = {
	
		// Save the data on the local storage to use it when the user reloads the page
	send : function(type, message) {    	
    	localStorage.setItem("jmetal-analysis-flash-message", JSON.stringify({type:type, message:message}));
    },
    
    // Call the callback if there is a flash messaged saved
    show: function(callback){
    	
    	// Get the item saved
    	var retrievedObject = localStorage.getItem('jmetal-analysis-flash-message');
    	
    	if(retrievedObject){
	    	// Convert the from json-formatted item to object one
	    	var data = JSON.parse(retrievedObject);
	    	// As we have a flash message, we need to remove the item
	    	localStorage.removeItem("jmetal-analysis-flash-message");    	
	    	// Call the callback
    		callback(data);
    	}
    }
}
