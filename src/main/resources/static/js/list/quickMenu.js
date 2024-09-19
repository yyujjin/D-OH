function chatting() {
    var chat = $(".chat");
    var cnt = $("#cnt").val(); // cnt 값을 가져옴
    var ids = cnt === "0" ? 'None' : 'On'; // cnt 값이 '0'이면 'None', 그 외는 'On'
    var txt = $("#txt" + ids); // txtNone 또는 txtOn을 선택

    // 채팅이 있으면 알림 배지를 보임
    if (cnt !== "0") {
        $(".notification-badge").show(); // 알림 배지를 보이기
    } else {
        $(".notification-badge").hide(); // 알림 배지를 숨기기
    }

    if (txt.hasClass("show")) {
        txt.removeClass("show");
        txt.hide();
        chat.removeClass("active");
    } else {
        txt.addClass("show");
        txt.show();
        chat.addClass("active");
    }
}

function scrollToTop() {
    $('html, body').animate({ scrollTop: 0 }, 'slow'); // 부드럽게 스크롤 상단으로 이동
}

// WebSocket 연결 및 메시지 수신 로직
const socket = new SockJS('/your-websocket-endpoint');
const stompClient = Stomp.over(socket);

stompClient.connect({}, function (frame) {
    // 구독 경로 설정 (로그인된 사용자의 ID에 맞게 설정)
    const userId = document.getElementById("userId").value; // 사용자 ID를 세션에서 가져옴
    stompClient.subscribe('/user/' + userId + '/queue/messages', function (messageOutput) {
        showNotificationIcon(); // 메시지 수신 시 알림 배지 표시
    });
});

// 알림 배지를 표시하는 함수
function showNotificationIcon() {
    const notificationIcon = document.querySelector('.notification-badge');
    if (notificationIcon) {
        notificationIcon.style.display = 'block'; // 알림 배지 보이기
    }
}

// 채팅 확인 시 알림 배지를 숨기는 함수
function hideNotificationIcon() {
    const notificationIcon = document.querySelector('.notification-badge');
    if (notificationIcon) {
        notificationIcon.style.display = 'none'; // 알림 배지 숨기기
    }
}

// 채팅 창이 열렸을 때 알림 배지를 제거하는 로직
document.querySelectorAll('.chat-icon').forEach(chatIcon => {
    chatIcon.addEventListener('click', function () {
        hideNotificationIcon(); // 채팅을 확인하면 알림 배지를 숨김
    });
});
