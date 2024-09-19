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
