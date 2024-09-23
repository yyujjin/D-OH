function toggleContent(element) {
    const targetId = element.getAttribute('data-target');
    const contentRow = document.querySelector(targetId);

    // 현재 행이 보이는지 여부 확인
    const isVisible = contentRow.style.display === 'table-row';

    // 모든 행을 숨기고, 토글된 행만 보이게 설정
    document.querySelectorAll('.content-row').forEach(row => {
        row.style.display = 'none'; // 모든 content-row 숨김
    });
    document.querySelectorAll('.toggle-row').forEach(row => {
        row.classList.remove('active-toggle'); // 모든 toggle-row에서 active-toggle 클래스 제거
    });

    // 클릭된 행만 표시 또는 숨김
    if (!isVisible) {
        contentRow.style.display = 'table-row'; // 클릭된 행 표시
        element.classList.add('active-toggle'); // 클릭된 토글 행에 active-toggle 클래스 추가
    } else {
        contentRow.style.display = 'none'; // 이미 보이는 경우 다시 숨기기
        element.classList.remove('active-toggle'); // active-toggle 클래스 제거
    }
}
