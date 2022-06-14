
let developers = []
let projects = []

function push() {
    let name = document.getElementById("name").value
    let project = document.getElementById("project").value

    developers.push(name)
    projects.push(project)
}

function add() {
    let ajax = new XMLHttpRequest()
    ajax.onreadystatechange = function() {
        if (this.status === 200) {
            window.location.reload(true);
        }
    }

    ajax.open('POST', 'addToProject.php', true)

    let json = JSON.stringify({'developers': developers, 'projects': projects})
    console.log(json)
    ajax.send(json)
}