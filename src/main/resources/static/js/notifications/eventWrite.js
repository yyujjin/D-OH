document.addEventListener("DOMContentLoaded", function () {
    // 임시 저장 여부를 나타내는 hidden input 요소 추가
    let eventTempSaveInput = document.getElementById("eventTempSave");
    if (!eventTempSaveInput) {
        eventTempSaveInput = document.createElement("input");
        eventTempSaveInput.setAttribute("type", "hidden");
        eventTempSaveInput.setAttribute("id", "eventTempSave");
        eventTempSaveInput.setAttribute("name", "eventTempSave");
        eventTempSaveInput.setAttribute("value", "false");
        document.getElementById("eventForm").appendChild(eventTempSaveInput);
    }

    // 현재 날짜를 YYYY-MM-DD 형식으로 가져와 이벤트 생성일 입력 필드에 설정
    const today = new Date();
    const formattedDate = today.toISOString().split('T')[0];
    document.getElementById("eventCreateTime").value = formattedDate;

    // 임시 저장 버튼 핸들러
        const tempSaveBtn = document.getElementById("tempSaveBtn");
        if (tempSaveBtn) {
            tempSaveBtn.addEventListener("click", function () {
                // 임시 저장 시 필수 입력 제거
                document.getElementById("eventCreateTime").removeAttribute("required");
                document.getElementById("eventStartDate").removeAttribute("required");
                document.getElementById("eventEndDate").removeAttribute("required");

                // 임시 저장 플래그 true 설정 및 폼 제출
                document.getElementById("eventTempSave").value = "true";
                document.getElementById("eventForm").submit();
            });
        }

    // 폼 제출 시 검증 함수 호출 (등록 버튼 클릭 시)
    document.getElementById("eventForm").addEventListener("submit", function (event) {
        if (!validateForm()) {
            event.preventDefault(); // 폼 제출을 막음
        }
    });
});

// 폼 검증 함수 (이벤트 제목만 검증)
function validateForm() {
    const eventTitle = document.getElementById("eventTitle").value;

    if (eventTitle.trim() === "") {
        alert("이벤트 제목을 입력하세요.");
        return false;
    }

    return true; // 검증 통과 시 true 반환
}
