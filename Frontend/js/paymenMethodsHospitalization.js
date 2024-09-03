document.getElementById("pay").addEventListener("click", async function () {
    const selectedPaymentMethod = document.querySelector('input[name="payment-method"]:checked').value;
    const urlParams = new URLSearchParams(window.location.search);
    const hospitalizationId = urlParams.get('id');
    
    if (selectedPaymentMethod == "cash") {
        window.location.href = "cashForHospitalization.html?id=" + hospitalizationId ;
    } else if (selectedPaymentMethod == "vnpay") {
        const port = window.location.port;
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
    
        if(response.code === 1000) {
            const hosptalization= response.result;
            var amount = 0;
            const numberDay= new Date(hosptalization.endDate).getDate()-new Date(hosptalization.startDate).getDate();
            if(numberDay==0) {amount= hosptalization.cageResponse.unitPrice}
            else {amount= numberDay*hosptalization.cageResponse.unitPrice;}
            
                const dataForVNPay = {
                    hospitalizationId: hospitalizationId,
                    typePayment: "HOSPITALIZATION",
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
           
        } else {
            alert("Đã có lỗi xảy ra: " + response.message);
        }
    } else {
        alert("Please select a payment method.");
    }
})