package com.medibook.mainservice.workhours;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkhoursRepository extends JpaRepository<Workhours, Long> {
}
