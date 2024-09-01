var amount = 0;
document.addEventListener('DOMContentLoaded', async function () {
    const urlParams = new URLSearchParams(window.location.search);
    const hospitalizationId = urlParams.get('id');
    const data = {
        endDate: new Date().toISOString().slice(0, 10),
    }
    const response = await fetchWithToken(`${API_BASE_URL}/v1/hospitalizations/${hospitalizationId}`, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })

    if (response.code == 1000) {
        const hospitalization = response.result;
        const startDate = new Date(hospitalization.startDate);
        const endDate = new Date(hospitalization.endDate);

        // Tính số ngày chính xác giữa hai ngày
        const numberDay = Math.ceil((endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24));
        document.getElementById("user-id").innerText = hospitalization.petResponse.customerResponse.id;
        document.getElementById("user-name").innerText = hospitalization.petResponse.customerResponse.name;
        document.getElementById("pet-id").innerText = hospitalization.petResponse.id;
        document.getElementById("service").innerText = 'Nhập viện';
        document.getElementById("time").innerText = hospitalization.startDate + " - " + hospitalization.endDate;
        document.getElementById("deposit-amount").innerText = hospitalization.cageResponse.unitPrice + " VND";
        document.getElementById("total-all-amount").innerText = numberDay == 0 ? 1 : numberDay
        amount = numberDay == 0 ? hospitalization.cageResponse.unitPrice : numberDay * hospitalization.cageResponse.unitPrice;
        document.getElementById("total-amount").innerText = amount;
    } else {
        alert(data.message);
    }
})

document.querySelector(".confirm-button").addEventListener("click", async function () {
    const urlParams = new URLSearchParams(window.location.search);
    const hospitalizationId = urlParams.get('id');
    const paymentMethod = {
        totalAmount: amount,
        hospitalizationId: hospitalizationId,
        typePayment: "HOSPITALIZATION"
    };
    const data = await fetchWithToken(`${API_BASE_URL}/v1/payments/cash`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(paymentMethod)
    });
    if (data.code == 1000) {
        window.location.href = "bookingSuccessForStaff.html";
    } else {
        window.location.href = "bookingFailedForStaff.html";

    }
})