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