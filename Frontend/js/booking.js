document.addEventListener('DOMContentLoaded', async function () {
    const bookingContainer = document.querySelector('.booking-list-body');
    const data = await fetchWithToken(`${API_BASE_URL}/v1/appointments`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    });

    if (data.code == 1000) {
        const bookings = data.result;
        const currentDate = new Date().setHours(0, 0, 0, 0); // Ngày hiện tại mà không xét giờ

        bookings.forEach(booking => {
            const appointmentDate = new Date(booking.appointmentDate).setHours(0, 0, 0, 0); // Ngày cuộc hẹn không xét giờ

            // Kiểm tra nếu ngày hiện tại đã qua ngày của cuộc hẹn
            if (currentDate > appointmentDate) {
                return; // Bỏ qua cuộc hẹn này nếu đã qua ngày
            }

            const bookingItem = document.createElement('ul');
            bookingItem.classList.add('booking-item');

            // Kiểm tra nếu veterinarianName không có giá trị thì thêm class để đổi màu chữ
            const veterinarianName = booking.veterinarianName ? booking.veterinarianName : "Chưa có";
            const veterinarianClass = booking.veterinarianName ? '' : 'no-vet-name';
            let status = "";

            // Xử lý trạng thái cuộc hẹn
            
            if (booking.status === 'Paid') {
                status = "Đã thanh toán";
            } else if (booking.status === 'Success') {
                status = "Thành công";
            
            }
            else if(booking.status === 'Examined'){
                status = "Đã khám";
            } 
            else {
                status = "Thất bại";
                return;
            }

            bookingItem.innerHTML = `
                <li>${booking.id}</li>
                <li class="${veterinarianClass}">${veterinarianName}</li>
                <li>${booking.pet.id}</li>
                <li>${booking.appointmentDate}</li>
                <li>${status}</li>
                <li>${booking.sessionResponse.startTime}-${booking.sessionResponse.endTime}</li>
                <li><a href="detailBooking.html?id=${booking.id}">Xem chi tiết</a></li>
            `;
            bookingContainer.appendChild(bookingItem);
        });
    } else {
        alert(data.message);
    }
});
