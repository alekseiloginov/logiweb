// JTable script

$(document).ready(function () {
    $('#DriverTableContainer').jtable({
        title: 'Table of drivers',
        actions: {
            listAction: 'DriverList.do',
            createAction: 'DriverSave.do',
            updateAction: '/GettingStarted/UpdatePerson',
            deleteAction: '/GettingStarted/DeletePerson'
        },
        fields: {
            id: {
                title: 'ID',
                width: '5%'
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
                }
            },
            truck: {
                title: 'Truck',
                width: '10%',
                display : function(data) {
                    return data.record.truck.plate_number;
                }
            }
        }
    }).jtable('load');
});