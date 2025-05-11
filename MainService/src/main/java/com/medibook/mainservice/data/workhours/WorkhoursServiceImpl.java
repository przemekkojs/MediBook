package com.medibook.mainservice.data.workhours;

import com.medibook.mainservice.data.doctor.Doctor;
import com.medibook.mainservice.data.doctor.IDoctorService;
import com.medibook.mainservice.util.generic.Validator;
import com.medibook.mainservice.data.workhours.dto.CreateWorkhoursDto;
import com.medibook.mainservice.data.workhours.dto.EditWorkhoursDto;
import com.medibook.mainservice.data.workhours.messages.WorkhourMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkhoursServiceImpl implements IWorkhoursService {
    private final WorkhoursRepository workhoursRepository;
    private final IDoctorService doctorService;
    private final Validator<CreateWorkhoursDto> createWorkhoursValidator;
    private final Validator<EditWorkhoursDto> editWorkhoursValidator;

    @Override
    public void createWorkHours(CreateWorkhoursDto dto, String doctorUsername) {
        createWorkhoursValidator.validate(dto);

        Doctor doctor = doctorService.getDoctorByUsername(doctorUsername);

        if(workhoursRepository.findByDayAndDoctorId(dto.day(),doctor.getId()) != null){
            throw WorkhourMessage.WORKHOURS_EXIST;
        }

        if (doctor == null) {
            throw WorkhourMessage.DOCTOR_NOT_FOUND;
        }

        Workhours workhours = Workhours.builder()
                .doctor(doctor)
                .day(dto.day())
                .endTime(dto.endTime())
                .startTime(dto.startTime())
                .build();

        workhoursRepository.save(workhours);
    }

    @Override
    public void updateWorkHours(EditWorkhoursDto dto, long id) {
        editWorkhoursValidator.validate(dto);

        Workhours workhours = getWorkhoursById(id);

        if (workhours == null) {
            throw WorkhourMessage.WORKHOURS_NOT_FOUND;
        }

        workhours.setStartTime(dto.startTime());
        workhours.setEndTime(dto.endTime());
        workhoursRepository.save(workhours);

    }

    @Override
    public void deleteWorkHours(long id) {
        workhoursRepository.deleteById(id);
    }

    @Override
    public Workhours getWorkhoursById(long id) {
        return workhoursRepository.findById(id).orElse(null);
    }


    @Override
    public List<Workhours> getWorkHoursForDoctor(String doctorId) {
        return List.of();
    }
}
