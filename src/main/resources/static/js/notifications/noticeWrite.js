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

        // 내용 유효성 검사
        if (content === '') {
            alert('내용을 입력하세요.');
            $('#content').focus();
            event.preventDefault(); // 폼 제출 방지
            return false;
        }
    });


$(document).ready(function() {
    // 임시 저장 버튼 클릭 시 처리
    $('#tempSaveBtn').on('click', function() {
        $('#noticeTempSave').val('true'); // 임시 저장 플래그 설정
        $('#noticeForm').submit(); // 폼 제출
    });

    // 등록 또는 수정 버튼 클릭 시 기본값으로 정식 등록 처리
    $('#noticeForm').on('submit', function() {
        $('#noticeTempSave').val('false'); // 기본적으로 정식 등록
    });
});
