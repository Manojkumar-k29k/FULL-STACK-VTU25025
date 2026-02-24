// --- 1. Keyboard Movement Logic ---
const box = document.getElementById("box");
let posX = 100;
let posY = 100;
const moveStep = 20;

document.addEventListener("keydown", function (event) {
    // Update coordinates based on key pressed
    if (event.key === "ArrowUp") {
        posY -= moveStep;
    }
    else if (event.key === "ArrowDown") {
        posY += moveStep;
    }
    else if (event.key === "ArrowLeft") {
        posX -= moveStep;
    }
    else if (event.key === "ArrowRight") {
        posX += moveStep;
    }

    // Apply style updates to the DOM object
    box.style.left = posX + "px";
    box.style.top = posY + "px";
});
