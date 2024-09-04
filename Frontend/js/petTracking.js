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
    document.querySelectorAll('.history-item').forEach(function (item) {
        item.addEventListener('click', function (event) {
            window.location.href = 'AppointmentDetails.html';
        });
    });
});
document.addEventListener("DOMContentLoaded", async function () {
    const hospitalizationContainer = document.querySelector('.hospitalized-list-container');
    const data= await fetchWithToken(`${API_BASE_URL}/v1/hospitalizations/my-hospitalization`, {
        method: 'GET',
        header: {
            'Content-Type': 'application/json'
        }
    });

    if(data.code==1000){
        const hospitalizations=data.result
        hospitalizations.forEach(hospitalization => {
            const hospitalizationItem= document.createElement('ul');
            hospitalizationItem.classList.add('hospitalized-item');

            hospitalizationItem.innerHTML=`
                    <li>${hospitalization.id}</li>
                    <li>${hospitalization.petResponse.name}</li>
                    <li>${hospitalization.startDate}</li>
                    <li>${hospitalization.cageResponse.cageNumber}</li>
                    <li>${hospitalization.healthCondition}</li>
            `
            hospitalizationContainer.appendChild(hospitalizationItem);
        })
    }
})
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