const tasksApiUrl = "http://localhost:8080/api/tasks";
const authApiUrl = "http://localhost:8080/auth/login";

function getToken() {
    return localStorage.getItem("token");
}

function setToken(token) {
    localStorage.setItem("token", token);
}

function logout() {
    localStorage.removeItem("token");
    window.location.href = "/auth/login";
}

async function apiFetch(url, options = {}) {
    const token = getToken();

    const response = await fetch(url, {
        ...options,
        headers: {
            "Content-Type": "application/json",
            ...(token ? { "Authorization": "Bearer " + token } : {}),
            ...(options.headers || {})
        }
    });

    if (response.status === 401 || response.status === 403) {
        window.location.href = "/auth/login";
        return null;
    }

    return response;
}

function isEmpty(input){
    return input.trim() === "";
}

function validTitle(title){
    return title.length <= 100;
}

function validDescription(description){
    return description.length <= 1000;
}

function getNewTaskData(){
    const title = document.getElementById("taskTitle").value;
    const description = document.getElementById("taskDescription").value;

    if(isEmpty(title)){
        showError("Empty task name!");
        return false;
    }

    if(!validTitle(title)){
        showError("Title can not be longer than 100 symbols!");
        return false;
    }

    if(!validDescription(description)){
        showError("Description can not be longer than 1000 symbols!");
        return false;
    }

    document.getElementById("taskTitle").value = "";
    document.getElementById("taskDescription").value = "";

    return {
        title,
        taskDescription: description
    };
}

async function addTask(){
    const newTaskData = getNewTaskData();
    if(!newTaskData) return;

    const response = await apiFetch(tasksApiUrl, {
        method: "POST",
        body: JSON.stringify(newTaskData)
    });

    if(!response || !response.ok){
        showError("Failed to save task!");
        return;
    }

    await showTasks();
}

async function getAllTasks(){
    const response = await apiFetch(tasksApiUrl);

    if(!response || !response.ok){
        showError("Failed to get your tasks!");
        return;
    }

    return await response.json();
}

async function markComplete(id){
    const response = await apiFetch(`${tasksApiUrl}/${id}/complete`, {
        method: "PUT"
    });

    if(!response || !response.ok){
        showError("Failed to mark task as completed!");
        return;
    }

    await showTasks();
}

async function deleteTask(id){
    const response = await apiFetch(`${tasksApiUrl}/${id}`, {
        method: "DELETE"
    });

    if(!response || !response.ok){
        showError("Failed to delete task!");
        return;
    }

    await showTasks();
}

async function updateTask(){
    const title = document.getElementById("updateTaskTitle").value;
    const description = document.getElementById("updateTaskDescription").value;

    if(isEmpty(title)){
        showError("Empty task name!");
        return;
    }

    if(!validTitle(title)){
        showError("Title can not be longer than 100 symbols!");
        return;
    }

    if(!validDescription(description)){
        showError("Description can not be longer than 1000 symbols!");
        return;
    }

    const response = await apiFetch(`${tasksApiUrl}/${currentTaskId}`, {
        method: "PUT",
        body: JSON.stringify({
            title,
            taskDescription: description
        })
    });

    if(!response || !response.ok){
        closeUpdateModal();
        showError("Failed to update task!");
        return;
    }

    closeUpdateModal();
    await showTasks();
}

function createHtmlTaskCard(taskData) {
    return `
        <div class="task ${taskData.completeStatus ? "completed" : ""}" data-id="${taskData.id}">
            <div class="task-info">
                <h3>${taskData.title}</h3>
                <p>${taskData.description}</p>
                <p>${taskData.creationDate}</p>
            </div>

            <div class="task-actions">
                ${taskData.completeStatus ? '' : `
                    <button class="done">✓</button>
                `}

                <button class="delete">✕</button>

                ${taskData.completeStatus ? '' : `
                    <button class="edit" onclick='openUpdateModal(${JSON.stringify(taskData)})'>✎</button>
                `}
            </div>
        </div>
    `;
}

function bindTaskEvents() {
    document.querySelectorAll(".task").forEach(task => {
        const id = task.dataset.id;

        const doneBtn = task.querySelector(".done");
        const deleteBtn = task.querySelector(".delete");
        const editBtn = task.querySelector(".edit");

        if (doneBtn) {
            doneBtn.onclick = () => markComplete(id);
        }

        deleteBtn.onclick = () => deleteTask(id);

        if (editBtn) {
            editBtn.onclick = () => {
                const taskData = {
                    id,
                    title: task.querySelector("h3").innerText,
                    description: task.querySelector("p").innerText
                };
                openUpdateModal(taskData);
            };
        }
    });
}

async function showTasks(){
    const tasks = await getAllTasks();
    if(!tasks) return;

    const container = document.getElementById("task-list");
    container.innerHTML = "";

    tasks.forEach(task => {
        container.innerHTML += createHtmlTaskCard(task);
    });

    bindTaskEvents();
}

function showError(message) {
    document.getElementById("errorMessage").innerText = message;
    document.getElementById("errorModal").classList.remove("hidden");
}

function closeError() {
    document.getElementById("errorModal").classList.add("hidden");
}

let currentTaskId;

function openUpdateModal(task) {
    currentTaskId = task.id;

    document.getElementById("updateTaskTitle").value = task.title;
    document.getElementById("updateTaskDescription").value = task.description;

    document.getElementById("updateModal").classList.remove("hidden");
}

function closeUpdateModal() {
    document.getElementById("updateModal").classList.add("hidden");
}

function eventListener(){
    document.getElementById("addBtn").addEventListener("click", addTask);
    document.getElementById("saveUpdateBtn").addEventListener("click", updateTask);

    document.getElementById("authBtn").addEventListener("click", function(){
        logout();
    });
}


document.addEventListener("DOMContentLoaded", async () => {
    eventListener();
    await showTasks();

    document.getElementById("errorModal").addEventListener("click", function(e) {
        if (e.target === this) closeError();
    });

    document.getElementById("updateModal").addEventListener("click", function(e){
        if (e.target === this) closeUpdateModal();
    });
});