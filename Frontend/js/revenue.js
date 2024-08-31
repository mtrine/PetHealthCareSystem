document.addEventListener('DOMContentLoaded', async function () {
    await getRevenueDay();
    await getRevenueMonth();
    await getRevenueYear();
});
async function getRevenueDay() {
    const today = new Date();
    const formattedDate = today.toISOString().split('T')[0]; // Định dạng ngày tháng theo yyyy-MM-dd
    const dayRevenueContainer = document.querySelector('.day-revenue-body');
    
    try {
        const data= await fetchWithToken(`${API_BASE_URL}/v1/payments/revenue/day?startDate=2024-07-01&endDate=${formattedDate}`);

        if (data.code === 1000) {
            const result = data.result;
            dayRevenueContainer.innerHTML = '';
            for (const [date, revenue] of Object.entries(result)) {
                const dateElement = document.createElement('ul');
                dateElement.classList.add('day-revenue-item');
                dateElement.innerHTML = `
                    <li>${date}</li>
                    <li>${revenue.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' })}</li>`;
                dayRevenueContainer.appendChild(dateElement);
            }
        } else {
            console.error('Failed to fetch data:', data.message);
        }
    } catch (error) {
        console.error('Error fetching data:', error);
    }
}
async function getRevenueMonth() {
    const thisYear= new Date().getFullYear();
    const monthRevenueContainer = document.querySelector('.month-revenue-body');
    try {
        const data= await fetchWithToken(`${API_BASE_URL}/v1/payments/revenue/month?year=${thisYear}`);

        if (data.code === 1000) {
            const result = data.result;
            monthRevenueContainer.innerHTML = '';
            for (const [month, revenue] of Object.entries(result)) {
                const dateElement = document.createElement('ul');
                dateElement.classList.add('month-revenue-item');
                dateElement.innerHTML = `
                    <li>Tháng ${month}/${thisYear}</li>
                    <li>${revenue.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' })}</li>`;
                    monthRevenueContainer.appendChild(dateElement);
            }
        } else {
            console.error('Failed to fetch data:', data.message);
        }
    } catch (error) {
        console.error('Error fetching data:', error);
    }
}

async function getRevenueYear(){
    const thisYear= new Date().getFullYear();
    const yearRevenueContainer = document.querySelector('.year-revenue-body');
    try {
        const data= await fetchWithToken(`${API_BASE_URL}/v1/payments/revenue/year?endYear=${thisYear}`);

        if (data.code === 1000) {
            const result = data.result;
            yearRevenueContainer.innerHTML = '';
            for (const [year, revenue] of Object.entries(result)) {
                const dateElement = document.createElement('ul');
                dateElement.classList.add('month-revenue-item');
                dateElement.innerHTML = `
                    <li>Năm ${year}</li>
                    <li>${revenue.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' })}</li>`;
                    yearRevenueContainer.appendChild(dateElement);
            }
        } else {
            console.error('Failed to fetch data:', data.message);
        }
    } catch (error) {
        console.error('Error fetching data:', error);
    }
}