var form;

function makeEditable() {
    // больше не нужны, т.к. в jsp вызываем функции напрямую
    /*    $('.delete').click(function () {
        deleteRow($(this).attr("id"));
    });

    $('#detailsForm').submit(function () {
        save();
        return false;
    });*/
    form = $('#detailsForm');
    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(event, jqXHR, options, jsExc);
    });
}

function add() {
    //$('#id').val(null);
    /**
    * очищаем поля ввода перед показом формы
    * find(":input") - селектор, выбор всех элементов input
    */
    form.find(":input").val("");
    $('#editRow').modal();
}

function deleteRow(id) {
    $.ajax({
        url: ajaxUrl + id,
        type: 'DELETE',
        success: function () {
            updateTable();
            successNoty('Deleted');
        }
    });
}

// у каждого энтити своя функция update
/*function updateTable() {
    $.get(ajaxUrl, function (data) {
        datatableApi.clear();
        $.each(data, function (key, item) {
            datatableApi.row.add(item);
        });
        datatableApi.draw();
    });
}*/

// обновляем таблицу, вставляем только измененные строки, вместо перерисовки всех
// http://anton.shevchuk.name/javascript/jquery-for-beginners-ajax/
// data – передаваемые данные – строка или объект
function updateNecessaryRows(data) {
    datatableApi.clear().rows.add(data).draw();
}

function save() {
    //var form = $('#detailsForm');
    $.ajax({
        type: "POST",
        url: ajaxUrl,
        // How to get the value of all inputs in a form
        // http://stackoverflow.com/a/18748592
        data: form.serialize(),
        success: function () {
            $('#editRow').modal('hide');
            updateTable();
            successNoty('Saved');
        }
    });
}

var failedNote;

function closeNoty() {
    if (failedNote) {
        failedNote.close();
        failedNote = undefined;
    }
}

function successNoty(text) {
    closeNoty();
    noty({
        text: text,
        type: 'success',
        layout: 'bottomRight',
        timeout: true
    });
}

function failNoty(event, jqXHR, options, jsExc) {
    closeNoty();
    failedNote = noty({
        text: 'Failed: ' + jqXHR.statusText + "<br>",
        type: 'error',
        layout: 'bottomRight'
    });
}
