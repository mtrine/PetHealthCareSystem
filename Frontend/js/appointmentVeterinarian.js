document.addEventListener('DOMContentLoaded', async function () {
    const appointmentContainer = document.querySelector('.booking-list-body');

    const data = await fetchWithToken(`${API_BASE_URL}/v1/appointments/my-appointment-for-veterinarian`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    });

    if (data.code == 1000) {
        const appointments = data.result;
        const today = new Date(); // Lấy ngày hiện tại
        appointments.forEach(appointment => {
            const appointmentDate = new Date(appointment.appointmentDate); // Ngày hẹn

            // Bỏ qua các lịch hẹn có trạng thái 'CANCELLED', 'SUCCESS' hoặc ngày đã qua
            if (appointment.status === 'CANCELLED' || 
                appointment.status === 'SUCCESS' || 
                appointmentDate < today) return;

            const appointmentItem = document.createElement('ul');
            appointmentItem.classList.add('booking-item');
            appointmentItem.innerHTML = `
                <li><input type="checkbox" name="select-item"></li>
                <li>${appointment.appointmentDate}</li>
                <li>${appointment.sessionResponse.startTime}-${appointment.sessionResponse.endTime}</li>
                <li>${appointment.pet.id}</li>
                <li>${appointment.pet.customerResponse.id}</li>
                <li>${appointment.servicesResponsesList[0].name}</li>
            `;
            appointmentContainer.appendChild(appointmentItem);
        });
    }
});
