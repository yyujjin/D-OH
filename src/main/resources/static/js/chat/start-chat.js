const chatRoomDTO = {
  roomId: null, // 서버에서 생성되기에 초기에는 null로 설정
  client: "세일러문", // 의뢰인
  creator: "제작자123", // 제작자
};

function startChat() {
  alert("1:1 채팅방으로 이동합니다.");

  fetch("http://localhost:8083/chat/room", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(chatRoomDTO),
  })
    .then((response) => {
      if (response.ok) {
        console.log("채팅방 이동 성공");
        window.location.href = "/chat/room";
      } else {
        console.error("채팅방 이동 실패");
      }
    })
    .catch((error) => {
      console.error("네트워크 에러", error);
    });
}
