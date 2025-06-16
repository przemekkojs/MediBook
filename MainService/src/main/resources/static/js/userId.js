let doctorToken = "";
let clientToken = "";

function getCookie(name) {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);

    if (parts.length === 2) {
        return parts.pop().split(';').shift();
    }

    return null;
}

const doctorCookieKey = "access_token_doctor";
const clientCookieKey = "access_token_client";

doctorToken = getCookie(doctorCookieKey);
clientToken = getCookie(clientCookieKey);

// To trzeba wywalić na produkcję
if (clientToken != null) {
    console.log(clientToken);
}
else if (doctorToken != null) {
    console.log(doctorToken);
}
else {
    console.log("Nie ma ciasteczka ani dla doktora ani dla klienta");
    window.location.href = "https://medibook.pl/login";
}