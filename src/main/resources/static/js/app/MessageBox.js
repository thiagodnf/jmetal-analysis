var MessageBox = {
    alert: function(title, color, content, onClose){
    	$.alert({
    	    title: title,
    	    type: color,
    	    icon: 'glyphicon glyphicon-exclamation-sign',
    	    typeAnimated: true,
    	    content: content,
    	    buttons: {   
    	        ok: {
    	        	action:onClose
    	        }
    	    }
    	});
    },
    error: function(content, onClose){
        this.alert("Error", "red", content, onClose);
    },
    success: function(content, onClose){
        this.alert("Success", "green", content, onClose);
    },
    warning: function(content, onClose){
        this.alert("Warning", "orange", content, onClose);
    },
    build: function(type, content, onClose){
    	if(type == "success"){
    		this.success(content, onClose);
    	}else if(type == "error"){
    		this.error(content, onClose);
    	}else if(type == "warning"){
    		this.warning(content, onClose);
    	}
    },
    confirm: function(message, callback){
    	$.confirm({
    	    title: 'Confirm',
    	    content: message,
    	    typeAnimated: true,
    	    buttons: {
    	        Yes: {
    	        	btnClass: 'btn-green',    	        
    	            action: callback
    	        },
    	        No: function () {}
    	    }
    	});
    }
}
