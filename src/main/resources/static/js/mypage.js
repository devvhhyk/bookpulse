document.addEventListener("DOMContentLoaded", function() {
    // 로그아웃 버튼 클릭 시 처리
    const logoutButton = document.querySelector(".logout-button");
    if (logoutButton) {
        logoutButton.addEventListener("click", function(event) {
            event.preventDefault(); // 기본 링크 동작 막기

            $.ajax({
                url: '/member/logout',
                type: 'POST',
                success: function(response) {
                    if (response.success) {
                        localStorage.removeItem('jwtToken'); // 토큰 제거
                        alert(response.message);
                        window.location.href = '/'; // 메인 페이지로 리다이렉트
                    } else {
                        alert("로그아웃에 실패했습니다. 다시 시도해주세요.");
                    }
                },
                error: function() {
                    alert("로그아웃 중 문제가 발생했습니다.");
                }
            });
        });
    }
});
