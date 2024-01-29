package com.sustech.football.controller;

import com.sustech.football.entity.Event;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/event")
public class EventController {
    @PostMapping("/create")
    public Event createEvent(@RequestBody Event event) {
        return null;
    }

    @GetMapping("/get")
    public Event getEvent(Long id) {
        return null;
    }

    @GetMapping("/getAll")
    public List<Event> getAllEvents() {
        return null;
    }

    @PutMapping("/update")
    public void updateEvent(@RequestBody Event event) {

    }

    @DeleteMapping("/delete")
    public void deleteEvent(Long eventId) {

    }

    @PostMapping("/manager/invite")
    public void inviteManager() {

    }

    @GetMapping("/manager/getAll")
    public void getManagers() {

    }

    @DeleteMapping("/manager/delete")
    public void deleteManager() {

    }

    @PostMapping("/team/invite")
    public void inviteTeam() {

    }

    @GetMapping("/team/getInvitations")
    public void getTeamInvitations() {

    }

    @PostMapping("/team/replyInvitation")
    public void replyTeamInvitation() {

    }

    @GetMapping("/team/getAll")
    public void getTeam() {

    }

    @DeleteMapping("/team/delete")
    public void deleteTeam() {

    }

    @PostMapping("/match/add")
    public void addMatch() {

    }

    @GetMapping("/match/getAll")
    public void getMatches() {

    }

    @DeleteMapping("/match/delete")
    public void deleteMatch() {

    }

    @PostMapping("/referee/invite")
    public void inviteReferee() {

    }

    @GetMapping("/referee/getAll")
    public void getReferees() {

    }

    @DeleteMapping("/referee/delete")
    public void deleteReferee() {

    }
}
