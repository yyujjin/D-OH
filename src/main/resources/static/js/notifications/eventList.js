document.addEventListener("DOMContentLoaded", function () {
    const currentDate = new Date();
    console.log("Current Date:", currentDate); // 현재 날짜 출력
    const eventCards = document.querySelectorAll(".card-link");

    eventCards.forEach(function (card) {
        const endDateText = card.querySelector(".card-text span:nth-of-type(2)").textContent;
        console.log("End Date Text:", endDateText); // 종료 날짜 텍스트 출력

        // "이벤트 기간: YYYY-MM-DD ~ YYYY-MM-DD" 형식에서 종료 날짜 추출
        const endDateString = endDateText.split(' ~ ')[1].trim(); // 종료 날짜 부분만 추출
        console.log("End Date String:", endDateString); // 변환된 종료 날짜 출력

        const endDate = new Date(endDateString); // 종료 날짜를 Date 객체로 변환
        console.log("Parsed End Date:", endDate); // 파싱된 종료 날짜 출력

        if (endDate < currentDate) {
            console.log("Card expired:", card); // 만료된 카드 출력
            card.classList.add("expired");

            // "이벤트 종료" 문구 추가
            const expiredMessage = document.createElement("span");
            expiredMessage.textContent = "이벤트 종료";
            expiredMessage.style.color = "red"; // 색상 변경 가능
            expiredMessage.style.fontWeight = "bold"; // 강조
            expiredMessage.classList.add("expired-message");

            // 카드에 추가
            card.querySelector(".card-body").appendChild(expiredMessage);
        }
    });
});
