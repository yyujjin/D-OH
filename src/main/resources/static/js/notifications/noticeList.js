function toggleContent(element) {
    const targetId = element.getAttribute('data-target');
    const contentRow = document.querySelector(targetId);

    // 현재 행이 보이는지 여부 확인
    const currentDisplay = window.getComputedStyle(contentRow).display;

    if (currentDisplay === 'table-row') {
        // 보이면 숨기기
        contentRow.style.display = 'none';
        element.classList.remove('active-toggle');
    } else {
        // 보이지 않으면 다른 행 숨기고 클릭한 행 보이기
        document.querySelectorAll('.content-row').forEach(row => row.style.display = 'none');
        document.querySelectorAll('.toggle-row').forEach(row => row.classList.remove('active-toggle'));

        contentRow.style.display = 'table-row';
        element.classList.add('active-toggle');
    }
}
