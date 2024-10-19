document.getElementById('loginForm').addEventListener('submit', function(event) {
    event.preventDefault();  // 폼의 기본 제출 동작을 막음

    const email = document.getElementById('email').value.trim();
    const password = document.getElementById('password').value.trim();

    if (email === '') {
        alert('이메일을 입력하세요');
        return;
    } else if (password === '') {
        alert('비밀번호를 입력하세요');
        return;
    }

    $.ajax({
        url: '/member/login',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({ email: email, password: password }),
        success: function(response) {
            if (response.success) {
                localStorage.setItem('jwtToken', response.token); // JWT 토큰 저장
                alert(response.message);
                window.location.href = '/';  // 메인 페이지로 이동
            } else {
                alert(response.message);
            }
        },
        error: function() {
            alert("로그인 중 문제가 발생했습니다.");
        }
    });
});
