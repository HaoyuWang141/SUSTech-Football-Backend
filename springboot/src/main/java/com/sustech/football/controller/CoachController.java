package com.sustech.football.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/coach")
public class CoachController {
    @PostMapping("/create")
    public void createCoach() {

    }

    @GetMapping("/{id}")
    public void getCoachById(@PathVariable Long id) {

    }

    @GetMapping("/all")
    public void getAllCoaches() {

    }

    @PutMapping("/update")
    public void updateCoach() {

    }

    @Deprecated
    @DeleteMapping("/{id}")
    public void deleteCoach(@PathVariable Long id) {

    }

    @GetMapping("/getTeamInvitations")
    public void getTeamInvitations() {

    }

    @PostMapping("/replyTeamInvitation")
    public void replyTeamInvitation() {

    }

    @GetMapping("/getTeams")
    public void getTeams() {

    }
}
