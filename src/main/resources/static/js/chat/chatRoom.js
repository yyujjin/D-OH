const MessageDTO = {
    sender : "발신자",
    receiver : "수신자",
    connect : null
  }


const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8083/chat'
});

stompClient.onConnect = (frame) => {
    //setConnected(true);
    console.log('Connected: ' + frame);
    stompClient.subscribe('/queue/messages', (message) => {
        showGreeting(JSON.parse(message.body).content);
        console.log("받은 메시지",message)
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

function sendName() {
    stompClient.publish({
        destination: "/app/message",
        body: JSON.stringify(MessageDTO)
    });
}

// 페이지 로드 시 웹소켓 연결 자동 시작
window.onload = function() {
    connect();
};