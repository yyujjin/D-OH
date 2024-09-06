$(document).ready(function(){

        $('.boxWrap').click(function(){
            var button = $(this).find('.buttonWrap button');
            if(button.is(':visible')) {
                button.slideUp();
                $(this).removeClass('active');
            } else {
                $('.boxWrap .buttonWrap button').slideUp();
                $('.boxWrap').removeClass('active');
                button.slideDown();
                $(this).addClass('active');
            }
        });

          // 수상작 버튼 클릭 시 전파 막기
          $('.buttonWrap button').click(function(event){
            event.stopPropagation();
            var result = confirm("수상작으로 선정하시겠습니까?")
            if(result){
            alert("수상작으로 선정하였습니다!"); // 수상작 선정 후 동작 예시
            $(this).addClass("active");
            }
        });
    // });
});//end of document ready