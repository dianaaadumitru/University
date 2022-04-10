$(document).ready(function(){

    var picWidth = 200;
    var poz = 0;
    var popupDiv = document.getElementById("popup-id");
    var popupImg = document.getElementById("popup-img");

    $("li").each(function() {
        poz += picWidth;
        $(this).css("right",poz);
    });

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
    function slide() {
        $("li").animate({"right":"+=10px"}, 100, again);
    }
    function again() {
        var right = $(this).parent().offset().left + $(this).offset().left;
        // document.getElementById("demo").innerHTML = right
        if (right <= 1600) {
            $(this).css("right",right - 1600);
        }
        slide();
    }

    slide();
});