package com.medibook.mainservice.data.doctor;

import com.medibook.mainservice.data.procedure.Procedure;
import com.medibook.mainservice.data.workhours.Workhours;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

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
    @OneToMany
    private List<Workhours> workhours;
    @OneToMany
    private List<Procedure> procedures;
}
