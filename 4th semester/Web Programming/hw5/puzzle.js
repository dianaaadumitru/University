window.onload = () => {
    const tableCells = document.getElementsByClassName("photo");

    let first = ""
    let second = ""
    let count = 0

    const check = function () {
        let result = ""
        let images = document.getElementsByTagName("img")
        for (let image of images) {
            result += image.getAttribute("id")[0] + ""
        }
        return result === "123456789"
    };

    const swap = function () {
        if (!first){
            first = this.getAttribute("id")[0]
            document.getElementById("first").innerHTML = document.getElementById(first).getElementsByTagName("img")[0].getAttribute("id")[0]
        }

        count++
        if (first && count > 1){
            second=this.getAttribute("id")[0]
            document.getElementById("second").innerHTML = document.getElementById(second).getElementsByTagName("img")[0].getAttribute("id")[0]

        }

        if (first && second && first != second){
            let first1 = document.getElementById(first);
            let second1 = document.getElementById(second);
            let x = first1.firstChild;
            let y = second1.firstChild;

            first1.appendChild(y);
            second1.appendChild(x);

            first = ""
            second = ""
            count = 0
            if (check()) {
                document.getElementById("win").innerText = "Well done!"
            }
        }
    };

    for (let i=0;i<tableCells.length;i++){
        tableCells[i].addEventListener('click', swap)
    }
}