document.addEventListener('DOMContentLoaded', async function() {
    getAllUser()
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
                            <input type="checkbox" id="checkbox" name="role" value="${user}" class="role-checkbox">
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