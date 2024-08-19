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

// Hiển thị modal
function showModal() {
    document.getElementById('cancel-modal').style.display = 'flex';
}

// Ẩn modal
function hideModal() {
    document.getElementById('cancel-modal').style.display = 'none';
}
document.addEventListener('DOMContentLoaded', async function () {
    const appointmentContainer = document.querySelector('.appointment-list-section');
    const data = await fetchWithToken(`${API_BASE_URL}/v1/appointments/get-my-appointment-for-customer`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        },
    });
    if (data && data.code === 1000) {
        const appointments= data.result;
       
        appointments.forEach(appointment => {
            const appointmentItem = document.createElement('ul');
            appointmentItem.classList.add('appointment-item');
            var vetName = appointment.veterinarianName;
            if(!vetName){
                vetName = 'Chưa có';
            }
            appointmentItem.innerHTML = `
                <li>${appointment.appointmentDate}</li>
                    <li>${vetName}</li>
                    <li>${appointment.petName}</li>
                    <li>${appointment.serviceName[0]}</li>
                    <li>${appointment.startTime}-${appointment.endTime}</li>
                    <li>Đã chuyển khoảng <div class="cancel" onclick="showModal()"><i class='bx bx-x-circle'></i></div>
                        <div class="cancel-hover">Hủy lịch hẹn</div>
                    </li>
            `;

            appointmentContainer.appendChild(appointmentItem);
        });
        
    } else {
        alert('Đã xảy ra lỗi: ' + data.message);
    }
})
