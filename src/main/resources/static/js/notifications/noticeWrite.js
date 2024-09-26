$(document).ready(function() {

    // 유효성 검증 함수
    function validateForm() {
        var title = $('#noticeTitle').val().trim();
        var content = $('#content').val().trim();

        // 제목 유효성 검사
        if (title === '') {
            alert('제목을 입력하세요.');
            $('#noticeTitle').focus();
            return false; // 검증 실패
        }

        return true; // 검증 통과
    }

    // 공지사항 등록 폼의 유효성 검사
    $('#noticeForm').on('submit', function(event) {
        if (!validateForm()) {
            event.preventDefault(); // 폼 제출 방지
            return false;
        }

        // 기본적으로 정식 등록
        $('#noticeTempSave').val('false');
    });

    // 임시 저장 버튼 클릭 시 처리
    $('#tempSaveBtn').on('click', function(event) {
        event.preventDefault(); // 폼 제출 방지

        if (!validateForm()) {
            return false; // 유효성 검증 실패 시 처리 중단
        }

        // 임시 저장 시 버튼 텍스트 변경
        $('#noticeTempSave').val('true'); // 임시 저장 플래그 설정
        $('#noticeForm').attr('action', '/notice/admin/tempSave'); // 임시 저장 경로 설정

        // '등록' 버튼을 '수정'으로, '임시 저장' 버튼을 '등록'으로 변경
        $('.btn-primary').text('등록');
        $('#tempSaveBtn').text('수정');

        // 폼 제출
        $('#noticeForm').submit();
    });
});
