document.addEventListener("DOMContentLoaded", function () {
    // 임시 저장 여부 필드 설정
    let eventTempSaveInput = document.getElementById("eventTempSave");
    if (!eventTempSaveInput) {
        eventTempSaveInput = document.createElement("input");
        eventTempSaveInput.setAttribute("type", "hidden");
        eventTempSaveInput.setAttribute("id", "eventTempSave");
        eventTempSaveInput.setAttribute("name", "eventTempSave");
        eventTempSaveInput.setAttribute("value", "false");
        document.getElementById("eventForm")?.appendChild(eventTempSaveInput);
    }

    // 오늘 날짜로 생성일 설정
    const today = new Date();
    const formattedDate = today.toISOString().split('T')[0];
    document.getElementById("eventCreateTime").value = formattedDate;

    // 임시 저장 버튼 핸들러
    const tempSaveBtn = document.getElementById("tempSaveBtn");
    if (tempSaveBtn) {
        tempSaveBtn.addEventListener("click", function () {
            document.querySelectorAll('#eventForm input[required]').forEach(function (field) {
                field.removeAttribute('required');
            });

            document.getElementById("eventTempSave").value = "true";
            document.getElementById("eventForm")?.submit();
        });
    }

    // 신규 등록 버튼 핸들러
    const registerBtn = document.getElementById("registerBtn");
    if (registerBtn) {
        registerBtn.addEventListener("click", function () {
            document.querySelectorAll('#eventForm input').forEach(function (field) {
                field.setAttribute('required', 'true');
            });

            document.getElementById("eventTempSave").value = "false";
            document.getElementById('eventForm')?.submit();
        });
    }

    // 수정 폼의 등록 버튼 핸들러
    const submitRegisterBtn = document.getElementById("submitRegisterBtn");
    if (submitRegisterBtn) {
        submitRegisterBtn.addEventListener("click", function () {
            document.querySelectorAll('#eventForm input').forEach(function (field) {
                field.setAttribute('required', 'true');
            });

            document.getElementById("eventTempSave").value = "false";
            document.getElementById('eventForm')?.submit();
        });
    }

    // 폼 제출 시 검증 함수
    const eventForm = document.getElementById("eventForm");
    if (eventForm) {
        eventForm.addEventListener("submit", function (event) {
            if (!validateForm()) {
                event.preventDefault();
            }
        });
    }

    // 삭제 버튼 핸들러
    document.getElementById("deleteBtn1")?.addEventListener("click", function (event) {
        event.preventDefault();  // 기본 동작 방지
        if (confirm("정말 삭제하시겠습니까?")) {
            document.getElementById("deleteForm1").submit();
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

    return true;
}
