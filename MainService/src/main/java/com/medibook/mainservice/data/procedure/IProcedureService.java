package com.medibook.mainservice.data.procedure;

import com.medibook.mainservice.data.procedure.dto.CreateProcedureDto;
import com.medibook.mainservice.data.procedure.dto.EditProcedureDto;

import java.util.List;

public interface IProcedureService {

    Procedure getProcedure(long id);

    List<Procedure> getProcedures();

    List<Procedure> getProceduresFromDoctorByUsername(String username);
    List<Procedure> getProceduresFromDoctorById(String id);

    Procedure createProcedure(CreateProcedureDto procedureDto,String username);

    Procedure editProcedure(EditProcedureDto procedureDto, long id);

    void deleteProcedure(long id);

}
