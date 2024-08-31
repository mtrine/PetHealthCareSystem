document.addEventListener('DOMContentLoaded', async function () {
    const urlParams = new URLSearchParams(window.location.search);
    const hospitalizationId = urlParams.get('hospitalizationId');
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

    // Lấy các nút
    const updateBtn = document.getElementById('update');

    // Lấy các nút trong modal

    const cancelUpdate = document.getElementById('cancel-update');
   

    // Hiển thị modal xóa khi nhấn nút xóa
    updateBtn.onclick = function () {
        updateModal.style.display = 'flex';
    }


    // Đóng modal khi nhấn vào nút hủy
    cancelUpdate.onclick = function () {
        updateModal.style.display = 'none';
    }

})

document.getElementById('confirm-update').addEventListener('click', async function () {
    data = {
        healthCondition: document.getElementById('status').value,
    }
    const urlParams = new URLSearchParams(window.location.search);
    const hospitalizationId = urlParams.get('hospitalizationId');
    const response = await fetchWithToken(`${API_BASE_URL}/v1/hospitalizations/${hospitalizationId}`, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })

    if(response.code === 1000) {
        changeStatusVisítchedule();
        alert("Cập nhật thành công");
        window.location.reload();
    } else {
        alert("Đã có lỗi xảy ra: " + response.message);
    }
});

async function changeStatusVisítchedule(){
    const urlParams = new URLSearchParams(window.location.search);
    const visitScheduleId = urlParams.get('visitScheduleId');

    const data= {
        status: true
    }

    const response = await fetchWithToken(`${API_BASE_URL}/v1/visitschedules/${visitScheduleId}`, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    });

    if(response.code === 1000) {
        console.log(response.result);
    } else {
        alert("Đã có lỗi xảy ra: " + response.message);
    }
}