// /src/main/webapp/assets/chat.js
document.addEventListener('DOMContentLoaded', () => {
  const chatForm = document.getElementById('chatForm');
  if (chatForm) {
    chatForm.addEventListener('submit', async (event) => {
      event.preventDefault();
      const message = document.getElementById('message').value;
      const response = await fetch('/api/chat', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ message }),
      });
      const result = await response.json();
      if (result.success) {
        const chatBox = document.getElementById('chatBox');
        const newMessage = document.createElement('p');
        newMessage.textContent = `익명: ${message}`;
        chatBox.appendChild(newMessage);
        document.getElementById('message').value = '';
      } else {
        alert('메시지 전송 실패!');
      }
    });
  }
});
