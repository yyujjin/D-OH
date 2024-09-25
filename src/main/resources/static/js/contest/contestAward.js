function submitAward() {
    // 폼 데이터 수집
    var formData = {};
    var allNone = true;  // 모든 값이 'none'인지 확인하는 변수
    var selectedRanks = [];  // 선택된 순위를 저장할 배열
    var hasDuplicate = false;  // 중복 여부를 저장하는 변수

    // 히든 필드에서 contestNum 값을 가져와서 formData에 추가
    var contestNum = $("input[name='contestNum']").val();
    formData["contestNum"] = contestNum;

    // 각 참여자의 이메일과 순위 선택 정보를 수집
    $("#awardForm select").each(function() {
        var email = $(this).attr("name");  // 이메일을 name 속성에서 가져옴
        var ranking = $(this).val();       // 선택한 순위를 가져옴

        // 하나라도 'none'이 아닌 값이 있으면 allNone을 false로 설정
        if (ranking !== 'none') {
            allNone = false;

            // 이미 선택된 순위인지 확인
            if (selectedRanks.includes(ranking)) {
                alert("같은 순위를 중복 선택할 수 없습니다. 순위를 다시 선택해주세요.");
                hasDuplicate = true;  // 중복 발생 표시
                return false;  // 중복이 발생하면 더 이상 진행하지 않음
            }

            // 중복이 아니면 배열에 순위 추가
            selectedRanks.push(ranking);
        }

        formData[email] = ranking;
    });

    // 중복이 있으면 제출하지 않음
    if (hasDuplicate) {
        return;  // 중복이 발생했으므로 AJAX 요청을 보내지 않고 종료
    }

    // 모든 선택이 'none'이면 제출하지 않고 경고 메시지를 출력
    if (allNone) {
        alert("최소 1명의 순위를 선택해주세요. 모든 참가자의 순위를 선택하지 않아도 됩니다.");
        return; // AJAX 요청을 보내지 않고 종료
    }

    // AJAX 요청
    $.ajax({
        url: '/users/contest/award',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(formData),  // 데이터를 JSON으로 변환하여 전송
        success: function(response) {
            alert('시상식 정보가 성공적으로 제출되었습니다!');
            window.location.href = '/contest/list';  // 성공 후 리다이렉트
        },
        error: function(xhr, status, error) {
            console.error('AJAX 요청 실패: ', error);
            alert('제출 중 오류가 발생했습니다. 다시 시도해주세요.');
        }
    });
}
