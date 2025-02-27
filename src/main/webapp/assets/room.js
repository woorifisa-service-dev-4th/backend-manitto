// /src/main/webapp/assets/room.js
document.addEventListener('DOMContentLoaded', async () => {
  const errorMessage = document.getElementById('errorMessage');

  // ✅ 방 생성 기능
  const createRoomForm = document.getElementById('createRoomForm');
  if (createRoomForm) {
    createRoomForm.addEventListener('submit', async (event) => {
      event.preventDefault();
      const response = await fetch('/api/room/create', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
      });
      const result = await response.json();
      if (result.success) {
        alert('방이 생성되었습니다! 초대 코드: ' + result.inviteCode);
      } else {
        errorMessage.innerText = '방 생성 실패!';
      }
    });
  }

  // ✅ 방 참가 기능 (roomId 포함하여 이동)
  const joinRoomForm = document.getElementById('joinRoomForm');
  if (joinRoomForm) {
    joinRoomForm.addEventListener('submit', async (event) => {
      event.preventDefault();
      const roomCode = document.getElementById('roomCode').value;

      const response = await fetch('/api/room/join', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ inviteCode: roomCode }),
      });

      const result = await response.json();
      if (result.success) {
        // ✅ 방 참가 성공 시 해당 roomId로 waiting-room.jsp 이동
        window.location.href = `waiting-room?roomId=${result.roomId}`;
      } else {
        errorMessage.innerText = result.message;
      }
    });
  }

  // ✅ 현재 로그인한 사용자의 참가한 방 목록 불러오기 (클릭하면 대기방으로 이동)
  try {
    const response = await fetch('/api/user/rooms', { method: 'GET' });
    const result = await response.json();

    if (result.success) {
      const roomList = document.getElementById('roomList');
      if (roomList) {
        roomList.innerHTML = ''; // 기존 목록 초기화

        if (result.rooms.length === 0) {
          roomList.innerHTML = '<p>참가한 방이 없습니다.</p>';
        } else {
          result.rooms.forEach((room) => {
            const roomItem = document.createElement('li');
            roomItem.innerHTML = `<a href="waiting-room.jsp?roomId=${room.roomId}" class="text-blue-500 underline">방 ID: ${room.roomId}</a>`;
            roomList.appendChild(roomItem);
          });
        }
      }
    } else {
      console.error('방 목록 불러오기 실패:', result.message);
    }
  } catch (error) {
    console.error('API 요청 중 오류 발생:', error);
  }
});
