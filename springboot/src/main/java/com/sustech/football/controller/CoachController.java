package com.sustech.football.controller;

import com.sustech.football.entity.*;
import com.sustech.football.exception.BadRequestException;
import com.sustech.football.exception.ResourceNotFoundException;
import com.sustech.football.service.CoachService;
import com.sustech.football.service.TeamCoachRequestService;
import com.sustech.football.service.TeamService;

import java.util.List;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/coach")
@Tag(name = "Coach Controller", description = "管理教练的接口")
public class CoachController {
    @Autowired
    private CoachService coachService;
    @Autowired
    private TeamService teamService;
    @Autowired
    private TeamCoachRequestService teamCoachRequestService;

    @Autowired
    public CoachController(CoachService coachService,
                           TeamService teamService,
                           TeamCoachRequestService teamCoachRequestService) {
        this.coachService = coachService;
        this.teamService = teamService;
        this.teamCoachRequestService = teamCoachRequestService;
    }

    @PostMapping("/create")
    @Operation(summary = "创建教练", description = "创建一个新的教练")
    @Parameter(name = "coach", description = "教练信息", required = true)
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
    @Operation(summary = "获取教练", description = "根据ID获取教练")
    @Parameter(name = "id", description = "教练ID", required = true)
    public Coach getCoachById(Long id) {
        if (id == null) {
            throw new BadRequestException("传入教练的ID为空");
        }
        Coach coach = coachService.getById(id);
        if (coach == null) {
            throw new ResourceNotFoundException("教练不存在");
        }
        return coach;
    }

    @GetMapping("/getAll")
    @Operation(summary = "获取所有教练", description = "获取所有教练")
    public List<Coach> getAllCoaches() {
        return coachService.list();
    }

    @PutMapping("/update")
    @Operation(summary = "更新教练", description = "更新教练信息")
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
    @Operation(summary = "获取球队邀请", description = "获取教练的球队邀请")
    @Parameter(name = "coachId", description = "教练 ID", required = true)
    public List<TeamCoachRequest> getTeamInvitations(Long coachId) {
        if (coachId == null) {
            throw new BadRequestException("教练ID不能为空");
        }
        if (coachService.getById(coachId) == null) {
            throw new ResourceNotFoundException("教练不存在");
        }
        return teamCoachRequestService.listWithTeam(coachId);
    }

    @PostMapping("/team/replyInvitation")
    @Operation(summary = "回复球队邀请", description = "回复教练的球队邀请")
    @Parameters({
            @Parameter(name = "coachId", description = "教练 ID", required = true),
            @Parameter(name = "teamId", description = "球队 ID", required = true),
            @Parameter(name = "accept", description = "是否接受", required = true)
    })
    public void replyTeamInvitation(Long coachId, Long teamId, Boolean accept) {
        if (coachId == null || teamId == null || accept == null) {
            throw new BadRequestException("教练ID、球队ID和状态不能为空");
        }
        if (coachService.getById(coachId) == null) {
            throw new ResourceNotFoundException("教练不存在");
        }
        if (teamService.getById(teamId) == null) {
            throw new ResourceNotFoundException("球队不存在");
        }
        if (!coachService.replyTeamInvitation(coachId, teamId, accept)) {
            throw new BadRequestException("处理邀请失败");
        }
    }

    @GetMapping("/team/getAll")
    @Operation(summary = "获取球队", description = "获取教练的球队")
    @Parameter(name = "coachId", description = "教练 ID", required = true)
    public List<Team> getTeams(Long coachId) {
        if (coachId == null) {
            throw new BadRequestException("教练ID不能为空");
        }
        if (coachService.getById(coachId) == null) {
            throw new ResourceNotFoundException("教练不存在");
        }
        return coachService.getTeams(coachId);
    }

    @GetMapping("/match/getAll")
    @Operation(summary = "获取比赛", description = "获取教练队伍的比赛")
    @Parameter(name = "coachId", description = "教练 ID", required = true)
    public List<Match> getMatches(Long coachId) {
        if (coachId == null) {
            throw new BadRequestException("教练ID不能为空");
        }
        if (coachService.getById(coachId) == null) {
            throw new ResourceNotFoundException("教练不存在");
        }
        return coachService.getMatches(coachId);
    }

    @GetMapping("/event/getAll")
    @Operation(summary = "获取赛事", description = "获取教练队伍的赛事")
    @Parameter(name = "coachId", description = "教练 ID", required = true)
    public List<Event> getEvents(@RequestParam Long coachId) {
        if (coachId == null) {
            throw new BadRequestException("教练ID不能为空");
        }
        if (coachService.getById(coachId) == null) {
            throw new ResourceNotFoundException("教练不存在");
        }
        return coachService.getEvents(coachId);
    }
}
