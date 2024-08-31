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

// Thao tác ấn vào pet-item
document.addEventListener("DOMContentLoaded", function () {
    // Handle pet-item clicks
    document.querySelectorAll('.pet-item').forEach(function (item) {
        item.addEventListener('click', function (event) {
            // Prevent navigation when clicking on buttons inside pet-item
            if (event.target.closest('.delete-btn') || event.target.closest('.edit-btn')) {
                return;
            }
            window.location.href = 'PetInfo.html';
        });
    });

    // Handle edit-btn clicks
    document.querySelectorAll('.edit-btn').forEach(function (button) {
        button.addEventListener('click', function (event) {
            event.stopPropagation(); // Prevent triggering the pet-item click event
            window.location.href = 'EditPet.html';
        });
    });

    // Handle PetInfo-btn clicks
    document.querySelectorAll('.add-pet').forEach(function (button) {
        button.addEventListener('click', function (event) {
            event.stopPropagation(); // Prevent triggering the pet-item click event
            window.location.href = 'AddPet.html';
        });
    });
});

// Hiển thị modal
function showModal() {
    document.getElementById('delete-modal').style.display = 'flex';
}

// Ẩn modal
function hideModal() {
    document.getElementById('delete-modal').style.display = 'none';
}

document.addEventListener("DOMContentLoaded", async function () {
    const petContainer = document.querySelector('.pet-items-container');

    const data = await fetchWithToken(`${API_BASE_URL}/v1/pets/my-pet`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        },
    });

    if (data && data.code === 1000) {
        const pets = data.result;
        var gender = "Cái";
        pets.forEach(pet => {
            const petItem = document.createElement('ul');
            petItem.classList.add('pet-item');
            
            if (pet.gender) {
                gender = "Đực";
            }
            petItem.innerHTML = `
                <li>${pet.id}</li> 
                <li>${pet.name}</li>
                <li>${pet.age}</li>
                <li>${pet.speciesResponse.name}/${gender}</li> 
                <li>    
                    <button onclick="showModal()" class="delete-btn" data-id="${pet.id}">
                        <i class='bx bx-trash' style='color:#5E5E5E'></i>
                    </button>
                     <button class="edit-btn" data-id="${pet.id}">
                        <i class='bx bx-pencil' style='color:#5E5E5E'></i>
                    </button>
                </li>
            `;
            petItem.addEventListener('click', function (event) {
                // Kiểm tra nếu phần tử được click không phải là button hoặc icon bên trong button
                if (!event.target.closest('button')) {
                    window.location.href = `PetInfo.html?id=${pet.id}`;
                }
            });

            const editBtn = petItem.querySelector('.edit-btn');
            editBtn.addEventListener('click', function (event) {
                event.stopPropagation(); // Ngăn không cho sự kiện click của petItem được kích hoạt
                const petId = editBtn.getAttribute('data-id');
                window.location.href = `EditPet.html?id=${petId}`;
            });
            petContainer.appendChild(petItem);
        });

    } else {
        alert('Đã xảy ra lỗi: ' + data.message);
    }
});

document.querySelector("#delete-btn").addEventListener("click", async function () {
    const deleteBtn = document.querySelector('.delete-btn'); // Lấy nút delete-btn đang được nhấn
    const petId = deleteBtn.getAttribute('data-id'); // Lấy pet.id từ thuộc tính data-id

    // Thực hiện yêu cầu xóa bằng cách sử dụng petId
    const response = await fetchWithToken(`${API_BASE_URL}/v1/pets/${petId}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        },
    });
    if (response && response.code === 1000) {
        alert('Xóa thú cưng thành công');
        location.reload(); // Tải lại trang để cập nhật danh sách thú cưng
    } else {
        alert('Đã xảy ra lỗi: ' + response.message);
    }
})

function navigate(url) {
        

    if (authToken) {
        window.location.href = url;
    } else {
        showModal();
    }
}