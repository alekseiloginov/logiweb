// JTable script

$(document).ready(function () {
    $('#TruckTableContainer').jtable({
        title: 'Table of trucks',
        actions: {
            listAction: 'TruckList.do',
            createAction: 'TruckSave.do',
            updateAction: '/GettingStarted/UpdatePerson',
            deleteAction: '/GettingStarted/DeletePerson'
        },
        fields: {
            plate_number: {
                title: 'Plate number',
                width: '25%'
            },
            driver_number: {
                title: 'Driver number',
                width: '15%'
            },
            capacity: {
                title: 'Capacity',
                width: '10%'
            },
            drivable: {
                title: 'Drivable',
                width: '10%'
            },
            location: {
                title: 'Location',
                width: '40%',
                display : function(data) {
                    return data.record.location.city;
                }
            }
        }
    }).jtable('load');
});