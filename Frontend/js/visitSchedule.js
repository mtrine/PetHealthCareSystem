document.addEventListener('DOMContentLoaded', async function () {
    const visitScheduleContainer = document.querySelector('.hospitalized-list-body');

    const data = await fetchWithToken(`${API_BASE_URL}/v1/visitschedules/my-visit-schedule`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    if (data.code == 1000) {
        const visitSchedules = data.result;
        console.log(visitSchedules);
        visitSchedules.forEach(visitSchedule => {
            const visitScheduleItem = document.createElement('ul');
            visitScheduleItem.classList.add('hospitalized-item');
            visitScheduleItem.innerHTML = `
                        <li>${visitSchedule.visitDate}</li>
                        <li>${visitSchedule.sessionResponse.startTime}-${visitSchedule.sessionResponse.endTime}</li>
                        <li>${visitSchedule.hospitalizationResponse.petResponse.id}</li>
                        <li>${visitSchedule.hospitalizationResponse.cageResponse.cageNumber}</li>
                        `
            visitScheduleContainer.appendChild(visitScheduleItem);
        })
    }
})