package com.medibook.mainservice.data.procedure.dto;

import com.medibook.mainservice.data.procedure.Procedure;
import org.springframework.stereotype.Component;

@Component
public class ProcedureMapper {

    public ProcedureDto toProcedureDto(Procedure procedure) {
        return new ProcedureDto(
                procedure.getId(),
                procedure.getName(),
                procedure.getDescription(),
                procedure.getPrice(),
                procedure.getLength()
        );
    }
}
