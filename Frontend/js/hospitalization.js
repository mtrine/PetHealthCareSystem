document.addEventListener('DOMContentLoaded', async function () {
    const hospilizationContainer = document.querySelector('.hospitalized-list-body')
    const data = await fetchWithToken(`${API_BASE_URL}/v1/hospitalizations`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        },
    });

    if (data.code == 1000) {
        const hospitalizations = data.result;
        hospitalizations.forEach(hospitalization => {
            if (hospitalization.endDate != null) {
                return;
            }
            const hospitalizationItem = document.createElement('ul');
            hospitalizationItem.classList.add('hospitalized-item');
            hospitalizationItem.innerHTML = `
                <li>${hospitalization.id}</li>
                <li>${hospitalization.startDate}</li>
                <li>${hospitalization.cageResponse.cageNumber}</li>
                <li>${hospitalization.healthCondition}</li>
                `
            hospilizationContainer.appendChild(hospitalizationItem);
            const hospitalizedItems = document.querySelectorAll('.hospitalized-item');

            // Thêm sự kiện click cho từng mục
            hospitalizedItems.forEach(item => {
                item.addEventListener('click', function () {
                    window.location.href = 'detailHospitalized.html?id=' + hospitalization.id;
                });
            });
        })
    }
})