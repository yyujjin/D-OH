$(document).ready(function() {
    // 공지사항 등록 폼의 유효성 검사
    $('#noticeForm').on('submit', function(event) {
        var title = $('#title').val().trim();
        var content = $('#content').val().trim();
        
        // 제목 유효성 검사
        if (title === '') {
            alert('제목을 입력하세요.');
            $('#title').focus();
            event.preventDefault();
            return false;
        }
        
        // 내용 유효성 검사
        if (content === '') {
            alert('내용을 입력하세요.');
            $('#content').focus();
            event.preventDefault();
            return false;
        }

        // 추가로 다른 유효성 검사를 여기에 추가할 수 있습니다.
    });

    // 임시 저장 버튼 클릭 시 처리
    $('#tempSaveBtn').on('click', function() {
        $('#noticeTempSave').val('true'); // 임시 저장으로 플래그 설정
        $('#noticeForm').submit(); // 폼 제출
    });

    // 등록 버튼 클릭 시 처리 (기본값으로 정식 등록)
    $('#noticeForm button[type="submit"]').on('click', function() {
        $('#noticeTempSave').val('false'); // 등록으로 플래그 설정
    });
});
