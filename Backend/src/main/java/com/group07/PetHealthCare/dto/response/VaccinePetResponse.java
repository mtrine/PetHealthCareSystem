package com.group07.PetHealthCare.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@Builder
public class VaccinePetResponse {
    private String vaccineId;
    private String vaccineName;  // Bạn có thể thay đổi tên này thành những trường mà bạn muốn lấy từ đối tượng Vaccine
    private String petId;
    private String petName;  // Tương tự, thay đổi nếu cần thiết
    private LocalDate stingDate;
    private LocalDate reStingDate;
}
