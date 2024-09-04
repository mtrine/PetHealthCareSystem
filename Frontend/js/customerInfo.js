var actualPassword = 'abcdef'; // Replace this with the actual password value
// Bật subnav của customer
document.addEventListener('DOMContentLoaded', function () {
    const headerBtn = document.querySelector('.header-btn');
    const subNavLoggedIn = document.querySelector('.sub-nav.logged-in');

    headerBtn.addEventListener('click', function () {
        // Toggle hiển thị sub-nav
        subNavLoggedIn.style.display = (subNavLoggedIn.style.display === 'block' ? 'none' : 'block');
    });

    // Đóng sub-nav khi nhấp ra ngoài
    document.addEventListener('click', function (event) {
        if (!headerBtn.contains(event.target)) {
            subNavLoggedIn.style.display = 'none';
        }
    });
});

// Toggle password visibility
const togglePassword = document.querySelector('#toggle-password');
const passwordDisplay = document.querySelector('#customer-password');

if (togglePassword && passwordDisplay) {
    togglePassword.addEventListener('click', function () {
        const isHidden = passwordDisplay.textContent.includes('•');

        if (isHidden) {
            passwordDisplay.textContent = actualPassword;
            togglePassword.innerHTML = '<i class="fa-solid fa-eye"></i>';
        } else {
            passwordDisplay.textContent = '••••••••';
            togglePassword.innerHTML = '<i class="fa-solid fa-eye-slash"></i>';
        }
    });
}

function showEditInfo() {
    window.location.href = 'EditCustomer.html';
}

document.addEventListener('DOMContentLoaded', async () => {
    const customerInfo = document.querySelector('.detail-container');
    const customerEmail = customerInfo.querySelector('.customer-email');
    const customerPhone = customerInfo.querySelector('#customer-phone');
    const customerAddress = customerInfo.querySelector('.customer-address');
    const customerName = customerInfo.querySelector('#customer-name');
   
    const customerGender = customerInfo.querySelector

    const data = await fetchWithToken(`${API_BASE_URL}/v1/users/my-info`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        },
    });
  
    if (data && data.code === 1000) {
        const customer = data.result;
        customerName.textContent = customer.name;
        customerEmail.textContent = customer.email;
        customerPhone.textContent = customer.phoneNumber;
        customerAddress.textContent = customer.address;
        if (customer.sex=true) {
            customerGender.textContent = 'Nam';
        }
        else{
            customerGender.textContent = 'Nữ';
        }
        customerAddress.textContent = customer.address;
        actualPassword=customer.password;
    }else {
            alert('Đã xảy ra lỗi: ' + data.message);
    }
})

function navigate(url) {
        

    if (authToken) {
        window.location.href = url;
    } else {
        showModal();
    }
}

document.querySelector("#logout").addEventListener("click", async function () {
    try {
        const response = await fetch(`${API_BASE_URL}/v1/auth/logout`, { // Thay thế URL bằng API của bạn
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
               token:authToken
            }),
        });

        if (!response.ok) {
            throw new Error('Đăng xuất thất bại');
        }

        const data = await response.json();

        // Kiểm tra mã phản hồi
        if (data.code === 1000) {
            // Xử lý dữ liệu nhận được từ API
            console.log('Đăng xuất thành công');

            // Lưu token vào localStorage và chuyển hướng người dùng
           localStorage.removeItem('authToken');
            window.location.href = 'index.html'; // Chuyển hướng đến trang sau khi đăng nhập thành công
        } else {
            throw new Error(data.message || 'Đăng xuất thất bại');
        }

    } catch (error) {
        console.error('Lỗi:', error);
        alert('Đăng xuất thất bại');
    }
})