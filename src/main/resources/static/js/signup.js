let emailSuccess = false;

function emailCheck() {
    let email = document.getElementById("email").value;
    let emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

    if (email.trim() === '') {
        alert("이메일을 입력하세요");
        return;
    }

    if (!emailPattern.test(email)) {
        alert("올바른 이메일 형식을 입력하세요");
        return;
    }

    $.ajax({
        url: '/member/checkEmail',
        data: { email: email },
        type: 'GET',
        dataType: 'json',
        success: function(result) {
            if (!result) {
                alert("사용 가능한 이메일입니다");
                emailSuccess = true;
            } else {
                alert("사용 중인 이메일입니다");
                document.getElementById("email").value = '';
                emailSuccess = false;
            }
        },
        error: function(jqXHR, status, error) {
            console.error("에러 발생:", jqXHR.responseText);
            alert("이메일 중복 체크 중 오류가 발생했습니다.");
        }
    });
}

document.addEventListener("DOMContentLoaded", function() {
    document.getElementById('signupForm').addEventListener('submit', function(event) {
        event.preventDefault();  // 폼의 기본 제출을 막음

        // 유효성 검사
        if (!validateForm()) {
            return;  // 유효성 검사가 실패하면 종료
        }

        // 폼 데이터 수집
        var name = document.getElementById("name").value;
        var email = document.getElementById("email").value;
        var password = document.getElementById("password").value;
        var phoneNumber = document.getElementById("phoneNumber").value;

        // AJAX 요청을 통해 서버에 데이터 전송
        $.ajax({
            url: '/member/signup',
            type: 'POST',
            contentType: 'application/json',  // JSON 데이터 전송
            data: JSON.stringify({
                name: name,
                email: email,
                password: password,
                phoneNumber: phoneNumber
            }),
            success: function(response) {
                if (response.success) {
                    alert(response.message);  // 성공 시 메시지 출력
                    window.location.href = '/member/login';  // 로그인 페이지로 이동
                } else {
                    alert(response.message);  // 실패 시 오류 메시지 출력
                }
            },
            error: function(xhr, status, error) {
                console.error("회원가입 요청 오류:", error);
                alert("회원가입 중 문제가 발생했습니다. 다시 시도해주세요.");
            }
        });
    });

    function validateForm() {
        var name = document.getElementById("name").value;
        var password = document.getElementById("password").value;
        var passwordConfirm = document.getElementById("passwordConfirm").value;
        var phoneNumber = document.getElementById("phoneNumber").value;

        if (name.trim() === '') {
            alert("이름을 입력하세요");
            return false;
        }

        if (password.trim() === '') {
            alert("비밀번호를 입력하세요");
            return false;
        }

        if (password.length < 8) {
            alert("비밀번호는 최소 8자 이상이어야 합니다");
            return false;
        }

        if (password !== passwordConfirm) {
            alert("비밀번호가 일치하지 않습니다");
            return false;
        }

        if (phoneNumber.trim() === '') {
            alert("휴대폰 번호를 입력하세요.");
            return false;
        }

        return true;
    }
});

