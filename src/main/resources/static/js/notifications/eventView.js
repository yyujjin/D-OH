
$(document).ready(function () {
    $('.register-btn a').on('click', function (e) {
        e.preventDefault();
        alert("응모되었습니다.");
    });
});

    document.querySelector('form').addEventListener('submit', function() {
        var date = document.getElementById('eventCreateTime').value; // YYYY-MM-DD
        var time = document.getElementById('eventTime').value; // HH:MM
        var dateTime = date + 'T' + time; // ISO 8601 형식: YYYY-MM-DDTHH:MM

        // 새로운 hidden input을 추가하여 결합된 값을 전송
        var input = document.createElement('input');
        input.type = 'hidden';
        input.name = 'eventCreateTime'; // DTO의 필드와 일치해야 함
        input.value = dateTime;
        this.appendChild(input);
    });