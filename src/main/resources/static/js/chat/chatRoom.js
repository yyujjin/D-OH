let MessageDTO = {
    sender : "testUser",
    receiver : "testUser",
    content :null
  }

  const  MessageType = ["sent", "receive"];

const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8083/chat'
});

stompClient.onConnect = (frame) => {
    //setConnected(true);
    console.log('Connected: ' + frame);
    stompClient.subscribe('/user/queue/messages', (message) => {
    
        const parsedMessage = JSON.parse(message.body).content;
        console.log('받은 메시지 : ' + parsedMessage)
        addMessageToChat(parsedMessage,'received');
    });
};

stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

function connect() {
    stompClient.activate();
}

function disconnect() {
    stompClient.deactivate();
    setConnected(false);
    console.log("Disconnected");
}

function sendMesssage() {
    const chatInput = document.getElementById('chatInput');
    MessageDTO.content = chatInput.value
    stompClient.publish({
        destination: "/app/message",
        body: JSON.stringify(MessageDTO)
    });
    addMessageToChat(MessageDTO.content,'sent')
}


// 페이지 로드 시 웹소켓 연결 자동 시작
window.onload = function() {
    connect();
};

const sendButton = document.getElementById("sendButton").addEventListener("click",sendMesssage);

function addMessageToChat(content, messageType) {

    const chatBody = document.getElementById('chatBody');

    // 말풍선(div) 요소 생성
    const messageDiv = document.createElement('div');
    messageDiv.classList.add('chat-message', messageType);

    // 메시지 내용이 들어갈 div 생성
    const messageBubble = document.createElement('div');
    messageBubble.classList.add('message-bubble',messageType);
    messageBubble.textContent = content;

    // 말풍선에 메시지 내용 추가
    messageDiv.appendChild(messageBubble);

    // 채팅창에 말풍선 추가
    chatBody.appendChild(messageDiv);

    // 새로 추가된 메시지까지 스크롤이 이동하도록 설정
    chatBody.scrollTop = chatBody.scrollHeight;
}