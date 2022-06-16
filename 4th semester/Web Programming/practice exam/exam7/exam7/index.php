<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Websites</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script defer>
        function buildTable(data) {
            let code_string = "<tr> <th>url</th> <th>documents</th> </tr>";
            for (let row in data) {
                console.log("1: ", data[row]['url'], "2 ", data[row]['documents']);
                code_string += "<tr> <td>" + data[row]['url'] + "</td> <td>" + data[row]['documents'] + "</td></tr>";
            }
            $("#data").html(code_string);
        }

        function displayDocuments(data) {
            let code_string = "<select name='select'>";
            for (let row in data) {
                code_string += "<option>" + data[row]['name'] + "</option>";
            }
            code_string += "</select>";
            $("#selectDocData").html(code_string);
        }

        function buildSecondTable(data) {
            let code_string = "<tr> <th>name</th> <th>keyboard1</th> <th>keyboard2</th> <th>keyboard3</th> <th>keyboard4</th> <th>keyboard5</th> </tr>";
            for (let row in data) {
                code_string += "<tr> <td>" + data[row]['name'] + "</td> <td>" + data[row]['keyward1'] + "</td> <td>" +
                    data[row]['keyward2'] + "</td> <td>" + data[row]['keyward3'] + "</td> <td>" + data[row]['keyward4'] + "</td> <td>" + data[row]['keyward5'] + "</td></tr>";
            }
            $("#inputs").html(code_string);
        }


        $(document).ready(function () {

            $.getJSON(
                "http://localhost/exam7/websitesDB.php")
                .done(
                    function (data) {
                        console.log("here")
                        console.log(data)
                        buildTable(data);
                    }
                );

            $.getJSON(
                "http://localhost/exam7/documentsDB.php")
                .done(
                    function (data) {
                        displayDocuments(data);
                    }
                );

            $('#addInput').click(function () {
                let input = "<input name='lines[]'> </input>"
                $("#inputs").append(input)
            });

            $('#reset').click(function () {
                $("#inputs").empty();
            });

            $('#searchDocButton').click(function (){
                $.ajax({
                    url: 'http://localhost/exam7/documentsDB.php',
                    type: 'post',
                    data: { "callFunc1": "1"},
                    success: function(response) { console.log(response); buildSecondTable(response); }
                });
            })

        });

    </script>
</head>
<body>

<h1>Update keyboard</h1>
<form method="post">
    Select document:
    <div id="selectDocData"></div>
    <br>Select keyboard nr: <br>
    <select name='keynr'>
        <option>keyword1</option>
        <option>keyword2</option>
        <option>keyword3</option>
        <option>keyword4</option>
        <option>keyword5</option>
    </select><br>
    <br>Introduce new values: <br><input name='newval' placeholder="New keyboard value"><br>
    <br>
    <button name="update" type="submit">Save changes</button>
</form>

<h1>Websites: </h1>
<table id="data"></table>

<h1>Get documents with exactly 3 matching keywords</h1>
<button id="addInput">+Add input</button>
<button id="reset">Reset</button>
<br>
<br>
<form method="post">
    <button type="submit" name="submit">Submit</button>
    <div id="inputs"></div>
</form>

</body>
</html>