package com.group07.PetHealthCare.pojo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="invalidated_token")
public class InvalidatedToken {
    @Id
    String id;
    Date expiryTime;
}
