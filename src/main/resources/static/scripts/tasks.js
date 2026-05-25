const tasksApiUrl = "http://localhost:8080/api/tasks";

function isEmpty(input){
    if(input.trim() === ""){
        return true;
    } return false;
}
function validTitle(title){
    if(title.length > 100){
        return false;
    } return true;
}
function validDescription(description){
    if(description.length > 1000){
        return false;
    } return true;
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

    const data = {
        "title": title,
        "taskDescription": description
    }
    document.getElementById("taskTitle").value = "";
    document.getElementById("taskDescription").value = "";
    return data;
}

async function addTask(){
    const newTaskData = getNewTaskData();
    if(!newTaskData){
        return;
    }
    const response = await fetch(tasksApiUrl, {
        method: "POST",
        credentials: "include",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            "title": newTaskData.title,
            "taskDescription": newTaskData.taskDescription
        })
    });
    if(!response.ok){
        showError("Failed to save task!");
        return null;
    }
    const data = await response.json();
    console.log(data);
    showTasks();
}

async function getAllTasks(){   
    const response = await fetch(tasksApiUrl, {credentials: "include"});
    if(!response.ok){
        showError("Failed to get your tasks!");
        return;
    }
    const data = await response.json();
    return data;
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
                    <button class="done" data-id="${taskData.id}" onclick="markComplete(this.dataset.id)">✓</button>
                    `}
                    <button class="delete" data-id="${taskData.id}" onclick="deleteTask(this.dataset.id)">✕</button>
                ${taskData.completeStatus ? '' : `            
                    <button class="edit" onclick='openUpdateModal(${JSON.stringify(taskData)})'> ✎ </button>
                    `}
            </div>
        </div>
    `};

async function showTasks(){
    const tasks = await getAllTasks();
    if(tasks === null){
        return;
    }
    const container = document.getElementById("task-list");
    container.innerHTML = "";

    tasks.forEach(task => {
        const htmlCard = createHtmlTaskCard(task);
        container.innerHTML += htmlCard;
    });
}

function eventListener(){
    document.getElementById("addBtn").addEventListener("click", addTask);
    document.getElementById("saveUpdateBtn").addEventListener("click", updateTask);
    document.getElementById("authBtn").addEventListener("click", function(e){
        window.location.href = "/login";
    })
}

async function markComplete(id){
    const response = await fetch(tasksApiUrl + "/" + id + "/complete", {method: "PUT", credentials: "include"});
    if(!response.ok){
        showError("Failed to mark task as completed!");
    }
    await showTasks();
}

async function deleteTask(id){
    const response = await fetch(tasksApiUrl + "/" + id, {method: "DELETE", credentials: "include"});
    if(!response.ok){
        showError("Failed to delete task!");
    }
    await showTasks();
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

async function updateTask() {
    const title = document.getElementById("updateTaskTitle").value;
    const description = document.getElementById("updateTaskDescription").value;
    
    if (isEmpty(title)) {
        showError("Empty task name!");
        return;
    }
    
    if (!validTitle(title)) {
        showError("Title can not be longer than 100 symbols!");
        return;
    }
    
    if (!validDescription(description)) {
        showError("Description can not be longer than 1000 symbols!");
        return;
    }
    
    const response = await fetch(tasksApiUrl + "/" + currentTaskId, {
        method: "PUT",
        credentials: "include",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            "title": title,
            "taskDescription": description
        })
    });
    
    if (!response.ok) {
        closeUpdateModal();
        showError("Failed to update task!");
        return;
    }
    
    closeUpdateModal();
    await showTasks();
}


document.addEventListener("DOMContentLoaded", async () => {
    eventListener();
    await showTasks();
    
    document.getElementById("errorModal").addEventListener("click", function(e) {
    if (e.target === this) {
        closeError();
    }});

    document.getElementById("updateModal").addEventListener("click", function(e){
        if(e.target === this){
            closeUpdateModal();
        }
    });
});