var data = [];
var chat = $(".chat");
var cnt = $("#cnt").val(); //id가 cnt인 value를 가지고 온다.
var latestChatList = []; //최신 메시지 가져오기 

//처음 실행 시 안읽은 메시지 가져오기
getUnreadMessages();
getLatestMessages();

//채팅 아이콘 눌렀을 때 실행 됨
function clickChatIcon() {

  var cnt = Object.keys(data).length === 0 ? 0 : 1;
  var ids = cnt === 0 ? "None" : "On";
  var txt = $("#txt" + ids);

  if (txt.hasClass("show")) {
    txt.removeClass("show");
    chat.removeClass("active");
  } else {
    txt.addClass("show");
    chat.addClass("active");
  }

  //메시지가 없을 때는 원래대로 진행중인 채팅이 없다는 문구 뜨고
  //data가 빈배열이 아닐 경우에는 innterhtml로 목록 다시 만들기
  if (ids == "On") {
    updateChatList();
  }else if(ids == "None"&& cnt==0 && latestChatList.length!=0){
    updateChatListByLatestMessages();
  }
}


//안읽은 메시지 가져오기
function getUnreadMessages(){
  var cnt = Object.keys(data).length === 0 ? 0 : 1;
  var ids = cnt === 0 ? "None" : "On";

  $.ajax({
    url: "/api/users/chat/messages/unread",
    method: "GET",
    success: function (messages) {
      console.log(messages);
      data = messages;
      if (ids === "On") {
        $(".notification-badge").show(); // 알림 배지를 보이기
      } else {
        $(".notification-badge").hide(); // 알림 배지를 숨기기
      }
    },
  });
}

checkLoginStatus().then(function (isLoggedIn) {
  if (isLoggedIn) {
    // 로그인이 되어 있을 때만 메시지 확인 로직을 실행
    setInterval(function () {
      getUnreadMessages(); //메시지 있는지 확인해서 아이콘 띄우기
      getLatestMessages(); //마지막 채팅 기록 가져오기
    }, 5000); // 5초마다 새로운 메시지 확인
  }
});



// 로그인 여부를 확인하는 함수 (AJAX로 서버에 요청)
function checkLoginStatus() {
  return $.ajax({
    url: "/api/users/chat/isLoggedIn",
    method: "GET",
  });
}

//채팅리스트 목록 만들기
function updateChatList() {
  var txt = $("#txtOn"); 
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
          <div class="chatImg"><img src ="${data[key][0].profilePhoto}" width="50" height="50" ></div>
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


//페이지의 스크롤을 맨 위로 이동시킴
function scrollToTop() {
  $(window).scrollTop(0);
}


//진행중인 채팅방의 최신 메시지 가져오기
function getLatestMessages(){
  $.ajax({
    url: `/api/users/chat/messages/latest`,
    type: "GET",
    data: JSON.stringify(),
    contentType: 'application/json', 
    success: function (response) {
      latestChatList = response;
      console.log("최신 메시지" , latestChatList);
    },
    error: function (error) {
      console.error("Error fetching messages: ", error);
    },
  });
  updateChatListByLatestMessages();
}

//최신 메시지 리스트 표시 
function updateChatListByLatestMessages() {
  console.log("최신 메시지 가져오기 실행되고 있음 ")
  var txt = $("#txtNone"); 
  txt.html(""); // 기존 내용을 초기화

  latestChatList.forEach(function (value) {
    console.log(value.receiver)

    var chatHtml = `
    <a href="/users/chat?receiver=${value.receiver}">
        <div class="wrap">
          <div class="chatImg"></div>
          <div class="infoWrap">
            <div class="content">
              <div class="name">${value.receiver}</div>
            </div>
            <div class="info">
              <div class="introduce">채팅방이 열려 있습니다.</div>
            </div>
          </div>
        </div>
        </a>
      `;
    txt.append(chatHtml);
  });
}