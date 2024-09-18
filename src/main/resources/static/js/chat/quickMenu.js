function chatting() {
    var chat = $(".chat");
    var cnt = $("#cnt").val();
    var ids = cnt == 0 ? 'None':'On';
    var txt = $("#txt" + ids);
    if (txt.hasClass("show")) {
        txt.removeClass("show");
        chat.removeClass("active");
    } else {
        txt.addClass("show");
        chat.addClass("active");
    }
}

function scrollTop() {
    $(window).animate({ scrollTop: 0 }, 'slow');
}

