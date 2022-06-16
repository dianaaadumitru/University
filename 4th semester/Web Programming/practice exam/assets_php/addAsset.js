var listToAdd = [];

$(document).ready(function () {

    $("#submit").click(function () {

        var name = $("#name").val();
        var description = $("#description").val();
        var value = $("#value").val();

        if (name === '' || description === '' || value === '') {
            alert("Please fill all fields.");
            return false;
        }

        listToAdd.push([name, description, value]);
        console.log(name, description, value);
        console.log(listToAdd.length);

    });

    $("#addAll").click(function () {
        var jsonArray = JSON.stringify(listToAdd);
        //while(listToAdd.length > 0) {
        //var asset = listToAdd.pop();
        $.ajax({
            type: "POST",
            url: "addAsset.php",
            data: {
                album: jsonArray,
            },
            cache: false,
            success: function (data) {
                alert(data);
            },
            error: function (xhr, status, error) {
                console.error(xhr);
            }
        });
        //}

    })

});