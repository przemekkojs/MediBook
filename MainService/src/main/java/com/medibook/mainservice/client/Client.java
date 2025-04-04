package com.medibook.mainservice.client;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Client {
    @Id
    private long id;
    @Column(unique = true)
    private String pesel;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String phoneNumber;


}
