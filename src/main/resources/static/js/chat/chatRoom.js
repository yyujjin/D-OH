const sender = document.getElementById("sender").value;
const receiver = document.getElementById("receiver").textContent;
const sendButton = document
  .getElementById("sendButton")
  .addEventListener("click", sendMesssage);

// 엔터키로 메시지 전송 가능하도록 설정
document
  .getElementById("chatInput")
  .addEventListener("keydown", function (event) {
    if (event.key === "Enter") {
      // 엔터키를 눌렀을 때
      event.preventDefault(); // 엔터키로 줄바꿈이 안되도록 기본 동작 방지
      sendMesssage(); // 메시지 전송 함수 호출
    }
  });

let MessageDTO = {
  sender: sender,
  receiver: receiver,
  content: null,
};

//heartbeat 설정
const stompClient = new StompJs.Client({
  brokerURL: "ws://localhost:8083/chat",

  heartbeatIncoming: 10000,
  heartbeatOutgoing: 10000,

  reconnectDelay: 5000,
});

//메시지 보내기
function sendMesssage() {
  const chatInput = document.getElementById("chatInput");
  MessageDTO.content = chatInput.value;
  stompClient.publish({
    destination: "/app/messages",
    body: JSON.stringify(MessageDTO),
  });
  addMessageToChat(MessageDTO.content, "sent");
  chatInput.value = "";
}

//메시지 받기
stompClient.onConnect = (frame) => {
  console.log("Connected: " + frame);

  stompClient.subscribe(`/queue/messages/${sender}`, (message) => {
    const parsedMessage = JSON.parse(message.body);
    addMessageToChat(parsedMessage, "received");
    //채팅방에 있을경우 메시지 받음과 동시에 읽음으로 처리
    makeMessagesAsRead();
  });
};


//메시지 읽음으로 변경
function makeMessagesAsRead() {
  $.ajax({
    url: `/api/users/chat/messages/makeMessagesAsRead`,
    type: "POST",
    contentType: "application/json",
    data: JSON.stringify(MessageDTO),
    success: function (data) {
    },
    error: function (error) {
      console.error("Error fetching messages: ", error);
    },
  });
}

stompClient.onWebSocketError = (error) => {
  console.error("Error with websocket", error);
};

stompClient.onStompError = (frame) => {
  console.error("Broker reported error: " + frame.headers["message"]);
  console.error("Additional details: " + frame.body);
};

//웹소켓 연결
function connect() {
  stompClient.activate();
}

//웹소켓 연결 끊기
function disconnect() {
  stompClient.deactivate();
  setConnected(false);
  console.log("Disconnected");
}

// 페이지 로드 시
window.onload = function () {
  connect();
  getAllMessages();
};

//메시지 버블 생성
function addMessageToChat(content, messageType) {
  const chatBody = document.getElementById("chatBody");

  const messageDiv = document.createElement("div");
  messageDiv.classList.add("chat-message", messageType);

  const messageBubble = document.createElement("div");
  messageBubble.classList.add("message-bubble", messageType);
  messageBubble.textContent = content;

  messageDiv.appendChild(messageBubble);

  chatBody.appendChild(messageDiv);

  chatBody.scrollTop = chatBody.scrollHeight;
}

// 특정 사용자와의 메시지 조회
function getAllMessages() {
  $.ajax({
    url: `/api/users/chat/messages/${sender}`,
    type: "POST",
    contentType: "application/json",
    data: JSON.stringify(MessageDTO),
    success: function (data) {
      const sentMessages = data.sentMessages;
      const receivedMessages = data.receivedMessages;
      displayMessages(sortMessagesByTimestamp(sentMessages, receivedMessages));
    },
    error: function (error) {
      console.error("Error fetching messages: ", error);
    },
  });
}

//시간별로 메시지 정렬
function sortMessagesByTimestamp(sentMessages, receivedMessages) {
  const allMessages = sentMessages.concat(receivedMessages);

  //a와 b는 allMessages 배열 내의 두 메시지 객체
  //시간별로 메시지 정렬
  allMessages.sort(function (a, b) {
    return new Date(a.timestamp) - new Date(b.timestamp);
  });

  // 정렬된 메시지 리스트 반환
  return allMessages;
}

//메시지 출력
function displayMessages(messages) {
  const chatBox = document.getElementById("chatBox");

  messages.forEach(function (message) {
    if (message.sender === sender) {
      addMessageToChat(message.content, "sent");
    } else {
      addMessageToChat(message.content, "received");
    }
  });
}

//채팅 종료 버튼
document.getElementById('exitButton').addEventListener('click', function() {
  if(confirm("채팅을 종료하고 데이터를 삭제하시겠습니까?")){
    $.ajax({
      url: `/api/users/chat/messages/delete`,
      type: "PATCH",
      data: JSON.stringify(MessageDTO),
      contentType: 'application/json', 
      success: function (response) {
        window.location.href = '/';
      },
      error: function (error) {
        console.error("Error fetching messages: ", error);
      },
    });
  }
});
