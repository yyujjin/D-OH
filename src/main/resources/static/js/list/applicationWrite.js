
    function addText(){
        var std ="<textarea name='' id='' placeholder='텍스트를 입력해주세요.' onkeydown='resize(this)' onkeyup='resize(this)'></textarea>";

        if ($(".textArea").find('textarea').length > 0) {
            console.log("Textarea 태그가 이미 있음...");
        } else {
            console.log("Textarea 태그가 없음!");
            $(".textArea").append(std);
        } 
    }


    function resize(obj) {
        obj.style.height = '1px';
        obj.style.height = (12 + obj.scrollHeight) + 'px';
    }