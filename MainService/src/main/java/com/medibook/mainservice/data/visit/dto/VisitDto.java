package com.medibook.mainservice.data.visit.dto;

public record VisitDto(
    String clientId,
    String doctorId,
    String visitState,
    long procedureId,
    String startTime,
    String date
) {}
