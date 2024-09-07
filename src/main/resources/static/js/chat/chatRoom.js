//객체는 {}를 사용하고, 각 값이 키-값 쌍으로 저장됩니다.
const chatRoomDTO = {
  roomId: null, // 서버에서 생성되기에 초기에는 null로 설정
  client: "세일러문", // 의뢰인
  creator: "제작자123", // 제작자
};

// 서버로 POST 요청 보내기
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
