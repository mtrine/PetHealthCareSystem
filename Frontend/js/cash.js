var amount = 0;
document.addEventListener('DOMContentLoaded', async function () {
    const urlParams = new URLSearchParams(window.location.search);
    const appointmentId = urlParams.get('id');
    const data = await fetchWithToken(`${API_BASE_URL}/v1/appointments/${appointmentId}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        },
    });
    console.log(data);
    if (data.code == 1000) {
        const appointment = data.result;
        document.getElementById("user-id").innerText = appointment.pet.customerResponse.id;
        document.getElementById("user-name").innerText = appointment.pet.customerResponse.name;
        document.getElementById("pet-id").innerText = appointment.pet.id;
        const serviceItem = document.getElementById("service");
        serviceItem.innerText = "";
        appointment.servicesResponsesList.forEach(service => {
            if (serviceItem.innerText === "") {
                serviceItem.innerText += `
                    ${service.name}
                `;
            }
            else {
                serviceItem.innerText += `
                , ${service.name}
            `;
            }
            amount+=service.unitPrice;
        })
        document.getElementById("veterinarian-name").innerText = appointment.veterinarianName;
        document.getElementById("time").innerText = appointment.appointmentDate + " " + appointment.sessionResponse.startTime + "-" + appointment.sessionResponse.endTime;
        document.getElementById("deposit-amount").innerText = appointment.deposit + " VND";
        document.getElementById("total-all-amount").innerText = amount + " VND";
        document.getElementById("total-amount").innerText = amount - appointment.deposit + " VND";
    } else {
        alert(data.message);
    }
})

document.querySelector(".confirm-button").addEventListener("click", async function () {
    const urlParams = new URLSearchParams(window.location.search);
    const appointmentId = urlParams.get('id');
    const paymentMethod = {
        totalAmount:amount,
        appointmentId: appointmentId,
        typePayment: "APPOINTMENT"
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