document.addEventListener("DOMContentLoaded", () => {
    // 로그인 처리
    const loginForm = document.getElementById("loginForm");
    if (loginForm) {
        loginForm.addEventListener("submit", async (event) => {
            event.preventDefault();
            const email = document.getElementById("email").value;
            const password = document.getElementById("password").value;

            const response = await fetch("/api/login", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ email, password })
            });

            const result = await response.json();

            if (result.success) {
                window.location.href = "room.html";
            } else {
                document.getElementById("errorMessage").innerText = result.message;
            }
        });
    }

    // 회원가입 처리
    const signupForm = document.getElementById("signupForm");
    if (signupForm) {
        signupForm.addEventListener("submit", async (event) => {
            event.preventDefault();
            const username = document.getElementById("username").value;
            const email = document.getElementById("email").value;
            const password = document.getElementById("password").value;

            const response = await fetch("/api/signup", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ username, email, password })
            });

            const result = await response.json();

            if (result.success) {
                window.location.href = "login.html";
            } else {
                document.getElementById("errorMessage").innerText = result.message;
            }
        });
    }

    // 방 생성 처리
    const createRoomForm = document.getElementById("createRoomForm");
    if (createRoomForm) {
        createRoomForm.addEventListener("submit", async (event) => {
            event.preventDefault();

            const response = await fetch("/api/room/create", { // 🔹 URL 변경
                method: "POST",
                headers: { "Content-Type": "application/json" }
            });

            const result = await response.json();

            if (result.success) {
                alert("방이 생성되었습니다! 초대 코드: " + result.inviteCode);
            } else {
                document.getElementById("errorMessage").innerText = "방 생성 실패!";
            }
        });
    }

    // 방 참가 처리
    const joinRoomForm = document.getElementById("joinRoomForm");
    if (joinRoomForm) {
        joinRoomForm.addEventListener("submit", async (event) => {
            event.preventDefault();
            const roomCode = document.getElementById("roomCode").value;

            const response = await fetch("/api/room/join", { // 🔹 URL 변경
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ inviteCode: roomCode })
            });

            const result = await response.json();

            if (result.success) {
                window.location.href = "chat.html";
            } else {
                document.getElementById("errorMessage").innerText = result.message;
            }
        });
    }



    // 채팅 메시지 전송
    const chatForm = document.getElementById("chatForm");
    if (chatForm) {
        chatForm.addEventListener("submit", async (event) => {
            event.preventDefault();
            const message = document.getElementById("message").value;

            // 서버로 메시지 전송
            const response = await fetch("/api/chat", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ message })
            });

            const result = await response.json();

            if (result.success) {
                const chatBox = document.getElementById("chatBox");
                const newMessage = document.createElement("p");
                newMessage.textContent = `익명: ${message}`;
                chatBox.appendChild(newMessage);
                document.getElementById("message").value = "";
            } else {
                alert("메시지 전송 실패!");
            }
        });
    }
});
