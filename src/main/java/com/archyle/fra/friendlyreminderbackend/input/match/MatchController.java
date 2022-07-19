package com.archyle.fra.friendlyreminderbackend.input.match;

import com.archyle.fra.friendlyreminderbackend.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;

    @PostMapping
    public ResponseEntity<CreateMatchResponse> createMatch(final CreateMatchRequest request) {
        return null;
    }
}
