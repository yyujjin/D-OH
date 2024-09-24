
function hitUp(e){

    const href = e.getAttribute('href');

    // URL에서 portfolioId 추출
    const urlParams = new URLSearchParams(href.split('?')[1]);
    const portfolioId = urlParams.get('portfolioId');

    console.log('Portfolio ID:', portfolioId);

        $.ajax({
            url: '/portfolio/hitUp',
            type: 'POST',
            data: { id: portfolioId },
            success: function() {
                console.log('조회수 증가 완료');
            },
            error: function(error) {
                console.log('조회수 증가 중 오류 발생:', error);
            }
        });
}