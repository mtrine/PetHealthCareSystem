// Get elements
const bookingItem = document.getElementById('booking-item');
const hospitalizedItem = document.getElementById('hospitalized-item');
const defaultSection = document.querySelector('.default-section');
const bookingSection = document.querySelector('.booking-section');
const petHospitalizedSection = document.querySelector('.pet-hospitalized-section');

// Event listeners for sidebar items
bookingItem.addEventListener('click', () => {
    // Hide all sections first
    defaultSection.style.display = 'none';
    bookingSection.style.display = 'block';
    petHospitalizedSection.style.display = 'none';
});

hospitalizedItem.addEventListener('click', () => {
    // Hide all sections first
    defaultSection.style.display = 'none';
    bookingSection.style.display = 'none';
    petHospitalizedSection.style.display = 'block';
});

document.addEventListener('DOMContentLoaded', () => {
    const addBookingBtn = document.getElementById('addBookingBtn');
    const addhospitalizedbtn = document.getElementById('addhospitalizedbtn');
    const confirmationModal = document.getElementById('confirmation-modal');
    const closeBtn = document.querySelector('.modal .close');
    const cancelBtn = document.getElementById('cancel-btn');
    const confirmBtn = document.getElementById('confirm-btn');

    // Function to open modal
    function openModal() {
        confirmationModal.style.display = 'block';
    }
    // Function to close modal
    function closeModal() {
        confirmationModal.style.display = 'none';
    }
    // Event listener for Add Booking button
    addBookingBtn.addEventListener('click', openModal);
    // addhospitalizedbtn.addEventListener('click', openModal);

    // Event listener for Cancel button in modal
    cancelBtn.addEventListener('click', closeModal);
    // Event listener for Confirm button in modal
    confirmBtn.addEventListener('click', () => {

        closeModal();
    });

});