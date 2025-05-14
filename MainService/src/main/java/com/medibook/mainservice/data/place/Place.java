package com.medibook.mainservice.data.place;

import com.medibook.mainservice.data.doctor.Doctor;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @Column
    private String name;
    @Column
    private String room;
    @Column
    private String address;
    @OneToOne
    private Doctor doctor;
}
