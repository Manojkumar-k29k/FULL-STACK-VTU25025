// --- 2. Mouse Quadrant Logic ---
document.addEventListener("mousemove", function (event) {
    const screenWidth = window.innerWidth;
    const screenHeight = window.innerHeight;

    const x = event.clientX;
    const y = event.clientY;

    // Segmenting the screen into quadrants
    if (x < screenWidth / 2 && y < screenHeight / 2) {
        // Top Left
        document.body.style.backgroundColor = "#ADD8E6"; // Light Blue
    }
    else if (x >= screenWidth / 2 && y < screenHeight / 2) {
        // Top Right
        document.body.style.backgroundColor = "#90EE90"; // Light Green
    }
    else if (x < screenWidth / 2 && y >= screenHeight / 2) {
        // Bottom Left
        document.body.style.backgroundColor = "#F08080"; // Light Coral
    }
    else {
        // Bottom Right
        document.body.style.backgroundColor = "#FFFFE0"; // Light Yellow
    }
});
