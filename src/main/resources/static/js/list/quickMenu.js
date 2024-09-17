var data = [];

function chatting() {
  var chat = $(".chat");
  var cnt = $("#cnt").val();
  var cnt = data.length === 0 ? 0 : 1;
  var ids = cnt == 0 ? "None" : "On";
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
  $(window).scrollTop(0);
}

//페이지 로드 될 때마다 로그인 확인
window.onload = function () {
  checkLoginStatus().then(function (isLoggedIn) {
    if (isLoggedIn) {
      // 로그인이 되어 있을 때만 메시지 확인 로직을 실행
      setInterval(function () {
        $.ajax({
          url: "/chat/messages/check",
          method: "GET",
          success: function (messages) {
            console.log(messages);
            data = messages;
          },
        });
      }, 5000); // 5초마다 새로운 메시지 확인
    }
  });
};

// 로그인 여부를 확인하는 함수 (AJAX로 서버에 요청)
function checkLoginStatus() {
  return $.ajax({
    url: "/chat/isLoggedIn",
    method: "GET",
  });
}
