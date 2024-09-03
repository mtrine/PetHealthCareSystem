document.getElementById("pay").addEventListener("click", async function () {
    const selectedPaymentMethod = document.querySelector('input[name="payment-method"]:checked').value;
    const urlParams = new URLSearchParams(window.location.search);
    const appointmentId = urlParams.get('id');
    if (selectedPaymentMethod == "cash") {
        
        window.location.href = "cash.html?id=" + appointmentId;
    } else if (selectedPaymentMethod == "vnpay") {

        const port = window.location.port;
        const data = await fetchWithToken(`${API_BASE_URL}/v1/appointments/${appointmentId}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            },
        });

        if (data.code == 1000) {
            const appointment = data.result;
            var amount = 0;
            appointment.servicesResponsesList.forEach(service => {
                amount += service.unitPrice;

            });
            amount =amount- appointment.deposit;
            if (amount <= 0) {
                window.location.href = "bookingSuccessForStaff.html";
            }
            else {
                const dataForVNPay = {
                    appointmentId: appointmentId,
                    typePayment: "APPOINTMENT",

                }

                const dataVNPay = await fetchWithToken(`${API_BASE_URL}/v1/payments/vn-pay?amount=${amount}&port=${port}`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(dataForVNPay)
                });
                if (dataVNPay.code == 1000) {
                    const url = dataVNPay.result;
                    window.location.href = url.paymentUrl;
                } else {
                    alert(dataVNPay.message);
                }
            }
        }
        else {
            alert(dataVNPay.message);
        }
    } else {
        alert("Please select a payment method.");
    }
})