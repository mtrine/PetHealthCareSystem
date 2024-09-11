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




    // Transition slider
    document.addEventListener('DOMContentLoaded', function () {
        const slides = document.querySelectorAll('.slide');
        const slideCount = slides.length;
        let currentIndex = 0;

        function showNextSlide() {
            currentIndex = (currentIndex + 1) % slideCount;
            const offset = -currentIndex * 100;
            document.querySelector('.slider-container').style.transform = `translateX(${offset}%)`;
        }

        setInterval(showNextSlide, 5000); // Chuyển slide mỗi 10 giây
    });

    // List service
    document.addEventListener('DOMContentLoaded', function () {
        const prevBtn = document.querySelector('.prev-btn');
        const nextBtn = document.querySelector('.next-btn');
        const serviceList = document.querySelector('.service-list');
        const serviceItems = document.querySelectorAll('.service-item');
        const itemsToShow = 4;
        const itemWidth = 282; // Điều chỉnh giá trị này dựa trên width và margin của card (192px width + 60px margin)
        const totalItems = serviceItems.length;
        let currentIndex = 0;

        prevBtn.addEventListener('click', function () {
            if (currentIndex > 0) {
                currentIndex--;
                updateServicePosition();
            }
        });

        nextBtn.addEventListener('click', function () {
            if (currentIndex < totalItems - itemsToShow) {
                currentIndex++;
                updateServicePosition();
            }
        });

        function updateServicePosition() {
            const offset = -currentIndex * itemWidth;
            serviceList.style.transform = `translateX(${offset}px)`;
            checkButtons();
        }

        function checkButtons() {
            if (currentIndex === 0) {
                prevBtn.classList.add('disabled');
            } else {
                prevBtn.classList.remove('disabled');
            }

            if (currentIndex >= totalItems - itemsToShow) {
                nextBtn.classList.add('disabled');
            } else {
                nextBtn.classList.remove('disabled');
            }
        }

        // Kiểm tra ban đầu khi tải trang
        checkButtons();
    });
document.querySelector("#logout").addEventListener("click", async function () {
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
            window.location.href = 'index.html'; // Chuyển hướng đến trang sau khi đăng nhập thành công
        } else {
            throw new Error(data.message || 'Đăng xuất thất bại');
        }

    } catch (error) {
        console.error('Lỗi:', error);
        alert('Đăng xuất thất bại');
    }
})

async function navigateRole() {
    if (authToken) {
        const userRole = await getUserRole(); // Fetch or decode the user role
        if (userRole) {
            switch (userRole) {
                case 'ADMIN':
                    window.location.href = '/Admin/index.html'; // Admin page
                    break;
                case 'STAFF':
                    window.location.href = '/Staff/index.html'; // Customer page
                    break;
                case 'VERTERINAIAN':
                    window.location.href = '/Doctor/index.html'; // Customer page
                    break;
                default:
                    break;
                     // Default page if role is not identified
            }
        }
    } else {
        showModal();
    }
}

// Function to decode the token and get the role
function decodeToken(token) {
    const payloadBase64 = token.split('.')[1];
    const payload = atob(payloadBase64);
    const parsedPayload = JSON.parse(payload);
    return parsedPayload.role; // Adjust this based on how your token is structured
}

// Fetch user role from API (if necessary)
async function getUserRole() {
    try {
        // Option 1: Decode the token to get the role (if role is embedded)
        const role = decodeToken(authToken);
        if (role) return role;

        // Option 2: Fetch user details from an API
        const response = await fetchWithToken(`${API_BASE_URL}/v1/users/my-info`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (response.code===1000) {
            const data = response.result;
            console.log('User details:', data);
            return data.role;
        } else {
            throw new Error('Failed to fetch user details');
        }
    } catch (error) {
        console.error('Error fetching user role:', error);
        return null;
    }
}



navigateRole()