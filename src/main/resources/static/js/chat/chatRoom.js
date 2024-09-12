
const userId = document.getElementById("userId").value;
console.log(userId);

let MessageDTO = null;

function makeMessageDTO(sender,receiver,content) {
    
    let MessageDTO = {
        sender : sender,
        receiver : receiver,
        content :content
    }

    return MessageDTO;
}



function makeUser() {

    if (window.location.href.includes("userA")) {
        MessageDTO = makeMessageDTO("aaaa","bbbb",null)
    } else if (window.location.href.includes("userB")) {
        MessageDTO = makeMessageDTO("aaaa","bbbb",null)
    }

    return MessageDTO;

}

MessageDTO = makeUser();

const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8083/chat',

    heartbeatIncoming: 10000, // 10초마다 서버에서 클라이언트로 heart-beat 받기
    heartbeatOutgoing: 10000, // 10초마다 클라이언트에서 서버로 heart-beat 보내기

    reconnectDelay: 5000, // 연결이 끊겼을 때 5초마다 재연결 시도
});

stompClient.onConnect = (frame) => {
    //setConnected(true);
    console.log('Connected: ' + frame);

    stompClient.subscribe(`/queue/messages/${userId}`, (message) => {
        const parsedMessage = JSON.parse(message.body);
        console.log(userId+'가 받은 메시지 : ' + parsedMessage)
        if(JSON.parse(message.body).sender==userId){
        addMessageToChat(MessageDTO.content,'sent')
        }else{
            addMessageToChat(parsedMessage,'received');
        }

        addMessageToChat(MessageDTO.content,'sent')
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
    
}


// 페이지 로드 시 웹소켓 연결 자동 시작
window.onload = function() {
    connect();
    makeUser();
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