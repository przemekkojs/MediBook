package com.medibook.mainservice.data.procedure.validation;

import com.medibook.mainservice.data.procedure.dto.CreateProcedureDto;
import com.medibook.mainservice.data.procedure.messages.ProcedureMessage;
import com.medibook.mainservice.util.generic.Validator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CreateProcedureValidator implements Validator<CreateProcedureDto> {

    @Override
    public void validate(CreateProcedureDto dto) {
        checkNotNUll(dto);
        checkPriceConstraints(dto);

    }

    private void checkNotNUll(CreateProcedureDto dto) {
        if (dto.name() == null || dto.name().isEmpty()) {
            throw ProcedureMessage.PROCEDURE_NAME_NULL_OR_EMPTY;
        }

        if (dto.description() == null || dto.description().isEmpty()) {
            throw ProcedureMessage.PROCEDURE_NAME_NULL_OR_EMPTY;
        }

        if(dto.price() == null){
            throw ProcedureMessage.PROCEDURE_PRICE_NULL;
        }

        if(dto.length() == null){
            throw ProcedureMessage.PROCEDURE_LENGTH_NULL;
        }
    }

    private void checkPriceConstraints(CreateProcedureDto dto) {
        if(dto.price().compareTo(new BigDecimal(0)) <= 0){
            throw ProcedureMessage.PROCEDURE_PRICE_NOT_POSITIVE;
        }
    }
}
