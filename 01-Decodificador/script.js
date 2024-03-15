function displayMessage(message) {
  document.querySelector(".mensagem__sem-mensagem").style.display = "none";

  document.querySelector(".copiar-wrapper").style.display = "flex";
  
  const encryptedMessage = document.getElementById("mensagem-codificada");
  encryptedMessage.style.display = "block";
  encryptedMessage.value = message;
}

function criptografar() {
  const message = document.getElementById("mensagem");

  let encryptedMessage = "";

  for (const letra of message.value) {
    switch (letra) {
      case "a":
        encryptedMessage += "ai";
        break;

      case "e":
        encryptedMessage += "enter";
        break;

      case "i":
        encryptedMessage += "imes";
        break;

      case "o":
        encryptedMessage += "ober";
        break;

      case "u":
        encryptedMessage += "ufat";
        break;

      default:
        encryptedMessage += letra;
    }
  }

  displayMessage(encryptedMessage);
}

function descriptografar() {
  const message = document.getElementById("mensagem");

  let unencryptedMessage = message.value
    .replaceAll("ai", "a")
    .replaceAll("enter", "e")
    .replaceAll("imes", "i")
    .replaceAll("ober", "o")
    .replaceAll("ufat", "u");

  displayMessage(unencryptedMessage);
}

function copiarMensagem() {
  const encryptedMessage = document.getElementById("mensagem-codificada");

  navigator.clipboard.writeText(encryptedMessage.value);

  const notification = document.querySelector(".notificacao");
  notification.style.top = "20px";
  document.querySelector(".notificacao p").innerText = "Mensagem Copiada!";

  setTimeout(() => {
    notification.style.top = "-70px";
  }, 3 * 1000);
}
