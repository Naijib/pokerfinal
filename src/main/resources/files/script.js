
choiceNumber = 7;

let socket = new WebSocket("ws://localhost:8080/chat");

function pokerChoice(number)
{
    choiceNumber = number;
    document.getElementById("p1").innerHTML = "" + choiceNumber;
    socket.send(choiceNumber);
    document.getElementById("button0").disabled = true;
    document.getElementById("button1").disabled = true;
    document.getElementById("button2").disabled = true;
    document.getElementById("button3").disabled = true;
    document.getElementById("button5").disabled = true;
    document.getElementById("button8").disabled = true;
}
function sendForm(){

    document.getElementById("button0").disabled = false;
    document.getElementById("button1").disabled = false;
    document.getElementById("button2").disabled = false;
    document.getElementById("button3").disabled = false;
    document.getElementById("button5").disabled = false;
    document.getElementById("button8").disabled = false;
    socket.onopen = function(e) {
        alert("[open] Connection established");

    };

    socket.onmessage = function(event) {
        alert(`[message] Data received from server: ${event.data}`);
    };

    socket.onclose = function(event) {
        if (event.wasClean) {
            alert(`[close] Connection closed cleanly, code=${event.code} reason=${event.reason}`);
        } else {
            alert('[close] Connection died');
        }
    };

    socket.onerror = function(error) {
        alert(`[error] ${error.message}`);
    };

}

function moyenne()
{
    socket.send("resultat");
}