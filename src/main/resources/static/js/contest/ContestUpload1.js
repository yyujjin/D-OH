let prizeIndex = 2; // 2등부터 시작

    function addPrize() {
        if (prizeIndex > 3) {
            alert("3등까지만 추가할 수 있습니다.");
            return;
        }

        const prizeContainer = document.getElementById('dynamicPrizes');

        // 새로운 등수를 나타내는 div 생성
        const newPrize = document.createElement('div');
        newPrize.className = 'flex-container';
        newPrize.setAttribute('data-index', prizeIndex);

        // 등수에 맞는 아이콘 및 텍스트 설정
        let rankIcon = `🏅 ${prizeIndex}등`;
        let peopleName = '', priceName = '';

        if (prizeIndex === 2) {
            rankIcon = `🥈 2등`;
            peopleName = 'conSecondPeople';  // 고정된 name 값
            priceName = 'conSecondPrice';    // 고정된 name 값
        } else if (prizeIndex === 3) {
            rankIcon = `🥉 3등`;
            peopleName = 'conThirdPeople';   // 고정된 name 값
            priceName = 'conThirdPrice';     // 고정된 name 값
        }

        // 새 등수 필드에 HTML 추가
        newPrize.innerHTML = `
            <div class="rank">
                <span>${rankIcon}</span>
            </div>
            <div class="winners">
                <input type="number" class ="plusInput" id="${peopleName}" name="${peopleName}" min="1" value="1" required>
            </div>
            <span class="label-text">명</span>
            <div class="prize">
                <input type="number" class ="plusInput" id="${priceName}" name="${priceName}" min="0" step="5" value="20" required>
            </div>
            <span class="label-text">만원</span>
            <div class="remove-btn-container">
                <button type="button" class="remove-btn" onclick="removePrize(this)">✖</button>
            </div>
        `;

        // 새 필드를 prizeContainer에 추가
        prizeContainer.appendChild(newPrize);

        // 등수 증가
        prizeIndex++;
        updateTotalPrize();
    }

    function removePrize(element) {
        element.parentElement.parentElement.remove();
        prizeIndex--;
        updateTotalPrize();
    }

    function updateTotalPrize() {
        let totalPrize = 0;

        // 1등 상금 계산: 사람 수 * 상금
        const firstPeople = parseInt(document.getElementById('conFirstPeople').value || 0);
        const firstPrize = parseInt(document.getElementById('conFirstPrice').value || 0);
        totalPrize += firstPeople * firstPrize;

        // 동적으로 추가된 2등, 3등 상금 계산: 사람 수 * 상금
        const dynamicPrizes = document.querySelectorAll('#dynamicPrizes .flex-container');
        dynamicPrizes.forEach(prize => {
            const people = parseInt(prize.querySelector('input[name$="People"]').value || 0);
            const prizeAmount = parseInt(prize.querySelector('input[name$="Price"]').value || 0);
            totalPrize += people * prizeAmount;
        });

        // 총 상금 표시
        document.getElementById('totalPrize').textContent = totalPrize;
    }


    // 상금 필드 값이 변경될 때마다 총 상금 업데이트
    document.addEventListener('input', function(event) {
        if (event.target.name.includes("Price") || event.target.name.includes("People")) {
            updateTotalPrize();
        }
    });

    // 현재 날짜를 가져오는 함수
    function getToday() {
        const today = new Date();
        const year = today.getFullYear();
        const month = ('0' + (today.getMonth() + 1)).slice(-2); // 월은 0부터 시작하므로 +1
        const day = ('0' + today.getDate()).slice(-2);
        return `${year}-${month}-${day}`;
    }

    // 시작 날짜 기본값을 현재 날짜로 설정하고, 종료 날짜도 자동으로 설정
    function initializeDates() {
        const startDateInput = document.getElementById('conStartDate');
        const endDateInput = document.getElementById('conEndDate');

        const today = getToday();
        startDateInput.value = today;
        startDateInput.min = today;

        setEndDate(); // 시작 날짜 기본값에 따라 종료 날짜를 7일 뒤로 설정
    }

    // 종료 날짜를 시작 날짜로부터 7일 뒤로 설정하는 함수
    function setEndDate() {
        const startDateInput = document.getElementById('conStartDate');
        const endDateInput = document.getElementById('conEndDate');

        const startDate = new Date(startDateInput.value);
        const endDate = new Date(startDate);
        endDate.setDate(startDate.getDate() + 7); // 7일 뒤로 설정

        const year = endDate.getFullYear();
        const month = ('0' + (endDate.getMonth() + 1)).slice(-2);
        const day = ('0' + endDate.getDate()).slice(-2);

        endDateInput.value = `${year}-${month}-${day}`;
        endDateInput.min = `${year}-${month}-${day}`; // 종료 날짜는 시작 날짜 + 7일 이후여야 함
    }

    // 페이지 로드 시 실행되는 초기화 함수
    window.onload = initializeDates;