const tasksApiUrl = "http://localhost:8080/api/tasks";
const userId = "rampampam";


function getNewTaskData(){
    const title = document.getElementById("taskTitle").value;
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
    })
    const data = await response.json();
    console.log(data);
    showTasks();
}

async function getAllTasks(){   
    const response = await fetch(tasksApiUrl + "?userId=" + userId);
    const data = await response.json();
    return data;
}

function createHtmlTaskCard(taskData){
    const taskDiv = document.createElement("div");
    if(taskData.completeStatus == false){
        taskDiv.className = "task";
    } else{
        taskDiv.className = "task completed";
    }
    
    const taskInfo = document.createElement("div");
    const taskTitle = document.createElement("h3");
    const taskDescription = document.createElement("p");
    const creationDate = document.createElement("p");
    taskInfo.className = "task-info";
    taskTitle.textContent = taskData.title;
    taskDescription.textContent = taskData.description;
    creationDate.textContent = taskData.creationDate;
    
    taskInfo.appendChild(taskTitle);
    taskInfo.appendChild(taskDescription);
    taskInfo.appendChild(creationDate);
    
    const taskButtons = document.createElement("div");
    const doneBtn = document.createElement("button");
    const deleteBtn = document.createElement("button");
    doneBtn.textContent = "✓"
    deleteBtn.textContent = "✕"
    taskButtons.className = "task-actions";
    doneBtn.className = "done";
    deleteBtn.className = "delete";
    
    taskButtons.appendChild(doneBtn);
    taskButtons.appendChild(deleteBtn);
    
    taskDiv.appendChild(taskInfo);
    taskDiv.appendChild(taskButtons);
    
    return taskDiv;
}

async function showTasks(){
    const tasks = await getAllTasks();

    const container = document.getElementById("task-list");
    container.innerHTML = "";

    tasks.forEach(task => {
        const htmlCard = createHtmlTaskCard(task);
        container.appendChild(htmlCard);
    });
}

function eventListener(){
    document.getElementById("addBtn").addEventListener("click", addTask);
}

document.addEventListener("DOMContentLoaded", () => {
    eventListener();
    showTasks();
})