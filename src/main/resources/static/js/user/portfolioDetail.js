
$(document).ready(function(){
    $(".user.more").click(function () {
        var profileInfo = $(".profileInfo");
        // var icon = $(".fa-angle-down, .fa-angle-up"); // 둘 다 선택
        var icon = $(".fa-solid");
        // $(".profileInfo").css({"display":"block"});

        // 현재 display 상태에 따라 block 또는 none으로 토글
        if (profileInfo.css("display") === "none") {
            profileInfo.css("display", "block");
            icon.removeClass("fa-angle-down").addClass("fa-angle-up");
        } else {
            profileInfo.css("display", "none");
            icon.removeClass("fa-angle-up").addClass("fa-angle-down");
        }
    })
});//end of document ready