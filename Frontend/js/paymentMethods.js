document.querySelector('#pay').addEventListener('click', function () {
    const isChecked = document.getElementById('vnpay').checked;
    const amount = document.getElementById('amount').value;
    if (!isChecked) {
        alert('Vui lòng chọn phương thức thanh toán');
        return;
    }
    const data = fetchWithToken(`${API_BASE_URL}/vn-pay?amount=${1000}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        },
    });
});