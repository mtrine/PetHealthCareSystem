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

document.addEventListener('DOMContentLoaded', function () {
    const submitButton = document.getElementById('submitButton');
    const notificationMessage = document.getElementById('notificationMessage');

    submitButton.addEventListener('click', function () {
        // Simulate a form submission or any other logic
        let isSuccess = true; // Set this based on your logic
        let message = '';

        if (isSuccess) {
            message = 'Đổi mật khẩu thành công!';
            notificationMessage.className = 'notification-message success';
        } else {
            message = 'Có lỗi xảy ra. Vui lòng thử lại!';
            notificationMessage.className = 'notification-message error';
        }

        // Update and display the message
        notificationMessage.textContent = message;
        notificationMessage.style.display = 'block';

        // Optionally, hide the message after a few seconds
        setTimeout(function () {
            notificationMessage.style.display = 'none';
        }, 3000); // 3 seconds
    });
});

document.querySelector("#submitButton").addEventListener("click", async function (event) {
    event.preventDefault();
    let oldPass = document.querySelector("#old-password").value;
    let newPass = document.querySelector("#new-password").value;
    let confirmPass = document.querySelector("#validate-password").value;

    if (oldPass === "" || newPass === "" || confirmPass === "") {
        document.querySelector("#notificationMessage").textContent = "Vui lòng điền đầy đủ thông tin!";
        document.querySelector("#notificationMessage").style.display = "block";
    } else if (newPass !== confirmPass) {
        document.querySelector("#notificationMessage").textContent = "Mật khẩu mới không trùng khớp!";
        document.querySelector("#notificationMessage").style.display = "block";
    } else {
        document.querySelector("#notificationMessage").textContent = "Đang xử lý...";
        document.querySelector("#notificationMessage").style.display = "block";
            const response =await fetchWithToken(`${API_BASE_URL}/v1/users/update-my-info`, { // Thay thế URL bằng API của bạn
                method: 'PATCH',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    oldPassword: oldPass,
                    password: newPass,
                }),
            });

            const data = await response.result;

            if (data.code === 1000) {
                notificationMessage.textContent = "Đổi mật khẩu thành công!";
            } else {
                notificationMessage.textContent = "Đã xảy ra lỗi: " + data.message;
            }
        }
})