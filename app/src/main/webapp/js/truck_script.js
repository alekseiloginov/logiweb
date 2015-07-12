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
                width: '25%',
                inputClass: 'validate[required]'
            },
            driver_number: {
                title: 'Driver number',
                width: '15%',
                inputClass: 'validate[required]'
            },
            capacity: {
                title: 'Capacity (tons)',
                width: '10%',
                inputClass: 'validate[required]'
            },
            drivable: {
                title: 'Drivable',
                width: '10%',
                type: 'radiobutton',
                options: { '1': '✔', '0': '✘' },
                defaultValue: '1'
            },
            location: {
                title: 'Location',
                width: '40%',
                options: 'LocationOptions.do',
                optionsSorting: 'text',
                display : function(data) {
                    return data.record.location.city;
                }
                //,
                //input: function (data) {
                //    var city_truck = data.record ? data.record.location.city : "";
                //    return '<input type="text" name="location" value="' + city_truck + '" />';
                //}
            }
        },
        formCreated: function (event, data) {
            //Initialize validation logic when a form is created
            data.form.validationEngine('attach', {
                promptPosition: "bottomLeft"
            });

            // THIS BLOCK FOR PRESENTATION ONLY
            $( "button" ).click(function() {
                //event.preventDefault();
                $( "#Edit-plate_number" ).val( "YZ09876" );
                $( "#Edit-driver_number" ).val( "2" );
                $( "#Edit-capacity" ).val( "3" );
            });
        },
        formSubmitting: function (event, data) {
            //Validate form when it is being submitted
            return data.form.validationEngine('validate');
        },
        formClosed: function (event, data) {
            //Dispose validation logic when form is closed
            data.form.validationEngine('hide');
            data.form.validationEngine('detach');
        }
    }).jtable('load');
});