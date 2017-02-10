var ajaxUrl = 'ajax/admin/users/';
var datatableApi;

function updateTable() {
    $.get(ajaxUrl, updateNecessaryRows);
}

// http://stackoverflow.com/questions/901712/how-do-i-check-if-a-checkbox-is-checked-in-jquery
function enable(chkbox, id) {
    // http://ru.stackoverflow.com/a/549355
    var isChecked = chkbox.is(":checked");

    $.ajax({
        url: ajaxUrl + id,
        type: 'POST',
        data: 'enabled=' + isChecked,
        success: function () {
            /**
             * closest - определяем строку, в которой находится checkbox
             * http://jquery.page2page.ru/index.php5/Ближайший_подходящий_предок
             */
            //chkbox.closest('tr').css("text-decoration", isChecked ? "none" : "line-through");
            chkbox.closest('tr').fadeTo(300, isChecked ? 1 : 0.3);

            successNoty(isChecked ? 'Enabled' : 'Disabled');
        }
    });
}

// $(document).ready(function () {
$(function () {
    datatableApi = $('#datatable').DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "name"
            },
            {
                "data": "email"
            },
            {
                "data": "roles"
            },
            {
                "data": "enabled"
            },
            {
                "data": "registered"
            },
            {
                "defaultContent": "Edit",
                "orderable": false
            },
            {
                "defaultContent": "Delete",
                "orderable": false
            }
        ],
        "order": [
            [
                0,
                "asc"
            ]
        ]
    });

    // затемняем tr с неактивными checkbox
    $(':checkbox').each(function () {
        if (!$(this).is(":checked")) {
            //$(this).closest('tr').css("text-decoration", "line-through");
            $(this).closest('tr').css("opacity", 0.3);
        }
    });

    makeEditable();
});