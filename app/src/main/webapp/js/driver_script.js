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
                width: '10%',
                inputClass: 'validate[required]'
            },
            surname: {
                title: 'Surname',
                width: '15%',
                inputClass: 'validate[required]'
            },
            email: {
                title: 'Email',
                width: '20%',
                inputClass: 'validate[required,custom[email]]'
            },
            password: {
                title: 'Password',
                type: 'password',
                width: '10%',
                inputClass: 'validate[required]',
                list: false
            },
            worked_hours: {
                title: 'Worked hours',
                width: '12%',
                inputClass: 'validate[required]'
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
                options: 'LocationOptions.do',
                optionsSorting: 'text',
                display : function(data) {
                    return data.record.location.city;
                }
                //,
                //input: function (data) {
                //    var city_driver = data.record ? data.record.location.city : "";
                //    return '<input type="text" name="location" value="' + city_driver + '" />';
                //}
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
            //Initialize validation logic when a form is created
            data.form.validationEngine('attach', {
                promptPosition: "bottomLeft"
            });

            // THIS BLOCK FOR PRESENTATION ONLY
            $( "button" ).click(function() {
                //event.preventDefault();
                $( "#Edit-name" ).val( "Vasya" );
                $( "#Edit-surname" ).val( "Pupkin" );
                $( "#Edit-email" ).val( "vas@abc.com" );
                $( "#Edit-password" ).val( "1234" );
                $( "#Edit-worked_hours" ).val( "50" );
                $( "#Edit-truck" ).val( "DE98765" );
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