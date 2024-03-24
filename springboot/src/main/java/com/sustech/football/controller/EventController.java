package com.sustech.football.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sustech.football.entity.*;
import com.sustech.football.exception.BadRequestException;
import com.sustech.football.exception.ResourceNotFoundException;
import com.sustech.football.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/event")
public class EventController {
    @Autowired
    private EventService eventService;
    @Autowired
    private UserService userService;
    @Autowired
    private TeamService teamService;
    @Autowired
    private MatchService matchService;
    @Autowired
    private RefereeService refereeService;
    @Autowired
    private EventGroupService eventGroupService;
    @Autowired
    private EventGroupTeamService eventGroupTeamService;
    @Autowired
    private EventStageService eventStageService;
    @Autowired
    private EventStageTagService eventStageTagService;


    @PostMapping("/create")
    public Event createEvent(Long ownerId, @RequestBody Event event) {
        if (ownerId == null) {
            throw new BadRequestException("赛事所有者ID不能为空");
        }
        if (userService.getById(ownerId) == null) {
            throw new BadRequestException("赛事所有者不存在");
        }
        if (event == null) {
            throw new BadRequestException("传入的赛事为空");
        }
        if (event.getEventId() != null) {
            throw new BadRequestException("传入的赛事已有ID");
        }
        if (!eventService.save(event)) {
            throw new BadRequestException("创建赛事失败");
        }
        if (!eventService.inviteManager(new EventManager(event.getEventId(), ownerId, true))) {
            throw new BadRequestException("创建比赛失败");
        }
        return event;
    }

