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
        error: function(jqXHR, status, error) {  // 매개변수 수정
                console.error("에러 발생:", jqXHR.responseText); // 서버 응답 메시지를 로그에 출력
                alert("이메일 중복 체크 중 오류가 발생했습니다.");
            }
        });
}

function validateForm() {
    var name = document.getElementById("name").value;
    var password = document.getElementById("password").value;
    var passwordConfirm = document.getElementById("passwordConfirm").value;
    var email = document.getElementById("email").value;

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

    if (!emailSuccess) {
        alert("이메일 중복 체크를 완료해주세요.");
        return false;
    }

    return true;
}
