// JTable script

$(document).ready(function () {
    $('#OrderTableContainer').jtable({
        title: 'Table of orders',
        actions: {
            listAction: 'OrderList.do',
            createAction: 'OrderSave.do',
            updateAction: '/GettingStarted/UpdatePerson',
            deleteAction: '/GettingStarted/DeletePerson'
        },
        fields: {
            id: {
                title: 'ID',
                width: '5%'
            },
            truck: {
                title: 'Truck',
                width: '10%',
                display : function(data) {
                    return data.record.truck.plate_number;
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
        }
    }).jtable('load');
});