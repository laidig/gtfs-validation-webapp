$(document).ready(function () {
    
    Date.prototype.addDays = function(days) {
	this.setDate(this.getDate() + days);
	return this;
    };

    var now = new Date();
    console.debug(new Date(now));
    $('#apcdateCalendar').DatePicker({
	format: 'Y-m-d',
	date: new Date(now),
	calendars: 1,
	starts: 1
    });
    console.debug($('#apcdateCalendar div.datepicker').offsetHeight);
/* 
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

*/
      
    $('body').bind( 'click', function(){
       	$('#apcdateCalendar').stop().animate({height: true ? 0 : $('#apcdateCalendar div.datepicker').get(0).offsetHeight}, 500);
    });
    



});
