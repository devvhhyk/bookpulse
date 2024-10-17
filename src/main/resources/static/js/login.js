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
        data: JSON.stringify({ email: email, password: password }),
        type: 'POST',
        contentType: 'application/json',
        dataType: 'json',
        success: function(result) {
            if (result.success) {
                alert(result.message);
                location.href = "/";
            } else {
                alert(result.message);
            }
        },
        error: function(jqXHR, status, error) {
            console.error("로그인 요청 오류:", error);
            alert("로그인 요청 중 문제가 발생했습니다.");
        }
    });
});
