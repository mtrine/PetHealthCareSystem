// Kiểm tra trạng thái của ca
document.addEventListener("DOMContentLoaded", function () {
    const timeSlots = document.querySelectorAll('.time-slot-container input[type="radio"]');

    timeSlots.forEach(slot => {
        // Check if the slot is available
        if (slot.dataset.available === "false") {
            // Find the corresponding label and mark it as unavailable
            const label = document.querySelector(`label[for="${slot.id}"]`);
            label.classList.add('unavailable');
            slot.disabled = true; // Disable the radio button
        }
    });

});

// Pet select
document.querySelector('#pet-select').addEventListener('click', function (event) {
    const icon = this.querySelector('.selected-option i');
    this.classList.toggle('open');
    icon.classList.toggle('bx-rotate-180');
});

document.querySelectorAll('#pet-select .options div').forEach(function (option) {
    option.addEventListener('click', function (event) {
        event.stopPropagation(); // Prevent the dropdown from immediately re-opening
        const select = option.closest('.custom-select');
        select.querySelector('.selected-option').innerHTML = option.innerText + " <i class='bx bx-chevron-down'></i>";
        select.classList.remove('open');
    });
});

// Close the dropdown if clicking outside of it
document.addEventListener('click', function (event) {
    if (!event.target.closest('#pet-select')) {
        document.querySelector('#pet-select').classList.remove('open');
    }
});

// doctor select
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

// Close the dropdown if clicking outside of it
document.addEventListener('click', function (event) {
    if (!event.target.closest('#doctor-select')) {
        document.querySelector('#doctor-select').classList.remove('open');
    }
});
document.querySelector('#service-select').addEventListener('click', function (event) {
    const icon = this.querySelector('.selected-option i');
    this.classList.toggle('open');
    icon.classList.toggle('bx-rotate-180');
});

document.querySelectorAll('#service-select .options div').forEach(function (option) {
    option.addEventListener('click', function (event) {
        event.stopPropagation(); // Prevent the dropdown from immediately re-opening
        const select = option.closest('.custom-select');
        select.querySelector('.selected-option').innerHTML = option.innerText + " <i class='bx bx-chevron-down'></i>";
        select.classList.remove('open');
    });
});
document.getElementById('customer-email').addEventListener('change', function (event) {
    const email = event.target.value;
    resetPetSelection();
    if (email === '') {
        showError('Email không được để trống.');
    } else {
        hideError();
        fetchPetListByCustomerEmail(email);
    }
});
function showError(message) {
    const errorElement = document.getElementById('email-error');
    errorElement.textContent = message;
    errorElement.style.display = 'block';
}

function hideError() {
    const errorElement = document.getElementById('email-error');
    errorElement.style.display = 'none';
}
function resetPetSelection() {
    const customSelect = document.getElementById('pet-select');
    const selectedOption = customSelect.querySelector('.selected-option');
    const optionsContainer = customSelect.querySelector('.options');

    // Đặt lại nội dung hiển thị mặc định
    selectedOption.textContent = 'Chọn thú cưng';
    selectedOption.dataset.value = '';

    // Xóa tất cả các lựa chọn thú cưng hiện tại
    optionsContainer.innerHTML = '';
}
async function fetchPetListByCustomerEmail(email) {
    const customSelect = document.getElementById('pet-select');
    const selectedOption = customSelect.querySelector('.selected-option');
    const optionsContainer = customSelect.querySelector('.options');
    try {
        const data = await fetchWithToken(`${API_BASE_URL}/v1/pets/customer-email/${email}`,
            {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                }
            }
        );

        if (data.code == 1000) {
            const pets = data.result;
            optionsContainer.innerHTML = '';
            pets.forEach((pet) => {
                const option = document.createElement('div');
                option.setAttribute('data-value', pet.id);
                option.textContent = pet.name;
                optionsContainer.appendChild(option);
            });
            optionsContainer.querySelectorAll('div').forEach(option => {
                option.addEventListener('click', () => {
                    const value = option.getAttribute('data-value');
                    selectedOption.textContent = option.textContent;
                    selectedOption.dataset.value = value;
                    selectedOption.innerHTML = `${option.textContent} <i class='bx bx-chevron-down'></i>`

                });
            });
        }

    } catch (error) {
        console.log(error);
        showError("Không tìm thấy thú cưng nào của khách hàng này.");
    }
}
async function fetchServices(){
    const customSelect = document.getElementById('service-select');
    const selectedOption = customSelect.querySelector('.selected-option');
    const optionsContainer = customSelect.querySelector('.options');
  
    // Fetch data from the API
    const data = await fetchWithToken(`${API_BASE_URL}/v1/services`); // Thay thế URL nếu cần

    if (data && data.code === 1000 && Array.isArray(data.result)) {
        // Clear existing options
        optionsContainer.innerHTML = '';
        
            
        // Populate options from API data
        data.result.forEach(species => {
            const option = document.createElement('div');
            option.setAttribute('data-value', species.id);
            option.textContent = species.name;
            optionsContainer.appendChild(option);
        });

        // Add event listeners for options
        optionsContainer.querySelectorAll('div').forEach(option => {
            option.addEventListener('click', () => {
                const value = option.getAttribute('data-value');
                selectedOption.textContent = option.textContent;
                selectedOption.dataset.value = value;
                 selectedOption.innerHTML = `${option.textContent} <i class='bx bx-chevron-down'></i>`

            });
        });
    } else {
        console.error('Failed to fetch species data:', data.message);
    }
}

