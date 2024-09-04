document.addEventListener('DOMContentLoaded', async function() {
    const staffName= document.getElementById('staff-name');
    const staffEmail= document.querySelector('.staff-email');
    const staffPhone= document.getElementById('staff-phone');
    const staffAddress= document.querySelector('.staff-address');
    const staffGender=document.querySelector('.staff-gender');

    const data = await fetchWithToken(`${API_BASE_URL}/v1/users/my-info`, {
        method: 'GET',
        header: {
            'Content-Type': 'application/json'
        }
    }
    );
    if(data.code==1000){
        const user= data.result;
        if(user.sex){
            staffGender.innerHTML="Nam";
        }
        else{
            staffGender.innerHTML="Nữ";
        }
        staffName.innerHTML=user.name;
        staffEmail.innerHTML=user.email;
        staffPhone.innerHTML=user.phoneNumber;
        staffAddress.innerHTML=user.address;

    }
})