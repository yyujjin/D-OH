$(document).ready(function(){

    $(".content.text").click(function () {
        console.log("click!!");
        var std ="<textarea name='content' id='textarea1' placeholder='텍스트를 입력해주세요.'></textarea>";
        $(".contArea").append(std);
    });//end of click function

});//end of document ready function

function previewImage(event) {
    var file = event.target.files[0];
    if (!file) {
        console.log("파일이 선택되지 않았습니다.");
        return;
    }

    // 이미지 파일인지 확인
    if (!file.type.startsWith("image/")) {
        console.log("이미지 파일이 아닙니다.");
        return;
    }

    var reader = new FileReader();
    reader.onload = function() {
        var imagePreviewBox = document.getElementById('image-preview-box');
        var img = document.createElement('img');
        img.src = reader.result;
        img.style.width = '470px';
        img.style.height = '470px';
        img.style.objectFit = 'contain';
        imagePreviewBox.innerHTML = ''; // 이전 이미지를 제거
        imagePreviewBox.appendChild(img); // 새로운 이미지 추가
    };
    reader.readAsDataURL(file);
}

        //mypage.js

            function viewAwardResult(conNum) {
        $.ajax({
            url: '/users/contest/award/result', // 요청할 서버의 URL
            type: 'GET', // GET 요청 방식
            data: { conNum: conNum }, // 쿼리스트링으로 conNum 전송
            success: function(response) {

                // 응답 결과에서 contestNum을 가져와서 URL에 포함
                window.location.href = '/users/contest/result?contestNum=' + response.conNum;

            },
            error: function(xhr, status, error) {
                alert("콘테스트가 진행 중입니다. 모집 기간 종료 후 결과를 확인할 수 있습니다!");
            }
        });
    }

    // 이미지 미리보기
    function previewImage2(event) {
        const file = event.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = function(e) {
                const output = document.getElementById('profilePreview2');
                if (output) {
                    output.src = e.target.result;  // 이미지가 로드되면 미리보기 설정
                } else {
                    console.error("profilePreview 요소를 찾을 수 없습니다.");
                }
            };
            reader.readAsDataURL(file);
        }
    }