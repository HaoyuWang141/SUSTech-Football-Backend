package com.sustech.football.controller;

import com.sustech.football.entity.Match;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/match")
public class MatchController {
    @PostMapping("/create")
    public Match createMatch(@RequestBody Match match) {
        return null;
    }

    @GetMapping("/get")
    public Match getMatch(Long id) {
        return null;
    }

    @GetMapping("/getAll")
    public List<Match> getAllMatches() {
        return null;
    }

    @PutMapping("/update")
    public void updateMatch(@RequestBody Match match) {

    }

    @DeleteMapping("/delete")
    public void deleteMatch(Long matchId) {

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

    @DeleteMapping("/team/delete")
    public void deleteTeam() {

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

    @PostMapping("/referee/updateResult")
    public void updateResult() {

    }
}
