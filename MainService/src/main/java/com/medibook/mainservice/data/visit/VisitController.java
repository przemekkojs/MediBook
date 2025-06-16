package com.medibook.mainservice.data.visit;

import com.medibook.mainservice.data.visit.dto.CreateVisitDto;
import com.medibook.mainservice.data.visit.dto.TimeSchedule;
import com.medibook.mainservice.data.visit.dto.VisitDto;
import com.medibook.mainservice.data.visit.dto.VisitMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/visits")
@RequiredArgsConstructor
@CrossOrigin
public class VisitController {
    private final IVisitService visitService;
    private final VisitMapper visitMapper;

    @GetMapping("/{id}")
    public ResponseEntity<VisitDto> getVisit(long id) {
        return ResponseEntity.ok(
            visitMapper.toVisitDto(visitService.getVisit(id))
        );
    }

    @GetMapping("/schedule")
    public ResponseEntity<TimeSchedule> getTimeSchedule(
        @RequestParam String doctorId,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return ResponseEntity.ok(visitService.getTimeSchedule(doctorId, date));
    }

    @GetMapping("/client")
    public ResponseEntity<List<VisitDto>> getAllVisitsFromClient(JwtAuthenticationToken auth) {
        String username = auth.getToken().getClaimAsString(StandardClaimNames.PREFERRED_USERNAME);

        return ResponseEntity.ok(
            visitService.getAllVisitsFromClient(username)
                .stream().map(visitMapper::toVisitDto)
                .toList()
        );
    }

    @GetMapping("/doctor")
    public ResponseEntity<List<VisitDto>> getAllVisitsFromDoctor(JwtAuthenticationToken auth) {
        String username = auth.getToken().getClaimAsString(StandardClaimNames.PREFERRED_USERNAME);

        return ResponseEntity.ok(
            visitService.getAllVisitsFromDoctor(username)
                .stream().map(visitMapper::toVisitDto)
                .toList()
        );
    }

    @PutMapping("/doctor/finish/{id}")
    public ResponseEntity<VisitDto> finishVisit(@PathVariable long id, JwtAuthenticationToken auth) {
        String username = auth.getToken().getClaimAsString(StandardClaimNames.PREFERRED_USERNAME);

        return ResponseEntity.ok(
            visitMapper.toVisitDto(visitService.finishVisit(id, username))
        );
    }

    @PostMapping("/client")
    public ResponseEntity<VisitDto> createVisit(@RequestBody CreateVisitDto visitDto, JwtAuthenticationToken auth) {
        String username = auth.getToken().getClaimAsString(StandardClaimNames.PREFERRED_USERNAME);

        return ResponseEntity.ok(
            visitMapper.toVisitDto(visitService.createVisit(visitDto, username))
        );
    }

    @DeleteMapping("/client/cancel/{id}")
    public ResponseEntity<VisitDto> cancelVisitClient(@PathVariable long id, JwtAuthenticationToken auth) {
        String username = auth.getToken().getClaimAsString(StandardClaimNames.PREFERRED_USERNAME);

        return ResponseEntity.ok(
            visitMapper.toVisitDto(visitService.cancelVisitClient(id, username))
        );
    }

    @DeleteMapping("/doctor/cancel/{id}")
    public ResponseEntity<VisitDto> cancelVisitDoctor(@PathVariable long id, JwtAuthenticationToken auth) {
        String username = auth.getToken().getClaimAsString(StandardClaimNames.PREFERRED_USERNAME);

        return ResponseEntity.ok(
            visitMapper.toVisitDto(visitService.cancelVisitDoctor(id, username))
        );
    }
}
