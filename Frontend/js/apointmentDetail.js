 // Bật subnav của customer
 document.addEventListener('DOMContentLoaded', function () {
    const headerBtn = document.querySelector('.header-btn');
    const subNavLoggedIn = document.querySelector('.sub-nav.logged-in');

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
});
// document.querySelectorAll('.rating input').forEach(radio => {
//     radio.addEventListener('change', function () {
//         const selectedRating = document.querySelector('.rating input:checked').value;
//         console.log("Số sao được chọn: " + selectedRating);
//         // Bạn có thể sử dụng giá trị này để lưu vào cơ sở dữ liệu hoặc xử lý theo ý muốn
//     });
// });
document.addEventListener('DOMContentLoaded', async function () {
    const urlParams = new URLSearchParams(window.location.search);
    const appointmentId = urlParams.get('id');
    const data = await fetchWithToken(`${API_BASE_URL}/v1/appointments/${appointmentId}`,{
        method:'GET',
        headers:{
            'Content-Type':'application/json'
        }
    });

    if(data.code==1000){
        const appointment=data.result;
        document.querySelector('#date').innerText = appointment.appointmentDate;
        document.querySelector('#doctor-name').innerText = appointment.veterinarianName;
        document.querySelector('.pet-name').innerText = appointment.pet.name;
        document.querySelector('.service-name').innerText = "";
        appointment.servicesResponsesList.forEach(service => {
            if(document.querySelector('.service-name').innerText == ""){
                document.querySelector('.service-name').innerText = service.name;
            }
            else{
                document.querySelector('.service-name').innerText += ", " + service.name;
            }
        });
    }
    else{
        alert(data.message);
    }
})

document.querySelector('#create-review').addEventListener('click', async function(){
    const urlParams = new URLSearchParams(window.location.search);
    const appointmentId = urlParams.get('id');
    const ratingElement = document.querySelector('.rating input:checked');
    const review = document.querySelector('#comment').value;
    if (!ratingElement) {
        alert('Vui lòng chọn số sao.');
        return; // Dừng việc thực thi nếu rating là null
    }

    const rating = ratingElement.value;
    const data={
        appointmentId: appointmentId,
        grades: rating,
        comments: review
    }

    const dataReview = await fetchWithToken(`${API_BASE_URL}/v1/reviews/my-reviews`,{
        method:'POST',
        headers:{
            'Content-Type':'application/json'
        },
        body: JSON.stringify(data)
    });
    
    if(dataReview.code==1000){
        alert('Tạo đánh giá thành công');
        window.location.href = 'appointmentTracking.html';
    }
    else{
        alert(data.message);
    }
});

function navigate(url) {
        

    if (authToken) {
        window.location.href = url;
    } else {
        showModal();
    }
}