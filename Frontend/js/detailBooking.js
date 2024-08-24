document.addEventListener('DOMContentLoaded', async function () {
    var showModalButton = document.getElementById('show-modal');
    var addServiceButton = document.getElementById('add-service');
    var deleteModal = document.getElementById('delete-modal');
    var addServiceModal = document.getElementById('add-service-modal');
    var confirmDeleteButton = document.getElementById('confirm-delete');
    var cancelDeleteButton = document.getElementById('cancel-delete');
    var successMessage = document.getElementById('success-message');
    var doctorContainer = document.querySelector('.doctor-container');
    const urlParams = new URLSearchParams(window.location.search);
    const appointmentId = urlParams.get('id');

    const data = await fetchWithToken(`${API_BASE_URL}/v1/appointments/${appointmentId}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    });
    const appointment = data.result;

    // Hiển thị nội dung tùy theo điều kiện isDoctorAssigned
    if (data.code == 1000) {
        document.querySelector('.pet-id').innerText = appointment.id;
        document.querySelector('#customer-id').innerText = appointment.pet.customerResponse.id;
        document.querySelector('.customer-phone').innerText = appointment.pet.customerResponse.phoneNumber;
        document.querySelector('#customer-pet-id').innerText = appointment.pet.id;
        document.querySelector('#visit-date').innerText = appointment.appointmentDate;
        document.querySelector('.shift').innerText = `${appointment.sessionResponse.startTime} - ${appointment.sessionResponse.endTime}`;
        const serviceContainer = document.querySelector('.service');
        serviceContainer.innerHTML = ''; // Xóa nội dung cũ

        appointment.servicesResponsesList.forEach(service => {
            if (serviceContainer.innerText === '') {
                // Nếu là phần tử đầu tiên, không thêm dấu phẩy
                serviceContainer.innerText = service.name;
            } else {
                // Nếu không phải phần tử đầu tiên, thêm dấu phẩy trước khi thêm tên dịch vụ
                serviceContainer.innerText += "," + service.name;
            }
        });
        if (appointment.veterinarianName) {
            doctorContainer.innerHTML = `
                    <label for="doctor-name">Bác sĩ</label>
                    <h1 class="doctor-name">${appointment.veterinarianName}</h1> 
            `;
        } else {
            const dataVeterinarian = await fetchWithToken(`${API_BASE_URL}/v1/veterinarians/${appointment.sessionResponse.id}/get-available-veterinarian-session?date=${appointment.appointmentDate}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                }
            })
            if (dataVeterinarian.code == 1000) {
                const veterinarians = dataVeterinarian.result;
                doctorContainer.innerHTML = ` 
                    <label for="doctor-name">Bác sĩ</label>
                    <div id="doctor-select" class="custom-select">
                        <div class="selected-option">Chọn bác sĩ<i class='bx bx-chevron-down'></i></div>
                        <div class="options">
                            ${veterinarians.map(veterinarian => `
                                <div data-value="${veterinarian.id}">${veterinarian.name}</div>
                            `).join('')}
                        </div>
                    </div>
                    <button id="choose-veterinarian">Chọn</button>
                `;
            }
        }
    }
    else {
        alert("Đã có lỗi xảy ra: ", data.message);
    }

    // Xử lý sự kiện cho modal
    showModalButton.addEventListener('click', function () {
        deleteModal.style.display = 'flex';
    });

    addServiceButton.addEventListener('click', function () {
        addServiceModal.style.display = 'flex';
    })
    confirmDeleteButton.addEventListener('click', function () {
        deleteModal.style.display = 'none';
        successMessage.style.display = 'block';
        successMessage.classList.add('show');

        setTimeout(function () {
            successMessage.classList.remove('show');
        }, 3000);
    });

    cancelDeleteButton.addEventListener('click', function () {
        deleteModal.style.display = 'none';
    });

    // Xử lý sự kiện cho phần chọn bác sĩ
    if (!appointment.veterinarianName) {
        document.querySelector('#doctor-select').addEventListener('click', function (event) {
            const icon = this.querySelector('.selected-option i');
            this.classList.toggle('open');
            icon.classList.toggle('bx-rotate-180');
        });

        document.querySelectorAll('#doctor-select .options div').forEach(function (option) {
            option.addEventListener('click', function (event) {
                event.stopPropagation(); // Ngăn không cho dropdown mở lại ngay lập tức
                const select = option.closest('.custom-select');
                // Cập nhật phần tử .selected-option với tên bác sĩ được chọn
                select.querySelector('.selected-option').innerHTML = option.innerText + " <i class='bx bx-chevron-down'></i>";
                // Lưu giá trị data-value vào phần tử .selected-option dưới dạng thuộc tính data
                select.querySelector('.selected-option').setAttribute('data-value', option.getAttribute('data-value'));
                select.classList.remove('open');
            });
        });

        document.addEventListener('click', function (event) {
            if (!event.target.closest('#doctor-select')) {
                document.querySelector('#doctor-select').classList.remove('open');
            }
        });
    }

    document.querySelector('#choose-veterinarian').addEventListener('click', async function () {
        const selectedOption = document.querySelector('#doctor-select .selected-option');
        const veterinarianId = selectedOption.getAttribute('data-value');
        console.log(veterinarianId);
        const dataAppointment = await fetchWithToken(`${API_BASE_URL}/v1/appointments/${appointmentId}`, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                veterinarianId: veterinarianId
            })
        });

        if (dataAppointment.code == 1000) {
            alert("Chọn bác sĩ thành công");
            location.reload();
        } else {
            alert("Đã có lỗi xảy ra: ", data.message);
        }
    })

    const dataServices = await fetchWithToken(`${API_BASE_URL}/v1/services`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    if(dataServices.code=1000){
        const services=dataServices.result;
        const serviceSelect=document.querySelector('#service-select');
        serviceSelect.innerHTML=`
            <div class="selected-option">Chọn dịch vụ<i class='bx bx-chevron-down'></i></div>
            <div class="options">
                ${services.map(service=>`
                    <div data-value="${service.id}">${service.name}</div>
                `).join('')}
            </div>
        `;
        document.querySelector('#service-select').addEventListener('click', function (event) {
            const icon = this.querySelector('.selected-option i');
            this.classList.toggle('open');
            icon.classList.toggle('bx-rotate-180');
        });

        document.querySelectorAll('#service-select .options div').forEach(function (option) {
            option.addEventListener('click', function (event) {
                event.stopPropagation(); // Ngăn không cho dropdown mở lại ngay lập tức
                const select = option.closest('.custom-select');
                // Cập nhật phần tử .selected-option với tên bác sĩ được chọn
                select.querySelector('.selected-option').innerHTML = option.innerText + " <i class='bx bx-chevron-down'></i>";
                // Lưu giá trị data-value vào phần tử .selected-option dưới dạng thuộc tính data
                select.querySelector('.selected-option').setAttribute('data-value', option.getAttribute('data-value'));
                select.classList.remove('open');
            });
        });

        document.addEventListener('click', function (event) {   
            if (!event.target.closest('#service-select')) {
                document.querySelector('#service-select').classList.remove('open');
            }
        });
    }
});
