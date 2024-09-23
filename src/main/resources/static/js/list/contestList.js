$(document).ready(function() {

    order();

});


function hitUp(e) {
    console.log("test");

    var id = $(e).siblings("#contestId").val();
    console.log(id);

    $.ajax({
        url: '/contest/hitUp',
        type: 'POST',
        data: { contestId: id },
        success: function() {
            console.log('조회수 증가 완료');
        },
        error: function(error) {
            console.log('조회수 증가 중 오류 발생:', error);
        }
    });
}

    function order(){
        alert("test03");
        var orderType = $(".orderWrap .orderType").val();
        console.log("this ->" +orderType);

        $.ajax({
            url: '/contest/list_ajax',//'/contest/list',
            type: 'GET',
            data: { orderType: orderType},
            success: function(result) {
                // 성공적으로 데이터를 받아왔을 때, 페이지 갱신 처리
                // result에는 서버에서 전송한 HTML 데이터가 담겨 있다고 가정합니다.

                // 받아온 데이터를 렌더링할 영역을 찾아서 업데이트
                $("#ajax_area").html(result);
            },
            error: function() {
                console.log("에러...");
            }
        });
    }

    function order_page(page){
        alert("test04");
        var orderType = $(".orderWrap .orderType").val();
        console.log("this ->" +orderType);

        $.ajax({
            url: '/contest/list_ajax',//'/contest/list',
            type: 'GET',
            data: { page: page, orderType: orderType},
            success: function(result) {
                // 성공적으로 데이터를 받아왔을 때, 페이지 갱신 처리
                // result에는 서버에서 전송한 HTML 데이터가 담겨 있다고 가정합니다.

                // 받아온 데이터를 렌더링할 영역을 찾아서 업데이트
                $("#ajax_area").html(result);
            },
            error: function() {
                console.log("에러...");
            }
        });
    }

 // fa-heart 아이콘 클릭 이벤트
    function scrap(e) {
        alert("test02");
        event.stopPropagation(); // 이벤트 버블링 방지, 상위 요소로의 이벤트 전달 차단
        event.preventDefault();  // a 태그의 기본 동작(페이지 이동)을 막음

        console.log("fa-heart 클릭!");

        var thisHeart = $(e);
        // 클릭한 fa-heart 아이콘 근처의 input 태그에서 contestId 값을 가져옴
        var id = $(e).closest(".listValue").find("#contestId").val();
        console.log("콘테스트 ID: " + id);

        $.ajax({
            url: '/contest/scrap', // 서버의 hitUp 컨트롤러 URL
            type: 'POST',
            data: { contestId: id },
            success: function(result) {

                console.log('반영 완료');
                if(result == 0){
                    alert("해당 콘테스트를 스크랩 했습니다.");
                    thisHeart.addClass("active");
                }else if(result == 1){
                    alert("해당 콘테스트가 스크랩 목록에서 삭제되었습니다.");
                    thisHeart.removeClass("active");
                }else{
                    alert("스크랩 기능을 이용하시려면 로그인해 주세요.");
                }
            },
            error: function(result) {
                console.log('좋아요 반영 중 오류 발생:', error);
            }
        });
    };