<%@ page import="com.tsystems.javaschool.loginov.logiweb.models.Driver" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Orders</title>
    <!-- My styles -->
    <link rel="stylesheet" href="../../../../css/style.css" />
    <!-- JQuery UI - Overcast theme styles -->
    <link rel="stylesheet" href="../../../../css/jquery-ui.css" />
    <link rel="stylesheet" href="../../../../css/jquery-ui.theme.css" />
    <link rel="stylesheet" href="../../../../css/jquery-ui.structure.css" />
    <!-- jTable styles -->
    <link rel="stylesheet" href="../../../../jtable/themes/metro/lightgray/jtable.min.css" />
</head>
<body>
<%Driver driver = (Driver) session.getAttribute("user");%>
<nav>
    <a href="Welcome.do?role=driver" title="Welcome page">Home</a>
</nav>
<br><br>

<div class="container">
    <div id="OrderTableContainer"></div>
</div>

<!-- jQuery lib -->
<script src="https://code.jquery.com/jquery-1.11.3.min.js" type="text/javascript" charset="utf-8"></script>
<!-- jQuery UI lib -->
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js" type="text/javascript"></script>
<!-- jTable script file -->
<script src="../../../../jtable/jquery.jtable.min.js" type="text/javascript"></script>
<!-- My JTable script -->
<script>
    $(document).ready(function () {
        $('#OrderTableContainer').jtable({
            title: 'Driver #<%=driver.getId()%> orders',
            actions: {
                // drivers can only see orders
                listAction: 'OrderList.do?role=driver&truckID=<%=driver.getTruck().getId()%>'
            },
            fields: {
                id: {
                    key: true,
                    title: 'Order #',
                    width: '5%',
                    create: false,
                    edit: false
                },
                //CHILD TABLE DEFINITION FOR "ORDER WAYPOINTS"
                waypoints: {
                    title: 'Waypoints',
                    width: '5%',
                    sorting: false,
                    edit: false,
                    create: false,
                    display: function (orderData) {
                        //Create an image that will be used to open child table
                        var $img = $('<img src="/images/list_metro.png" title="View waypoints" />');
                        //Open child table when user clicks the image
                        $img.click(function () {
                            $('#OrderTableContainer').jtable('openChildTable',
                                    $img.closest('tr'),
                                    {
                                        title: 'Order #' + orderData.record.id + ' - Waypoints',
                                        actions: {
                                            // with URL encoding of the current order ID
                                            listAction: 'OrderWaypointList.do?orderID=' + orderData.record.id
                                        },
                                        fields: {
                                            // Order ID
                                            //id: {
                                            //    type: 'hidden',
                                            //    defaultValue: orderData.record.id
                                            //},

                                            id: {
                                                title: 'Waypoint #',
                                                key: true,
                                                create: false,
                                                edit: false,
                                                list: false
                                            },
                                            location: {
                                                title: 'Location',
                                                width: '15%',
                                                edit: false,
                                                options: 'LocationOptions.do',
                                                optionsSorting: 'text',
                                                display : function(data) {
                                                    return data.record.location.city;
                                                }
                                            },
                                            freight: {
                                                title: 'Freight',
                                                width: '10%',
                                                edit: false,
                                                dependsOn: 'location',
                                                options: function (data) {
                                                    if (data.source == 'list') {
                                                        //Return url of all countries for optimization.
                                                        //This method is called for each row on the table and jTable caches options based on this url.
                                                        return 'FreightOptions.do?city=0&orderID=' + orderData.record.id;
                                                    }

                                                    //This code runs when user opens edit/create form or changes continental combobox on an edit/create form.
                                                    //data.source == 'edit' || data.source == 'create'
                                                    return 'FreightOptions.do?city=' + data.dependedValues.location + '&orderID=' + orderData.record.id;
                                                },
                                                display : function(data) {
                                                    return data.record.freight.name;
                                                }
                                            },
                                            operation: {
                                                title: 'Operation',
                                                width: '15%',
                                                create: false,
                                                edit: false
                                            }
                                        }
                                    }, function (data) { //opened handler
                                        data.childTable.jtable('load');
                                    });
                        });
                        //Return image to show on the person row
                        return $img;
                    }
                },
                truck: {
                    title: 'Truck',
                    width: '10%',
                    options: 'TruckOptions.do',
                    optionsSorting: 'text',
                    display : function(data) {
                        return data.record.truck.plate_number;
                    }
                },
                //CHILD TABLE DEFINITION FOR "ORDER TRUCK DRIVERS"
                drivers: {
                    title: 'Drivers',
                    width: '5%',
                    sorting: false,
                    edit: false,
                    create: false,
                    display: function (orderData) {
                        //Create an image that will be used to open child table
                        var $img = $('<img src="/images/list_metro.png" title="View drivers" />');
                        //Open child table when user clicks the image
                        $img.click(function () {
                            $('#OrderTableContainer').jtable('openChildTable',
                                    $img.closest('tr'),
                                    {
                                        title: 'Order #' + orderData.record.id + ' - Truck drivers',
                                        actions: {
                                            // with URL encoding of the current order ID
                                            listAction: 'OrderTruckDriverList.do?orderID=' + orderData.record.id
                                        },
                                        fields: {
                                            // Order ID
                                            //id: {
                                            //    type: 'hidden',
                                            //    defaultValue: orderData.record.id
                                            //},

                                            id: {
                                                title: 'Driver #',
                                                width: '6%',
                                                key: true,
                                                create: false,
                                                edit: false
                                            },
                                            name: {
                                                title: 'Name',
                                                width: '10%',
                                                create: false,
                                                edit: false
                                            },
                                            surname: {
                                                title: 'Surname',
                                                width: '15%',
                                                create: false,
                                                edit: false
                                            },
                                            email: {
                                                title: 'Email',
                                                width: '20%',
                                                options: 'DriverOptions.do?orderID=' + orderData.record.id,
                                                display : function(data) {
                                                    return data.record.email;
                                                }
                                            },
                                            location: {
                                                title: 'Location',
                                                width: '15%',
                                                create: false,
                                                edit: false,
                                                display : function(data) {
                                                    return data.record.location.city;
                                                }
                                            },
                                            worked_hours: {
                                                title: 'Worked hours',
                                                width: '12%',
                                                create: false,
                                                edit: false
                                            }
                                        }
                                    }, function (data) { //opened handler
                                        data.childTable.jtable('load');
                                    });
                        });
                        //Return image to show on the person row
                        return $img;
                    }
                },
                completed: {
                    title: 'Completed',
                    width: '15%',
                    type: 'radiobutton',
                    options: { '1': 'Yes', '0': 'No' },
                    defaultValue: '0'
                }
            },
            formCreated: function (event, data) {
                var $dialogDiv = data.form.closest('.ui-dialog');
                $dialogDiv.position({
                    my: "top",
                    at: "top",
                    of: window
                });
            }
        }).jtable('load');
    });
</script>
</body>
</html>
