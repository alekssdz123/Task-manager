const tasksApiUrl = "http://localhost:8080/api/tasks";
const userId = "rampampam";


function isEmpty(input){
    if(input.trim() === ""){
        return true;
    } return false;
}

function getNewTaskData(){
    const title = document.getElementById("taskTitle").value;
    if(isEmpty(title)){
        return false;
    }
    const description = document.getElementById("taskDescription").value;
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
    if(newTaskData === false){
        alert("Empty task name!");
        return;
    }
    const response = await fetch(tasksApiUrl, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            "userId": userId,
            "title": newTaskData.title,
            "taskDescription": newTaskData.taskDescription
        })
    });
    const data = await response.json();
    console.log(data);
    showTasks();
}

async function getAllTasks(){   
    const response = await fetch(tasksApiUrl + "?userId=" + userId);
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
                <button class="done" data-id="${taskData.id}" onclick="markComplete(this.dataset.id)">✓</button>
                <button class="delete" data-id="${taskData.id}" onclick="deleteTask(this.dataset.id)">✕</button>
            </div>
        </div>
    `;
}

async function showTasks(){
    const tasks = await getAllTasks();

    const container = document.getElementById("task-list");
    container.innerHTML = "";

    tasks.forEach(task => {
        const htmlCard = createHtmlTaskCard(task);
        container.innerHTML += htmlCard;
    });
}

function eventListener(){
    document.getElementById("addBtn").addEventListener("click", addTask);
}

document.addEventListener("DOMContentLoaded", () => {
    eventListener();
    showTasks();
})

async function markComplete(id){
    try{
        const response = await fetch( tasksApiUrl + "/" + id + "/complete", {method: "PUT"})
        showTasks();
    } catch(e){

    }
}