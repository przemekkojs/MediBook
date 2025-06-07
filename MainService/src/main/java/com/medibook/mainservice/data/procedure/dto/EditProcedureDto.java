package com.medibook.mainservice.data.procedure.dto;

import java.math.BigDecimal;
import java.time.LocalTime;

public record EditProcedureDto(
        String name,
        String description,
        BigDecimal price,
        LocalTime length) {
}
