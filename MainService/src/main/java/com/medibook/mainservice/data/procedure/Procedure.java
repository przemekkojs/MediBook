package com.medibook.mainservice.data.procedure;


import com.medibook.mainservice.data.doctor.Doctor;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalTime;

@Entity
@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Procedure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String description;

    private BigDecimal price;

    private LocalTime length;

    @ManyToOne
    private Doctor doctor;
}
