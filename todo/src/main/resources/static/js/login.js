const signUpBtn = document.getElementById("signUp");
const signInBtn = document.getElementById("signIn");
const container = document.querySelector(".container");

signUpBtn.addEventListener("click", () => {
    container.classList.add("right-panel-active");
});
signInBtn.addEventListener("click", () => {
    container.classList.remove("right-panel-active");
});

$(document).ready(function() {
    $('#signUpForm').on("click", function (e) {
        e.preventDefault(); // 기본 폼 제출 방지

        var name = $('#name').val();
        var email = $('#email').val();
        var password = $('#password').val();

        if (!name || !email || !password) {
            alert("이름, 이메일, 패스워드 모두 입력해야 합니다.");
            return;
        }

        // AJAX 요청
        $.ajax({
            url: '/v1/user/signup',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                username: name,
                email: email,
                password: password
            }),
            success: function (res) {
                alert("회원가입에 성공헀습니다!");
            },
            error: function (xhr, status, error) {
                alert("회원가입 실패...! :" + xhr.responseText || error + status);
            }
        });
    });
});

$(document).ready(() => {
    $('#signInForm').on("click", (e) => {
        e.preventDefault();

        var email = $('#loginEmail').val();
        var password = $('#loginPassword').val();

        if (!email || !password) {
            alert("이메일 비밀번호를 모두 입력해야 합니다.")
        }

        $.ajax({
            url: 'v1/user/login',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                email: email,
                password: password
            }),
            success: function (res) {
                alert("회원가입에 성공헀습니다!");
            },
            error: function (xhr, status, error) {
                alert("회원가입 실패...! :" + xhr.responseText || error + status);
            }
        })
    })
})