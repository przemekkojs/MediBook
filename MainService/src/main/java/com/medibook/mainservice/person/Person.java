package com.medibook.mainservice.person;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Person {

    @Id
    private long id;
    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private String email;


}
