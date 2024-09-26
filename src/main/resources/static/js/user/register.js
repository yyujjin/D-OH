document.addEventListener('DOMContentLoaded', function () {
    let isEmailVerified = false;  // 이메일 인증 상태 추적
    let isNickNameDuplicate = false; // 닉네임 중복 여부
    let isEmailDuplicate = false;    // 이메일 중복 여부

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

    // 이메일 중복 확인
    function checkEmail(callback) {
        const userEmail = document.getElementById('userEmail').value;

        $.ajax({
            url: '/api/v1/users/check-email',
            type: 'GET',
            data: { userEmail: userEmail },
            success: function (emailDuplicate) {
                if (emailDuplicate) {
                    alert('이미 사용 중인 이메일입니다.');
                    isEmailDuplicate = true;
                } else {
                    isEmailDuplicate = false;
                }
                if (typeof callback === 'function') {
                    callback(isEmailDuplicate);
                }
            },
            error: function () {
                alert('이메일 확인에 실패하였습니다.');
                if (typeof callback === 'function') {
                    callback(true); // 오류 시 중복으로 간주
                }
            }
        });
    }

    // 인증 버튼 클릭 시 이벤트 핸들러
    document.getElementById('auth-btn').addEventListener('click', function () {
        if (!validateEmail()) {
            return;
        }

        // 이메일 중복 확인
        checkEmail(function (isEmailDuplicate) {
            if (isEmailDuplicate) {
                return;
            }

            // 이메일 중복이 아니면 인증번호 전송 절차 진행
            show();
        });
    });

    function show() {
        document.getElementById('auth-btn').style.display = 'none';
        document.getElementById('resend-btn').style.display = 'block'; // 이메일 유효 시 재전송 버튼 보이기
        document.getElementById('verification-container').style.display = 'block'; // 인증번호 입력 필드 보이기

        // 인증하기 눌렸을 때 에러 메시지 숨기기
        document.getElementById('emailVerificationError').style.display = 'none';

        sendNumber();
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
                    isEmailVerified = true; // 이메일 인증 성공 시 true
                } else {
                    alert("인증 번호가 올바르지 않습니다.");
                    isEmailVerified = false; // 인증 실패 시 false
                }
            },
            error: function () {
                alert("인증에 실패하였습니다. 다시 시도해주세요.");
                isEmailVerified = false; // 인증 실패 시 false(=오류로 인한 전송 실패)
            }
        });
    }

    // 닉네임 중복 확인
    function checkNickname(showAlert, callback) {
        const nickName = document.getElementById('nickName').value;

        $.ajax({
            url: '/api/v1/users/check-nickname',
            type: 'GET',
            data: { nickName: nickName },
            success: function (nickNameDuplicate) {
                if (nickNameDuplicate) {
                    if (showAlert) alert('이미 사용 중인 닉네임입니다.');
                    isNickNameDuplicate = true;
//                     document.getElementById('nickName').value = "";
                } else {
                    if (showAlert) alert('사용 가능한 닉네임입니다.');
                    isNickNameDuplicate = false;
                }
                if (typeof callback === 'function') {
                    callback(isNickNameDuplicate);
                }
            },
            error: function () {
                if (showAlert) alert('닉네임 확인에 실패하였습니다.');
                isNickNameDuplicate = true;
                if (typeof callback === 'function') {
                    callback(isNickNameDuplicate);
                }
            }
        });
    }

    // 약관 보기 함수
    function showTerms() {
        document.getElementById('terms-overlay').style.display = 'flex';
    }

    function hideTerms() {
        document.getElementById('terms-overlay').style.display = 'none';
    }

    // 내용 토글 함수
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

    // 이벤트 바인딩
    document.querySelector('label[for="agreement"] a').addEventListener('click', showTerms);
    document.getElementById('confirm-btn').addEventListener('click', hideTerms);
    document.getElementById('toggle-btn').addEventListener('click', function () {
        toggleContent('content1');
    });
    document.getElementById('resend-btn').addEventListener('click', sendNumber);
    document.querySelector('.submit-verify-btn').addEventListener('click', confirmNumber);

    // 닉네임 입력 필드에서 blur 이벤트 발생 시 중복 확인
    document.getElementById('nickName').addEventListener('blur', function() {
        var nickNameInput = document.getElementById('nickName').value.trim();
        if (nickNameInput !== '') {
            checkNickname(true); // 메시지 표시
        }
    });

    // 키 입력 시 유효성 검사 메시지 숨김 및 검증
    document.getElementById('userEmail').addEventListener('keyup', function () {
        const emailVerificationError = document.getElementById('emailVerificationError');
        emailVerificationError.style.display = "none";
        validateEmail();
    });
    document.getElementById('password').addEventListener('keyup', function () {
        const passwordMatchError = document.getElementById('passwordMatchError');
        passwordMatchError.style.display = "none";
    });
    document.getElementById('confirm-password').addEventListener('keyup', function () {
        const passwordMatchError = document.getElementById('passwordMatchError');
        passwordMatchError.style.display = "none";
    });

    // 폼 제출 시 유효성 검사(서버 측 검증)
    $('#signup-form').on('submit', function (e) {
        e.preventDefault(); // 기본 동작 막기

        // 개인정보 수집 및 이용 동의 체크박스 확인
            const agreeCheckbox = document.getElementById('agree');
            if (!agreeCheckbox.checked) {
                alert('개인정보 수집 및 이용 동의에 체크해주셔야 회원가입이 가능합니다.');
                return;
            }

        const emailVerificationError = document.getElementById('emailVerificationError');
        const passwordMatchError = document.getElementById('passwordMatchError');
        const userPassword = document.getElementById('password').value;
        const confirmPassword = document.getElementById('confirm-password').value;

        // 이메일 인증 확인
        if (!isEmailVerified) {
            emailVerificationError.style.display = "flex";
            return;
        }

        // 이메일 중복 확인 후 처리
        checkEmail(function (isEmailDuplicate) {
            if (isEmailDuplicate) {
                alert("이미 사용 중인 이메일입니다. 다른 이메일을 입력해주세요.");
                return;
            }

            // 닉네임 중복 확인 후 처리
            checkNickname(false, function (isNickNameDuplicate) {
                if (isNickNameDuplicate) {
                    alert("닉네임이 중복되었습니다. 다른 닉네임을 입력해주세요.");
                    return;
                }

                // 비밀번호 일치 여부 확인
                if (userPassword !== confirmPassword) {
                    passwordMatchError.style.display = "flex";
                    return;
                } else {
                    passwordMatchError.style.display = "none";

                    // 모든 유효성 검사를 통과한 경우 폼 제출
                    alert("🎉회원가입이 완료되었습니다.");
                    $('#signup-form').off('submit').submit();  // 폼을 정상적으로 제출
                }
            });
        });
    });
});
