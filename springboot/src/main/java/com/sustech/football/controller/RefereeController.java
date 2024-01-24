package com.sustech.football.controller;

import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/referee")
public class RefereeController {
    @RequestMapping("/create")
    public void createReferee() {

    }

    @RequestMapping("/{id}")
    public void getRefereeById(@PathVariable Long id) {

    }

    @RequestMapping("/all")
    public void getAllReferees() {

    }

    @RequestMapping("/update")
    public void updateReferee() {

    }

    @Deprecated
    @RequestMapping("/delete")
    public void deleteReferee() {

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

    @GetMapping("/getEventInvitations")
    public void getEventInvitations() {

    }

    @PostMapping("/replyEventInvitation")
    public void replyEventInvitation() {

    }

    @GetMapping("/getEvents")
    public void getEvents() {

    }


}
