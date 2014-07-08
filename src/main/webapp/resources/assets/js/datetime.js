$(document).ready(function () {
    
    Date.prototype.addDays = function(days) {
	this.setDate(this.getDate() + days);
	return this;
    };

    var now = new Date();
    $('#apcdateCalendar').DatePicker({
	flat: true,
	format: 'Y-m-d',
	date: [new Date(now), new Date(now)],
	calendars: 1,
	mode: 'range',
	starts: 1,
	onChange: function(formated) {
	    $('#apcdateField span').get(0).innerHTML = formated.join('    <->    ');
	}
    });
 
    var state = false;
    $('#apcdateField>a').bind('click', function(){
	$('#apcdateCalendar').stop().animate({height: state ? 0 : $('#apcdateCalendar div.datepicker').get(0).offsetHeight}, 500);
	state = !state;
	return false;
    });

    $('#apcdateCalendar div.datepicker').css('position', 'absolute');
    if(date1 != "null" && date2 != "null")
	$('#apcdateCalendar').DatePickerSetDate([date1,date2], true);
 
   $('#apcdateField span').get(0).innerHTML =$('#apcdateCalendar').DatePickerGetDate(true).join('    <->    ');


    $.widget( "custom.catcomplete", $.ui.autocomplete, {
	_renderMenu: function( ul, items ) {
	    var that = this,
	    currentCategory = "";
	    $.each( items, function( index, item ) {
		if ( item.category != currentCategory ) {
		    ul.append( "<li class='ui-autocomplete-category'>" + item.category + "</li>" );
		    currentCategory = item.category;
		}
		that._renderItemData( ul, item );
	    });
	}
    });
    
    
    $('#search').keydown(function(event) {
        if (event.keyCode == 13) {
	    var route = $(this).val().toUpperCase();
	    var date1 = $('#apcdateCalendar').DatePickerGetDate(true)[0];
	    var date2 = $('#apcdateCalendar').DatePickerGetDate(true)[1];
	    
	    if(route){
		window.open("/route/"+route+"/"+date1+"/"+date2,"_self");
		
	    }else
	    {
		alert("Please enter a route");
	    }
	    return false;
	}
    });
    
    $("#search_icon").click(function(event){
	    var route = $('#search').val().toUpperCase();
	    var date1 = $('#apcdateCalendar').DatePickerGetDate(true)[0];
	    var date2 = $('#apcdateCalendar').DatePickerGetDate(true)[1];
	    if(route){
		window.open("/route/"+route+"/"+date1+"/"+date2,"_self");
	
	    }else
	    {
//		alert("Please enter a route");
	
	    }	
	    return false;    
    });
    
    $('body').bind( 'click', function(){
       	$('#apcdateCalendar').stop().animate({height: true ? 0 : $('#apcdateCalendar div.datepicker').get(0).offsetHeight}, 500);
    });
    



});
