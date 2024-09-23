$(document).ready(function () {
    $('.register-btn a').on('click', function (e) {
        e.preventDefault();
        alert("응모되었습니다.");
    });
});

document.querySelector('form').addEventListener('submit', function() {
    var date = document.getElementById('eventCreateTime').value; // YYYY-MM-DD format

    // Remove time and only send date value
    var input = document.createElement('input');
    input.type = 'hidden';
    input.name = 'eventCreateTime'; // DTO field should match this
    input.value = date; // Only the date value is sent now
    this.appendChild(input);
});
