package com.sustech.football.controller;

import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/match")
public class MatchController {
    @RequestMapping("/create")
    public void createMatch() {

    }

    @RequestMapping("/{id}")
    public void getMatchById() {

    }

    @RequestMapping("/all")
    public void getAllMatches() {

    }

    @RequestMapping("/update")
    public void updateMatch() {

    }

    @RequestMapping("/delete")
    public void deleteMatch() {

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

    @GetMapping("/getTeam")
    public void getTeam() {

    }

    @DeleteMapping("/deleteTeam")
    public void deleteTeam() {

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

    @PostMapping("/updateResult")
    public void updateResult() {

    }
}
