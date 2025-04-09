const arraySizeSlider = document.getElementById("arraySize");
const arraySizeValue = document.getElementById("arraySizeValue");
const algorithmSelect = document.getElementById("algorithmSelect");
const visualizerContainer = document.getElementById("visualizers");

let array = [];
const selectedAlgorithms = new Set();
const canvasMap = {};
const startTimeMap = {};
const finishMap = {};

arraySizeSlider.oninput = () => {
    arraySizeValue.textContent = arraySizeSlider.value;
};

function generateRandomArray(size) {
    return Array.from({ length: size }, () => Math.floor(Math.random() * 300) + 10);
}

function drawArray(ctx, arr, highlights = []) {
    ctx.clearRect(0, 0, 400, 300);
    const width = 400 / arr.length;
    arr.forEach((value, i) => {
        ctx.fillStyle = highlights.includes(i) ? 'red' : 'black';
        ctx.fillRect(i * width, 300 - value, width - 1, value);
    });
}

function createVisualizer(algorithm, arr) {
    const wrapper = document.createElement("div");
    wrapper.className = "visualizer";
    wrapper.id = `visualizer-${algorithm}`;

    const title = document.createElement("div");
    title.textContent = algorithm;
    wrapper.appendChild(title);

    const canvas = document.createElement("canvas");
    canvas.width = 400;
    canvas.height = 300;
    wrapper.appendChild(canvas);

    const removeBtn = document.createElement("button");
    removeBtn.className = "remove-btn";
    removeBtn.textContent = "Remove from Race";
    removeBtn.onclick = () => {
        wrapper.remove();
        selectedAlgorithms.delete(algorithm);
        delete canvasMap[algorithm];
    };
    wrapper.appendChild(removeBtn);

    const ctx = canvas.getContext("2d");
    drawArray(ctx, arr, []);
    canvasMap[algorithm] = ctx;

    const timeDisplay = document.createElement("div");
    timeDisplay.id = `time-${algorithm}`;
    timeDisplay.style.marginTop = "6px";
    timeDisplay.style.fontSize = "14px";
    timeDisplay.textContent = "⏱ …";
    wrapper.appendChild(timeDisplay);

    visualizerContainer.appendChild(wrapper);
}

document.getElementById("randomize").onclick = () => {
    array = generateRandomArray(Number(arraySizeSlider.value));
    selectedAlgorithms.forEach(algo => {
        const ctx = canvasMap[algo];
        if (ctx) drawArray(ctx, array, []);
    });
};

document.getElementById("addToRace").onclick = () => {
    const selected = algorithmSelect.value;
    if (selectedAlgorithms.has(selected)) {
        alert("Algorithm already added to race.");
        return;
    }
    selectedAlgorithms.add(selected);
    createVisualizer(selected, array);
};

document.getElementById("startRace").onclick = () => {
    if (selectedAlgorithms.size === 0) {
        alert("Add at least one algorithm to race.");
        return;
    }

    const ws = new WebSocket("ws://localhost:8080/ws/sort");

    ws.onopen = () => {
        const now = performance.now();
        Array.from(selectedAlgorithms).forEach(algo => {
            startTimeMap[algo] = now;
        });

        ws.send(JSON.stringify({
            algorithms: Array.from(selectedAlgorithms),
            array: array
        }));
    };

    ws.onmessage = (event) => {
        const data = JSON.parse(event.data);

        if (data.done && data.algorithm) {
            const endTime = performance.now();
            const duration = endTime - startTimeMap[data.algorithm];
            finishMap[data.algorithm] = duration;

            const label = document.getElementById(`time-${data.algorithm}`);
            if (label) {
                label.textContent = `⏱ ${duration.toFixed(1)} ms`;
            }
            return;
        }

        const ctx = canvasMap[data.algorithm];
        if (ctx) {
            drawArray(ctx, data.step.arraySnapshot, data.step.highlightIndices);
        }
    };

    ws.onerror = () => alert("WebSocket error!");
};

array = generateRandomArray(Number(arraySizeSlider.value));