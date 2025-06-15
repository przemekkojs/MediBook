package com.medibook.mainservice.data.visit;

import com.medibook.mainservice.data.client.Client;
import com.medibook.mainservice.data.doctor.Doctor;
import com.medibook.mainservice.data.procedure.Procedure;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Client client;

    private LocalTime startTime;

    private LocalDate date;

    @ManyToOne(fetch = FetchType.EAGER)
    private Procedure procedure;

    @ManyToOne(fetch = FetchType.EAGER)
    private Doctor doctor;

    private BigDecimal totalPrice;
}
