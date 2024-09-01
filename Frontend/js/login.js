

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
                localStorage.setItem('authToken', data.result.token);
                if (data.result.userResponse.role === "CUSTOMER") {
                    window.location.href = "index.html";
                }
                else if (data.result.userResponse.role === "STAFF") {
                    window.location.href = "/Staff/index.html";
                }
                else if (data.result.userResponse.role === "ADMIN") {
                    window.location.href = "/Admin/index.html";
                }
                else {
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
});
