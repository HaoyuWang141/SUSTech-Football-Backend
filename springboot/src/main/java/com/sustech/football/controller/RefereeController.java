package com.sustech.football.controller;

import com.sustech.football.entity.Referee;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/referee")
public class RefereeController {
    @PostMapping("/create")
    public Referee createReferee(@RequestBody Referee referee) {
        return null;
    }

    @GetMapping("/get")
    public Referee getReferee(Long id) {
        return null;
    }

    @GetMapping("/getAll")
    public List<Referee> getAllReferees() {
        return null;
    }

    @PutMapping("/update")
    public void updateReferee(@RequestBody Referee referee) {

    }

    @Deprecated
    @DeleteMapping("/delete")
    public void deleteReferee(Long refereeId) {

    }

    @GetMapping("/match/getInvitations")
    public void getMatchInvitations() {

    }

    @PostMapping("/match/replyInvitation")
    public void replyMatchInvitation() {

    }

    @GetMapping("/match/getAll")
    public void getMatches() {

    }

    @GetMapping("/event/getInvitations")
    public void getEventInvitations() {

    }

    @PostMapping("/event/replyInvitation")
    public void replyEventInvitation() {

    }

    @GetMapping("/event/getAll")
    public void getEvents() {

    }


}
