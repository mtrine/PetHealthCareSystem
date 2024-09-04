document.addEventListener('DOMContentLoaded', async function() {
    const doctorName= document.getElementById('doctor-name');
    const doctorEmail= document.querySelector('.doctor-email');
    const doctorPhone= document.getElementById('doctor-phone');
    const doctorAddress= document.querySelector('.doctor-address');
    const doctorGender=document.querySelector('.doctor-gender');
    const doctorJob= document.querySelector('.staff-job');
    const data = await fetchWithToken(`${API_BASE_URL}/v1/veterinarians/my-info`, {
        method: 'GET',
        header: {
            'Content-Type': 'application/json'
        }
    }
    );
    if(data.code==1000){
        const user= data.result;
        
        if(user.sex){
            doctorGender.innerHTML="Nam";
        }
        else{
            doctorGender.innerHTML="Nữ";
        }

        doctorJob.innerHTML=user.isFulltime?"Bác sĩ full-time":"Bác sĩ part-time";
        doctorName.innerHTML=user.name;
        doctorEmail.innerHTML=user.email;
        doctorPhone.innerHTML=user.phoneNumber;
        doctorAddress.innerHTML=user.address;
    }
})