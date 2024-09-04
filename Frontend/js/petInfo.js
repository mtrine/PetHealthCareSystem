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

// Pet select
document.querySelector('.pet-select').addEventListener('click', function (event) {
    const icon = this.querySelector('.selected-option i');
    this.classList.toggle('open');
    icon.classList.toggle('bx-rotate-180');
});

document.querySelectorAll('.pet-select .options div').forEach(function (option) {
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
// Báo lỗi khi nhập tuổi âm
function validateAge() {
    const ageInput = document.getElementById('pet-age');
    const errorMessage = document.getElementById('age-error');

    if (ageInput.value < 0) {
        errorMessage.textContent = 'Tuổi không thể là số âm.';
        errorMessage.style.display = 'block';
    } else {
        errorMessage.style.display = 'none';
    }
}


document.addEventListener("DOMContentLoaded", function () {
    // Lấy tham số từ URL
    const urlParams = new URLSearchParams(window.location.search);
    const petId = urlParams.get('id');

    if (petId) {
      
        fetchPetDetails(petId);
        fetchAppointment(petId);
        fetchHospitalization(petId);
        fetchVaccinePet(petId);
    } else {
        alert('Không tìm thấy thông tin thú cưng!');
    }
});

async function fetchPetDetails(petId) {
    const historyContainer = document.querySelector('.history-list-container');
    const data = await fetchWithToken(`${API_BASE_URL}/v1/pets/${petId}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        },
    });
  
    if (data && data.code === 1000) {
        const pet = data.result;
        var gender="Cái"
        document.getElementById('pet-name').innerText = pet.name;
        document.getElementById('pet-age').innerText =  pet.age;
        document.querySelector(".pet-select").innerText=pet.speciesResponse.name;
        if(pet.gender){
            gender="Đực"
        }
        document.querySelector(".pet-gender").innerText=gender
    }
    else{
        alert('Không tìm thấy thông tin thú cưng!');
    }
}
async function fetchAppointment(petId) {
    const historyContainer = document.querySelector('.history-list-container');
    const data = await fetchWithToken(`${API_BASE_URL}/v1/appointments/${petId}/pets`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        },
    });
    if (data && data.code === 1000) {
        const appointments=data.result;
        if(appointments.length==0){
            historyContainer.innerText="Không có lịch sử phòng khám"
            historyContainer.style.textAlign="center"
        }
        appointments.forEach(appointment => {
            const historyItem = document.createElement('ul');
            historyItem.classList.add('history-item');
            historyItem.innerHTML = `
                <li>${appointment.appointmentDate}</li>
                <li>${appointment.sessionResponse.startTime}-${appointment.sessionResponse.endTime}</li>
                <li>${appointment.veterinarianName?appointment.veterinarianName:"Chưa có"}</li>
                <li>${appointment.servicesResponsesList[0].name}</li>
            `;
            historyContainer.appendChild(historyItem);
        });
    }
}

async function fetchHospitalization(petId){
    const historyContainer = document.getElementById('history-container-hospitalization');
    const data=await fetchWithToken(`${API_BASE_URL}/v1/hospitalizations?petId=${petId}`,{
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        },
    });
    if (data && data.code === 1000) {
        const hospitalizations=data.result;
        if(hospitalizations.length==0){
            historyContainer.innerText="Không có lịch sử nhập viện"
            historyContainer.style.textAlign="center"
        }
        hospitalizations.forEach(hospitalization => {
            const historyItem = document.createElement('ul');
            historyItem.classList.add('history-item');
            historyItem.innerHTML = `
                <li>${hospitalization.startDate}</li>
                <li>${hospitalization.endDate?hospitalization.endDate:"Chưa ra viện"}</li>
                <li>${hospitalization.cageResponse.cageNumber}</li>
                <li>${hospitalization.reasonForHospitalization}</li>
            `;
            historyContainer.appendChild(historyItem);
        });
    }
    else{
        alert('Không tìm thấy thông tin lịch sử phòng khám!');
    }
}

async function fetchVaccinePet(petId){
    const historyContainer = document.getElementById('history-container-vaccine');
    const data=await fetchWithToken(`${API_BASE_URL}/v1/vaccines-pet/${petId}/pets`,{
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        },
    });
    
    if (data && data.code === 1000) {
        const vaccines=data.result;
        if(vaccines.length==0){
            historyContainer.innerText="Không có lịch sử  tiêm"
            historyContainer.style.textAlign="center"
        }
        vaccines.forEach(vaccine => {
            const historyItem = document.createElement('ul');
            historyItem.classList.add('history-item');
            historyItem.innerHTML = `
                <li>${vaccine.vaccineResponse.name}</li>
                <li>${vaccine.stingDate}</li>
                <li>${vaccine.reStingDate}</li>
            `;
            historyContainer.appendChild(historyItem);
        });
    }
    else{
        alert('Không tìm thấy thông tin lịch sử phòng khám!');
    }
}
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