package com.medibook.mainservice.data.procedure;

import com.medibook.mainservice.data.procedure.dto.CreateProcedureDto;
import com.medibook.mainservice.data.procedure.dto.EditProcedureDto;
import com.medibook.mainservice.data.procedure.dto.ProcedureDto;
import com.medibook.mainservice.data.procedure.dto.ProcedureMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/procedures")
@CrossOrigin
public class ProcedureController {
    private final IProcedureService procedureService;
    private final ProcedureMapper procedureMapper;

    @GetMapping
    public ResponseEntity<List<ProcedureDto>> getAllProcedures() {
        return ResponseEntity.ok(
                procedureService.getProcedures()
                        .stream().map(procedureMapper::toProcedureDto)
                        .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProcedureDto> getProcedureById(@PathVariable long id) {
        Procedure procedure = procedureService.getProcedure(id);

        if (procedure == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(procedureMapper.toProcedureDto(procedure));
    }

    @GetMapping("/doctor/token")
    public ResponseEntity<List<ProcedureDto>> getAllDoctorProcedures(JwtAuthenticationToken auth) {
        String username = auth.getToken().getClaimAsString(StandardClaimNames.PREFERRED_USERNAME);

        return ResponseEntity.ok(
                procedureService.getProceduresFromDoctor(username)
                        .stream().map(procedureMapper::toProcedureDto)
                        .toList()
        );
    }

    @GetMapping("/doctor/{id}")
    public ResponseEntity<List<ProcedureDto>> getDoctorProcedures(@PathVariable("id") String id) {
        return ResponseEntity.ok(List.of());
    }

    @PostMapping
    public ResponseEntity<ProcedureDto> createProcedure(@RequestBody CreateProcedureDto dto, JwtAuthenticationToken auth) {
        String username = auth.getToken().getClaimAsString(StandardClaimNames.PREFERRED_USERNAME);

        return ResponseEntity.ok(
                procedureMapper.toProcedureDto(
                        procedureService.createProcedure(dto, username)
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProcedureDto> editProcedure(@RequestBody EditProcedureDto dto, @PathVariable("id") long id) {
        return ResponseEntity.ok(
                procedureMapper.toProcedureDto(
                        procedureService.editProcedure(dto,id)
                )
        );
    }

    @DeleteMapping
    public void deleteProcedure(long id) {
        procedureService.deleteProcedure(id);
    }
}
