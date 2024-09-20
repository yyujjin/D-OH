
function hitUp(e) {
   console.log("test");
   var id = $(e).siblings("#contestId").val();
   console.log(id);

       $.ajax({
           url: '/contest/hitUp',
           type: 'POST',
           data: { contestId: id },
           success: function() {
               // 조회수 증가 성공 시 페이지 이동
               console.log('조회수 증가 완료');
               location.href = '/contest/view?contestId='+ id;  // 페이지 이동
           },
           error: function() {
               // 조회수 증가 실패 시 에러 처리
               console.log('조회수 증가 중 오류 발생:', error);
           }
       });

}
