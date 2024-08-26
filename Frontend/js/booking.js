document.addEventListener('DOMContentLoaded', async function () {
    const bookingContainer = document.querySelector('.booking-list-body');
    const data = await fetchWithToken(`${API_BASE_URL}/v1/appointments`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    });
    console.log(data);
    if (data.code == 1000) {
        const bookings = data.result;
        const currentDateTime = new Date();

        bookings.forEach(booking => {
            const appointmentDate = new Date(booking.appointmentDate);
            const [endHours, endMinutes] = booking.sessionResponse.endTime.split(':');
            appointmentDate.setHours(endHours, endMinutes);

            // Kiểm tra nếu giờ hiện tại đã qua giờ kết thúc của cuộc hẹn
            if (currentDateTime > appointmentDate) {
                return; // Bỏ qua cuộc hẹn này nếu đã qua giờ
            }

            const bookingItem = document.createElement('ul');
            bookingItem.classList.add('booking-item');

            // Kiểm tra nếu veterinarianName không có giá trị thì thêm class để đổi màu chữ
            const veterinarianName = booking.veterinarianName ? booking.veterinarianName : "Chưa có";
            const veterinarianClass = booking.veterinarianName ? '' : 'no-vet-name';

            bookingItem.innerHTML = `
                <li>${booking.id}</li>
                <li class="${veterinarianClass}">${veterinarianName}</li>
                <li>${booking.pet.id}</li>
                <li>${booking.appointmentDate}</li>
                <li>${booking.sessionResponse.startTime}-${booking.sessionResponse.endTime}</li>
                <li><a href="detailBooking.html?id=${booking.id}">Xem chi tiết</a></li>
            `;
            bookingContainer.appendChild(bookingItem);
        });
    } else {
        alert(data.message);
    }
});
