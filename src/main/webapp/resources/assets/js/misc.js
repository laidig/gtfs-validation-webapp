 function PostIt(url, data){

	  $(this).click(function(event){

	        event.preventDefault();

	        $('body').append($('<form/>', {
	          id: 'jQueryPostItForm',
	          method: 'POST',
	          action: url
	        }));

	        for(var i in data){
	          $('#jQueryPostItForm').append($('<input/>', {
	            type: 'hidden',
	            name: i,
	            value: data[i]
	          }));
	        }

	        $('#jQueryPostItForm').submit();
	    });
	}
 
 function isInt(n) {
	   return typeof n === 'number' && n % 1 == 0;
	}