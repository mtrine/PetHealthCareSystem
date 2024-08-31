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