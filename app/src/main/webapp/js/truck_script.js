// JTable script

$(document).ready(function () {
    $('#TruckTableContainer').jtable({
        title: 'Table of trucks',
        sorting: true,
        actions: {
            listAction: 'TruckList.do',
            createAction: 'TruckSave.do',
            updateAction: 'TruckUpdate.do',
            deleteAction: 'TruckDelete.do'
        },
        fields: {
            id: {
                key: true,
                title: 'ID',
                width: '5%',
                create: false,
                edit: false,
                list: false
            },
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
                width: '10%',
                type: 'radiobutton',
                options: { '1': 'Yes', '0': 'No' },
                defaultValue: '1'
            },
            location: {
                title: 'Location',
                width: '40%',
                display : function(data) {
                    return data.record.location.city;
                },
                input: function (data) {
                    var city_truck = data.record ? data.record.location.city : "";
                    return '<input type="text" name="location" value="' + city_truck + '" />';
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