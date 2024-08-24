document.addEventListener('DOMContentLoaded', async function () {
    const urlParams = new URLSearchParams(window.location.search);
    const hospitalizationId = urlParams.get('id');
    const data = await fetchWithToken(`${API_BASE_URL}/v1/hospitalizations/${hospitalizationId}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        },
    });

    if (data.code == 1000) {
        const hospitalization = data.result;
        document.getElementById('pet-id').innerText = hospitalization.petResponse.id;
        document.querySelector(".pet-name").innerText = hospitalization.petResponse.name;
        document.getElementById('date').innerText = hospitalization.startDate;
        document.getElementById('cage').innerText = hospitalization.cageResponse.cageNumber;
        document.getElementById('status').value = hospitalization.healthCondition;

    }
    else {
        alert("Đã có lỗi xảy ra: ", data.message);
    }
})
document.addEventListener("DOMContentLoaded", function () {
    // Lấy các phần tử modal
    const updateModal = document.getElementById('update-modal');
    const dischargedModal = document.getElementById('discharged-modal');

    // Lấy các nút
    const updateBtn = document.getElementById('update');
    const dischargedBtn = document.getElementById('discharged-btn');

    // Lấy các nút trong modal

    const cancelUpdate = document.getElementById('cancel-update');
   
    const cancelDischarged = document.getElementById('cancel-discharged');

    // Hiển thị modal xóa khi nhấn nút xóa
    updateBtn.onclick = function () {
        updateModal.style.display = 'flex';
    }

    // Hiển thị modal cập nhật khi nhấn nút xuất viện
    dischargedBtn.onclick = function () {
        dischargedModal.style.display = 'flex';
    }

    // Đóng modal khi nhấn vào nút hủy
    cancelUpdate.onclick = function () {
        updateModal.style.display = 'none';
    }

    cancelDischarged.onclick = function () {
        dischargedModal.style.display = 'none';
    }

    // Đóng modal khi nhấn vào bên ngoài modal-content
    // window.onclick = function (event) {
    //     if (event.target === deleteModal) {
    //         deleteModal.style.display = 'none';
    //     } else if (event.target === dischargedModal) {
    //         dischargedModal.style.display = 'none';
    //     }
    // }

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

})
document.addEventListener('DOMContentLoaded', async function () {
    const sessionContainer = document.querySelector('.tabs');
    const dateInput = document.querySelector('.date');
    
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

    // Lắng nghe sự kiện thay đổi của ngày và session
    dateInput.addEventListener('change', handleSelectionChange);
    sessionContainer.addEventListener('change', handleSelectionChange);

    function handleSelectionChange() {
        const selectedDate = dateInput.value;
        const selectedSession = document.querySelector('input[name="time"]:checked');

        // Kiểm tra xem cả ngày và session đã được chọn chưa
        if (selectedDate && selectedSession) {
            fetchDoctorList();
        }
    }

    async function fetchDoctorList() {
        const sessionId = document.querySelector('input[name="time"]:checked').getAttribute('data-value');
        const date = dateInput.value;
        const customSelect = document.getElementById('doctor-select');
        const selectedOption = customSelect.querySelector('.selected-option');
        const optionsContainer = customSelect.querySelector('.options');
        const dataVeterinarian = await fetchWithToken(`${API_BASE_URL}/v1/veterinarians/${sessionId}/get-available-veterinarian-session?date=${date}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });
        if (dataVeterinarian.code == 1000) {
            const veterinarians = dataVeterinarian.result;
            optionsContainer.innerHTML = '';
            veterinarians.forEach((veterinarian) => {
                const option = document.createElement('div');
                option.setAttribute('data-value', veterinarian.id);
                option.textContent = veterinarian.name;
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
        else {
            console.error('Failed to fetch available veterinarians:', dataVeterinarian.message);
            alert("Đã có lỗi xảy ra: " + dataVeterinarian.message);
        }
    };
});

document.querySelector('#set-visit-btn').addEventListener('click', async function () {
    const urlParams = new URLSearchParams(window.location.search);
    const hospitalizationId = urlParams.get('id');
    const date = document.querySelector('.date').value;
    const veterinarianId = document.querySelector('.selected-option').dataset.value;
    const sessionId = document.querySelector('input[name="time"]:checked')?.dataset.value; // Optional chaining to handle undefined

    // Kiểm tra tất cả các giá trị đều có
    if (!hospitalizationId || !date || !veterinarianId || !sessionId) {
        alert("Vui lòng điền đầy đủ thông tin trước khi đặt lịch.");
        return; // Dừng thực thi nếu có giá trị nào đó bị thiếu
    }

    const data = {
        visitDate: date,
        veterinarianId: veterinarianId,
        hospitalizationId: hospitalizationId,
        sessionId: sessionId
    }
    
    console.log(data);

    const response = await fetchWithToken(`${API_BASE_URL}/v1/visitschedules`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    });

    if (response.code == 1000) {
        alert("Đặt lịch thăm thành công");
        window.location.href = 'index.html';
    }
    else {
        alert("Đã có lỗi xảy ra: " + response.message);
    }
});

document.getElementById('confirm-update').addEventListener('click', async function () {
    data = {
        healthCondition: document.getElementById('status').value,
    }
    const urlParams = new URLSearchParams(window.location.search);
    const hospitalizationId = urlParams.get('id');
    const response = await fetchWithToken(`${API_BASE_URL}/v1/hospitalizations/${hospitalizationId}`, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })

    if(response.code === 1000) {
        alert("Cập nhật trạng thái thành công");
        window.location.reload();
    } else {
        alert("Đã có lỗi xảy ra: " + response.message);
    }
});

document.getElementById('confirm-discharged').addEventListener('click', async function () {
    const urlParams = new URLSearchParams(window.location.search);
    const hospitalizationId = urlParams.get('id');
    data = {
        endDate: new Date().toISOString().slice(0, 10),
    }
    const response = await fetchWithToken(`${API_BASE_URL}/v1/hospitalizations/${hospitalizationId}`, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })

    if(response.code === 1000) {
        alert("Xuất viện thành công");
        window.location.href = 'index.html';
    } else {
        alert("Đã có lỗi xảy ra: " + response.message);
    }
})