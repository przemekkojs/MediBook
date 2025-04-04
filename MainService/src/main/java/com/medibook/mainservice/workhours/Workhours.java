package com.medibook.mainservice.workhours;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.sql.Time;

@Entity
public class Workhours {

    @Id
    private long id;
    private String day;
    private Time startTime;
    private Time endTime;
}
