
  // 폼 검증 함수
        function validateForm() {
            const eventTitle = document.getElementById("eventTitle").value;

            if (eventTitle.trim() === "") {
                alert("이벤트 제목을 입력하세요.");
                return false;
            }
            return true;
        }

        document.addEventListener("DOMContentLoaded", function () {
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
                    document.getElementById("eventTempSave").value = "true"; // 임시 저장으로 설정
                    document.getElementById("eventForm").submit();
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

            // 수정/등록 버튼 핸들러
            const submitRegisterBtn = document.getElementById("submitRegisterBtn");
            if (submitRegisterBtn) {
                submitRegisterBtn.addEventListener("click", function () {
                    document.querySelectorAll('#eventForm input:not(#eventImageUrl)').forEach(function (field) {
                        field.setAttribute('required', 'true');
                    });
                    document.getElementById("eventTempSave").value = "false"; // 정식 등록으로 설정
                });
            }

        });