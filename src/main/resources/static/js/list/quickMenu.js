var data = [];

function chatting() {
  var chat = $(".chat");

  var cnt = $("#cnt").val(); //id가 cnt인 value를 가지고 온다.

  // data가 빈 배열이면 cnt 값을 0으로 설정, 그렇지 않으면 1로 설정
  var cnt = Object.keys(data).length === 0 ? 0 : 1;

  var ids = cnt === 0 ? "None" : "On";

  var txt = $("#txt" + ids);

  //txt 요소에 "show"라는 클래스가 있는지 확인합니다.
// 만약 txt가 "show" 클래스를 가지고 있다면, true를 반환하고, 없다면 false를 반환합니다.

//true일 경우, txt에서 "show" 클래스를 제거하고,
// chat에서 "active" 클래스를 제거합니다.
  if (txt.hasClass("show")) {
    txt.removeClass("show"); 
    chat.removeClass("active");
  } else {
    txt.addClass("show");
    chat.addClass("active");
  }

  //data가 빈배열이 아닐 때 목록 만들기 
  if (ids == "On") {
    updateChatList();
  }
}




//안읽은 메시지 가져오기
function getUnreadMessages(){
  $.ajax({
    url: "/api/users/chat/messages/unread",
    method: "GET",
    success: function (messages) {
      console.log(messages);
      data = messages;//값을 전역변수 data에 저장함
      if (ids === "On") {
        $(".notification-badge").show(); // 알림 배지를 보이기
      } else {
        $(".notification-badge").hide(); // 알림 배지를 숨기기
      }
    },
  });
}



function scrollTop() {
  $(window).scrollTop(0);
}



//페이지 로드 될 때마다 로그인 확인
window.onload = function () {
  getUnreadMessages();
  checkLoginStatus().then(function (isLoggedIn) {
    if (isLoggedIn) {
      // 로그인이 되어 있을 때만 메시지 확인 로직을 실행
      setInterval(function () {
        getUnreadMessages(); //메시지 있는지 확인 
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

//채팅리스트 목록 만들기
function updateChatList() {
  var txt = $("#txtOn"); // 채팅 리스트를 담을 요소
  txt.html(""); // 기존 내용을 초기화

  Object.keys(data).forEach(function (key) {
    // key에는 배열의 각 요소가 순서대로 전달됨
    console.log("현재 key:", key);
    console.log("현재 key에 해당하는 값:", data[key]);

    //원래는 진행중인 채팅이 없습니다. 라고 뜨는데 값이 있으면 
    //이렇게 바꾸는 거임 
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

