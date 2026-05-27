const apiUrl = "http://localhost:8080/auth/login";

document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("reg_btn").addEventListener("click", () => {
        window.location.href = "/auth/register";
    });

    document.getElementById("loginBtn").addEventListener("click", login);
});

async function login() {
    try {
        const username = document.getElementById("username").value;
        const password = document.getElementById("password").value;

        if (username.length < 3) {
            showError("Invalid username");
            return;
        }

        if (password.length < 6) {
            showError("Invalid password");
            return;
        }

        const response = await fetch(apiUrl, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ username, password })
        });

        if (!response.ok) {
            showError("Login failed!");
            return;
        }

        const token = await response.text();

        if (!token || token.length < 10) {
            showError("Invalid token from server");
            return;
        }

        localStorage.setItem("token", token);

        showSuccess("Login successful!");

        setTimeout(() => {
            window.location.replace("/");
        }, 300);

    } catch (e) {
        showError("Server error: " + e.message);
    }
}

function showError(message) {
    const label = document.getElementById("label");
    label.className = "error-message";
    label.innerText = message;
}

function showSuccess(message) {
    const label = document.getElementById("label");
    label.className = "success-message";
    label.innerText = message;
}