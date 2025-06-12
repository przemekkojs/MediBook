package com.medibook.mainservice.data.visit;

import com.medibook.mainservice.data.visit.dto.CreateVisitDto;
import com.medibook.mainservice.data.visit.dto.VisitDto;
import com.medibook.mainservice.data.visit.dto.VisitMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/client")
    public ResponseEntity<VisitDto> createVisit(@RequestBody CreateVisitDto visitDto, JwtAuthenticationToken auth) {
        String username = auth.getToken().getClaimAsString(StandardClaimNames.PREFERRED_USERNAME);

        return ResponseEntity.ok(
                visitMapper.toVisitDto(visitService.createVisit(visitDto, username))
        );
    }

}
