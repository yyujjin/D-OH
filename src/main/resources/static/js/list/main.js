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

let currentSlide = 0;
const slides = document.querySelectorAll('.bannerContent');
const totalSlides = slides.length;

function showSlide(index) {
    slides.forEach((slide, i) => {
        slide.classList.remove('active');
        if (i === index) {
            slide.classList.add('active');
        }
    });
}

function nextSlide() {
    currentSlide = (currentSlide + 1) % totalSlides;
    showSlide(currentSlide);
}

function prevSlide() {
    currentSlide = (currentSlide - 1 + totalSlides) % totalSlides;
    showSlide(currentSlide);
}

document.querySelector('.next').addEventListener('click', nextSlide);
document.querySelector('.prev').addEventListener('click', prevSlide);

// 자동 재생 기능
setInterval(nextSlide, 2500); // 5초마다 자동으로 다음 슬라이드로 이동
