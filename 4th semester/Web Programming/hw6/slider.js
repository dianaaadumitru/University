$(document).ready(function(){
    var picWidth = 200;
    var poz = 0;

    $("li").each(function() {
        poz += picWidth;
        $(this).css("left",poz);
    });

    function slide() {
        $("li").animate({"left":"+=10px"}, 100, again);
    }

    function again() {
        var left = $(this).parent().offset().left + $(this).offset().left;
        console.log("left="+left);
        if (left >= 1600) {
            $(this).css("left",left - 1600);
        }
        slide();
    }

    slide()

});