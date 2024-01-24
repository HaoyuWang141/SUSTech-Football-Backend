package com.sustech.football.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/team")
public class TeamController {
    @PostMapping("/create")
    public void createTeam() {

    }

    @GetMapping("/{id}")
    public void getTeamById(@PathVariable Long id) {

    }

    @GetMapping("/all")
    public void getAllTeams() {

    }

    @PutMapping("/update")
    public void updateTeam() {

    }

    @DeleteMapping("/{id}")
    public void deleteTeam() {

    }

    @PostMapping("/inviteManager")
    public void inviteManager() {

    }

    @GetMapping("/getManagers")
    public void getManagers() {

    }

    @DeleteMapping("/deleteManager")
    public void deleteManager() {

    }

    @PostMapping("/invitePlayer")
    public void invitePlayer() {

    }

    @GetMapping("/getPlayerApplications")
    public void getPlayerApplications() {

    }

    @PostMapping("/replyPlayerApplication")
    public void replyPlayerApplication() {

    }

    @GetMapping("/getPlayers")
    public void getPlayers() {

    }

    @DeleteMapping("/deletePlayer")
    public void deletePlayer() {

    }

    @PostMapping("/inviteCoach")
    public void inviteCoach() {

    }

    @GetMapping("/getCoaches")
    public void getCoaches() {

    }

    @DeleteMapping("/deleteCoach")
    public void deleteCoach() {

    }

    @GetMapping("/getMatchInvitations")
    public void getMatchInvitations() {

    }

    @PostMapping("/replyMatchInvitation")
    public void replyMatchInvitation() {

    }

    @GetMapping("/getMatches")
    public void getMatches() {

    }

    @PostMapping("/requestJoinEvent")
    public void requestJoinEvent() {

    }

    @GetMapping("/getEventApplications")
    public void getEventApplications() {

    }

    @PostMapping("/replyEventApplication")
    public void replyEventApplication() {

    }

    @GetMapping("/getEvents")
    public void getEvents() {

    }

    @GetMapping("/getTeamUniformUrls")
    public void getTeamUniformUrls() {

    }


}
