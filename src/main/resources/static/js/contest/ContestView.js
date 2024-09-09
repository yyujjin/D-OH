function showContent(section) {
    // 모든 섹션 숨기기
    const sections = document.querySelectorAll('.request, .comments, .work');
    sections.forEach(sec => sec.style.display = 'none');

    // 모든 탭 비활성화
    const tabs = document.querySelectorAll('.meta span');
    tabs.forEach(tab => tab.classList.remove('active'));

    // 선택된 섹션만 보이기
    document.querySelector(`.${section}`).style.display = 'block';

    // 선택된 탭 활성화
    document.getElementById(`${section}-tab`).classList.add('active');
}

// 페이지 로드 시 기본적으로 '요청 사항' 섹션을 표시
window.onload = function() {
    showContent('request');
};