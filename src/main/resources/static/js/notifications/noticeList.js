function toggleContent(element) {
    const targetId = element.getAttribute('data-target'); // data-target으로 해당 content-row의 id 가져오기
    const contentRow = document.querySelector(targetId); // 클릭한 행에 해당하는 숨겨진 content-row 선택

    // 현재 클릭된 행이 열려 있는지 확인
    const isVisible = contentRow.style.display === 'table-row';

    // 모든 content-row를 숨기고 모든 toggle-row에서 active-toggle 클래스를 제거
    document.querySelectorAll('.content-row').forEach(row => {
        row.style.display = 'none'; // 모든 content-row 숨김
    });
    document.querySelectorAll('.toggle-row').forEach(row => {
        row.classList.remove('active-toggle'); // 모든 toggle-row에서 active-toggle 클래스 제거
    });

    // 클릭된 행에 active-toggle 클래스를 추가
    element.classList.add('active-toggle');

    // 클릭된 행만 표시하거나 숨김 (토글)
    if (!isVisible) {
        contentRow.style.display = 'table-row'; // 클릭된 행의 내용을 표시
        element.classList.add('active-toggle'); // 클릭된 행에 active-toggle 클래스 추가
    } else {
        contentRow.style.display = 'none'; // 이미 보이는 경우 다시 숨기기
        element.classList.remove('active-toggle'); // active-toggle 클래스 제거
    }
}
