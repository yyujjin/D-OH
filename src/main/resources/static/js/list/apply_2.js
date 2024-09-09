$(document).ready(function(){

    $(".content.text").click(function () {
        console.log("click!!");
        var std ="<textarea name='' id='' placeholder='텍스트를 입력해주세요.'></textarea>";
        // $(".contArea").appand(std); 오타
        $(".contArea").append(std);
    });//end of click function

    

});//end of document ready function