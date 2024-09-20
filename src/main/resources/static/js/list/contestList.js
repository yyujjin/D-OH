
function hitUp() {
   console.log("test");
   var id = document.getElementById('contestId').value;
   console.log(id);

    // Ajax로 서버에 contestId 값을 보내는 로직
       $.ajax({
           url: '/contest/hitUp',  // 조회수 증가 API 경로
           type: 'POST',
           data: { contestId: id },
           success: function() {
               // 조회수 증가 성공 시 페이지 이동
               console.log('조회수 증가 완료');
               window.location.href = '/contest/view/?contestId=' + id;  // 페이지 이동
           },
           error: function() {
               // 조회수 증가 실패 시 에러 처리
               console.log('조회수 증가 중 오류 발생:', error);
           }
       });

}
