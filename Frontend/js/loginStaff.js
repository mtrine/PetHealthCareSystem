document.querySelector(".login-btn").addEventListener("click", async function () {
    const email = document.querySelector('#email').value;
    const password = document.querySelector('#password').value;
    try {
        const response = await fetch(`${API_BASE_URL}/v1/auth/login`, { // Thay thế URL bằng API của bạn
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                email: email,
                password: password,
            }),
        });

        if (!response.ok) {
            throw new Error('Đăng nhập thất bại');
        }

        const data = await response.json();

        // Kiểm tra mã phản hồi
        if (data.code === 1000) {


            if (data.result.userResponse.role === "CUSTOMER") {
                alert('Bạn là khách hàng không thể truy cập');
            }
            else if (data.result.userResponse.role === "STAFF") {
                localStorage.setItem('authToken', data.result.token);
                window.location.href = "index.html";
            }
            else if (data.result.userResponse.role === "ADMIN") {
                window.location.href = "indexAdmin.html";
            }
            else {
                localStorage.setItem('authToken', data.result.token);
                window.location.href = "/Doctor/index.html";
            }

        } else {
            throw new Error(data.message || 'Đăng nhập thất bại');
        }

    } catch (error) {
        console.error('Lỗi:', error);
        alert('Đăng nhập thất bại, vui lòng kiểm tra lại thông tin');
    }
});