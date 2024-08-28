

document.addEventListener('DOMContentLoaded', () => {
    const form = document.querySelector('form');
    form.addEventListener('submit', async (event) => {
        event.preventDefault(); // Ngăn không gửi form theo cách mặc định

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
                // Xử lý dữ liệu nhận được từ API
                console.log('Đăng nhập thành công:', data);

                // Lưu token vào localStorage và chuyển hướng người dùng
                localStorage.setItem('authToken', data.result.token);
                window.location.href = 'index.html'; // Chuyển hướng đến trang sau khi đăng nhập thành công
            } else {
                throw new Error(data.message || 'Đăng nhập thất bại');
            }

        } catch (error) {
            console.error('Lỗi:', error);
            alert('Đăng nhập thất bại, vui lòng kiểm tra lại thông tin');
        }
    });
});
