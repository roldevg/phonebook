"use strict";

function readImage() {
    if (this.files && this.files[0]) {
        var fileReader = new FileReader();
        fileReader.onload = function (e) {
            $('#photo').attr("src", e.target.result);
        };
        fileReader.readAsDataURL(this.files[0]);
    }
}

function saveConfirm(event, form) {
    event.preventDefault();
    bootbox.confirm("Уверены ли вы, что хотите сохранить изменения?", function (result) {
        if (result) {
            form.submit();
        }
    });
}

function refreshPhonesListener() {
    var phones = $("#phones").find(".remove_phone");
    phones.off();
    phones.on("click", function () {
        var removeBtn = $(this);
        removePhone(removeBtn);
    });
}

function removePhone(removeBtn) {
    var parent = removeBtn.parent();
    parent.remove();
    recalculationIndexes();
}

function recalculationIndexes() {
    $("phoneList[index].number").each(function () {
        var $this = $(this);
        $this.attr("index", $this.attr("index").match(/\[\d+]/g, "[" + index + "]"));
    });
}

$(document).ready(function () {
    $('.search-employee').typeahead({
        source: function (query, process) {
            return $.post(
                '/ajax/search',
                {query: query},
                function (data) {
                    process(data);
                },
                "json");
        },
        minLength: 3
    });

    $("#datepicker").datepicker({
        format: 'dd.mm.yyyy',
        language: 'ru',
        orientation: 'bottom'
    });

    $("#saveEmployee").submit(saveConfirm(event, this));

    $(document).on('change', '.btn-file :file', function() {
        var input = this;
        var numFiles = input.get(0).files ? input.get(0).files.length : 1;
        var label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
        input.trigger('fileselect', [numFiles, label]);
    });
    
    $(".phone_container #add").click(function () {
        var index = $(".phone_item").size();
        $("#phones").append("<div class=\"phone_item\">" +
            "	<input type=\"text\" class=\"phone\" name=\"phoneList[" + index + "].number\">" +
            "	<input type=\"button\" class=\"remove_phone\" value=\"remove\">" +
            "</div>");
        refreshPhonesListener();
    });

    $("#file").change(readImage());

    $('.input-group.date').datepicker({});
});