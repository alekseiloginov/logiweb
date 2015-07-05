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
                width: '10%',
                list: false
            },
            worked_hours: {
                title: 'Worked hours',
                width: '12%'
            },
            status: {
                title: 'Status',
                width: '10%'
            },
            location: {
                title: 'Location',
                width: '15%',
                display : function(data) {
                    return data.record.location.city;
                },
                input: function (data) {
                    return '<input type="text" name="location" value="' + data.record.location.city + '" />';
                }
            },
            truck: {
                title: 'Truck',
                width: '10%',
                display : function(data) {
                    return data.record.truck.plate_number;
                },
                input: function (data) {
                    return '<input type="text" name="truck" value="' + data.record.truck.plate_number + '" />';
                }
            }
        }
    }).jtable('load');
});