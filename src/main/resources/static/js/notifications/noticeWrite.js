$(document).ready(function() {
    // 공지사항 등록 폼의 유효성 검사
    $('#noticeForm').on('submit', function(event) {
        var title = $('#noticeTitle').val().trim();
        var content = $('#content').val().trim();

        // 제목 유효성 검사
        if (title === '') {
            alert('제목을 입력하세요.');
            $('#noticeTitle').focus();
            event.preventDefault(); // 폼 제출 방지
            return false;
        }

        // 기본적으로 정식 등록
        $('#noticeTempSave').val('false');
    });

    // 임시 저장 버튼 클릭 시 처리
    $('#tempSaveBtn').on('click', function() {
        $('#noticeTempSave').val('true'); // 임시 저장 플래그 설정
        $('#noticeForm').attr('action', '/notice/admin/tempSave'); // 임시 저장 경로 설정
        $('#noticeForm').submit(); // 폼 제출
    });
});
