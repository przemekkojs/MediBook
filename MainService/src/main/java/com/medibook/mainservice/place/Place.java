package com.medibook.mainservice.place;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Place {

    @Id
    private long id;
    @Column
    private String name;
    @Column
    private String address;
}
