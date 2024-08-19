var authToken=localStorage.getItem("authToken")

    function navigate(url) {
        var authToken = localStorage.getItem("authToken");

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