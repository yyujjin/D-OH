document.addEventListener('DOMContentLoaded', function () {
    var dropdownTriggerList = [].slice.call(document.querySelectorAll('.dropdown-toggle'))
    var dropdownList = dropdownTriggerList.map(function (dropdownToggleEl) {
        return new bootstrap.Dropdown(dropdownToggleEl)
    });
});
