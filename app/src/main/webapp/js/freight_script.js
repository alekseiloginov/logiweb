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
                width: '10%',
                create: false,
                edit: false
            },
            name: {
                title: 'Title',
                width: '20%'
            },
            weight: {
                title: 'Weight',
                width: '10%'
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
                width: '20%',
                type: 'radiobutton',
                options: { 'prepared': 'prepared', 'shipped': 'shipped', 'delivered': 'delivered' },
                defaultValue: 'prepared'
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