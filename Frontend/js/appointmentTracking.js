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

// Thao tác ấn vào pet-item
document.addEventListener("DOMContentLoaded", function () {
    // Handle pet-item clicks
    document.querySelectorAll('.history-item').forEach(function (item) {
        item.addEventListener('click', function (event) {
            window.location.href = 'AppointmentDetails.html';
        });
    });
});

// Hiển thị modal và gán appointmentId vào modal
function showModal(appointmentId,textModel,price) {
    document.getElementById('cancel-modal').style.display = 'flex';
    document.querySelector('.modal-content').querySelector('p').innerHTML=textModel;
    // Gán appointmentId vào một phần tử ẩn trong modal
    document.getElementById('modal-appointment-id').value = appointmentId;
    document.getElementById('modal-appointment-price').value =price;
}

// Ẩn modal
function hideModal() {
    document.getElementById('cancel-modal').style.display = 'none';
}

document.addEventListener('DOMContentLoaded', async function () {
    const appointmentContainer = document.querySelector('.appointment-list-section');
    const data = await fetchWithToken(`${API_BASE_URL}/v1/appointments/my-appointment-for-customer`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        },
    });
    
    if (data && data.code === 1000) {
        const appointments = data.result;
        console.log(appointments);
        appointments.forEach(appointment => {
            const appointmentItem = document.createElement('ul');
            appointmentItem.classList.add('appointment-item');
            var vetName = appointment.veterinarianName;
            var status = "";
            let cancelHtml = ""; // Biến lưu trữ HTML cho phần hủy lịch hẹn
            var textModel=""
            var today=new Date();
            var appointmentDate=new Date(appointment.appointmentDate);
            var numberDay=today.getDate()-appointmentDate.getDate();
            if (appointment.status === 'processing') {
                status = "Đang xử lý";
            } else if (appointment.status === 'Paid') {
                status = "Đã thanh toán";
                if(numberDay<=2){
                    textModel="Bạn sẽ được hoàn 100% tiền cọc"
                }
                else if(numberDay>2 && numberDay<=6){
                    textModel="Bạn sẽ được hoàn 75% tiền cọc"
                }
                else{
                    textModel="Bạn sẽ không được hoàn"
                }
                if(numberDay<7){
                    cancelHtml = `<div class="cancel" onclick="showModal('${appointment.id}','${textModel}','${appointment.deposit}')"><i class='bx bx-x-circle'></i></div>
                        <div class="cancel-hover">Hủy lịch hẹn</div>`;
                }
            } else if (appointment.status === 'Cancelled') {
                status = "Đã hủy";
            } else if (appointment.status === 'Success') {
                status = "Thành công";
            } else if (appointment.status === 'REVIEWED') {
                status = "Đã đánh giá";
            } 
            else if (appointment.status === 'Examined') {
                status = "Đã khám";
            }
            else {
                status = "Thất bại";
            }

            if (!vetName) {
                vetName = 'Chưa có';
            }

            appointmentItem.innerHTML = `
                <li>${appointment.appointmentDate}</li>
                <li>${vetName}</li>
                <li>${appointment.pet.name}</li>
                <li>${appointment.servicesResponsesList[0].name}</li>
                <li>${appointment.sessionResponse.startTime}-${appointment.sessionResponse.endTime}</li>
                <li>${status} ${cancelHtml}</li>
            `;

            appointmentContainer.appendChild(appointmentItem);
        });
    } else {
        alert('Đã xảy ra lỗi: ' + data.message);
    }
});

document.getElementById("confirm-cancel").addEventListener('click', async function () {
    const appointmentId = document.getElementById('modal-appointment-id').value;
    const data = await fetchWithToken(`${API_BASE_URL}/v1/payments/refund`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            appointmentId: appointmentId,
        })
    })

    if (data && data.code === 1000) {
        alert('Hủy lịch hẹn thành công');
        window.location.reload();
    } else {
        alert('Đã xảy ra lỗi: ' + data.message);
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