package com.medibook.mainservice.data.visit;

import com.medibook.mainservice.data.client.Client;
import com.medibook.mainservice.data.client.IClientService;
import com.medibook.mainservice.data.doctor.Doctor;
import com.medibook.mainservice.data.doctor.IDoctorService;
import com.medibook.mainservice.data.procedure.IProcedureService;
import com.medibook.mainservice.data.procedure.Procedure;
import com.medibook.mainservice.data.visit.dto.CreateVisitDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitServiceImpl implements IVisitService {

    private final VisitRepository visitRepository;
    private final IClientService clientService;
    private final IDoctorService doctorService;
    private final IProcedureService procedureService;


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
                .doctor(doctor)
                .totalPrice(procedure.getPrice())
                .build();

        return visitRepository.save(visit);
    }

    @Override
    public void deleteVisit(long id) {
        visitRepository.deleteById(id);
    }
}
