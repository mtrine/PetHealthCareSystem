var vaccine5 = ["c6328204-53aa-4dd5-b9c9-7c6e495fd16d", "e370cf05-56cd-4c43-84b2-68da784408ee", "a861595f-1b4a-4192-b3bf-e73e44509361", "25a20c2a-d8a0-4bb6-95e0-9b7cda36d335", "73f2a4e2-cbc1-4ec0-a5e6-c4b4dafb2833"]
var vaccine7 = ["c6328204-53aa-4dd5-b9c9-7c6e495fd16d", "e370cf05-56cd-4c43-84b2-68da784408ee", "a861595f-1b4a-4192-b3bf-e73e44509361", "25a20c2a-d8a0-4bb6-95e0-9b7cda36d335", "73f2a4e2-cbc1-4ec0-a5e6-c4b4dafb2833", "c703e042-d22c-499a-9420-85dbc8d2d77e", "09e05a3d-13aa-48e9-bac1-471354b92dc7"]
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
            if (appointment.status != 'Paid' ||
                appointmentDate < today) return;

            const appointmentItem = document.createElement('ul');
            appointmentItem.classList.add('booking-item');
            appointmentItem.innerHTML = `
                <li><input type="checkbox" name="select-item" value="${appointment.id}"></li>
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

async function updateAppointmentStatus(appointmentId, status) {
    const data = {
        status: status
    };

    const response = await fetchWithToken(`${API_BASE_URL}/v1/appointments/${appointmentId}`, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    });

    if (response.code == 1000) {
        
    } else {
        alert('Có lỗi xảy ra: ' + response.message);
    }
}
async function getInforAppointment(appointmentId) {

    const response = await fetchWithToken(`${API_BASE_URL}/v1/appointments/${appointmentId}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        },

    });

    if (response.code == 1000) {
        const appointment = response.result;
        appointment.servicesResponsesList.forEach(service => {
            if (service.id == "173bd147-efea-40ec-950f-d118c9a9c3a1") {
                vaccine5.forEach(vaccine => {
                    addVaccine(appointment.pet.id, vaccine);
                })
            }
            else if (service.id == "08f5f4f0-0ad6-4726-bc19-9a26677df9a1") {
                vaccine7.forEach(vaccine => {
                    addVaccine(appointment.pet.id, vaccine);
                })
            }
            else {

            }
        })
    } else {
        alert('Có lỗi xảy ra: ' + response.message);
    }
}
document.getElementById('confirm-btn').addEventListener('click', async function () {
    const selectedItems = document.querySelectorAll('input[name=select-item]:checked');

    if (selectedItems.length == 0) {
        alert('Vui lòng chọn ít nhất một lịch hẹn');
        return;
    }

    const appointmentIds = [];
    selectedItems.forEach(item => {
        appointmentIds.push(item.value);
    });
    appointmentIds.forEach(appointmentId => {
        getInforAppointment(appointmentId);
        updateAppointmentStatus(appointmentId, 'Examined');
    });
    window.location.reload();
})

async function addVaccine(petId, vaccineId) {
    const today = new Date();

    // Ngày hôm nay một năm sau
    const reStingDate = new Date();
    reStingDate.setFullYear(today.getFullYear() + 1);
    const data = await fetchWithToken(`${API_BASE_URL}/v1/vaccines-pet`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            petId: petId,
            vaccineId: vaccineId,
            stingDate: today,
            reStingDate: reStingDate
        })
    })
    if (data.code == 1000) {
        console.log('Thêm vaccine thành công');
    } else {
        alert('Có lỗi xảy ra: ' + data.message);
    }
}