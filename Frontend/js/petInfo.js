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
        appointments.forEach(appointment => {
            const historyItem = document.createElement('ul');
            historyItem.classList.add('history-item');
            historyItem.innerHTML = `
                <li>${appointment.appointmentDate}</li>
                <li>${appointment.startTime}-${appointment.endTime}</li>
                <li>${appointment.veterinarianName}</li>
                <li>${appointment.serviceName[0]}</li>
            `;
            historyContainer.appendChild(historyItem);
        });
    }
}