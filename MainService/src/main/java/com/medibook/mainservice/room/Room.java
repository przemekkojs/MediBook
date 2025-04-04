package com.medibook.mainservice.room;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Room {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private long id;

    private String name;
}
