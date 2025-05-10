package com.medibook.mainservice.visit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {
}
