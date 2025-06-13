package com.medibook.mainservice.data.procedure;

import com.medibook.mainservice.data.doctor.Doctor;
import com.medibook.mainservice.data.doctor.IDoctorService;
import com.medibook.mainservice.data.procedure.dto.CreateProcedureDto;
import com.medibook.mainservice.data.procedure.dto.EditProcedureDto;
import com.medibook.mainservice.data.procedure.messages.ProcedureMessage;
import com.medibook.mainservice.data.procedure.validation.CreateProcedureValidator;
import com.medibook.mainservice.data.procedure.validation.EditProcedureValidator;
import com.medibook.mainservice.data.workhours.dto.CreateWorkhoursDto;
import com.medibook.mainservice.util.generic.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProcedureServiceImpl implements IProcedureService {

    private final ProcedureRepository procedureRepository;

    private final Validator<CreateProcedureDto> createProcedureValidator;

    private final Validator<EditProcedureDto> editProcedureValidator;

    private final IDoctorService doctorService;

    @Override
    public Procedure getProcedure(long id) {
        return procedureRepository.findById(id).orElse(null);
    }

    @Override
    public List<Procedure> getProcedures() {
        return procedureRepository.findAll();
    }

    @Override
    public List<Procedure> getProceduresFromDoctor(String username) {
        return procedureRepository.getProceduresByDoctorUsername(username);
    }

    @Override
    public Procedure createProcedure(CreateProcedureDto procedureDto,String username) {
        createProcedureValidator.validate(procedureDto);

        Doctor doctor = doctorService.getDoctorByUsername(username);

        if (doctor == null) {
            throw ProcedureMessage.DOCTOR_NOT_FOUND;
        }

        Procedure procedure = Procedure.builder()
                .price(procedureDto.price())
                .name(procedureDto.name())
                .description(procedureDto.description())
                .length(procedureDto.length())
                .doctor(doctor)
                .build();

        return procedureRepository.save(procedure);
    }

    @Override
    public Procedure editProcedure(EditProcedureDto procedureDto, long id) {
        editProcedureValidator.validate(procedureDto);

        Procedure procedure = procedureRepository.findById(id).orElse(null);

        if (procedure == null) {
            throw ProcedureMessage.PROCEDURE_NOT_FOUND;
        }

        if (procedureDto.description() != null) {
            procedure.setDescription(procedureDto.description());
        }

        if (procedureDto.price() != null) {
            procedure.setPrice(procedureDto.price());
        }

        if (procedureDto.name() != null) {
            procedure.setName(procedureDto.name());
        }

        if(procedureDto.length() != null){
            procedure.setLength(procedureDto.length());
        }

        return procedureRepository.save(procedure);
    }

    @Override
    public void deleteProcedure(long id) {
        procedureRepository.deleteById(id);
    }
}
