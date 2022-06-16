function addAsset() {
    const name = document.getElementById('nameAdd').value;
    const description = document.getElementById('descriptionAdd').value;
    const value = document.getElementById('valueAdd').value;

    if (name === '' || description === '' || value === '') {
        alert("Please fill all fields");
        return false;
    }

    $.ajax({
        type: "POST",
        url: "addAsset.php",
        data: {
            name: name,
            description: description,
            value: value
        },
        cache: false,
        success: function(data) {
            alert(data);
        },
        error: function(xhr, status, error) {
            console.error(xhr);
        }
    });
    // console.log(name)
    alert("yay")
}