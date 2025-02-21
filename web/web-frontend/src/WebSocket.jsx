const socket = new WebSocket("ws://172.16.12.242:8000/ws/notifications/");

socket.onmessage = function(event) {
    const data = JSON.parse(event.data);
    console.log("Notification received:", data.message);
};

socket.onopen = function() {
    console.log("WebSocket connection established");
};

socket.onclose = function() {
    console.log("WebSocket connection closed");
};
