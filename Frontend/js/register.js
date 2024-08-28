
function showStep2() {
    document.getElementById('step1').style.display = 'none';
    document.getElementById('step2').style.display = 'flex';
}

document.getElementById('continue-btn').addEventListener('click', function(event) {
    event.preventDefault();
    showStep2();
});

document.getElementById('form-step2').addEventListener('submit', async function(event) {
    event.preventDefault();

    // Collect data from both steps
    const phone = document.getElementById('phone').value;
    const email = document.getElementById('email').value;
    const fullname = document.getElementById('fullname').value;
    const address = document.getElementById('address').value;
    const gender = document.querySelector('input[name="gender"]:checked').value;
    const password = document.getElementById('password').value;
    const confirmPassword = document.getElementById('confirm-password').value;

    // Validate password and confirm password
    if (password !== confirmPassword) {
        alert('Mật khẩu và Nhập lại mật khẩu không khớp!');
        return;
    }

    var sex = false;
    if (gender === "Nam") {
        sex = true;
    }

    const userData = {
        phoneNumber: phone,
        email: email,
        name: fullname,
        address: address,
        sex: sex,
        password: password,
        role:"CUSTOMER"
    };


    try {
        const response = await fetch(`${API_BASE_URL}/v1/auth/register`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(userData)
        });
        if (!response.ok) {
            throw new Error('Đăng ký thất bại');
        }

        const data = await response.json();

        if (data.code === 1000) {
            // Xử lý dữ liệu nhận được từ API
            console.log('Đăng ký thành công:', data);
            alert('Đăng ký thành công!');
            window.location.href = 'login.html'; // Chuyển hướng đến trang sau khi đăng nhập thành công
        } else {
            alert('Đăng ký thất bại: ' + data.message);
            throw new Error(data.message || 'Đăng nhập thất bại');
        }

    } catch (error) {
        console.error('Lỗi:', error);
        alert('Đăng nhập thất bại, vui lòng kiểm tra lại thông tin');
    }
    
});
