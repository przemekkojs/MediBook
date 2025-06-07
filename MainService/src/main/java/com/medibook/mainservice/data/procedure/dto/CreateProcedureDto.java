package com.medibook.mainservice.data.procedure.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalTime;

public record CreateProcedureDto(
        @NotBlank String name,
        @NotBlank String description,
        @NotNull BigDecimal price,
        @NotNull LocalTime length) {
}