async function fetchSessionAvailable() {
    const doctorId = document.querySelector('#doctor-select .selected-option').dataset.value;
    const date = document.querySelector('#date').value;
    const sessionContainer = document.querySelector('.tabs');

    // Xóa các session cũ trước khi thêm session mới
    sessionContainer.innerHTML = '';

    try {
        const response = await fetchWithToken(`${API_BASE_URL}/v1/veterinarians/${doctorId}/available-sessions?date=${date}`);
        
        if (response.code === 1000 && Array.isArray(response.result)) {
            if(response.result.length === 0) {
                sessionContainer.innerHTML = 'Không có ca trống';
                return;
            }
            response.result.forEach((session, index) => {
                // Tạo id và checked status cho các session
                const sessionId = `radio-${index + 1}`;
                const isChecked = index === 0 ? 'checked' : ''; // Checked session đầu tiên
                const startTime = session.startTime.slice(0, 5); // Lấy từ 0 đến 5 để giữ lại HH:MM
                const endTime = session.endTime.slice(0, 5);
                // Thêm radio button và label tương ứng
                const radioInput = `
                    <input type="radio" id="${sessionId}" name="time" data-value="${session.id}" ${isChecked}>
                    <label class="tab" for="${sessionId}">${startTime} - ${endTime}</label>
                `;
                sessionContainer.insertAdjacentHTML('beforeend', radioInput);
            });

            // Add the glider span at the end
            const glider = `<span class="glider"></span>`;
            sessionContainer.insertAdjacentHTML('beforeend', glider);
        } else {
            console.error('Failed to fetch available sessions:', response.message);
        }
    } catch (error) {
        console.error('Error fetching data:', error);
    }
}

async function fetchSessionAvailable() {
    const doctorId = document.querySelector('#doctor-select .selected-option').dataset.value;
    const date = document.querySelector('#date').value;
    const sessionContainer = document.querySelector('.tabs');
    
    // Xóa các session cũ trước khi thêm session mới
    sessionContainer.innerHTML = '';

    try {
        const response = await fetchWithToken(`${API_BASE_URL}/v1/veterinarians/${doctorId}/available-sessions?date=${date}`);
        
        if (response.code === 1000 && Array.isArray(response.result)) {
            if(response.result.length === 0) {
                sessionContainer.innerHTML = 'Không có ca trống';
                return;
            }
            const arraySession = response.result.filter((session) => {
                const sessionDateTime = new Date(`${date}T${session.endTime}`);
                const currentDateTime = new Date(); // Ngày và giờ hiện tại

                // Trả về true nếu session này còn trong tương lai
                return sessionDateTime > currentDateTime;
            });

            if (arraySession.length === 0) {
                // Hiển thị thông báo nếu không có ca trống
                sessionContainer.insertAdjacentHTML('beforeend', '<p>Không có ca trống</p>');
                return;
            }

            arraySession.forEach((session, index) => {
                const sessionId = `radio-${index + 1}`;
                const isChecked = index === 0 ? 'checked' : ''; // Checked session đầu tiên
                const startTime = session.startTime.slice(0, 5); // Lấy từ 0 đến 5 để giữ lại HH:MM
                const endTime = session.endTime.slice(0, 5);

                // Thêm radio button và label tương ứng
                const radioInput = `
                    <input type="radio" id="${sessionId}" name="time" data-value="${session.id}" ${isChecked}>
                    <label class="tab" for="${sessionId}">${startTime} - ${endTime}</label>
                `;
                sessionContainer.insertAdjacentHTML('beforeend', radioInput);
            });

            // Thêm glider vào cuối cùng
            sessionContainer.insertAdjacentHTML('beforeend', '<span class="glider"></span>');
        } else {
            console.error('Failed to fetch available sessions:', response.message);
        }
    } catch (error) {
        console.error('Error fetching data:', error);
    }
}

