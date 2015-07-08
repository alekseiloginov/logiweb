// JTable script

$(document).ready(function () {
    $('#OrderTableContainer').jtable({
        title: 'Table of orders',
        sorting: true,
        actions: {
            listAction: 'OrderList.do',
            createAction: 'OrderSave.do',
            updateAction: 'OrderUpdate.do',
            deleteAction: 'OrderDelete.do'
        },
        fields: {
            id: {
                key: true,
                title: 'ID',
                width: '5%',
                create: false,
                edit: false
            },
            truck: {
                title: 'Truck',
                width: '10%',
                options: 'TruckOptions.do',
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
                    var $img = $('<img src="/images/list_metro.png" title="Edit drivers" />');
                    //Open child table when user clicks the image
                    $img.click(function () {
                        $('#OrderTableContainer').jtable('openChildTable',
                            $img.closest('tr'),
                            {
                                title: orderData.record.truck.plate_number + ' - Truck drivers',
                                actions: {
                                    // with URL encoding of the current order ID
                                    listAction: 'OrderTruckDriverList.do?orderID=' + orderData.record.id,
                                    createAction: 'OrderTruckDriverSave.do?orderID=' + orderData.record.id,
                                    updateAction: '/Demo/UpdatePhone',
                                    deleteAction: '/Demo/DeletePhone'
                                },
                                fields: {
                                    // Order ID
                                    //id: {
                                    //    type: 'hidden',
                                    //    defaultValue: orderData.record.id
                                    //},

                                    // Driver ID
                                    id: {
                                        key: true,
                                        create: false,
                                        edit: false,
                                        list: false
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
                                        },
                                        input: function (data) {
                                            var city_driver = data.record ? data.record.location.city : "";
                                            return '<input type="text" name="location" value="' + city_driver + '" />';
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
            //},
            //waypoints: {
            //    title: 'Waypoints',
            //    width: '20%'
                //,
                //display : function(data) {
                //    return data.record.waypoints.(operation, location.city, freight.name);
                    // + freight (id, name, weight, status)
                    // + type (loading/unloading)
                //}
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