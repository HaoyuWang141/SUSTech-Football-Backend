package com.sustech.football.controller;

import com.sustech.football.entity.Coach;
import com.sustech.football.entity.Match;
import com.sustech.football.entity.Team;
import com.sustech.football.entity.TeamCoachRequest;
import com.sustech.football.exception.BadRequestException;
import com.sustech.football.exception.ResourceNotFoundException;
import com.sustech.football.service.CoachService;
import com.sustech.football.service.TeamCoachRequestService;
import com.sustech.football.service.TeamService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/coach")
public class CoachController {
    private final CoachService coachService;
    private final TeamService teamService;
    private final TeamCoachRequestService teamCoachRequestService;

    @Autowired
    public CoachController(CoachService coachService,
            TeamService teamService,
            TeamCoachRequestService teamCoachRequestService) {
        this.coachService = coachService;
        this.teamService = teamService;
        this.teamCoachRequestService = teamCoachRequestService;
    }

    @PostMapping("/create")
    public Coach createCoach(@RequestBody Coach coach) {
        if (coach == null) {
            throw new BadRequestException("传入教练为空");
        }
        if (coach.getCoachId() != null) {
            throw new BadRequestException("传入教练的ID不为空");
        }
        if (!coachService.save(coach)) {
            throw new BadRequestException("创建教练失败");
        }
        return coach;
    }

    @GetMapping("/get")
    public Coach getCoachById(Long id) {
        if (id == null) {
            throw new BadRequestException("传入教练的ID为空");
        }
        Coach coach = coachService.getById(id);
        if (coach == null) {
            throw new BadRequestException("教练不存在");
        }
        return coach;
    }

    @GetMapping("/getAll")
    public List<Coach> getAllCoaches() {
        return coachService.list();
    }

    @PutMapping("/update")
    public Coach updateCoach(@RequestBody Coach coach) {
        if (coach == null) {
            throw new BadRequestException("传入教练为空");
        }
        if (coach.getCoachId() == null) {
            throw new BadRequestException("传入教练的ID为空");
        }
        if (!coachService.updateById(coach)) {
            throw new BadRequestException("更新教练失败");
        }
        return coach;
    }

    @Deprecated
    @DeleteMapping("/delete")
    public void deleteCoach(Long id) {
        if (!coachService.removeById(id)) {
            throw new BadRequestException("删除教练失败");
        }
    }

    @GetMapping("/team/getInvitations")
    public List<TeamCoachRequest> getTeamInvitations(@RequestParam Long coachId) {
        if (coachId == null) {
            throw new BadRequestException("教练id不能为空");
        }
        if (coachService.getById(coachId) == null) {
            throw new ResourceNotFoundException("教练不存在");
        }
        return teamCoachRequestService.listWithTeam(coachId);
    }

    @PostMapping("/team/replyInvitation")
    public void replyTeamInvitation(
            @RequestParam Long coachId,
            @RequestParam Long teamId,
            @RequestParam Boolean accept) {
        if (coachId == null || teamId == null || accept == null) {
            throw new BadRequestException("教练id、球队id和状态不能为空");
        }
        if (coachService.getById(coachId) == null) {
            throw new ResourceNotFoundException("教练不存在");
        }
        if (teamService.getById(teamId) == null) {
            throw new ResourceNotFoundException("球队不存在");
        }
        coachService.replyTeamInvitation(coachId, teamId, accept);
    }

    @GetMapping("/team/getAll")
    public List<Team> getTeams(@RequestParam Long coachId) {
        if (coachId == null) {
            throw new BadRequestException("教练id不能为空");
        }
        if (coachService.getById(coachId) == null) {
            throw new ResourceNotFoundException("教练不存在");
        }
        return coachService.getTeams(coachId);
    }

    @GetMapping("/getMatches")
    public List<Match> getMatches(@RequestParam Long coachId) {
        if (coachId == null) {
            throw new BadRequestException("教练id不能为空");
        }
        if (coachService.getById(coachId) == null) {
            throw new ResourceNotFoundException("教练不存在");
        }
        return null;
        // TODO: 2024-3-2
//        return coachService.getMatches(coachId);
    }
}
