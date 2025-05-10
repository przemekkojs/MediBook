package com.medibook.mainservice.doctor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter
@Builder
@ToString
@EqualsAndHashCode
public class Doctor {
    @Id
    private String id;
    @Column(unique = true)
    private String username;

}
