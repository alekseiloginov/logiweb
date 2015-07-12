// JTable script

$(document).ready(function () {
    $('#FreightTableContainer').jtable({
        title: 'Table of freights',
        sorting: true,
        actions: {
            listAction: 'FreightList.do',
            createAction: 'FreightSave.do',
            updateAction: 'FreightUpdate.do',
            deleteAction: 'FreightDelete.do'
        },
        fields: {
            id: {
                key: true,
                title: 'ID',
                width: '6%',
                create: false,
                edit: false
            },
            name: {
                title: 'Title',
                width: '20%',
                inputClass: 'validate[required]'
            },
            weight: {
                title: 'Weight (kilos)',
                width: '15%',
                inputClass: 'validate[required]'
            },
            loading: {
                title: 'Loading',
                width: '20%',
                options: 'LocationOptions.do',
                optionsSorting: 'text'
            },
            unloading: {
                title: 'Unloading',
                width: '20%',
                options: 'LocationOptions.do',
                optionsSorting: 'text'
            },
            status: {
                title: 'Status',
                width: '15%',
                type: 'radiobutton',
                options: { 'prepared': 'prepared', 'shipped': 'shipped', 'delivered': 'delivered' },
                defaultValue: 'prepared'
            }
        },
        formCreated: function (event, data) {
            //Initialize validation logic when a form is created
            data.form.validationEngine('attach', {
                promptPosition: "bottomLeft"
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