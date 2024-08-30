var authToken=localStorage.getItem("authToken")

    function navigate(url) {
        

        if (authToken) {
            window.location.href = url;
        } else {
            showModal();
        }
    }

    function showModal() {
        document.getElementById('login-modal').style.display = 'flex';
    }


    // Ẩn modal
    function hideModal() {
        document.getElementById('login-modal').style.display = 'none';
    }

    function login() {
        window.location.href = 'login.html';
        hideModal();
    }

    document.addEventListener('DOMContentLoaded', function () {
        const headerBtn = document.querySelector('.header-btn');
        const subNav = document.querySelector('.sub-nav');
        const subNavLoggedIn = document.querySelector('.sub-nav.logged-in');
        if(authToken){
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
        }
        else{
            headerBtn.addEventListener('click', function () {
            // Toggle hiển thị sub-nav
            subNav.style.display = (subNav.style.display === 'flex' ? 'none' : 'flex');
        });

            // Đóng sub-nav khi nhấp ra ngoài
            document.addEventListener('click', function (event) {
                if (!headerBtn.contains(event.target)) {
                    subNav.style.display = 'none';
                }   
            });}

    });

document.querySelector('#pay').addEventListener('click', async function () {
    const isChecked = document.getElementById('vnpay').checked;
    const urlParams = new URLSearchParams(window.location.search);
    const appointmentId = urlParams.get('id');
    const port = window.location.port;
    if (!isChecked) {
        alert('Vui lòng chọn phương thức thanh toán');
        return;
    }
    
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
        const dataForVNPay = {
            appointmentId: appointmentId,
            typePayment: "APPOINTMENT",
            
        }

        const dataVNPay =  await fetchWithToken(`${API_BASE_URL}/v1/payments/vn-pay?amount=${amount}&port=${port}`, {
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
    else{
        alert(dataVNPay.message);
    }
   
});

function navigate(url) {
        

    if (authToken) {
        window.location.href = url;
    } else {
        showModal();
    }
}