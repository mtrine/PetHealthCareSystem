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

document.addEventListener('DOMContentLoaded', async () => {
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
      
});

document.getElementById('add-pet').addEventListener('click', async (event) => {
        event.preventDefault();
      
        const name = document.getElementById('pet-name').value;
        const speciesId = document.querySelector('#pet-select .selected-option').dataset.value;
        const age = document.getElementById('pet-age').value;
        const gender = document.querySelector('input[name="gender"]:checked').value;
        // Validate input
        if (!name || !speciesId || !age) {
            alert('Vui lòng nhập đầy đủ thông tin.');
            return;
        }
        
        var sex = false;
        if(gender==="duc"){
            sex=true;
        }

        // Create a new pet object
        const pet = {
            name:name,
            speciesID: speciesId,
            age:age,
            gender:sex
        };
      
        const data = await fetchWithToken(`${API_BASE_URL}/v1/pets/my-pet`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(pet)
        });
      
        if (data && data.code === 1000) {
            alert('Thêm thú cưng thành công!');
            window.location.href = 'PetList.html';
        } else {
            alert('Đã xảy ra lỗi: ' + data.message);
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