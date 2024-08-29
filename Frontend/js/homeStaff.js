document.addEventListener('DOMContentLoaded', () => {
    // Lấy các phần tử cần thiết
    const sections = {
        defaultSection: document.querySelector('.default-section'),
        cageSection: document.querySelector('.cage-section'),
        bookingSection: document.querySelector('.booking-section'),
        petHospitalizedSection: document.querySelector('.pet-hospitalized-section')
    };

    const sidebarItems = {
        cageItem: document.getElementById('cage-item'),
        bookingItem: document.getElementById('booking-item'),
        hospitalizedItem: document.getElementById('hospitalized-item'),
        logo: document.querySelector('.sidebar-logo img')
    };

    // Hàm để ẩn tất cả các phần và chỉ hiển thị phần được chọn
    function showSection(sectionToShow) {
        Object.values(sections).forEach(section => {
            section.style.display = 'none'; // Ẩn tất cả các phần
        });
        sectionToShow.style.display = 'block'; // Hiển thị phần được chọn
    }

    // Cài đặt sự kiện click cho các mục bên thanh bên
    sidebarItems.cageItem.addEventListener('click', () => showSection(sections.cageSection));
    sidebarItems.bookingItem.addEventListener('click', () => showSection(sections.bookingSection));
    sidebarItems.hospitalizedItem.addEventListener('click', () => showSection(sections.petHospitalizedSection));
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


// Hiên cage modal
document.addEventListener('DOMContentLoaded', function () {
    var cageItem = document.querySelector('.cage-item');
    var modal = document.querySelector('.modal-cage');
    var modalSection = document.querySelector('.modal-cage-section');
    var petIdInput = document.querySelector('#pet-id');
    var inputIdPetContainer = document.querySelector('.input-id-pet');
    var fullCheckbox = document.querySelector('#full');
    var emptyCheckbox = document.querySelector('#empty');

    // Theo dõi thay đổi của checkbox 'Trống'
    emptyCheckbox.addEventListener('change', function () {
        if (this.checked) {
            inputIdPetContainer.style.display = 'flex';
        } else {
            inputIdPetContainer.style.display = 'none';
        }
    });

    // Theo dõi thay đổi của checkbox 'Đầy' và tự động kiểm tra checkbox 'Trống'
    fullCheckbox.addEventListener('change', function () {
        if (this.checked) {
            emptyCheckbox.checked = false;
            inputIdPetContainer.style.display = 'none';
        }
    });

    // Đóng modal khi nhấn vào khu vực ngoài modal
    window.addEventListener('click', function (event) {
        if (event.target === modal) {
            modal.style.display = 'none';
        }
    });

    // Ngăn chặn sự kiện click trên modal-cage-section
    modalSection.addEventListener('click', function (event) {
        event.stopPropagation();
    });


});

// Chuyển sang add booking
// document.getElementById('addBookingBtn').addEventListener('click', function () {
//     window.location.href = 'addBooking.html';
// });

const addBookingBtn = document.getElementById('addBookingBtn');
const modalBooking = document.getElementById('modalBooking');

// Khi nhấn vào nút "Thêm"
addBookingBtn.addEventListener('click', function () {
    modalBooking.style.display = 'flex'; // Hiển thị modal
});

// Đóng modal khi nhấn ra ngoài modal
window.addEventListener('click', function (event) {
    if (event.target === modalBooking) {
        modalBooking.style.display = 'none'; // Ẩn modal
    }
});

// Lắng nghe sự kiện khi nút "Xác nhận" được nhấn
document.getElementById('confirmBookingBtn').addEventListener('click', function() {
    // Kiểm tra checkbox nào được chọn
    const unreCustomerCheckbox = document.getElementById('unre-customer').checked;
    const customerCheckbox = document.getElementById('customer').checked;
    
    // Điều hướng đến trang tương ứng
    if (unreCustomerCheckbox) {
        window.location.href = 'addBooking2.html';
    } else if (customerCheckbox) {
        window.location.href = 'addBooking.html';
    } else {
        alert('Vui lòng chọn một loại khách trước khi xác nhận.');
    }
});


// Lấy tất cả các mục hospitalized-item


const checkboxes = document.querySelectorAll('.role-checkbox');

// Thêm sự kiện change cho từng checkbox
checkboxes.forEach(checkbox => {
    checkbox.addEventListener('change', () => {
        // Nếu checkbox được chọn, bỏ chọn các checkbox khác
        if (checkbox.checked) {
            checkboxes.forEach(cb => {
                if (cb !== checkbox) {
                    cb.checked = false;
                }
            });
        }
    });
});

document.querySelector('.add-hospitalized-btn').addEventListener('click', function () {
    window.location.href = 'addHospitalized.html';
})