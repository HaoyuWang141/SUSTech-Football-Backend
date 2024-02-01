package com.sustech.football.controller;

import com.sustech.football.entity.*;
import com.sustech.football.exception.BadRequestException;
import com.sustech.football.exception.ResourceNotFoundException;
import com.sustech.football.service.EventService;
import com.sustech.football.service.MatchService;
import com.sustech.football.service.RefereeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/referee")
public class RefereeController {
    @Autowired
    private RefereeService refereeService;
    @Autowired
    private MatchService matchService;
    @Autowired
    private EventService eventService;

    @PostMapping("/create")
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
    public List<Referee> getAllReferees() {
        return refereeService.list();
    }

    @PutMapping("/update")
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
    public void replyMatchInvitation(Long refereeId, Long matchId, Boolean accepted) {
        if (refereeId == null || matchId == null || accepted == null) {
            throw new BadRequestException("传入的参数含有空值");
        }
        if (refereeService.getById(refereeId) == null) {
            throw new ResourceNotFoundException("裁判不存在");
        }
        if (matchService.getById(matchId) == null) {
            throw new ResourceNotFoundException("比赛不存在");
        }
        String status = accepted ? MatchRefereeRequest.STATUS_ACCEPTED : MatchRefereeRequest.STATUS_REJECTED;
        if (!refereeService.replyMatchInvitation(new MatchRefereeRequest(
                matchId, refereeId, status
        ))) {
            throw new RuntimeException("回复比赛邀请失败");
        }
    }

    @GetMapping("/match/getAll")
    public List<Match> getMatches(Long refereeId) {
        if (refereeId == null) {
            throw new BadRequestException("传入的裁判id为空");
        }
        if (refereeService.getById(refereeId) == null) {
            throw new ResourceNotFoundException("裁判不存在");
        }
        return refereeService.getMatches(refereeId);
    }

    @GetMapping("/event/getInvitations")
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
    public void replyEventInvitation(Long refereeId, Long eventId, Boolean accepted) {
        if (refereeId == null || eventId == null || accepted == null) {
            throw new BadRequestException("传入的参数含有空值");
        }
        if (refereeService.getById(refereeId) == null) {
            throw new ResourceNotFoundException("裁判不存在");
        }
        if (eventService.getById(eventId) == null) {
            throw new ResourceNotFoundException("赛事不存在");
        }
        String status = accepted ? EventRefereeRequest.STATUS_ACCEPTED : EventRefereeRequest.STATUS_REJECTED;
        if (!refereeService.replyEventInvitation(new EventRefereeRequest(
                eventId, refereeId, status
        ))) {
            throw new RuntimeException("回复赛事邀请失败");
        }
    }

    @GetMapping("/event/getAll")
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
