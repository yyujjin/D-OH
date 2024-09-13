    $(document).ready(function() {
        // .title 클래스의 클릭 이벤트 리스너 추가
        $('.tabTitle .title').on('click', function() {
            // 모든 .title 요소에서 active 클래스 제거
            $('.tabTitle .title').removeClass('active');
            // 클릭된 .title 요소에 active 클래스 추가
            $(this).addClass('active');

            // 모든 .tabContent 요소에서 active 클래스 제거
            $('.tabContent').removeClass('active');
            // 클릭된 .title 요소의 인덱스에 해당하는 .tabContent 요소에 active 클래스 추가
            var index = $(this).index();
            $('.tabContent').eq(index).addClass('active');
        });
    });

//Swiper
document.addEventListener('DOMContentLoaded', function () {
  var swiper = new Swiper(".mySwiper", {
    cssMode: true,
    navigation: {
      nextEl: ".swiper-button-next",
      prevEl: ".swiper-button-prev",
    },
    pagination: {
      el: ".swiper-pagination",
      clickable: true,
    },
    autoplay: {
      delay: 3000, // 3초 (3000ms)마다 슬라이드 전환
      disableOnInteraction: false, // 사용자 상호작용 후에도 자동 전환 유지
    },
  });
});

