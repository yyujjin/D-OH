let stompClient = null;
console.log("이 페이지 실행됨")

// WebSocket 연결하고
function connect() {
  const socket = new SockJS("ws://localhost:8083/chat");
  stompClient = Stomp.over(socket);

  // WebSocket 연결
  stompClient.connect({}, function (frame) {
    console.log("WebSocket 연결됨:", frame); //연결성공하면 connect라고 뜨겠지

    // 메시지 구독 설정
    stompClient.subscribe("/queue/messages", function (messageOutput) {
      //서버에서 받아온 메시지를
      console.log("수신한 메시지 ", JSON.parse(messageOutput.body));
      showMessage(JSON.parse(messageOutput.body).content); // json으로 파싱하고 -> 알아보기
    });
  });
}

// 메시지 전송 함수
function sendMessage() {
  const messageContent = document.getElementById("messageInput").value.trim(); //input에 적은 텍스트 가져와서 공백 제거
  if (messageContent && stompClient) {
    //메시지가 있고 stomp가 활성상태일때만 가능하게 하기
    stompClient.send(
      "/app/message",
      {},
      JSON.stringify({ content: messageContent })
    );
    document.getElementById("messageInput").value = ""; // 입력 필드 초기화
  }
}

// 수신된 메시지를 화면에 표시하는 함수
function showMessage(message) {
  const messagesList = document.getElementById("messages");
  const messageElement = document.createElement("li");
  messageElement.appendChild(document.createTextNode(message));
  messagesList.appendChild(messageElement);
}

// 페이지 로드 시 WebSocket 연결 시작
document.addEventListener("DOMContentLoaded", function () {
  connect();
});
