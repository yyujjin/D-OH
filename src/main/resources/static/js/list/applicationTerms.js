$(document).ready(function() {
    let today = new Date();

    let year = today.getFullYear(); // 년도
    let month = today.getMonth() + 1;  // 월
    let date = today.getDate();  // 날짜

    let day = year + '년 ' + month + '월 ' + date+ '일 기준';
//    console.log(day);

    $(".check.date").text(day);


    // 아이디어 업로드 체크리스트
    $('#check\\ A').change(function() {
        if($(this).is(':checked')) {
            $('.popUP.A').slideDown();
        } else {
            $('.popUP.A').slideUp();
        }
    });

    // 전체 약관 동의 체크박스
    $('#checkAll').change(function() {
        var isChecked = $(this).is(':checked');
        $('input[type="checkbox"]').prop('checked', isChecked);// 모든 하위 항목을 checkAll 상태와 일치시킴
        if(isChecked) {
            $('.popUP').slideDown();
        } else {
            $('.popUP').slideUp();
        }
    });

    $('#check\\ A, #check\\ B, #check\\ C').change(function(){
        $('#check\\ A, #check\\ B, #check\\ C').change(function() {
            // 하위 항목 체크박스들 중 체크되지 않은 항목이 있는지 확인
            if ($('#check\\ A').is(':checked') && $('#check\\ B').is(':checked') && $('#check\\ C').is(':checked')) {
                $('#checkAll').prop('checked', true);  // 모든 항목이 체크되어 있으면 checkAll도 체크
            } else {
                $('#checkAll').prop('checked', false); // 하나라도 체크가 안되어 있으면 checkAll 해제
            }
        });
    });

    // 콘테스트 이용 약관
    $('#check\\ B').change(function() {
        if($(this).is(':checked')) {
            $('.popUP.B').slideDown();
        } else {
            $('.popUP.B').slideUp();
        }
    });

    // 콘테스트 참여 주의사항
    $('#check\\ C').change(function() {
        if($(this).is(':checked')) {
            $('.popUP.C').slideDown();
        } else {
            $('.popUP.C').slideUp();
        }
    });
});//end of document ready

//유효성 검사
function check() {
    const urlParams = new URLSearchParams(location.search);
    //현재 페이지의 URL 값을 가져옴
    const contestNum = urlParams.get('contestNum');
    //현재 페이지의 URL에서 noticem_num 값을 가져옴
    console.log(contestNum);

    var checkAll = document.querySelector("#checkAll");
    if(!checkAll.checked)
    {
        alert("약관에 모두 동의해야 콘테스트 참여가 가능합니다.");

        return false;
     }

     location.href="/api/users/contest/application/write?contestNum="+contestNum;
 } 