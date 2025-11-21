package it.unisalento.music_virus_project.event_fundraising_service.controllers;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contributions")
@Validated
public class ContributionController {
//
//    private final ContributionService contributionService;
//
//    public ContributionController(ContributionService contributionService) {
//        this.contributionService = contributionService;
//    }
//
//    @PostMapping
//    public ResponseEntity<ContributionResponse> contribute(
//            @RequestHeader("X-Fan-Id") String fanId,
//            @Valid @RequestBody ContributionCreateRequest request
//    ) {
//        request.setFanId(fanId); // forza coerenza con header
//        ContributionResponse created = contributionService.contribute(request);
//        return ResponseEntity.status(HttpStatus.CREATED).body(created);
//    }
//
//    @GetMapping("/event/{eventId}")
//    public ResponseEntity<List<ContributionResponse>> listByEvent(@PathVariable String eventId) {
//        return ResponseEntity.ok(contributionService.findByEvent(eventId));
//    }
//
//    @GetMapping("/fan/{fanId}")
//    public ResponseEntity<List<ContributionResponse>> listByFan(@PathVariable String fanId) {
//        return ResponseEntity.ok(contributionService.findByFan(fanId));
//    }
}
