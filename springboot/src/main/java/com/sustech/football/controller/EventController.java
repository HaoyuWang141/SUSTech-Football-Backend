package com.sustech.football.controller;

import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/event")
public class EventController {
    @PostMapping("/create")
    public void createEvent() {

    }

    @GetMapping("/{id}")
    public void getEventById(@PathVariable Long id) {

    }

    @GetMapping("/all")
    public void getAllEvents() {

    }

    @PutMapping("/update")
    public void updateEvent() {

    }

    @DeleteMapping("/delete")
    public void deleteEvent() {

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

    @PostMapping("/inviteTeam")
    public void inviteTeam() {

    }

    @GetMapping("/getTeamInvitations")
    public void getTeamInvitations() {

    }

    @PostMapping("/replyTeamInvitation")
    public void replyTeamInvitation() {

    }

    @GetMapping("/getTeam")
    public void getTeam() {

    }

    @DeleteMapping("/deleteTeam")
    public void deleteTeam() {

    }

    @PostMapping("/addMatch")
    public void addMatch() {

    }

    @GetMapping("/getMatches")
    public void getMatches() {

    }

    @DeleteMapping("/deleteMatch")
    public void deleteMatch() {

    }

    @PostMapping("/inviteReferee")
    public void inviteReferee() {

    }

    @GetMapping("/getReferees")
    public void getReferees() {

    }

    @DeleteMapping("/deleteReferee")
    public void deleteReferee() {

    }
}
