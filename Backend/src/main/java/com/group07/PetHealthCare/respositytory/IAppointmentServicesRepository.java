package com.group07.PetHealthCare.respositytory;

import com.group07.PetHealthCare.pojo.AppointmentServices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAppointmentServicesRepository extends JpaRepository<AppointmentServices, String> {
    void deleteByAppointmentId(String appointmentId);
}