    @GetMapping("/get")
    public Event getEvent(Long id) {
        if (id == null) {
            throw new BadRequestException("传入的赛事ID为空");
        }
        Event event = null;
        try {
            event = eventService.getDetailedEvent(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (event == null) {
            throw new BadRequestException("传入的赛事ID不存在");
        }
        return event;
    }

    @GetMapping("/getAll")
    public List<Event> getAllEvents() {
        return eventService.list();
    }

    @GetMapping("/getByIdList")
    public List<Event> getEventsByIdLists(List<Long> idList) {
        return eventService.listByIds(idList);
    }

    @PutMapping("/update")
    public void updateEvent(@RequestBody Event event) {
        if (event == null) {
            throw new BadRequestException("传入的赛事为空");
        }
        if (event.getEventId() == null) {
            throw new BadRequestException("传入的赛事ID为空");
        }
        if (!eventService.updateById(event)) {
            throw new BadRequestException("更新赛事失败");
        }
    }

    @DeleteMapping("/delete")
    public void deleteEvent(Long eventId) {
        if (eventId == null) {
            throw new BadRequestException("传入的赛事ID为空");
        }
        if (!eventService.removeById(eventId)) {
            throw new BadRequestException("删除赛事失败");
        }
    }

    @PostMapping("/manager/invite")
    public void inviteManager(Long eventId, Long managerId) {
        if (eventId == null || managerId == null) {
            throw new BadRequestException("传入的赛事ID或管理员ID为空");
        }
        if (eventService.getById(eventId) == null) {
            throw new ResourceNotFoundException("赛事不存在");
        }
        if (userService.getById(managerId) == null) {
            throw new ResourceNotFoundException("管理员不存在");
        }
        if (!eventService.inviteManager(new EventManager(eventId, managerId, false))) {
            throw new RuntimeException("邀请管理员失败");
        }
    }

    @GetMapping("/manager/getAll")
    public List<Long> getManagers(Long eventId) {
        if (eventId == null) {
            throw new BadRequestException("传入的赛事ID为空");
        }
        if (eventService.getById(eventId) == null) {
            throw new ResourceNotFoundException("赛事不存在");
        }
        return eventService.getManagers(eventId);
    }

    @DeleteMapping("/manager/delete")
    public void deleteManager(Long eventId, Long managerId) {
        if (eventId == null || managerId == null) {
            throw new BadRequestException("传入的赛事ID或管理员ID为空");
        }
        if (eventService.getById(eventId) == null) {
            throw new ResourceNotFoundException("赛事不存在");
        }
        if (userService.getById(managerId) == null) {
            throw new ResourceNotFoundException("管理员不存在");
        }
        if (!eventService.deleteManager(new EventManager(eventId, managerId, false))) {
            throw new RuntimeException("删除管理员失败");
        }
    }

    @PostMapping("/team/invite")
    public void inviteTeam(Long eventId, Long teamId) {
        if (eventId == null || teamId == null) {
            throw new BadRequestException("传入的赛事ID或球队ID为空");
        }
        if (eventService.getById(eventId) == null) {
            throw new ResourceNotFoundException("赛事不存在");
        }
        if (userService.getById(teamId) == null) {
            throw new ResourceNotFoundException("球队不存在");
        }
        if (!eventService.inviteTeam(new EventTeamRequest(
                eventId, teamId, EventTeamRequest.TYPE_INVITATION
        ))) {
            throw new RuntimeException("邀请球队失败");
        }
    }

    @GetMapping("/team/getInvitations")
    public List<EventTeamRequest> getTeamInvitations(Long eventId) {
        if (eventId == null) {
            throw new BadRequestException("传入的赛事ID为空");
        }
        if (eventService.getById(eventId) == null) {
            throw new ResourceNotFoundException("赛事不存在");
        }
        return eventService.getTeamInvitations(eventId);
    }

    @GetMapping("/team/getApplications")
    public List<EventTeamRequest> getTeamApplications(Long eventId) {
        if (eventId == null) {
            throw new BadRequestException("传入的赛事ID为空");
        }
        if (eventService.getById(eventId) == null) {
            throw new ResourceNotFoundException("赛事不存在");
        }
        return eventService.getTeamApplications(eventId);
    }

    @PostMapping("/team/replyApplication")
    public void replyTeamApplication(Long eventId, Long teamId, Boolean accepted) {
        if (eventId == null || teamId == null || accepted == null) {
            throw new BadRequestException("传入的参数含有空值");
        }
        if (eventService.getById(eventId) == null) {
            throw new ResourceNotFoundException("赛事不存在");
        }
        if (teamService.getById(teamId) == null) {
            throw new ResourceNotFoundException("球队不存在");
        }
        if (!eventService.replyTeamApplication(new EventTeamRequest(
                eventId, teamId, EventTeamRequest.TYPE_APPLICATION,
                accepted ? EventTeamRequest.STATUS_ACCEPTED : EventTeamRequest.STATUS_REJECTED
        ))) {
            throw new RuntimeException("回复球队申请失败");
        }
    }

    @GetMapping("/team/getAll")
    public List<Team> getTeams(Long eventId) {
        if (eventId == null) {
            throw new BadRequestException("传入的赛事ID为空");
        }
        if (eventService.getById(eventId) == null) {
            throw new ResourceNotFoundException("赛事不存在");
        }
        return eventService.getTeams(eventId);
    }

    @DeleteMapping("/team/delete")
    public void deleteTeam(Long eventId, Long teamId) {
        if (eventId == null || teamId == null) {
            throw new BadRequestException("传入的赛事ID或球队ID为空");
        }
        if (eventService.getById(eventId) == null) {
            throw new ResourceNotFoundException("赛事不存在");
        }
        if (teamService.getById(teamId) == null) {
            throw new ResourceNotFoundException("球队不存在");
        }
        if (!eventService.deleteTeam(new EventTeam(eventId, teamId))) {
            throw new RuntimeException("删除球队失败");
        }
    }

    @PostMapping("/group/new")
    public EventGroup newGroup(Long eventId, String groupName) {
        if (eventId == null || groupName == null) {
            throw new BadRequestException("传入的赛事ID或组名为空");
        }
        if (eventService.getById(eventId) == null) {
            throw new ResourceNotFoundException("赛事不存在");
        }
        EventGroup group = new EventGroup(eventId, groupName);
        if (!eventGroupService.save(group)) {
            throw new RuntimeException("创建分组失败");
        }
        return group;
    }

    @GetMapping("/group/getByEventId")
    public List<EventGroup> getGroups(Long eventId) {
        if (eventId == null) {
            throw new BadRequestException("传入的赛事ID为空");
        }
        if (eventService.getById(eventId) == null) {
            throw new ResourceNotFoundException("赛事不存在");
        }
        QueryWrapper<EventGroup> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("event_id", eventId);
        return eventGroupService.list(queryWrapper);
    }

    @PutMapping("/group/updateName")
    public void updateGroup(Integer groupId, String name) {
        if (groupId == null || name == null) {
            throw new BadRequestException("传入的分组ID或组名为空");
        }
        if (eventGroupService.getById(groupId) == null) {
            throw new ResourceNotFoundException("分组不存在");
        }
        EventGroup group = eventGroupService.getById(groupId);
        group.setName(name);
        if (!eventGroupService.updateById(group)) {
            throw new RuntimeException("更新分组名失败");
        }
    }

    @DeleteMapping("/group/delete")
    public void deleteGroup(Long groupId) {
        if (groupId == null) {
            throw new BadRequestException("传入的分组ID为空");
        }
        if (eventGroupService.getById(groupId) == null) {
            throw new ResourceNotFoundException("分组不存在");
        }
        if (eventGroupTeamService.remove(new QueryWrapper<EventGroupTeam>().eq("group_id", groupId))) {
            throw new RuntimeException("删除分组内的球队失败");
        }
        if (!eventGroupService.removeById(groupId)) {
            throw new RuntimeException("删除分组失败");
        }
    }

    @PostMapping("/group/addTeam")
    public void addTeamIntoGroup(Long groupId, Long teamId) {
        if (groupId == null || teamId == null) {
            throw new BadRequestException("传入的分组ID或球队ID为空");
        }
        if (eventGroupService.getById(groupId) == null) {
            throw new ResourceNotFoundException("分组不存在");
        }
        if (teamService.getById(teamId) == null) {
            throw new ResourceNotFoundException("球队不存在");
        }
        if (!eventGroupTeamService.saveOrUpdateByMultiId(new EventGroupTeam(groupId, teamId))) {
            throw new RuntimeException("添加球队到分组失败");
        }
    }

    @DeleteMapping("/group/deleteTeam")
    public void deleteTeamFromGroup(Long groupId, Long teamId) {
        if (groupId == null || teamId == null) {
            throw new BadRequestException("传入的分组ID或球队ID为空");
        }
        if (eventGroupService.getById(groupId) == null) {
            throw new ResourceNotFoundException("分组不存在");
        }
        if (teamService.getById(teamId) == null) {
            throw new ResourceNotFoundException("球队不存在");
        }
        if (!eventGroupTeamService.deleteByMultiId(new EventGroupTeam(groupId, teamId))) {
            throw new RuntimeException("删除球队失败");
        }
    }

    @PostMapping("/stage/new")
    public EventStage newStage(Long eventId, String stageName) {
        if (eventId == null || stageName == null) {
            throw new BadRequestException("传入的赛事ID或阶段名为空");
        }
        if (eventService.getById(eventId) == null) {
            throw new ResourceNotFoundException("赛事不存在");
        }
        EventStage stage = new EventStage(eventId, stageName);
        if (!eventStageService.saveOrUpdateByMultiId(stage)) {
            throw new RuntimeException("创建阶段失败");
        }
        return stage;
    }

    @DeleteMapping("/stage/delete")
    public void deleteStage(Long eventId, String stageName) {
        if (eventId == null || stageName == null) {
            throw new BadRequestException("传入的赛事ID或阶段名为空");
        }
        if (eventService.getById(eventId) == null) {
            throw new ResourceNotFoundException("赛事不存在");
        }
        if (eventStageService.selectByMultiId(new EventStage(eventId, stageName)) == null) {
            throw new ResourceNotFoundException("阶段不存在");
        }
        if (!eventStageService.deleteByMultiId(new EventStage(eventId, stageName))) {
            throw new RuntimeException("删除阶段失败");
        }
    }

    @PostMapping("/tag/new")
    public EventStageTag newTag(Long eventId, String stageName, String tagName) {
        if (eventId == null || stageName == null || tagName == null) {
            throw new BadRequestException("传入的赛事ID或阶段名或标签名为空");
        }
        if (eventService.getById(eventId) == null) {
            throw new ResourceNotFoundException("赛事不存在");
        }
        if (eventStageService.selectByMultiId(new EventStage(eventId, stageName)) == null) {
            throw new ResourceNotFoundException("阶段不存在");
        }
        EventStageTag tag = new EventStageTag(eventId, stageName, tagName);
        if (!eventStageTagService.saveOrUpdateByMultiId(tag)) {
            throw new RuntimeException("创建标签失败");
        }
        return tag;
    }

    @DeleteMapping("/tag/delete")
    public void deleteTag(Long eventId, String stageName, String tagName) {
        if (eventId == null || stageName == null || tagName == null) {
            throw new BadRequestException("传入的赛事ID或阶段名或标签名为空");
        }
        if (eventService.getById(eventId) == null) {
            throw new ResourceNotFoundException("赛事不存在");
        }
        if (eventStageService.selectByMultiId(new EventStage(eventId, stageName)) == null) {
            throw new ResourceNotFoundException("阶段不存在");
        }
        if (eventStageTagService.selectByMultiId(new EventStageTag(eventId, stageName, tagName)) == null) {
            throw new ResourceNotFoundException("标签不存在");
        }
        if (!eventStageTagService.deleteByMultiId(new EventStageTag(eventId, stageName, tagName))) {
            throw new RuntimeException("删除标签失败");
        }
    }

    @PostMapping("/match/add")
    public void addMatch(Long eventId, Match match, String stage, String tag) {
        if (eventId == null || match == null) {
            throw new BadRequestException("传入的赛事ID或比赛为空");
        }
        if (eventService.getById(eventId) == null) {
            throw new ResourceNotFoundException("赛事不存在");
        }
        if (match.getMatchId() != null) {
            throw new BadRequestException("传入的比赛已有ID");
        }
        if (!eventService.addMatch(eventId, match, stage, tag)) {
            throw new RuntimeException("添加比赛失败");
        }
    }

    @GetMapping("/match/getAll")
    public List<Match> getMatches(Long eventId) {
        if (eventId == null) {
            throw new BadRequestException("传入的赛事ID为空");
        }
        if (eventService.getById(eventId) == null) {
            throw new ResourceNotFoundException("赛事不存在");
        }
        return eventService.getMatches(eventId);
    }

    @DeleteMapping("/match/delete")
    public void deleteMatch(Long eventId, Long matchId) {
        if (eventId == null || matchId == null) {
            throw new BadRequestException("传入的赛事ID或比赛ID为空");
        }
        if (eventService.getById(eventId) == null) {
            throw new ResourceNotFoundException("赛事不存在");
        }
        if (matchService.getById(matchId) == null) {
            throw new ResourceNotFoundException("比赛不存在");
        }
        if (!eventService.deleteMatch(new EventMatch(eventId, matchId))) {
            throw new RuntimeException("删除比赛失败");
        }
    }

    @PostMapping("/referee/invite")
    public void inviteReferee(Long eventId, Long refereeId) {
        if (eventId == null || refereeId == null) {
            throw new BadRequestException("传入的赛事ID或裁判ID为空");
        }
        if (eventService.getById(eventId) == null) {
            throw new ResourceNotFoundException("赛事不存在");
        }
        if (refereeService.getById(refereeId) == null) {
            throw new ResourceNotFoundException("裁判不存在");
        }
        if (!eventService.inviteReferee(new EventRefereeRequest(eventId, refereeId))) {
            throw new RuntimeException("邀请裁判失败");
        }
    }

    @GetMapping("/referee/getAll")
    public List<Referee> getReferees(Long eventId) {
        if (eventId == null) {
            throw new BadRequestException("传入的赛事ID为空");
        }
        if (eventService.getById(eventId) == null) {
            throw new ResourceNotFoundException("赛事不存在");
        }
        return eventService.getReferees(eventId);
    }

    @DeleteMapping("/referee/delete")
    public void deleteReferee(Long eventId, Long refereeId) {
        if (eventId == null || refereeId == null) {
            throw new BadRequestException("传入的赛事ID或裁判ID为空");
        }
        if (eventService.getById(eventId) == null) {
            throw new ResourceNotFoundException("赛事不存在");
        }
        if (refereeService.getById(refereeId) == null) {
            throw new ResourceNotFoundException("裁判不存在");
        }
        if (!eventService.deleteReferee(new EventReferee(eventId, refereeId))) {
            throw new RuntimeException("删除裁判失败");
        }

    }

//    private void checkParams(Map<String, Long> params) {
//        params.forEach((k, v) -> {
//            if (v == null) {
//                throw new BadRequestException("传入的" + k + "为空");
//            }
//            if (services.get(k).getById(v) == null) {
//                throw new ResourceNotFoundException(k + "不存在");
//            }
//        });
//    }
}
