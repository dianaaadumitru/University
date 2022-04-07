$(document).ready(function(){

    var picWidth = 200;
    var poz = 0;
    var popupDiv = document.getElementById("popup-id");
    var popupImg = document.getElementById("popup-img");

    $("img").click(function(){
        var img = $(this).attr('src');
        popupDiv.style.display = 'block';
        popupImg.src = img;
        $("li").clearQueue();
        $("li").stop();
    });

    popupImg.onclick = function() {
        popupDiv.style.display = 'none';
        slide();
    }

    $("li").each(function() {
        poz += picWidth;
        $(this).css("left",poz);
    });


    function slide() {
        $("li").animate({"left":"+=10px"}, 'fast', repeat);
    }
    function repeat() {
        var left = $(this).parent().offset().left + $(this).offset().left;
        if (left >= 1600) {
            $(this).css("left",left - 1600);
        }
        slide();
    }

    slide();
});