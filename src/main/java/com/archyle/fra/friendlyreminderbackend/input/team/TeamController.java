package com.archyle.fra.friendlyreminderbackend.input.team;

import com.archyle.fra.friendlyreminderbackend.output.repository.TeamEntity;
import com.archyle.fra.friendlyreminderbackend.output.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/teams")
@RequiredArgsConstructor
public class TeamController {
  private final TeamRepository teamRepository;

  @PostMapping
  public ResponseEntity<CreateTeamResponse> createTeam(
      @Valid @RequestBody final CreateTeamRequest request) {
    TeamEntity savedTeam =
        teamRepository.save(TeamEntity.builder().name(request.getName()).build());
    return ResponseEntity.created(URI.create("/teams/" + savedTeam.getId())).build();
  }
}
