//var xhr =occurredLHttpRequest();
//xhr.onreadystatechange = function() {
//    if (xhr.readyState === 4) {
//        // Parse fetched (JSON) data into the table
//
//        // JSON.parse returns a JS object - array of the fetched trucks (JS objects)
//        var trucks = JSON.parse(xhr.responseText);
//
//        // Create truckHTML string to inject as a table
//        var truckHTML = "<table><tbody><tr><th>Plate number</th><th>Driver number</th><th>Capacity</th><th>Drivable</th><th>Location</th><th>Added</th><th>Last modified</th></tr>";
//
//        for (var i=0; i<trucks.length; i+=1) {
//            truckHTML += "<tr><td>" + trucks[i].plate_number + "</td>";
//            truckHTML += "<td>" + trucks[i].driver_number + "</td>";
//            truckHTML += "<td>" + trucks[i].capacity + "</td>";
//            truckHTML += "<td>" + trucks[i].drivable + "</td>";
//            truckHTML += "<td>" + trucks[i].location.city + "</td>";
//            truckHTML += "<td>" + trucks[i].created_time + "</td>";
//            truckHTML += "<td>" + trucks[i].last_modified_time + "</td></tr>";
//        }
//        truckHTML += "</tbody></table>";
//
//        // Inject truckHTML into the page
//        document.getElementById("truckList").innerHTML = truckHTML;
//    }
//};
//xhr.open("GET", "TruckListUpdateService");
//xhr.send();


// jQuery style
//$.get("drivers.jsp", function(response) {
//    $("#truckList").html(response);
//});

//$("#truckList").load("landing.html");

// fetching JSON truck object from the TruckListUpdateService
var test = function () {
    $.ajax("TruckListService", {

        success : function (response) {
        var truckHTML = "<table><tbody><tr><th>Plate number</th><th>Driver number</th><th>Capacity</th><th>Drivable</th><th>Location</th><th>Added</th><th>Last modified</th></tr>";

        $.each(response, function (index, truck) {
            truckHTML += "<tr><td>" + truck.plate_number + "</td>";
            truckHTML += "<td>" + truck.driver_number + "</td>";
            truckHTML += "<td>" + truck.capacity + "</td>";
            truckHTML += "<td>" + truck.drivable + "</td>";
            truckHTML += "<td>" + truck.location.city + "</td>";
            truckHTML += "<td>" + truck.created_time + "</td>";
            truckHTML += "<td>" + truck.last_modified_time + "</td></tr>";
        });
        truckHTML += "</tbody></table>";

        $("truckList").html(truckHTML);
        //instead of:  document.getElementById("truckList").innerHTML = truckHTML;
    }});
};

// posting a new truck to the database using jQuery
$("form").submit(function(event) {
    event.preventDefault();  // to not to actually submit data, we will do it instead

    var url = $(this).attr("action");
    var formData = $(this).serialize();  // creates a query string for submission

    //  POST shorthand method
    $.post(url, formData, function(response) {
        $("#someId").html("<p>New truck added</p>");
    }).fail(function(jqXHR) {  // fail() method do not work for $.load() method and remote requests
        alert(jqXHR.status);
    });

    //  AJAX method
    $.ajax(url, {

        data : formData,

        type : "POST",

        success : function(response) {
            // update the truck table
            $("#someId").html("<p>New truck added.</p>");
        },

        error : function(response) {
            $("#someId").html("<p>Problem occurred. " + response.statusText + ". Try again.</p>");
        }
    });

});