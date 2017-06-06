var PostRequest = {
	send : function(path, params, callback) {
		
		var success = callback || function(response) {
			// Save the message as flash one to show to the user when the page is reloaded
			//FlashMessage.send(response.type, response.message);
			// If we have a flash message to show, we do that
			MessageBox.build(response.type, response.message, function(){
				// Reload the page
				location.reload();
			});			
		};
		
		if(params instanceof Array){
			params = JSON.stringify(params);
		}else if(params instanceof Object){
			params = JSON.stringify(params);
		}	

		$.ajax({
			url : path,
			type : 'POST',
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			data : params,
			success : success,
			error : function(error) {
				var response = JSON.parse(error.responseText);
//				// Save the message as flash one to show to the user when the page is reloaded
//				FlashMessage.send("error", response.message);
//				// Reload the page
//				location.reload();
				MessageBox.build("error", response.message, function(){
					// Reload the page
					location.reload();
				});
			}
		});
	}
}
