package com.medibook.mainservice.workhours;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
    private long id;
    @Column(nullable = false)
    private String day;
    @Column(nullable = false)
    private Time startTime;
    @Column(nullable = false)
    private Time endTime;
}