async function fetchDotor(){
    const customSelect = document.getElementById('doctor-select');
    const selectedOption = customSelect.querySelector('.selected-option');
    const optionsContainer = customSelect.querySelector('.options');

    try {
        // Fetch data from the API
        const response = await fetchWithToken(`${API_BASE_URL}/v1/veterinarians`); // Thay thế URL nếu cần
   

        if (response.code === 1000 && Array.isArray(response.result)) {
            // Clear existing options
            optionsContainer.innerHTML = '';
            
            // Populate options from API data
            response.result.forEach(veterinarian => {
                if (veterinarian.isFulltime) {
                    const option = document.createElement('div');
                    option.setAttribute('data-value', veterinarian.id);
                    option.textContent = veterinarian.name;
                    optionsContainer.appendChild(option);
                }
            });

            // Add event listeners for options
            optionsContainer.querySelectorAll('div').forEach(option => {
                option.addEventListener('click', () => {
                    const value = option.getAttribute('data-value');
                    selectedOption.textContent = option.textContent;
                    selectedOption.dataset.value = value;
                     selectedOption.innerHTML = `${option.textContent} <i class='bx bx-chevron-down'></i>`
                    
                });
            });
        } else {
            console.error('Failed to fetch veterinarians data:', data.message);
        }
    } catch (error) {
        console.error('Error fetching data:', error);
    }
}

document.addEventListener('DOMContentLoaded', async () => {
    const doctorSelect = document.getElementById('doctor-select');
    const dateInput = document.getElementById('date');
    function checkAndFetchSessions() {
        const doctorId = doctorSelect.querySelector('.selected-option').dataset.value;
        const date = dateInput.value;

        if (doctorId && date) {
            fetchSessionAvailable();
        }
        
    }
    fetchServices();
    fetchDotor()
     doctorSelect.addEventListener('click', function () {
        checkAndFetchSessions();
    });

    doctorSelect.querySelectorAll('.options div').forEach(option => {
        option.addEventListener('click', function () {
            checkAndFetchSessions();
        });
    });

    dateInput.addEventListener('change', function () {
        checkAndFetchSessions();
    });
})

document.getElementById('add').addEventListener('click', async function (event) {
    const petId = document.querySelector('#pet-select .selected-option').dataset.value;
    const doctorId = document.querySelector('#doctor-select .selected-option').dataset.value;
    const serviceId = document.querySelector('#service-select .selected-option').dataset.value;
    const date = document.querySelector('#date').value;
    const timeSlot = document.querySelector('input[name="time"]:checked');
    var selectedValue=""; 
    if (timeSlot) {
        selectedValue = timeSlot.getAttribute('data-value');
    }
    const data=await fetchWithToken(`${API_BASE_URL}/v1/appointments/addByVeterinarian`,{
        method: 'POST',
        headers: {
            "Content-Type": "application/json"
          },
        body: JSON.stringify({
            appointmentDate:date,
            veterinarianId:doctorId,
            serviceId:[serviceId],
            sessionId: selectedValue,
            deposit:0,
            petId:petId,
            status:"Processing"
        })  
    })
    if(data.code==1000){
        alert("Đăng ký lịch thành công")
        window.location.href = "index.html";
    }
    else{
        alert("Đăng ký lịch thất bại:" +data.message)
    }
})