function toggleContent(element) {
    const targetId = element.getAttribute('data-target');
    const contentRow = document.querySelector(targetId);

    // Check if the content row is currently visible
    if (contentRow.style.display === 'table-row') {
        // If visible, hide it
        contentRow.style.display = 'none';
        element.classList.remove('active-toggle');
    } else {
        // If not visible, hide all other content rows and show the clicked one
        document.querySelectorAll('.content-row').forEach(row => row.style.display = 'none');
        document.querySelectorAll('.toggle-row').forEach(row => row.classList.remove('active-toggle'));

        contentRow.style.display = 'table-row';
        element.classList.add('active-toggle');
    }
}