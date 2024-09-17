$(document).ready(function() {
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
        // if($(this).isChecked){
        //     $('#checkAll').prop('checked', isChecked);
        // }
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
    
    // var checkAll = document.querySelector("#checkAll");
    var checkAll = document.querySelector("#checkAll");
    if(!checkAll.checked)
    {
        alert("약관에 동의해야 지원이 가능합니다.");

        return false;
     }

     location.href="/contest/application/write";
 } 