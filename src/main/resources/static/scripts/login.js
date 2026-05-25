const apiUrl = "http://localhost:8080/auth/login";

document.addEventListener("DOMContentLoaded", async () => {
    document.getElementById("reg_btn").addEventListener("click", function(e){
            window.location.href = "/auth/register";
        });
    document.getElementById("loginBtn").addEventListener("click", login);
});

async function login() {

    try {
        let username = document.getElementById("username").value;
        let password = document.getElementById("password").value;

        if(username.length < 3){
            showError("Invalid username");
            return null;
        }

        if(password.length < 6){
            showError("Invalid password");
            return null;
        }

        const response = await fetch(apiUrl, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                "username": username,
                "password": password
            })
        });

        if(!response.ok){
            showError("Login failed!");
            return null;
        }
        showSuccess("Login successful!");

        setTimeout(() => {
            window.location.href = "/";
        }, 500);

    } catch(e){
        showError("Server error: " + e.message);
    }
}

function showError(message){
    const label = document.getElementById("label");
    label.className = "error-message";
    label.innerText = message;
}

function showSuccess(message){
    const label = document.getElementById("label");
    label.className = "success-message";
    label.innerText = message;
}