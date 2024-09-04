document.addEventListener('DOMContentLoaded', async function() {
    const doctorName= document.getElementById('doctor-name');
    const doctorPhone= document.getElementById('doctor-phone');
    const doctorAddress= document.getElementById('doctor-address');

    const data = await fetchWithToken(`${API_BASE_URL}/v1/users/my-info`, {
        method: 'GET',
        header: {
            'Content-Type': 'application/json'
        }
    }
    );
    if(data.code==1000){
        const user= data.result;
        if (user.sex) {
            document.getElementById('gender-male').checked = true;
            document.getElementById('gender-female').checked = false;
          } else {
            document.getElementById('gender-male').checked = false;
            document.getElementById('gender-female').checked = true;
          }
        doctorName.value=user.name;
        doctorPhone.value=user.phoneNumber;
        doctorAddress.value=user.address;

    }
})
document.getElementById('submitButton').addEventListener('click', async function(){
    const name = document.getElementById('doctor-name').value;
    const phone = document.getElementById('doctor-phone').value;
    const address = document.getElementById('doctor-address').value;
    const  genderCheckBox=document.querySelector('input[name=gender]:checked').value;
    var sex=false
    if(genderCheckBox=='nam'){
        sex=true;
    }
    const body={
        name: name,
        sex:sex,
        phoneNumber: phone,
        address: address
    }
    const data = await fetchWithToken(`${API_BASE_URL}/v1/users/my-info`, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(body)
    });
    if(data.code==1000){
        alert('Cập nhật thông tin thành công');
    }
    else{
        alert('Cập nhật thông tin thất bại');
        console.log(data.message);
    }
});