
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

// Rest of your code, such as handling glider animation
});

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


function openPaymentModal() {
    document.getElementById('payment-modal').style.display = 'flex';
}

function closePaymentModal() {
    document.getElementById('payment-modal').style.display = 'none';
}

function confirmBooking() {
    // You can add form submission logic here
    alert('Booking confirmed!');
    closePaymentModal();
}

// Doctor select
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


// document.addEventListener('DOMContentLoaded', function () {
//     const headerBtn = document.querySelector('.header-btn');
//     const subNav = document.querySelector('.sub-nav');

//     headerBtn.addEventListener('click', function () {
//         // Toggle hiển thị sub-nav
//         subNav.style.display = (subNav.style.display === 'flex' ? 'none' : 'flex');
//     });

//     // Đóng sub-nav khi nhấp ra ngoài
//     document.addEventListener('click', function (event) {
//         if (!headerBtn.contains(event.target)) {
//             subNav.style.display = 'none';
//         }
//     });
// });


document.addEventListener('DOMContentLoaded', async () => {
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
});

document.addEventListener('DOMContentLoaded', async function () {
    const doctorSelect = document.getElementById('doctor-select');
    const serviceSelect = document.getElementById('service-select');
    const petSelect = document.getElementById('pet-select');
    const dateInput = document.getElementById('date');

    // Hàm kiểm tra điều kiện và gọi fetchSessionAvailable
    function checkAndFetchSessions() {
        const doctorId = doctorSelect.querySelector('.selected-option').dataset.value;
        const date = dateInput.value;

        if (doctorId && date) {
            fetchSessionAvailable();
        }
    }

    // Cập nhật và gọi fetchSessionAvailable khi chọn bác sĩ
    doctorSelect.addEventListener('click', function () {
        checkAndFetchSessions();
    });

    doctorSelect.querySelectorAll('.options div').forEach(option => {
        option.addEventListener('click', function () {
            checkAndFetchSessions();
        });
    });

    // Cập nhật và gọi fetchSessionAvailable khi chọn dịch vụ
    serviceSelect.addEventListener('click', function () {
        checkAndFetchSessions();
    });

    serviceSelect.querySelectorAll('.options div').forEach(option => {
        option.addEventListener('click', function () {
            checkAndFetchSessions();
        });
    });

    // Cập nhật và gọi fetchSessionAvailable khi chọn thú cưng
    petSelect.addEventListener('click', function () {
        checkAndFetchSessions();
    });

    petSelect.querySelectorAll('.options div').forEach(option => {
        option.addEventListener('click', function () {
            checkAndFetchSessions();
        });
    });

    // Cập nhật và gọi fetchSessionAvailable khi thay đổi ngày
    dateInput.addEventListener('change', function () {
        checkAndFetchSessions();
    });
});
async function fetchSessionAvailable() {
    const doctorId = document.querySelector('#doctor-select .selected-option').dataset.value;
    const date = document.querySelector('#date').value;
    const sessionContainer = document.querySelector('.tabs');

    // Xóa các session cũ trước khi thêm session mới
    sessionContainer.innerHTML = '';

    try {
        const response = await fetchWithToken(`${API_BASE_URL}/v1/veterinarians/${doctorId}/available-sessions?date=${date}`);
        
        if (response.code === 1000 && Array.isArray(response.result)) {
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

            // Duyệt qua các session có sẵn để hiển thị
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
    const doctorId = document.querySelector('#doctor-select .selected-option').dataset.value;
    const serviceId = document.querySelector('#service-select .selected-option').dataset.value;
    const petId = document.querySelector('#pet-select .selected-option').dataset.value;
    const date = document.querySelector('#date').value;
    const selectedRadio = document.querySelector('input[name="time"]:checked');
    var selectedValue=""; 
    if (selectedRadio) {
        selectedValue = selectedRadio.getAttribute('data-value');
    }
    if (!serviceId || !petId || !date || !selectedValue|| !doctorId) {
        alert('Vui lòng điền đầy đủ thông tin.');
        return; // Dừng việc tiếp tục thực hiện khi có trường trống
    }
    var appointment = {
        status: "Processing",
        appointmentDate: date,
        petId: petId,
        serviceId: [serviceId],
        sessionId:  selectedValue,
        veterinarianId:doctorId,
    }
    const data = await fetchWithToken(`${API_BASE_URL}/v1/appointments/addByVeterinarian`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(appointment)
    });

    if (data.code === 1000) {
        const appointmentId = data.result.id;
        alert('Đặt lịch thành công');
        window.location.href = `paymentMethods.html?id=${appointmentId}`;
    } else {
        alert('Đặt lịch không thành công');
    }
});

function navigate(url) {
        

    if (authToken) {
        window.location.href = url;
    } else {
        showModal();
    }
}

document.querySelector("#logout").addEventListener("click", async function () {
    try {
        const response = await fetch(`${API_BASE_URL}/v1/auth/logout`, { // Thay thế URL bằng API của bạn
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
               token:authToken
            }),
        });

        if (!response.ok) {
            throw new Error('Đăng xuất thất bại');
        }

        const data = await response.json();

        // Kiểm tra mã phản hồi
        if (data.code === 1000) {
            // Xử lý dữ liệu nhận được từ API
            console.log('Đăng xuất thành công');

            // Lưu token vào localStorage và chuyển hướng người dùng
           localStorage.removeItem('authToken');
            window.location.href = 'index.html'; // Chuyển hướng đến trang sau khi đăng nhập thành công
        } else {
            throw new Error(data.message || 'Đăng xuất thất bại');
        }

    } catch (error) {
        console.error('Lỗi:', error);
        alert('Đăng xuất thất bại');
    }
})