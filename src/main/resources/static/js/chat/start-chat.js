const chatRoomDTO = {
  roomId: null, // 서버에서 생성되기에 초기에는 null로 설정
  client: "세일러문", // 의뢰인
  creator: "제작자123", // 제작자
};
function startChat() {
  // 여기에 채팅방 생성 및 이동 로직을 추가합니다.
  alert("1:1 채팅방으로 이동합니다.");
  // 예시로 페이지 이동
  window.location.href = "chatRoom.html"; // 실제 채팅방 경로로 변경

  fetch("http://localhost:8083/chat/room", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(chatRoomDTO),
  })
    .then((response) => response.json())
    .then((data) => {
      console.log("Success:", data);
    })
    .catch((error) => {
      console.error("Error:", error);
    });
}
