document.addEventListener('DOMContentLoaded', async function() {
    const staffName= document.getElementById('staff-name');
    const staffPhone= document.getElementById('staff-phone');
    const staffAddress= document.getElementById('staff-address');

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
        staffName.value=user.name;
        staffPhone.value=user.phoneNumber;
        staffAddress.value=user.address;

    }
})
document.getElementById('submitButton').addEventListener('click', async function(){
    const name = document.getElementById('staff-name').value;
    const phone = document.getElementById('staff-phone').value;
    const address = document.getElementById('staff-address').value;
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