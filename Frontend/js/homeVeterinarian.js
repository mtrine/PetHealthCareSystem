// Get elements
const bookingItem = document.getElementById('booking-item');
const hospitalizedItem = document.getElementById('hospitalized-item');
const doctorItem = document.getElementById('doctor-info-item');
const defaultSection = document.querySelector('.default-section');
const bookingSection = document.querySelector('.booking-section');
const petHospitalizedSection = document.querySelector('.pet-hospitalized-section');
const doctorSection = document.querySelector('#doctor-info-section');

// Event listeners for sidebar items
bookingItem.addEventListener('click', () => {
    // Hide all sections first
    defaultSection.style.display = 'none';
    bookingSection.style.display = 'block';
    petHospitalizedSection.style.display = 'none';
    doctorSection.style.display = 'none';
});

hospitalizedItem.addEventListener('click', () => {
    // Hide all sections first
    defaultSection.style.display = 'none';
    bookingSection.style.display = 'none';
    petHospitalizedSection.style.display = 'block';
    doctorSection.style.display = 'none';
});

doctorItem.addEventListener('click', () => {
    // Hide all sections first
    defaultSection.style.display = 'none';
    bookingSection.style.display = 'none';
    petHospitalizedSection.style.display = 'none';
    doctorSection.style.display = 'block';
});

// Chuyển sang EditDoctor
function showEditInfo() {
    window.location.href = 'EditDoctor.html';
}


document.addEventListener('DOMContentLoaded', () => {
    const addBookingBtn = document.getElementById('addBookingBtn');
    const addhospitalizedbtn = document.getElementById('addhospitalizedbtn');
    const confirmationModal = document.getElementById('confirmation-modal');
    const closeBtn = document.querySelector('.modal .close');
    const cancelBtn = document.getElementById('cancel-btn');
    const confirmBtn = document.getElementById('confirm-btn');

    // Function to open modal
    function openModal() {
        confirmationModal.style.display = 'block';
    }
    // Function to close modal
    function closeModal() {
        confirmationModal.style.display = 'none';
    }
    // Event listener for Add Booking button
    addBookingBtn.addEventListener('click', openModal);
    // addhospitalizedbtn.addEventListener('click', openModal);

    // Event listener for Cancel button in modal
    cancelBtn.addEventListener('click', closeModal);
    // Event listener for Confirm button in modal
    confirmBtn.addEventListener('click', () => {

        closeModal();
    });

});

document.querySelector("#logout").addEventListener("click", async function () {
    var authToken=localStorage.getItem("authToken")
    
        try {
            const response = await fetch(`${API_BASE_URL}/v1/auth/logout`, { // Thay thế URL bằng API của bạn
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                   token:authToken
                }),
            });
    
            if (!response.ok) {
                throw new Error('Đăng xuất thất bại');
            }
    
            const data = await response.json();
    
            // Kiểm tra mã phản hồi
            if (data.code === 1000) {
                // Xử lý dữ liệu nhận được từ API
                console.log('Đăng xuất thành công');
    
                // Lưu token vào localStorage và chuyển hướng người dùng
                localStorage.removeItem('authToken');
                window.location.href = '/index.html'; // Chuyển hướng đến trang sau khi đăng nhập thành công
            } else {
                throw new Error(data.message || 'Đăng xuất thất bại');
            }
    
        } catch (error) {
            console.error('Lỗi:', error);
            alert('Đăng xuất thất bại');
        }
    })