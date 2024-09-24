$(document).ready(function () {
    $('.register-btn a').on('click', function (e) {
        e.preventDefault(); // 링크 클릭 시 기본 동작 방지
        alert("응모되었습니다."); // 알림 표시
    });

    // 수정 버튼 클릭 시 폼 제출 방지
    $('form').on('submit', function(e) {
        var date = document.getElementById('eventCreateTime').value; // YYYY-MM-DD 형식

        // 시간 제거하고 날짜 값만 전송
        var input = document.createElement('input');
        input.type = 'hidden';
        input.name = 'eventCreateTime'; // DTO 필드와 일치해야 함
        input.value = date; // 이제 날짜 값만 전송
        this.appendChild(input);

        // 폼 제출 진행
        return true; // true를 반환하여 폼 제출 허용
    });
});
