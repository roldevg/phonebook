"use strict";

import * as bootbox from "./bootbox";

function isConfirmSaveEmployeeByUser(event, form) {
    const message = "Are you sure?";
    bootbox.confirm(message, function (result) {
        if (result) {
            form.submit();
        }
    });
}

function recalculationIndexes() {
    $("phoneList[index].number").each(function (index) {
        const $this = $(this);
        $this.attr("index", $this.attr("index").match(/\[\d+]/g, "[" + index + "]"));
    });
}

function removePhone(removeBtn) {
    const parent = removeBtn.parent();
    parent.remove();
    recalculationIndexes();
}

function refreshPhonesListener() {
    const phones = $("#phones").find(".remove_phone");
    phones.off();
    phones.on("click", function () {
        const removeBtn = $(this);
        removePhone(removeBtn);
    });
}

$(document).ready(function () {

    $("#saveEmployee").click(function (event) {
        event.preventDefault();
        const form = $("#employeeForm");
        isConfirmSaveEmployeeByUser(event, form);
    });

    $("#searchEmployee").typeahead({
        source(query, process) {
            const url = "/ajax/search";
            return $.post(
                url, {
                    query
                },
                function (data) {
                    process(data);
                },
                "json");
        },
        minLength: 3
    });

    $("#datepicker").datepicker({
        format: "dd.mm.yyyy",
        language: "ru",
        orientation: "bottom"
    });

    $(document).on("change", ".photo :file", function() {
        const input = this;
        const files = input.get(0).files;
        const length = files ? files.length : 1;
        const label = input.val().replace(/\\/g, "/").replace(/.*\//, "");
        input.trigger("fileselect", [length, label]);
    });

    $("#file").change(function() {
        const files = this.files;
        if (files && files[0]) {
            const fileReader = new FileReader();
            fileReader.onload = function (e) {
                $("#photo").attr("src", e.target.result);
            };
            fileReader.readAsDataURL(files[0]);
        }
    });

    $("#addPhone").click(function () {
        const phoneItems = $(".phoneItem");
        const length = phoneItems.size();
        $("#phones").append("<div class=\"phone_item\">" +
            "<input type=\"text\" class=\"phone\" name=\"phoneList[" + length + "].number\">" +
            "<input type=\"button\" class=\"remove_phone\" value=\"remove\">" +
            "</div>");

        refreshPhonesListener();
    });
});
