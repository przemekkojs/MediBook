package com.medibook.mainservice.data.workhours;

import com.medibook.mainservice.data.workhours.dto.CreateWorkhoursDto;
import com.medibook.mainservice.data.workhours.dto.EditWorkhoursDto;
import com.medibook.mainservice.data.workhours.dto.WorkhourMapper;
import com.medibook.mainservice.data.workhours.dto.WorkhoursDto;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/workhours")
@RequiredArgsConstructor
@CrossOrigin
public class WorkhoursController {
    private final IWorkhoursService workhoursService;
    private final WorkhourMapper workhourMapper;


    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @RolesAllowed({"DOCTOR"})
    public void createWorkhours(@RequestBody CreateWorkhoursDto dto, JwtAuthenticationToken auth) {
        String username = auth.getToken().getClaimAsString(StandardClaimNames.PREFERRED_USERNAME);
        System.out.println(auth);
        workhoursService.createWorkHours(dto, username);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.CREATED)
    @RolesAllowed({"DOCTOR"})
    public void updateWorkhours(@RequestBody EditWorkhoursDto dto, @PathVariable int day, JwtAuthenticationToken auth) {
        String username = auth.getToken().getClaimAsString(StandardClaimNames.PREFERRED_USERNAME);
        System.out.println(username);
        workhoursService.updateWorkHours(dto,day,username);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @RolesAllowed({"DOCTOR"})
    public void deleteWorkhours(@PathVariable long id) {
        workhoursService.deleteWorkHours(id);
    }

    @GetMapping("/{doctorId}")
    public List<WorkhoursDto> getWorkhoursFromDoctor(@PathVariable String doctorId) {
        return workhoursService.getWorkHoursForDoctor(doctorId)
                .stream()
                .map(workhour -> workhourMapper.toDto(workhour))
                .toList();
    }



}
