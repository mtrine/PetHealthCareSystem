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


document.addEventListener('DOMContentLoaded', async () => {
    const customerInfo = document.querySelector('.detail-container');
    const customerPhone = customerInfo.querySelector('#customer-phone');
    const customerAddress = customerInfo.querySelector('#customer-address');
    const customerName = customerInfo.querySelector('#customer-name');

    const data = await fetchWithToken(`${API_BASE_URL}/v1/users/my-info`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        },
    });
  
    if (data && data.code === 1000) {
        const customer = data.result;
        customerName.value = customer.name;
        customerPhone.value = customer.phoneNumber;
        customerAddress.value = customer.address;
        if (customer.sex=true) {
            document.querySelector(`input[name="gender"][value="nam"]`).checked = true;
        }
        else{
            document.querySelector(`input[name="gender"][value="nu"]`).checked = true;
        }
        customerAddress.value = customer.address;
        actualPassword=customer.password;
    }else {
            alert('Đã xảy ra lỗi: ' + data.message);
    }
})

document.querySelector("#submitButton").addEventListener("click", async function (event) {
    event.preventDefault();
    const customerInfo = document.querySelector('.detail-container');

    const customerPhone = customerInfo.querySelector('#customer-phone').value;
    const customerAddress = customerInfo.querySelector('#customer-address').value;
    const customerName = customerInfo.querySelector('#customer-name').value;
    var selectedGender = document.querySelector('input[name="gender"]:checked').value;

    if (customerName === ""  || customerPhone === "" || customerAddress === "") {
        document.querySelector("#notificationMessage").textContent = "Vui lòng điền đầy đủ thông tin!";
        document.querySelector("#notificationMessage").style.display = "block";
    }  
    else {
        document.querySelector("#notificationMessage").textContent = "Đang xử lý...";
        document.querySelector("#notificationMessage").style.display = "block";
        var sex= false
        if (selectedGender === 'nam') {
            selectedGender = true;
        }
        var user={
            name: customerName,
                    phoneNumber: customerPhone,
                    address: customerAddress,
                    sex: sex
        }
            const response =await fetchWithToken(`${API_BASE_URL}/v1/users/update-my-info`, { // Thay thế URL bằng API của bạn
                method: 'PATCH',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(user),
            });

            const data = await response.result;
            if (response.code === 1000) {
                notificationMessage.textContent = "Đổi thông tin thành công!";
            } else {
                notificationMessage.textContent = "Đã xảy ra lỗi: " + data.message;
            }
        }
})