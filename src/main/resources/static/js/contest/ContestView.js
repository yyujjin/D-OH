function showContent(section) {
            // 모든 섹션 숨기기
            document.querySelector('.request').style.display = 'none';
            document.querySelector('.comments').style.display = 'none';
            document.querySelector('.work').style.display = 'none';

            // 선택된 섹션만 보이기 및 탭 스타일링 초기화
            document.getElementById('request-tab').classList.remove('active');
            document.getElementById('comments-tab').classList.remove('active');
            document.getElementById('work-tab').classList.remove('active');

            // 선택된 섹션 보이기 및 탭 활성화
            if (section === 'request') {
                document.querySelector('.request').style.display = 'block';
                document.getElementById('request-tab').classList.add('active');

            } else if (section === 'comments') {
                document.querySelector('.comments').style.display = 'block';
                document.getElementById('comments-tab').classList.add('active');

            } else if (section === 'work') {
                document.querySelector('.work').style.display = 'block';
                document.getElementById('work-tab').classList.add('active');
            }
        }

        // 페이지 로드 시 기본적으로 '요청 사항' 섹션을 표시
        window.onload = function() {
            showContent('request');
        };