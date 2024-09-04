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

document.addEventListener('DOMContentLoaded', async function () {
    const appointmentContainer = document.querySelector('.history-container');
    const data = await fetchWithToken(`${API_BASE_URL}/v1/appointments/my-appointment-for-customer`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        },
    });
    if(data.code==1000){
        const appointments = data.result;
        appointments.forEach(appointment => {
            const appointmentItem = document.createElement('ul');
            appointmentItem.classList.add('history-item');
            if(appointment.status == 'REVIEWED'||appointment.status!="Success") return;
            appointmentItem.innerHTML = `
               <li>${appointment.appointmentDate}</li>
                <li>${appointment.veterinarianName}</li>
                    <li>${appointment.pet.name}</li>
                    <li>${appointment.servicesResponsesList[0].name}</li>
                    <li>${appointment.sessionResponse.startTime}-${appointment.sessionResponse.endTime}</li>
            `;
            appointmentContainer.appendChild(appointmentItem);
            document.querySelectorAll('.history-item').forEach(function (item) {
                item.addEventListener('click', function (event) {
                    window.location.href = 'AppointmentDetails.html?id=' + appointment.id;
                });
            });
        })
    }
});

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