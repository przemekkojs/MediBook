package com.medibook.mainservice.data.place;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
    Place findByDoctor_Id(String id);
}
