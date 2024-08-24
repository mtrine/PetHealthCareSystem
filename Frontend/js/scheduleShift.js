function openPaymentMethods() {
    window.location.href = 'paymentMethods.html';
}
// Hiển thị payment modal

document.addEventListener("DOMContentLoaded", function () {
    const bookingForm = document.querySelector("form");
    bookingForm.addEventListener("submit", function (event) {
        event.preventDefault(); // Prevent the default form submission

        // Show the payment modal
        document.getElementById("payment-modal").style.display = "flex";
    });

});


function closePaymentModal() {
    document.getElementById("payment-modal").style.display = "none";
}

function confirmBooking() {
    // Additional logic for confirming the booking can go here
}


// Service select
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

// Close the dropdown if clicking outside of it
document.addEventListener('click', function (event) {
    if (!event.target.closest('#service-select')) {
        document.querySelector('#service-select').classList.remove('open');
    }
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


document.addEventListener('DOMContentLoaded', function () {
    const headerBtn = document.querySelector('.header-btn');
    const subNav = document.querySelector('.sub-nav');

    headerBtn.addEventListener('click', function () {
        // Toggle hiển thị sub-nav
        subNav.style.display = (subNav.style.display === 'flex' ? 'none' : 'flex');
    });

    // Đóng sub-nav khi nhấp ra ngoài
    document.addEventListener('click', function (event) {
        if (!headerBtn.contains(event.target)) {
            subNav.style.display = 'none';
        }
    });
});

document.addEventListener('DOMContentLoaded', async function () {
    const sessionContainer = document.querySelector('.tabs');

    // Xóa các session cũ trước khi thêm session mới
    sessionContainer.innerHTML = '';

    try {
        const response = await fetchWithToken(`${API_BASE_URL}/v1/sessions`);
        
        if (response.code === 1000 && Array.isArray(response.result)) {
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
});
document.addEventListener('DOMContentLoaded', async function () {
    const serviceSelect = document.getElementById('service-select');
    const selectedOption = serviceSelect.querySelector('.selected-option');
    const optionsContainer = serviceSelect.querySelector('.options');
    try {
        // Fetch data from the API
        const response = await fetchWithToken(`${API_BASE_URL}/v1/services`); // Thay thế URL nếu cần
   

        if (response.code === 1000 && Array.isArray(response.result)) {
            // Clear existing options
            optionsContainer.innerHTML = '';
            
            // Populate options from API data
            response.result.forEach(service => {
                
                    const option = document.createElement('div');
                    option.setAttribute('data-value', service.id);
                    option.textContent = service.name;
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
            console.error('Failed to fetch veterinarians data:', data.message);
        }
    } catch (error) {
        console.error('Error fetching data:', error);
    }
});
document.addEventListener('DOMContentLoaded', async function () {
    const petSelect = document.getElementById('pet-select');
    const selectedOption = petSelect.querySelector('.selected-option');
    const optionsContainer = petSelect.querySelector('.options');
    try {
        // Fetch data from the API
        const response = await fetchWithToken(`${API_BASE_URL}/v1/pets/my-pet`); // Thay thế URL nếu cần
   

        if (response.code === 1000 && Array.isArray(response.result)) {
            // Clear existing options
            optionsContainer.innerHTML = '';
            
            // Populate options from API data
            response.result.forEach(pet => {
                
                    const option = document.createElement('div');
                    option.setAttribute('data-value', pet.id);
                    option.textContent = pet.name;
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
            console.error('Failed to fetch veterinarians data:', data.message);
        }
    } catch (error) {
        console.error('Error fetching data:', error);
    }
});

document.querySelector("#submitSchedule").addEventListener('click', async function () {
    const serviceId = document.querySelector('#service-select .selected-option').dataset.value;
    const petId = document.querySelector('#pet-select .selected-option').dataset.value;
    const date = document.querySelector('#date').value;
    const selectedRadio = document.querySelector('input[name="time"]:checked');
    var selectedValue=""; 
    if (selectedRadio) {
        selectedValue = selectedRadio.getAttribute('data-value');
    }
    if (!serviceId || !petId || !date || !selectedValue) {
        alert('Vui lòng điền đầy đủ thông tin.');
        return; // Dừng việc tiếp tục thực hiện khi có trường trống
    }
    var appointment = {
        status: "Processing",
        appointmentDate: date,
        petId: petId,
        serviceId: [serviceId],
        sessionId:  selectedValue,
    }
    const data = await fetchWithToken(`${API_BASE_URL}/v1/appointments/addBySession`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(appointment)
    });
    console.log(data.result.id);
});