$(document).ready(function() {
    var email = "${userEmail}";  // 서버에서 전달되는 변수라고 가정
    console.log("session 정보 확인: " + email);

    // fa-heart 아이콘 클릭 이벤트
    $(".listArea .fa-heart").click(function(e) {
        e.stopPropagation(); // 이벤트 버블링 방지, 상위 요소로의 이벤트 전달 차단
        e.preventDefault();  // a 태그의 기본 동작(페이지 이동)을 막음

        console.log("fa-heart 클릭!");

        // 클릭한 fa-heart 아이콘 근처의 input 태그에서 contestId 값을 가져옴
        var id = $(this).closest(".listValue").find("#contestId").val();
        console.log("콘테스트 ID: " + id);

        // 서버로 Ajax 요청을 보냄
        $.ajax({
            url: '/contest/scrap', // 서버의 hitUp 컨트롤러 URL
            type: 'POST',
            data: { contestId: id },
            success: function(result) {
//                console.log('좋아요 반영 완료');
                console.log('반영 완료');
                if(!result){
                    alert("해당 콘테스트를 스크랩 했습니다.");
                    $(this).addClass("active");
                }else{
                    alert("해당 콘테스트가 스크랩 목록에서 삭제되었습니다.");
                    $(this).removeClass("active");
                }
            },
            error: function(boolean) {
                console.log('좋아요 반영 중 오류 발생:', error);
            }
        });
    });
});


function hitUp(e) {
    console.log("test");

    // `$(e)`를 올바르게 사용, e는 함수에 전달된 DOM 요소라고 가정
    var id = $(e).siblings("#contestId").val();
    console.log(id);

    $.ajax({
        url: '/contest/hitUp',
        type: 'POST',
        data: { contestId: id },
        success: function() {
            // 조회수 증가 성공 시 페이지 이동
            console.log('조회수 증가 완료');
//            location.href = '/contest/view?contestId=' + id;  // 페이지 이동
        },
        error: function(error) {
            // 조회수 증가 실패 시 에러 처리
            console.log('조회수 증가 중 오류 발생:', error);
        }
    });
}
