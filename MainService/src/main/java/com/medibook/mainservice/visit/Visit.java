package com.medibook.mainservice.visit;

import jakarta.persistence.Entity;
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
    private long id;


}
