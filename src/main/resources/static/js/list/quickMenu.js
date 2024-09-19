var data = []; //일단 빈 배열로 설정

function chatting() {
  var chat = $(".chat");

  var cnt = $("#cnt").val(); //id가 cnt인 value를 가지고 온다.

  // data가 빈 배열이면 cnt 값을 0으로 설정, 그렇지 않으면 1로 설정
  var cnt = data.length === 0 ? 0 : 1;

  var ids = cnt == 0 ? "None" : "On";

  //data가 빈배열이 아닐 때
  if (ids == "On") {
    updateChatList();
  }

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
          url: "/api/users/chat/messages/unread",
          method: "GET",
          success: function (messages) {
            console.log(messages);
            data = messages;
            //값을 전역변수 data에 저장함
          },
        });
      }, 5000); // 5초마다 새로운 메시지 확인
    }
  });
};

// 로그인 여부를 확인하는 함수 (AJAX로 서버에 요청)
function checkLoginStatus() {
  return $.ajax({
    url: "/api/users/chat/isLoggedIn",
    method: "GET",
  });
}

function updateChatList() {
  var txt = $("#txtOn"); // 채팅 리스트를 담을 요소
  txt.html(""); // 기존 내용을 초기화

  Object.keys(data).forEach(function (key) {
    // key에는 배열의 각 요소가 순서대로 전달됨
    console.log("현재 key:", key);
    console.log("현재 key에 해당하는 값:", data[key]);

    var chatHtml = `
    <a href="/users/chat?receiver=${key}">
        <div class="wrap">
          <div class="chatImg"></div>
          <div class="infoWrap">
            <div class="content">
              <div class="name">${key}</div>
            </div>
            <div class="info">
              <div class="introduce">새로운 메시지가 있습니다.</div>
              <div class="num">
                <span>${data[key].length}</span>
              </div>
            </div>
          </div>
        </div>
        </a>
      `;
    txt.append(chatHtml);
  });
}
