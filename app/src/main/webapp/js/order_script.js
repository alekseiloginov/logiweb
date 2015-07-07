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
                display : function(data) {
                    return data.record.truck.plate_number;
                },
                input: function (data) {
                    var plate_number_order = data.record ? data.record.truck.plate_number : "";
                    return '<input type="text" name="truck" value="' + plate_number_order + '" />';
                }
            },
            drivers: {
                title: 'Drivers',
                width: '20%'
                //,
                //display : function(data) {
                //    return data.record.drivers.driver.name;
                //}
            },
            waypoints: {
                title: 'Waypoints',
                width: '20%'
                //,
                //display : function(data) {
                //    return data.record.waypoints.(operation, location.city, freight.name);
                    // + freight (id, name, weight, status)
                    // + type (loading/unloading)
                //}
            },
            completed: {
                title: 'Completed',
                width: '15%'
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