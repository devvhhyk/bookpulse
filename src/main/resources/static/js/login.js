document.getElementById('loginForm').addEventListener('submit', function(event) {
    const email = document.getElementById('email').value.trim();
    const password = document.getElementById('password').value.trim();

    if (email === '') {
        alert('이메일을 입력하세요');
        event.preventDefault();
    } else if (password === '') {
        alert('비밀번호를 입력하세요');
        event.preventDefault();
    }
});
