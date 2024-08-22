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

document.addEventListener('DOMContentLoaded', async function () {
    const urlParams = new URLSearchParams(window.location.search);
    const petId = urlParams.get('id');
    const petInfo= await fetchWithToken(`${API_BASE_URL}/v1/pets/${petId}`,{
        method:'GET',
        headers:{
            'Content-Type':'application/json'
        }
    })
    if(petInfo.code==1000){
        const pet=petInfo.result;
        document.querySelector('#pet-name').value=pet.name
        document.querySelector('.pet-id').textContent=pet.id
        document.querySelector('#pet-age').value=pet.age
        document.querySelector('.selected-option').innerHTML=`${pet.speciesResponse.name} <i class='bx bx-chevron-down'></i>`
        document.querySelector('.selected-option').setAttribute('data-value', pet.speciesResponse.id)
        if (pet.gender) {
            document.getElementById("duc").checked = true;
        } else {
            document.getElementById("cai").checked = true;
        }
        fetchSpecices()
        
    }
})
async function fetchSpecices(){
    const customSelect = document.getElementById('pet-select');
    const selectedOption = customSelect.querySelector('.selected-option');
    const optionsContainer = customSelect.querySelector('.options');
  
    // Fetch data from the API
    const data = await fetchWithToken(`${API_BASE_URL}/v1/species`); // Thay thế URL nếu cần

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

document.getElementById('update').addEventListener('click', async function (event) {
    event.preventDefault();
    const petId = document.querySelector('.pet-id').textContent
    const name = document.getElementById('pet-name').value;
    const speciesId = document.querySelector('#pet-select .selected-option').dataset.value;
    const age = document.getElementById('pet-age').value;
    var isDucChecked = document.getElementById("duc").checked;
    var isCaiChecked = document.getElementById("cai").checked;

    // Tạo biến để lưu trữ kết quả kiểm tra
    var ducIsTrueAndCaiIsFalse = isDucChecked && !isCaiChecked;

    const data={
        name:name,
        speciesID:speciesId,
        age:age,
        gender:ducIsTrueAndCaiIsFalse
    }

    const result = await fetchWithToken(`${API_BASE_URL}/v1/pets/${petId}`, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    });

    if(result.code===1000){
        alert('Cập nhật thành công')
        window.location.href = 'PetList.html'
    }
    else{
        alert('Cập nhật thất bại')
    }
})