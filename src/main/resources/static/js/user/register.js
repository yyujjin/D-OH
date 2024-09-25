document.addEventListener('DOMContentLoaded', function () {
    let isEmailVerified = false;  // 이메일 인증 상태 추적

    //인증 버튼을 눌렸을 때
    function show() {
        if (validateEmail()) {
            document.getElementById('auth-btn').style.display = 'none';
            document.getElementById('resend-btn').style.display = 'block'; // 이메일 유효 시 재전송 버튼 보이기
            document.getElementById('verification-container').style.display = 'block'; // 인증번호 입력 필드 보이기

            //인증하기 눌렸을 때 에러 메시지 숨기기
            document.getElementById('emailVerificationError').style.display = 'none';

            sendNumber();  // 인증번호 전송 트리거
        }
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

    // 이메일 검증 함수
    function validateEmail() {
        const email = document.getElementById('userEmail');
        const emailRegex = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
        const emailError = document.getElementById('emailError');

        if (!emailRegex.test(email.value)) {
            email.classList.add('error-border');
            emailError.style.display = "flex";
            document.getElementById('auth-btn').style.display = 'block';
            document.getElementById('resend-btn').style.display = 'none';
            document.getElementById('verification-container').style.display = 'none';
            return false;
        } else {
            email.classList.remove('error-border');
            emailError.style.display = "none"; // 에러 메시지 숨기기
            return true;
        }
    }

    // 이메일 인증 번호 전송
    function sendNumber() {
        const email = document.getElementById('userEmail').value;
        if (!validateEmail()) {
            return;
        }
        $.ajax({
            url: "/api/v1/email/send",
            type: "post",
            contentType: "application/json",
            data: JSON.stringify({ "userEmail": email }),
            success: function (data) {
                alert("인증번호가 발송되었습니다.");
                document.getElementById('mail_number').classList.add("visible");
            },
            error: function () {
                alert("이메일 발송에 실패하였습니다. 다시 시도해주세요.");
            }
        });
    }

    // 이메일 인증 번호 확인
    function confirmNumber() {
        const email = document.getElementById('userEmail').value;
        const number = document.getElementById('verification-code').value;

        $.ajax({
            url: "/api/v1/email/verify",
            type: "post",
            contentType: "application/json",
            dataType: "json",
            data: JSON.stringify({ "userEmail": email, "verifyCode": number }),
            success: function (data) {
                if (data) {
                    alert("이메일 인증에 성공하였습니다.");
                    isEmailVerified = true; // 이메일 인증 성공 시 true로 설정
                } else {
                    alert("인증 번호가 올바르지 않습니다.");
                    isEmailVerified = false; // 인증 실패 시 false로 설정
                }
            },
            error: function () {
                alert("인증에 실패하였습니다. 다시 시도해주세요.");
                isEmailVerified = false; // 인증 실패 시 false로 설정
            }
        });
    }

    // 가입하기 버튼 클릭 시 추가 유효성 검사
    document.getElementById('join-btn').addEventListener('click', function (e) {
        const emailVerificationError  = document.getElementById('emailVerificationError');
        //비밀번호 일치하는지 확인
        const passwordMatchError = document.getElementById('passwordMatchError');
        const userPassword = document.getElementById('password').value;  // 비밀번호 입력 값
        const confirmPassword = document.getElementById('confirm-password').value;  // 비밀번호 확인 값

        if (!isEmailVerified) {
            e.preventDefault(); // 폼 제출 방지
            emailVerificationError.style.display = "flex";
        }
        if (userPassword !== confirmPassword) {
            e.preventDefault();
            passwordMatchError.style.display = "flex";
            } else {
            passwordMatchError.style.display = "none";
            }
    });

    // 이벤트 바인딩
    document.getElementById('auth-btn').addEventListener('click', show);
    document.querySelector('label[for="agreement"] a').addEventListener('click', showTerms);
    document.getElementById('confirm-btn').addEventListener('click', hideTerms);
    document.getElementById('toggle-btn').addEventListener('click', function () {
        toggleContent('content1');
    });
    document.getElementById('resend-btn').addEventListener('click', sendNumber);
    document.querySelector('.submit-verify-btn').addEventListener('click', confirmNumber);

    // keyup 이벤트(사용자가 키를 누른 후 뗄 때마다 실행) -> validate 함수 호출
    document.getElementById('userEmail').addEventListener('keyup', function () {
        emailVerificationError.style.display = "none";
        validateEmail();
    });
    document.getElementById('password').addEventListener('keyup', function () {
        passwordMatchError.style.display = "none";
    });
    document.getElementById('confirm-password').addEventListener('keyup', function () {
        passwordMatchError.style.display = "none";
    });

       // 폼 제출 시 유효성 검사
    $('#signup-form').on('submit', function (e) {
        if (!isEmailVerified) {
            e.preventDefault(); // 폼 제출 방지
            emailError.style.display = "flex";
        } else {
            alert("🎉회원가입이 완료되었습니다.");
        }
    });

});
