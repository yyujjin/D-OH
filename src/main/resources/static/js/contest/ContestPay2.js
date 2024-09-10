function numberWithCommas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

// DOMContentLoaded 이벤트가 발생할 때까지 기다림
document.addEventListener("DOMContentLoaded", function() {
    // 각 금액을 세자리마다 콤마로 포맷팅
    let totalPrizeElement = document.getElementById('totalPrize');
    let serviceFeeElement = document.getElementById('serviceFee');
    let totalAmountElement = document.getElementById('totalAmount');

    // 각 요소의 텍스트 내용에 콤마 포맷팅 적용
    totalPrizeElement.textContent = numberWithCommas(totalPrizeElement.textContent);
    serviceFeeElement.textContent = numberWithCommas(serviceFeeElement.textContent);
    totalAmountElement.textContent = numberWithCommas(totalAmountElement.textContent);

    $(function(){
        $('#apibtn').click(function(){
            $.ajax({
                url:"/contest/payment/submit"
                dataType:'json',
                success:function(data){
                    var box = data.next_redirect_pc_url;
                    window.open(box);
                    alert(data.tid);
                    var
                    } ,
                    error:function(error){
                        alert(error);
                    }
                });
            });
        });

});
