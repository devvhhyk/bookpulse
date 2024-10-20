// 도서 검색 함수
function searchBooks() {
    const query = document.getElementById('search').value.trim();
    if (query) {
        window.location.href = `/books/search?query=${encodeURIComponent(query)}`;
    } else {
        alert("검색어를 입력해주세요.");
    }
}

document.addEventListener("DOMContentLoaded", function() {
    const token = localStorage.getItem('jwtToken');
    const isLoggedIn = token !== null;

    if (isLoggedIn) {
        // 토큰이 있으면 서버에 유효성 검증 요청
        $.ajax({
            url: '/member/validateToken',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({ token: token }),
            success: function(response) {
                if (response.valid) {
                    updateButtonDisplay(true);
                } else {
                    // 토큰이 유효하지 않으면 로그아웃 처리
                    localStorage.removeItem('jwtToken');
                    updateButtonDisplay(false);
                }
            },
            error: function() {
                // 서버 요청에 실패하면 로그아웃 처리
                localStorage.removeItem('jwtToken');
                updateButtonDisplay(false);
            }
        });
    } else {
        updateButtonDisplay(false);
    }

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
                        localStorage.removeItem('jwtToken'); // 로그아웃 시 토큰 제거
                        alert(response.message);
                        updateButtonDisplay(false);
                        window.location.href = '/';
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

// 로그인 여부에 따라 버튼 표시를 업데이트하는 함수
function updateButtonDisplay(isLoggedIn) {
    const loginButton = document.querySelector(".login-button");
    const logoutButton = document.querySelector(".logout-button");
    const mypageButton = document.querySelector(".mypage-button");

    if (isLoggedIn) {
        if (loginButton) loginButton.style.display = "none";
        if (logoutButton) logoutButton.style.display = "block";
        if (mypageButton) mypageButton.style.display = "block";
    } else {
        if (loginButton) loginButton.style.display = "block";
        if (logoutButton) logoutButton.style.display = "none";
        if (mypageButton) mypageButton.style.display = "none";
    }
}
