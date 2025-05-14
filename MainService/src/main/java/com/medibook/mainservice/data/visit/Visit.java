package com.medibook.mainservice.data.visit;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

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


}
