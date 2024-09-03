document.addEventListener('DOMContentLoaded', async function () {
    const cageContainer = document.querySelector('.cage-list-body');
    const data = await fetchWithToken(`${API_BASE_URL}/v1/cages`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        },
    });

    if (data.code === 1000) {
        const cages = data.result;
        cages.forEach(cage => {
            const cageItem = document.createElement('ul');
            cageItem.classList.add('cage-item');
            cageItem.innerHTML = `
                <li>${cage.cageNumber}</li>
                <li>${cage.status ? "Đầy" : "Trống"}</li>
            `;
            cageContainer.appendChild(cageItem);

            // Xử lý sự kiện click cho từng cage-item
            cageItem.addEventListener('click', function () {
                const modal = document.querySelector('.modal-cage');
                const fullCheckbox = modal.querySelector('#full');
                const emptyCheckbox = modal.querySelector('#empty');
                const inputIdPetContainer = modal.querySelector('.input-id-pet');

                // Cập nhật trạng thái của các checkbox dựa trên cage.status
                if (cage.status) {
                    fullCheckbox.checked = true;
                    emptyCheckbox.checked = false;
                    inputIdPetContainer.style.display = 'none';
                } else {
                    fullCheckbox.checked = false;
                    emptyCheckbox.checked = true;
                    inputIdPetContainer.style.display = 'flex';
                }

                // Mở modal
                modal.style.display = 'flex';
            });
        });
    }
});