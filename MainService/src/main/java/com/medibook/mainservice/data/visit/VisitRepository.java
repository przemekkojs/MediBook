package com.medibook.mainservice.data.visit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {

    List<Visit> findAllByClientUsername(String username);

    List<Visit> findAllByDoctorUsername(String username);
}
