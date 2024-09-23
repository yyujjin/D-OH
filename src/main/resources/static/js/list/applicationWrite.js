$(document).ready(function () {
    // label을 클릭하면 파일 선택창이 뜨도록 처리
    $(".imageUpload").on("click", function () {
        $("#image").click();
    });

    // 파일이 선택되면 이미지를 미리보기로 표시
    $("#image").on("change", function () {
        var files = this.files;
        var imgArea = $(".imgArea");
        imgArea.empty(); // 기존 이미지를 초기화

        // 선택한 파일을 하나씩 처리하여 미리보기로 추가
        if (files) {
            $.each(files, function (i, file) {
                var reader = new FileReader();
                reader.onload = function (e) {
                    imgArea.append('<img src="' + e.target.result + '" class="previewImage" style="max-width: 100%; height: auto;"/>');
                };
                reader.readAsDataURL(file); // 파일을 읽어서 미리보기로 출력
            });
        }
    });

    // 폼 제출 시 선택된 파일을 서버로 전송
    $("form").on("submit", function (e) {
        e.preventDefault(); // 기본 폼 제출 막기

        var formData = new FormData(this); // 폼 데이터를 객체로 저장

        // AJAX를 이용한 서버로 파일 전송
        $.ajax({
            url: "/contest/application/upload", // 업로드할 엔드포인트
            type: "POST",
            data: formData,
            processData: false,
            contentType: false,
            success: function (response) {
                alert("파일이 성공적으로 업로드되었습니다!");
            },
            error: function (error) {
                alert("업로드 중 오류가 발생했습니다.");
            }
        });
    });
});

    function addText(){
        var std ="<textarea name='applyContent' id='' placeholder='텍스트를 입력해주세요.' onkeydown='resize(this)' onkeyup='resize(this)' required></textarea>";

        if ($(".textArea").find('textarea').length > 0) {
            console.log("Textarea 태그가 이미 있음...");
        } else {
            console.log("Textarea 태그가 없음!");
//            $(".textArea").append(std);
             // textarea를 append하고 나서 그 요소를 찾아서 focus 적용
            var newTextarea = $(std).appendTo(".textArea");
            newTextarea.focus();  // 새로 추가된 textarea에 focus
        }
    }


    function resize(obj) {
        obj.style.height = '1px';
        obj.style.height = (12 + obj.scrollHeight) + 'px';
    }