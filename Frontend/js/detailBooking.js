document.addEventListener('DOMContentLoaded', async function () {
    var showModalButton = document.getElementById('show-modal');
    var deleteModal = document.getElementById('delete-modal');
    var confirmDeleteButton = document.getElementById('confirm-delete');
    var cancelDeleteButton = document.getElementById('cancel-delete');
    var successMessage = document.getElementById('success-message');
    var doctorContainer = document.querySelector('.doctor-container');
    const urlParams = new URLSearchParams(window.location.search);
    const appointmentId = urlParams.get('id');
    
    const data = await fetchWithToken(`${API_BASE_URL}/v1/appointments/${appointmentId}`,{
        method:'GET',
        headers:{
            'Content-Type':'application/json'
        }
    });
    const appointment=data.result;
   
    // Hiển thị nội dung tùy theo điều kiện isDoctorAssigned
    if(data.code==1000){
        document.querySelector('.pet-id').innerText = appointment.id;
        document.querySelector('#customer-id').innerText = appointment.pet.customerResponse.id;
        document.querySelector('.customer-phone').innerText = appointment.pet.customerResponse.phoneNumber;
        document.querySelector('#customer-pet-id').innerText = appointment.pet.id;
        document.querySelector('#visit-date').innerText = appointment.appointmentDate;
        document.querySelector('.shift').innerText = `${appointment.sessionResponse.startTime} - ${appointment.sessionResponse.endTime}`;
        document.querySelector('.service').innerText = appointment.serviceName[0];
        if (appointment.veterinarianName) {
            doctorContainer.innerHTML = `
                    <label for="doctor-name">Bác sĩ</label>
                    <h1 class="doctor-name">${appointment.veterinarianName}</h1> 
            `;
        } else {
            const dataVeterinarian=await fetchWithToken(`${API_BASE_URL}/v1/veterinarians`,{
                method:'GET',
                headers:{
                    'Content-Type':'application/json'
                }
            })
            doctorContainer.innerHTML = ` 
                    <label for="doctor-name">Bác sĩ</label>
                    <div id="doctor-select" class="custom-select">
                        <div class="selected-option">Chọn bác sĩ<i class='bx bx-chevron-down'></i></div>
                        <div class="options">
                            <div data-value="d1">Bác sĩ 1</div>
                            <div data-value="d2">Bác sĩ 2</div>
                            <div data-value="d3">Bác sĩ 3</div>
                            <div data-value="d4">Bác sĩ 4</div>
                        </div>
                    </div>
                    <button id="choose-veterinarian">Chọn</button>
            `;
        }
    }
    else{
        alert("Đã có lỗi xảy ra: ",data.message);
    }

    // Xử lý sự kiện cho modal
    showModalButton.addEventListener('click', function () {
        deleteModal.style.display = 'flex';
    });

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
                event.stopPropagation(); // Prevent the dropdown from immediately re-opening
                const select = option.closest('.custom-select');
                select.querySelector('.selected-option').innerHTML = option.innerText + " <i class='bx bx-chevron-down'></i>";
                select.classList.remove('open');
            });
        });

        document.addEventListener('click', function (event) {
            if (!event.target.closest('#doctor-select')) {
                document.querySelector('#doctor-select').classList.remove('open');
            }
        });
    }
});
