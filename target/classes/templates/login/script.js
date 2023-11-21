document.getElementById('login-form').addEventListener('submit', function (e) {
    e.preventDefault();

    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    // Thực hiện kiểm tra tên đăng nhập và mật khẩu
    if (username === 'admin' && password === 'password123') {
        document.getElementById('message').textContent = 'Đăng nhập thành công!';
    } else {
        document.getElementById('message').textContent = 'Sai tên đăng nhập hoặc mật khẩu. Vui lòng thử lại.';
    }
});
