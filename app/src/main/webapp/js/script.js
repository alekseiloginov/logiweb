jQuery("h2").hide();

jQuery("h2").show("slow");


$("h2").hide().show("slow");


$( "button" ).click(function() {
    $( "#name" ).val( "Vasya" );
    $( "#surname" ).val( "Pupkin" );
    $( "#email" ).val( "abc@abc.com" );
    $( "#password" ).val( "1234" );
});