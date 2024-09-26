$(document).ready(function() {
            $('#summernote').summernote({
                height: 300,                 // 에디터 높이 설정
	            lang: 'ko-KR',               // 에디터 한글 설정
                callbacks: {
                    onImageUpload: function(files) {
                        uploadImage(files[0]); // 이미지 업로드 처리
                    }
                }
            });

            function uploadImage(file) {
                var data = new FormData();
                data.append("file", file);

                $.ajax({
                    url: '/users/contest/uploadImage',
                    method: 'POST',
                    data: data,
                    contentType: false,
                    processData: false,
                    success: function(response) {
                        $('#summernote').summernote('insertImage', response.url); // Summernote에 이미지 삽입
                    },
                    error: function() {
                        alert('이미지 업로드에 실패했습니다.');
                    }
                });
            }
        });