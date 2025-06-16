package com.medibook.mainservice.data.visit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {

    List<Visit> findAllByClientUsername(String username);

    List<Visit> findAllByDoctorUsername(String username);

    List<Visit> findAllByDoctorIdAndDate(String username, LocalDate date);

    Visit findByIdAndDoctorUsername(long id, String username);
    Visit findByIdAndClientUsername(long id,String username);
}
