// document.addEventListener('DOMContentLoaded', function () {
//   const participantList = document.getElementById('participantList');

//   // 현재 페이지 URL에서 roomId 추출
//   const urlParams = new URLSearchParams(window.location.search);
//   const roomId = urlParams.get('roomId');

//   if (!roomId) {
//     participantList.innerHTML =
//       "<p class='text-red-500'>잘못된 접근입니다.</p>";
//     return;
//   }

//   // 방 ID 표시 (선택사항)
//   const title = document.querySelector('h2');
//   title.textContent = `매칭 대기방 - 방 ID: ${roomId}`;

//   // 서버에서 참가자 목록 불러오기 (예제 데이터)
//   function fetchParticipants() {
//     // 여기에서 서버 API로 변경 가능 (예: /api/rooms/{roomId}/participants)
//     setTimeout(() => {
//       const participants = ['사용자1', '사용자2', '사용자3', '사용자4']; // 가상의 데이터

//       if (participants.length === 0) {
//         participantList.innerHTML =
//           "<p class='text-gray-500'>현재 참가자가 없습니다.</p>";
//       } else {
//         participantList.innerHTML = participants
//           .map((name) => `<p class="p-2 border-b">${name}</p>`)
//           .join('');
//       }
//     }, 1000); // 1초 후 데이터 불러오기 (로딩 효과)
//   }

//   fetchParticipants();
// });
document.addEventListener('DOMContentLoaded', async function () {
  const participantList = document.getElementById('participantList');

  // ✅ 현재 페이지 URL에서 roomId 추출
  const urlParams = new URLSearchParams(window.location.search);
  const roomId = urlParams.get('roomId');

  if (!roomId) {
    participantList.innerHTML =
      "<p class='text-red-500'>잘못된 접근입니다.</p>";
    return;
  }

  // ✅ 방 ID 표시 (선택사항)
  const title = document.querySelector('h2');
  title.textContent = `매칭 대기방 - 방 ID: ${roomId}`;

  // ✅ 서버에서 참가자 목록 불러오기
  async function fetchParticipants() {
    try {
      const response = await fetch(`/api/room/participants?roomId=${roomId}`);
      const result = await response.json();

      if (result.success) {
        if (result.participants.length === 0) {
          participantList.innerHTML =
            "<p class='text-gray-500'>현재 참가자가 없습니다.</p>";
        } else {
          participantList.innerHTML = result.participants
            .map(
              (p) =>
                `<p class="p-2 border-b">${p.username} (ID: ${p.userId})</p>`
            )
            .join('');
        }
      } else {
        participantList.innerHTML = `<p class='text-red-500'>오류: ${result.message}</p>`;
      }
    } catch (error) {
      participantList.innerHTML =
        "<p class='text-red-500'>데이터를 불러오는 중 오류 발생</p>";
      console.error('참가자 목록 불러오기 실패:', error);
    }
  }

  fetchParticipants();
});
