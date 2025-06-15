package com.medibook.mainservice.data.procedure.dto;

import java.math.BigDecimal;
import java.time.LocalTime;

public record ProcedureDto(
    long id,
    String name,
    String description,
    BigDecimal price,
    LocalTime length
) {}