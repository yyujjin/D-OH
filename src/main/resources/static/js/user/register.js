 function show() {
            document.getElementById('auth-btn').style.display = 'none'; // 인증 버튼 숨기기
            document.getElementById('resend-btn').style.display = 'block'; // 재전송 버튼 보이기
            document.getElementById('verification-container').style.display = 'block'; // 인증번호 입력 필드 보이기
        }

        function showTerms() {
            document.getElementById('terms-overlay').style.display = 'flex';
        }

        function hideTerms() {
            document.getElementById('terms-overlay').style.display = 'none';
        }

        function toggleContent(contentId) {
            var content = document.getElementById(contentId);
            var toggleBtn = document.getElementById('toggle-btn');

            if (content.style.display === "none" || content.style.display === "") {
                content.style.display = "block";
                toggleBtn.textContent = '접기';
            } else {
                content.style.display = "none";
                toggleBtn.textContent = '펼치기';
            }
        }