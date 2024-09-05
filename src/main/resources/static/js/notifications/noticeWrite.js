$(document).ready(function() {
    // 공지사항 등록 폼의 유효성 검사
    $('form').on('submit', function(event) {
        var title = $('#title').val().trim();
        var content = $('#content').val().trim();
        
        if (title === '') {
            alert('제목을 입력하세요.');
            event.preventDefault();
            return false;
        }
        
        if (content === '') {
            alert('내용을 입력하세요.');
            event.preventDefault();
            return false;
        }
        
        // 추가로 다른 유효성 검사를 여기에 추가할 수 있습니다.
    });

    // 다른 인터랙티브한 기능을 여기에 추가 가능
});
