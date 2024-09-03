document.addEventListener('DOMContentLoaded', () => {
    // Lấy các phần tử cần thiết
    const sections = {
        defaultSection: document.querySelector('.default-section'),
        accountSection: document.querySelector('.account-section'),
        revenueSection: document.querySelector('.revenue-section'),
        addAccSection: document.querySelector('.add-account-section')
    };

    const sidebarItems = {
        accountItem: document.getElementById('account-item'),
        revenueItem: document.getElementById('revenue-item'),
        logo: document.querySelector('.sidebar-logo img')
    };

    const addAccountBtn = document.getElementById('addAccountBtn')

    // Hàm để ẩn tất cả các phần và chỉ hiển thị phần được chọn
    function showSection(sectionToShow) {
        Object.values(sections).forEach(section => {
            section.style.display = 'none'; // Ẩn tất cả các phần
        });
        sectionToShow.style.display = 'block'; // Hiển thị phần được chọn
    }

    // Cài đặt sự kiện click cho các mục bên thanh bên
    sidebarItems.accountItem.addEventListener('click', () => showSection(sections.accountSection));

    sidebarItems.revenueItem.addEventListener('click', () => showSection(sections.revenueSection));

    // Cài đặt sự kiện click cho nút thêm tài khoản
    addAccountBtn.addEventListener('click', () => showSection(sections.addAccSection))

    // Cài đặt sự kiện click cho logo để trở về default-section
    sidebarItems.logo.addEventListener('click', () => {
        // Hiển thị phần default-section với display flex
        sections.defaultSection.style.display = 'flex';
        // Ẩn tất cả các phần khác
        Object.values(sections).forEach(section => {
            if (section !== sections.defaultSection) {
                section.style.display = 'none';
            }
        });
    });

    // Hiển thị phần default-section khi trang mới tải
    document.querySelector('.default-section').style.display = 'flex';
});


document.getElementById('deleteAccountBtn').addEventListener('click', function () {
    // Kiểm tra có checkbox nào được chọn hay không
    const checkboxes = document.querySelectorAll('.role-checkbox');
    const isChecked = Array.from(checkboxes).some(checkbox => checkbox.checked);

    if (isChecked) {
        // Nếu có checkbox được chọn, hiện bảng delete modal
        document.getElementById('delete-modal').style.display = 'flex';
    }
});

// Đóng modal khi nhấn nút hủy
document.getElementById('cancel-delete').addEventListener('click', function () {
    document.getElementById('delete-modal').style.display = 'none';
});

// Đóng modal khi xác nhận xóa
document.getElementById('confirm-delete').addEventListener('click', function () {
    // Xử lý logic xóa tài khoản ở đây
    document.getElementById('delete-modal').style.display = 'none';
});

document.addEventListener('DOMContentLoaded', function () {
    const submitButton = document.getElementById('submitButton');
    const notificationMessage = document.getElementById('notificationMessage');

    submitButton.addEventListener('click', function () {
        // Simulate a form submission or any other logic
        let isSuccess = true; // Set this based on your logic
        let message = '';

        if (isSuccess) {
            message = 'Thêm thành công!';
            notificationMessage.className = 'notification-message success';
        } else {
            message = 'Có lỗi xảy ra. Vui lòng thử lại!';
            notificationMessage.className = 'notification-message error';
        }

        // Update and display the message
        notificationMessage.textContent = message;
        notificationMessage.style.display = 'flex';

        // Optionally, hide the message after a few seconds
        setTimeout(function () {
            notificationMessage.style.display = 'none';
        }, 3000); // 3 seconds
    });
});
// Revenue section
function toggleRevenueSection(role) {
    const daySection = document.querySelector('.day-revenue-section');
    const monthSection = document.querySelector('.month-revenue-section');
    const yearSection = document.querySelector('.year-revenue-section');

    // Reset all sections to hidden
    daySection.style.display = 'none';
    monthSection.style.display = 'none';
    yearSection.style.display = 'none';

    // Show the selected section
    if (role === 'day') {
        daySection.style.display = 'block';
    } else if (role === 'month') {
        monthSection.style.display = 'block';
    } else if (role === 'year') {
        yearSection.style.display = 'block';
    }
}