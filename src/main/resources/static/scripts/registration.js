const apiUrl = "http://localhost:8080/auth/register";

document.addEventListener("DOMContentLoaded", async () => {
    document.getElementById("log_btn").addEventListener("click", function(e){
        window.location.href = "/auth/login";
    });
    document.getElementById("regBtn").addEventListener("click", register);
});

function validateData(username, email, password){
    if(!(document.getElementById("passwordRepeat").value == password)){
        showError("Paswords aren't equal!");
        return false;
    }
    if(username.length < 3 || username.length > 30){
        showError("Invalid username (must be 3-30 characters long)");
        return false;
    }
    if(password.length < 6 || password.length > 30){
        showError("Invalid password (must be 3-30 characters long)");
        return false;
    }
    if(email.length < 5 || email.length > 50){
        showError("Invalid email (must be 5-50 characters long)");
        return false;
    }
    return true;
}

async function register() {
    try{
        let username = document.getElementById("username").value;
        let email = document.getElementById("email").value;
        let password = document.getElementById("password").value;
    
        if(!validateData(username, email, password)){
            return null;
        }
    
        const response = await fetch(apiUrl, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    username: username,
                    email: email,
                    password: password
                })
            });

        document.getElementById("username").value = "";
        document.getElementById("email").value = "";
        document.getElementById("password").value = "";
        document.getElementById("passwordRepeat").value = "";

        if(!response.ok){
            showError("Registration failed " + await response.text());
            return null;
        }
        showSuccess("Registration successful!");

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