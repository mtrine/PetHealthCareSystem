
document.addEventListener("DOMContentLoaded", function () {

    // pet select
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

    // cage select
    document.querySelector('#cage-select').addEventListener('click', function (event) {
        const icon = this.querySelector('.selected-option i');
        this.classList.toggle('open');
        icon.classList.toggle('bx-rotate-180');
    });

    document.querySelectorAll('#cage-select .options div').forEach(function (option) {
        option.addEventListener('click', function (event) {
            event.stopPropagation(); // Prevent the dropdown from immediately re-opening
            const select = option.closest('.custom-select');
            select.querySelector('.selected-option').innerHTML = option.innerText + " <i class='bx bx-chevron-down'></i>";
            select.classList.remove('open');
        });
    });

    // Close the dropdown if clicking outside of it
    document.addEventListener('click', function (event) {
        if (!event.target.closest('#cage-select')) {
            document.querySelector('#cage-select').classList.remove('open');
        }
    });

})
document.getElementById('customer-email').addEventListener('change', function (event) {
    const email = event.target.value;
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

document.addEventListener('DOMContentLoaded', async function () {
    const customSelect = document.getElementById('cage-select');
    const selectedOption = customSelect.querySelector('.selected-option');
    const optionsContainer = customSelect.querySelector('.options');
    const data = await fetchWithToken(`${API_BASE_URL}/v1/cages`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        },
    });
    if (data.code == 1000) {
        const cages = data.result;
        optionsContainer.innerHTML = '';
        cages.forEach((cage) => {
            if(cage.status === true) return;
            const option = document.createElement('div');
            option.setAttribute('data-value', cage.cageNumber);
            option.textContent = cage.cageNumber;
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
});

document.getElementById('add-hospitalization').addEventListener('click', async function (event) {
    const data = {
        petID: document.getElementById('pet-select').querySelector('.selected-option').dataset.value,
        cageNumber: document.getElementById('cage-select').querySelector('.selected-option').dataset.value,
        healthCondition: document.getElementById('status').value,
        reasonForHospitalization:document.getElementById('reason').value,
        startDate: document.getElementById('date').value,
    }


    if (data.petID === undefined || data.cageNumber === undefined || data.healthCondition === '' || data.reasonForHospitalization === '' || data.startDate === '') {
        alert('Vui lòng điền đầy đủ thông tin.');
        return;
    }

    const response = await fetchWithToken(`${API_BASE_URL}/v1/hospitalizations`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    });

    if (response.code == 1000) {
        alert('Thêm lịch hẹn thành công.');
        window.location.href = 'index.html';
    } else {
        alert('Thêm lịch hẹn thất bại.');
    }
})