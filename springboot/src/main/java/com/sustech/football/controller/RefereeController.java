package com.sustech.football.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sustech.football.entity.*;
import com.sustech.football.exception.BadRequestException;
import com.sustech.football.exception.ResourceNotFoundException;
import com.sustech.football.model.referee.VoRefereeEvent;
import com.sustech.football.model.referee.VoRefereeMatch_brief;
import com.sustech.football.model.referee.VoRefereeTeam;
import com.sustech.football.service.*;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/referee")
@Tag(name = "Referee Controller", description = "裁判管理接口")
public class RefereeController {
    @Autowired
    private RefereeService refereeService;
    @Autowired
    private MatchService matchService;
    @Autowired
    private EventService eventService;
    @Autowired
    private EventMatchService eventMatchService;

    @PostMapping("/create")
    @Operation(summary = "创建裁判", description = "创建一个新的裁判")
    public Referee createReferee(@RequestBody Referee referee) {
        if (referee == null) {
            throw new BadRequestException("传入的裁判为空");
        }
        if (referee.getRefereeId() != null) {
            throw new BadRequestException("传入的id应当为空");
        }
        if (!refereeService.save(referee)) {
            throw new RuntimeException("创建裁判失败");
        }
        return referee;
    }

    @GetMapping("/get")
    @Operation(summary = "获取裁判", description = "根据裁判 ID 获取裁判信息")
    @Parameter(name = "id", description = "裁判 ID", required = true)
    public Referee getReferee(Long id) {
        if (id == null) {
            throw new BadRequestException("传入裁判id为空");
        }
        Referee referee = refereeService.getById(id);
        if (referee == null) {
            throw new ResourceNotFoundException("裁判未找到");
        }
        return referee;
    }

    @GetMapping("/getAll")
    @Operation(summary = "获取所有裁判", description = "获取所有裁判信息")
    public List<Referee> getAllReferees() {
        return refereeService.list();
    }

    @PutMapping("/update")
    @Operation(summary = "更新裁判", description = "更新裁判信息")
    public void updateReferee(@RequestBody Referee referee) {
        if (referee == null) {
            throw new BadRequestException("传入的裁判为空");
        }
        if (referee.getRefereeId() == null) {
            throw new BadRequestException("传入裁判的id为空");
        }
        if (!refereeService.updateById(referee)) {
            throw new RuntimeException("更新裁判失败");
        }
    }

    @Deprecated
    @DeleteMapping("/delete")
    public void deleteReferee(Long refereeId) {
        if (refereeId == null) {
            throw new BadRequestException("传入id为空");
        }
        if (refereeService.getById(refereeId) == null) {
            throw new ResourceNotFoundException("该裁判不存在");
        }
        if (!refereeService.removeById(refereeId)) {
            throw new BadRequestException("删除裁判失败");
        }
    }

    @GetMapping("/match/getInvitations")
    @Operation(summary = "获取比赛邀请", description = "根据裁判 ID，获取裁判的比赛邀请")
    @Parameter(name = "refereeId", description = "裁判 ID", required = true)
    public List<MatchRefereeRequest> getMatchInvitations(Long refereeId) {
        if (refereeId == null) {
            throw new BadRequestException("传入的id为空");
        }
        if (refereeService.getById(refereeId) == null) {
            throw new ResourceNotFoundException("该裁判不存在");
        }
        return refereeService.getMatchInvitations(refereeId);
    }

    @PostMapping("/match/replyInvitation")
    @Operation(summary = "回复比赛邀请", description = "根据裁判 ID，比赛 ID，回复比赛邀请")
    @Parameters({
            @Parameter(name = "refereeId", description = "裁判 ID", required = true),
            @Parameter(name = "matchId", description = "比赛 ID", required = true),
            @Parameter(name = "accept", description = "是否接受邀请", required = true)
    })
    public void replyMatchInvitation(Long refereeId, Long matchId, Boolean accept) {
        if (refereeId == null || matchId == null || accept == null) {
            throw new BadRequestException("传入的参数含有空值");
        }
        if (refereeService.getById(refereeId) == null) {
            throw new ResourceNotFoundException("裁判不存在");
        }
        if (matchService.getById(matchId) == null) {
            throw new ResourceNotFoundException("比赛不存在");
        }
        if (!refereeService.replyMatchInvitation(refereeId, matchId, accept)) {
            throw new BadRequestException("回复比赛邀请失败");
        }
    }

