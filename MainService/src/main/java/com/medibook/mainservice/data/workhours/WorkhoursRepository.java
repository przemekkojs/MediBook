package com.medibook.mainservice.data.workhours;

import com.medibook.mainservice.data.doctor.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkhoursRepository extends JpaRepository<Workhours, Long> {

    Workhours findByDayAndDoctorId(int day, String doctorId);
}
