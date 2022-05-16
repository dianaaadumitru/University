function get_filtered_severity() {
    let ajax = new XMLHttpRequest();
    ajax.onreadystatechange = function () {
        if (this.status === 200) {
            let table = document.getElementsByTagName("table")[0];
            let oldTableBody = document.getElementsByTagName("tbody")[0];
            let newTableBody = document.createElement('tbody');

            let json = jsonParse(this.responseText);
            for (let i = 0; i < json.length; i++) {
                let report = json[i];
                let row = newTableBody.insertRow();

                Object.keys(report).forEach(function (key) {
                    let text;
                    let cell = row.insertCell();
                    text = report[key];
                    cell.appendChild(document.createTextNode(text));
                })
            }

            table.replaceChild(newTableBody, oldTableBody);
        }
    }

    ajax.open('POST', 'severity.php', true);
    let severity = (document.getElementsByTagName("select")[0]).value;
    let json = JSON.stringify({'severity': severity});
    ajax.send(json);
}


function get_filtered_type() {
    let ajax = new XMLHttpRequest();

    ajax.onreadystatechange = function () {
        if (this.status === 200 && this.readyState === 4)  {
            let table = document.getElementsByTagName("table")[1];
            let oldTableBody = document.getElementsByTagName("tbody")[1];
            let newTableBody = document.createElement('tbody');

            let json = jsonParse(this.responseText);



            for (let i = 0; i < json.length; i++) {
                let report = json[i];
                let row = newTableBody.insertRow();

                Object.keys(report).forEach(function (key) {
                    let text;
                    let cell = row.insertCell();
                    text = report[key];
                    cell.appendChild(document.createTextNode(text));
                })
            }

            table.replaceChild(newTableBody, oldTableBody);
        }
    }
    ajax.open('POST', 'type.php', true);
    let type = document.getElementsByTagName("select")[1].value;
    let json = JSON.stringify({'type': type});
    // document.getElementById("demo").innerHTML = json
    ajax.send(json);
}


function jsonParse(text) {
    let json;
    try {
        json = JSON.parse(text);
    } catch (e) {
        return false;
    }
    return json;
}