    @GetMapping("/match/getAll")
    @Operation(summary = "获取裁判比赛", description = "根据裁判 ID，获取裁判的所有比赛")
    @Parameter(name = "refereeId", description = "裁判 ID", required = true)
    public List<VoRefereeMatch_brief> getMatches(Long refereeId) {
        if (refereeId == null) {
            throw new BadRequestException("传入的裁判id为空");
        }
        if (refereeService.getById(refereeId) == null) {
            throw new ResourceNotFoundException("裁判不存在");
        }
        List<Match> matchList = refereeService.getMatches(refereeId);

        List<VoRefereeMatch_brief> voMatchList = new ArrayList<>();
        for (Match match : matchList) {
            VoRefereeMatch_brief voMatch = new VoRefereeMatch_brief();
            voMatch.setMatchId(match.getMatchId());
            voMatch.setTime(match.getTime());
            voMatch.setStatus(match.getStatus());

            VoRefereeTeam homeTeam = new VoRefereeTeam();
            homeTeam.setTeamId(match.getHomeTeamId());
            homeTeam.setName(match.getHomeTeam().getName());
            homeTeam.setLogoUrl(match.getHomeTeam().getLogoUrl());
            homeTeam.setScore(match.getHomeTeamScore());
            homeTeam.setPenalty(match.getHomeTeamPenalty());
            voMatch.setHomeTeam(homeTeam);

            VoRefereeTeam awayTeam = new VoRefereeTeam();
            awayTeam.setTeamId(match.getAwayTeamId());
            awayTeam.setName(match.getAwayTeam().getName());
            awayTeam.setLogoUrl(match.getAwayTeam().getLogoUrl());
            awayTeam.setScore(match.getAwayTeamScore());
            awayTeam.setPenalty(match.getAwayTeamPenalty());
            voMatch.setAwayTeam(awayTeam);

            QueryWrapper<EventMatch> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("match_id", match.getMatchId());
            EventMatch eventMatch = eventMatchService.getOne(queryWrapper);
            VoRefereeEvent event = new VoRefereeEvent();
            if (eventMatch != null) {
                event.setEventId(eventMatch.getEventId());
                event.setEventName(eventService.getById(eventMatch.getEventId()).getName());
                event.setStage(eventMatch.getStage());
                event.setTag(eventMatch.getTag());
            }
            voMatch.setEvent(event);

            voMatchList.add(voMatch);
        }

        return voMatchList;
    }

    @GetMapping("/event/getInvitations")
    @Operation(summary = "获取赛事邀请", description = "根据裁判 ID，获取裁判的赛事邀请")
    @Parameter(name = "refereeId", description = "裁判 ID", required = true)
    public List<EventRefereeRequest> getEventInvitations(Long refereeId) {
        if (refereeId == null) {
            throw new BadRequestException("传入的裁判id为空");
        }
        if (refereeService.getById(refereeId) == null) {
            throw new ResourceNotFoundException("裁判不存在");
        }
        return refereeService.getEventInvitations(refereeId);
    }

    @PostMapping("/event/replyInvitation")
    @Operation(summary = "回复赛事邀请", description = "根据裁判 ID，赛事 ID，回复赛事邀请")
    @Parameters({
            @Parameter(name = "refereeId", description = "裁判 ID", required = true),
            @Parameter(name = "eventId", description = "赛事 ID", required = true),
            @Parameter(name = "accept", description = "是否接受邀请", required = true)
    })
    public void replyEventInvitation(Long refereeId, Long eventId, Boolean accept) {
        if (refereeId == null || eventId == null || accept == null) {
            throw new BadRequestException("传入的参数含有空值");
        }
        if (refereeService.getById(refereeId) == null) {
            throw new ResourceNotFoundException("裁判不存在");
        }
        if (eventService.getById(eventId) == null) {
            throw new ResourceNotFoundException("赛事不存在");
        }
        if (!refereeService.replyEventInvitation(refereeId, eventId, accept)) {
            throw new BadRequestException("处理邀请失败");
        }
    }

    @GetMapping("/event/getAll")
    @Operation(summary = "获取裁判赛事", description = "根据裁判 ID，获取裁判的所有赛事")
    @Parameter(name = "refereeId", description = "裁判 ID", required = true)
    public List<Event> getEvents(Long refereeId) {
        if (refereeId == null) {
            throw new BadRequestException("传入的裁判id为空");
        }
        if (refereeService.getById(refereeId) == null) {
            throw new ResourceNotFoundException("裁判不存在");
        }
        return refereeService.getEvents(refereeId);
    }
}
