document.addEventListener('DOMContentLoaded', async function() {
    await getAllUser()
    const deleteButton=document.querySelector('#confirm-delete');
    deleteButton.addEventListener('click',deleteUser);
    const addButton=document.querySelector('#submitButton');
    const notificationMessage = document.getElementById('notificationMessage');
    addButton.addEventListener('click',addUser);
});
async function getAllUser(){
    const userContainer = document.querySelector('.account-list-body');
    const data=await fetchWithToken(`${API_BASE_URL}/v1/users`);
    if(data.code==1000){
        const result=data.result;
        userContainer.innerHTML='';
        for(const user of result){
            const userElement=document.createElement('ul');
            userElement.classList.add('account-item');
            var role='';
            if(user.role=='ADMIN') return
            else if(user.role=='CUSTOMER') role='KHÁCH HÀNG';
            else if(user.role=='STAFF') role='NHÂN VIÊN';
            else role='BÁC SĨ';
            userElement.innerHTML=`
                  <label class="checkbox">
                            <input type="checkbox" id="checkbox" name="role" value="${user.id}" class="role-checkbox">
                            <span class="checkmark"></span>
                        </label>
                        <li>${user.id}</li>
                        <li>${user.email}</li>
                        <li>${user.phoneNumber}</li>
                        <li>${role}</li>
            `;
            userContainer.appendChild(userElement);
        }
    }
}

async function deleteUser(){
    const selectedItems = document.querySelectorAll('input[name=role]:checked');
    const userDelete=[];
    if(selectedItems.length==0){
        alert('Vui lòng chọn tài khoản cần xóa');
        return;
    }
    selectedItems.forEach(item=>{
        userDelete.push(item.value);
    });
    
    userDelete.forEach(async user=>{
        const data=await fetchWithToken(`${API_BASE_URL}/v1/users/${user}`,{
            method:'DELETE',
            headers:{
                'Content-Type':'application/json'
            }
        });
        if(data.code==1000){
            alert('Xóa tài khoản thành công');
            getAllUser();
        }
        else{
            alert(data.message);
        }
    })

}

async function addUser(){
    const name=document.querySelector('#customer-name').value;
    const email=document.querySelector('#customer-email').value;
    const phoneNumber=document.querySelector('#customer-phone').value;
    const address=document.querySelector('#customer-address').value;
    const genderRadios = document.querySelectorAll('input[name="gender"]');
    var gender = false;
    // Tìm radio được checked
    genderRadios.forEach(radio => {
        if (radio.checked) {
            gender= radio.value ;
        }
    });
    const password=document.querySelector('#customer-password').value;
    const roleRadios=document.querySelectorAll('input[name="role"] ');
    var role='';
    roleRadios.forEach(radio=>{
        if(radio.checked){
            role=radio.value;
        }
    });
    let message = '';
    if(name==''||email==''||phoneNumber==''||address==''||gender==''||password==''||role==''){
        alert('Vui lòng nhập đầy đủ thông tin');
        return;}
    const data={
        name:name,
        email:email,
        phoneNumber:phoneNumber,
        address:address,
        sex:gender,
        password:password,
        role:role
    }
    console.log(data);
    const dataUser=await fetchWithToken(`${API_BASE_URL}/v1/auth/register`,{
        method:'POST',
        headers:{
            'Content-Type':'application/json'
        },
        body:JSON.stringify(data)
    });
    if(dataUser.code==1000){
        message = 'Thêm thành công!';
        notificationMessage.className = 'notification-message success';
        getAllUser();
    }
    
    else{
        message = 'Có lỗi xảy ra. Vui lòng thử lại!';
        notificationMessage.className = 'notification-message error';
        console.log(dataUser.message);
    }
    notificationMessage.textContent = message;
    notificationMessage.style.display = 'flex';

    // Optionally, hide the message after a few seconds
    setTimeout(function () {
        notificationMessage.style.display = 'none';
    }, 3000); // 3 seconds
}