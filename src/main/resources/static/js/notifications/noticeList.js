function toggleContent(element) {
    const targetId = element.getAttribute('data-target');
    const contentRow = document.querySelector(targetId);

    // 현재 행이 보이는지 여부 확인
    const isVisible = window.getComputedStyle(contentRow).display === 'table-row';

    // 모든 행을 숨기고, 토글된 행만 보이게 설정
    document.querySelectorAll('.content-row').forEach(row => row.style.display = 'none');
    document.querySelectorAll('.toggle-row').forEach(row => row.classList.remove('active-toggle'));

    // 클릭된 행만 표시
    if (!isVisible) {
        contentRow.style.display = 'table-row';
        element.classList.add('active-toggle');
    }
}
