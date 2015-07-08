// JTable script

$(document).ready(function () {
    $('#DriverTableContainer').jtable({
        title: 'Table of drivers',
        actions: {
            listAction: 'DriverList.do',
            createAction: 'DriverSave.do',
            updateAction: 'DriverUpdate.do',
            deleteAction: 'DriverDelete.do'
        },
        fields: {
            id: {
                key: true,
                title: 'ID',
                width: '5%',
                create: false,
                edit: false
            },
            name: {
                title: 'Name',
                width: '10%'
            },
            surname: {
                title: 'Surname',
                width: '15%'
            },
            email: {
                title: 'Email',
                width: '20%'
            },
            password: {
                title: 'Password',
                type: 'password',
                width: '10%',
                list: false
            },
            worked_hours: {
                title: 'Worked hours',
                width: '12%'
            },
            status: {
                title: 'Status',
                width: '10%',
                type: 'radiobutton',
                options: { 'free': 'free', 'shift': 'shift', 'driving': 'driving' },
                defaultValue: 'free'
            },
            location: {
                title: 'Location',
                width: '15%',
                display : function(data) {
                    return data.record.location.city;
                },
                input: function (data) {
                    var city_driver = data.record ? data.record.location.city : "";
                    return '<input type="text" name="location" value="' + city_driver + '" />';
                }
            },
            truck: {
                title: 'Truck',
                width: '10%',
                display : function(data) {
                    return data.record.truck !== undefined ? data.record.truck.plate_number : "";
                },
                input: function (data) {
                    var plate_number_truck = (!data.record || data.record.truck === undefined) ? "" : data.record.truck.plate_number ;
                    return '<input type="text" name="truck" value="' + plate_number_truck + '" />';
                }
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