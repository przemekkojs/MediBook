package com.medibook.mainservice.data.workhours;

import com.medibook.mainservice.data.doctor.Doctor;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.sql.Time;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class Workhours {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @Column(nullable = false)
    @Max(7)
    @Min(1)
    private int day;
    @Column(nullable = false)
    private Time startTime;
    @Column(nullable = false)
    private Time endTime;
    @ManyToOne
    private Doctor doctor;
}
