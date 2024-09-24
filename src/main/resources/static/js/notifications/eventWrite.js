$(document).ready(function() {
    // 현재 날짜를 yyyy-MM-dd 형식으로 가져오는 함수
    function getCurrentDate() {
        let today = new Date();
        let year = today.getFullYear();
        let month = ('0' + (today.getMonth() + 1)).slice(-2);  // 월은 0부터 시작하므로 1을 더함
        let day = ('0' + today.getDate()).slice(-2);
        return year + '-' + month + '-' + day;
    }

    // 이벤트 생성일이 비어있을 경우 현재 날짜로 설정
    if (!$('#eventCreateTime').val()) {
        $('#eventCreateTime').val(getCurrentDate());
    }

    // 폼 유효성 검사 함수
    function validateForm() {
        let title = $('#eventTitle').val(); // 'eventTitle'는 폼의 제목 입력란 id
        if (title === '') {
            alert('제목을 입력하세요.');
            $('#eventTitle').focus();
            return false;
        }
        return true;
    }

    // 임시 저장 버튼 클릭 시 처리
    $('#tempSaveBtn').on('click', function(event) {
        event.preventDefault(); // 폼 제출 방지

        // 유효성 검사 실패 시 처리 중단
        if (!validateForm()) {
            return false;
        }

        // 이벤트 시작일과 마감일이 비어 있으면 null로 처리
        let eventStartDate = $('#eventStartDate').val();
        let eventEndDate = $('#eventEndDate').val();

        if (eventStartDate === '') {
            $('#eventStartDate').val(null); // null로 설정
        }
        if (eventEndDate === '') {
            $('#eventEndDate').val(null); // null로 설정
        }

        // 임시 저장 플래그 설정 및 폼 제출
        $('#eventTempSave').val('true'); // 임시 저장 플래그 (숨겨진 필드) 값 설정
        $('#eventForm').attr('action', '/event/admin/tempSaveEvent'); // 임시 저장 경로 설정
        $('#eventForm').submit();
    });

    // 등록 또는 수정 버튼 클릭 시 기본값으로 정식 등록 처리
    $('#eventForm').on('submit', function() {
        $('#eventTempSave').val('false'); // 기본적으로 정식 등록 처리
    });
});
