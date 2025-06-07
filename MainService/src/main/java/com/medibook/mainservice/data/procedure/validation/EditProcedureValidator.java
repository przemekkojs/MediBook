package com.medibook.mainservice.data.procedure.validation;

import com.medibook.mainservice.data.procedure.dto.CreateProcedureDto;
import com.medibook.mainservice.data.procedure.dto.EditProcedureDto;
import com.medibook.mainservice.util.generic.Validator;
import org.springframework.stereotype.Component;

@Component
public class EditProcedureValidator implements Validator<EditProcedureDto> {
    @Override
    public void validate(EditProcedureDto dto) {

    }
}
