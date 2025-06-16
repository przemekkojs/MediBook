package com.medibook.mainservice.data.visit;

import com.medibook.mainservice.data.client.Client;
import com.medibook.mainservice.data.client.IClientService;
import com.medibook.mainservice.data.client.dto.ClientDTO;
import com.medibook.mainservice.data.doctor.Doctor;
import com.medibook.mainservice.data.doctor.IDoctorService;
import com.medibook.mainservice.data.doctor.dto.DoctorDto;
import com.medibook.mainservice.data.procedure.IProcedureService;
import com.medibook.mainservice.data.procedure.Procedure;
import com.medibook.mainservice.data.visit.dto.CreateVisitDto;
import com.medibook.mainservice.data.visit.dto.TimeSchedule;
import com.medibook.mainservice.data.visit.dto.TimeScheduleEntry;
import com.medibook.mainservice.data.workhours.IWorkhoursService;
import com.medibook.mainservice.data.workhours.Workhours;
import com.medibook.mainservice.tools.keycloak.KeycloakService;
import com.medibook.mainservice.tools.rabbitmq.RabbitMQService;
import com.medibook.mainservice.tools.rabbitmq.dto.ClientVisitConfirmation;
import com.medibook.mainservice.util.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class VisitServiceImpl implements IVisitService {

    private final VisitRepository visitRepository;
    private final IClientService clientService;
    private final IDoctorService doctorService;
    private final IProcedureService procedureService;
    private final RabbitMQService rabbitMQService;
    private final KeycloakService keycloakService;
    private final IWorkhoursService workhoursService;


    @Override
    public Visit getVisit(long id) {
        return visitRepository.findById(id).orElse(null);
    }

    @Override
    public List<Visit> getAllVisitsFromClient(String username) {
        return visitRepository.findAllByClientUsername(username);
    }

    @Override
    public List<Visit> getAllVisitsFromDoctor(String username) {
        return visitRepository.findAllByDoctorUsername(username);
    }


    @Override
    public Visit createVisit(CreateVisitDto visitDto,String username) {

        Client client = clientService.getClientByUsername(username);
        if (client == null) {
            throw new IllegalArgumentException("Client not found");
        }

        Doctor doctor = doctorService.getDoctorById(visitDto.doctorId());

        if (doctor == null) {
            throw new IllegalArgumentException("Doctor not found");
        }

        Procedure procedure = procedureService.getProcedure(visitDto.procedureId());

        if (procedure == null) {
            throw new IllegalArgumentException("Procedure not found");
        }

        Visit visit = Visit.builder()
                .client(client)
                .startTime(visitDto.startTime())
                .procedure(procedure)
                .date(visitDto.date())
                .state(VisitState.CREATED)
                .doctor(doctor)
                .totalPrice(procedure.getPrice())
                .build();

        visit = visitRepository.save(visit);


        try {

            ClientDTO clientDTO = keycloakService.getClientByUsername(client.getUsername());
            DoctorDto doctorDTO = keycloakService.getDoctorByUsername(doctor.getUsername());
            ClientVisitConfirmation confirmation =
                    ClientVisitConfirmation.builder()
                            .doctorName(doctorDTO.name() + " " + doctorDTO.lastName())
                            .clientName(clientDTO.name() + " " + clientDTO.lastName())
                            .clientEmail(clientDTO.email())
                            .visitTime(visit.getStartTime().toString())
                            .visitDate(visit.getDate().toString())
                            .build();
            rabbitMQService.sendClientVisitNotificationMessage(confirmation);
        }catch (Exception e){
            log.warn("Mail message not sent because of following error: " + e.getMessage());
        }



        return visit;
    }

    public TimeSchedule getTimeSchedule(String id, LocalDate date) {
        List<Visit> visits = visitRepository.findAllByDoctorIdAndDate(id, date)
                .stream().filter(visit -> visit.getState() == VisitState.CREATED).toList();
        Integer dayOfWeek = date.getDayOfWeek().getValue();

        Workhours workhours = workhoursService.getWorkHoursForDoctor(id)
                .stream()
                .filter(wh -> wh.getDay() == dayOfWeek)
                .findFirst()
                .orElse(null);

        if (workhours == null) {
            return new TimeSchedule(List.of());
        }
        List<TimeScheduleEntry> scheduleEntries = new ArrayList<>();

        LocalTime startTime = workhours.getStartTime();

        for (Visit visit : visits) {
            LocalTime endTime = visit.getStartTime().plusSeconds(visit.getProcedure().getLength().toSecondOfDay());
            if (visit.getStartTime().isAfter(startTime)) {
                scheduleEntries.add(new TimeScheduleEntry(startTime,visit.getStartTime()));
            }
            startTime = endTime;
        }

        if(startTime.isBefore(workhours.getEndTime())) {
            scheduleEntries.add(new TimeScheduleEntry(startTime, workhours.getEndTime()));
        }

        return new TimeSchedule(scheduleEntries);
    }

    @Override
    public void deleteVisit(long id) {
        visitRepository.deleteById(id);
    }

    @Override
    public Visit finishVisit(long id, String username) {
        Visit visit = visitRepository.findByIdAndDoctorUsername(id, username);

        if (visit == null) {
            throw new ApplicationException("visit/0001", "Visit not found or you are not authorized to finish this visit",401);
        }

        if (visit.getState() != VisitState.CREATED){
            throw new ApplicationException("visit/0002", "Visit is already finished or cancelled", 400);
        }

        visit.setState(VisitState.FINISHED);

        return visitRepository.save(visit);
    }

    @Override
    public Visit cancelVisitClient(long id, String username) {
        Visit visit = visitRepository.findByIdAndClientUsername(id, username);

        if (visit == null) {
            throw new ApplicationException("visit/0001", "Visit not found or you are not authorized to cancel this visit",401);
        }

        if (visit.getState() != VisitState.CREATED){
            throw new ApplicationException("visit/0002", "Visit is already finished or cancelled", 400);
        }

        visit.setState(VisitState.CANCELLED);

        return visitRepository.save(visit);
    }

    @Override
    public Visit cancelVisitDoctor(long id, String username) {
        Visit visit = visitRepository.findByIdAndDoctorUsername(id, username);

        if (visit == null) {
            throw new ApplicationException("visit/0001", "Visit not found or you are not authorized to cancel this visit",401);
        }

        if (visit.getState() != VisitState.CREATED){
            throw new ApplicationException("visit/0002", "Visit is already finished or cancelled", 400);
        }

        visit.setState(VisitState.CANCELLED);

        return visitRepository.save(visit);
    }


}
