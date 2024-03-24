package com.sustech.football.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sustech.football.entity.*;
import com.sustech.football.exception.ConflictException;
import com.sustech.football.exception.ResourceNotFoundException;
import com.sustech.football.mapper.EventMapper;
import com.sustech.football.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EventServiceImpl extends ServiceImpl<EventMapper, Event> implements EventService {
    @Autowired
    private EventManagerService eventManagerService;
    @Autowired
    private EventTeamService eventTeamService;
    @Autowired
    private EventTeamRequestService eventTeamRequestService;
    @Autowired
    private MatchService matchService;
    @Autowired
    private EventMatchService eventMatchService;
    @Autowired
    private EventRefereeService eventRefereeService;
    @Autowired
    private EventRefereeRequestService eventRefereeRequestService;
    @Autowired
    private EventGroupService eventGroupService;
    @Autowired
    private EventGroupTeamService eventGroupTeamService;
    @Autowired
    private TeamPlayerService teamPlayerService;

    @Override
    public Event getDetailedEvent(Long eventId) {
        Event event = getById(eventId);
        if (event == null) {
            throw new ResourceNotFoundException("赛事不存在");
        }
        List<EventManager> eventManagers = eventManagerService.list(new QueryWrapper<EventManager>().eq("event_id", eventId));
        event.setManagerList(eventManagers.stream().map(EventManager::getUserId).toList());
        List<EventTeam> eventTeams = eventTeamService.listWithTeam(eventId);
        for (EventTeam et : eventTeams) {
            List<TeamPlayer> teamPlayerList = teamPlayerService.listWithPlayer(et.getTeam().getTeamId());
            et.getTeam().setPlayerList(teamPlayerList.stream().map(TeamPlayer::getPlayer).toList());
        }
        event.setTeamList(eventTeams.stream().map(EventTeam::getTeam).map(t -> new Event.Team(t.getTeamId(), t.getName(), t.getLogoUrl(), t.getPlayerList().size())).toList());
        List<EventGroup> eventGroups = eventGroupService.list(new QueryWrapper<EventGroup>().eq("event_id", eventId));
        event.setGroupList(eventGroups.stream().map(g -> {
            Event.Group group = new Event.Group();
            group.setGroupId(g.getGroupId());
            group.setName(g.getName());
            List<EventGroupTeam> eventGroupTeams = eventGroupTeamService.list(new QueryWrapper<EventGroupTeam>().eq("group_id", g.getGroupId()));
            group.setTeamList(eventGroupTeams.stream().map(t -> {
                Event.Group.Team team = new Event.Group.Team();
                for (EventTeam et : eventTeams) {
                    if (et.getTeamId().equals(t.getTeamId())) {
                        team.setTeam(new Event.Team(et.getTeamId(), et.getTeam().getName(), et.getTeam().getLogoUrl(), et.getTeam().getPlayerList().size()));
                        break;
                    }
                }
                team.setNumWins(t.getNumWins());
                team.setNumDraws(t.getNumDraws());
                team.setNumLosses(t.getNumLosses());
                team.setNumGoalsFor(t.getNumGoalsFor());
                team.setNumGoalsAgainst(t.getNumGoalsAgainst());
                team.setScore(t.getScore());
                return team;
            }).toList());
            return group;
        }).toList());
        List<EventMatch> eventMatches = eventMatchService.list(new QueryWrapper<EventMatch>().eq("event_id", eventId));
        eventMatches.forEach(em -> {
            Match match = matchService.getById(em.getMatchId());
            for (EventTeam et : eventTeams) {
                if (et.getTeamId().equals(match.getHomeTeamId())) {
                    match.setHomeTeam(et.getTeam());
                }
                if (et.getTeamId().equals(match.getAwayTeamId())) {
                    match.setAwayTeam(et.getTeam());
                }
            }
            match.setMatchEvent(new MatchEvent(em));
            em.setMatch(match);
        });
        event.setMatchList(eventMatches.stream().map(EventMatch::getMatch).toList());
        event.setStageList(eventMatches.stream().map(EventMatch::getStage).distinct().map(stage -> {
            Event.Stage eventStage = new Event.Stage();
            eventStage.setStageName(stage);
            eventStage.setTags(eventMatches.stream().filter(em -> em.getStage().equals(stage)).map(EventMatch::getTag).distinct().map(tag -> {
                Event.Tag eventTag = new Event.Tag();
                eventTag.setTagName(tag);
                eventTag.setMatches(eventMatches.stream().filter(em2 -> em2.getStage().equals(stage) && em2.getTag().equals(tag)).map(EventMatch::getMatch).toList());
                return eventTag;
            }).toList());
            return eventStage;
        }).toList());
        return event;
    }

    @Override
    public boolean inviteManager(EventManager eventManager) {
        if (eventManagerService.selectByMultiId(eventManager) != null) {
            throw new ConflictException("该用户已经是该赛事的管理员");
        }
        if (!eventManagerService.saveOrUpdateByMultiId(eventManager)) {
            throw new RuntimeException("邀请失败");
        }
        return true;
    }

    @Override
    public List<Long> getManagers(Long eventId) {
        QueryWrapper<EventManager> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("event_id", eventId);
        return eventManagerService.list(queryWrapper).stream().map(EventManager::getUserId).toList();
    }

    @Override
    public boolean deleteManager(EventManager eventManager) {
        if (!eventManagerService.deleteByMultiId(eventManager)) {
            throw new RuntimeException("删除管理员失败");
        }
        return true;
    }

    @Override
    @Transactional
    public boolean inviteTeam(EventTeamRequest eventTeamRequest) {
        EventTeam eventTeam = new EventTeam(eventTeamRequest.getEventId(), eventTeamRequest.getTeamId());
        if (eventTeamService.selectByMultiId(eventTeam) != null) {
            throw new ConflictException("该队伍已经是该赛事的参赛队伍");
        }
        if (eventTeamRequestService.selectByMultiId(eventTeamRequest) != null) {
            throw new ConflictException("该队伍已经被邀请参加赛事");
        }
        if (!eventTeamRequestService.saveOrUpdateByMultiId(eventTeamRequest)) {
            throw new RuntimeException("邀请失败");
        }
        return true;
    }

    @Override
    public List<EventTeamRequest> getTeamInvitations(Long eventId) {
        return eventTeamRequestService.listWithTeam(eventId, EventTeamRequest.TYPE_INVITATION);
    }

    @Override
    public List<EventTeamRequest> getTeamApplications(Long eventId) {
        return eventTeamRequestService.listWithTeam(eventId, EventTeamRequest.TYPE_APPLICATION);
    }

    @Override
    @Transactional
    public boolean replyTeamApplication(EventTeamRequest eventTeamRequest) {
        EventTeam eventTeam = new EventTeam(eventTeamRequest.getEventId(), eventTeamRequest.getTeamId());
        if (eventTeamService.selectByMultiId(eventTeam) != null) {
            throw new ConflictException("该队伍已经是该赛事的参赛队伍");
        }
        if (eventTeamRequestService.selectByMultiId(eventTeamRequest) == null) {
            throw new ResourceNotFoundException("该队伍没有申请参加赛事");
        }
        if (!eventTeamRequestService.updateByMultiId(eventTeamRequest)) {
            throw new RuntimeException("回复球队申请失败");
        }
        if (eventTeamRequest.getStatus().equals(EventTeamRequest.STATUS_ACCEPTED)) {
            if (!eventTeamService.saveOrUpdateByMultiId(eventTeam)) {
                throw new RuntimeException("添加参赛队伍失败");
            }
        }
        return true;
    }

    @Override
    public List<Team> getTeams(Long eventId) {
        return eventTeamService.listWithTeam(eventId).stream().map(EventTeam::getTeam).toList();
    }

    @Override
    public boolean deleteTeam(EventTeam eventTeam) {
        if (eventTeamService.selectByMultiId(eventTeam) == null) {
            throw new ResourceNotFoundException("该队伍不是该赛事的参赛队伍");
        }
        if (!eventTeamService.deleteByMultiId(eventTeam)) {
            throw new RuntimeException("删除参赛队伍失败");
        }
        return true;
    }

    @Override
    public boolean addMatch(Long eventId, Match match, String stage, String tag) {
        if (!matchService.save(match)) {
            throw new RuntimeException("添加比赛失败");
        }
        if (!eventMatchService.saveOrUpdateByMultiId(new EventMatch(eventId, match.getMatchId(), stage, tag))) {
            throw new RuntimeException("添加赛事比赛失败");
        }
        return true;
    }

    @Override
    public List<Match> getMatches(Long eventId) {
        return eventMatchService.listWithMatch(eventId).stream().map(EventMatch::getMatch).toList();
    }

    @Override
    public boolean deleteMatch(EventMatch eventMatch) {
        if (eventMatchService.selectByMultiId(eventMatch) == null) {
            throw new ResourceNotFoundException("该比赛不是该赛事的比赛");
        }
        if (!eventMatchService.deleteByMultiId(eventMatch)) {
            throw new RuntimeException("删除赛事比赛失败");
        }
        return true;
    }

    @Override
    public boolean inviteReferee(EventRefereeRequest eventRefereeRequest) {
        EventReferee eventReferee = new EventReferee(eventRefereeRequest.getEventId(), eventRefereeRequest.getRefereeId());
        if (eventRefereeService.selectByMultiId(eventReferee) != null) {
            throw new ConflictException("该裁判已经执法该赛事");
        }
        if (eventRefereeRequestService.selectByMultiId(eventRefereeRequest) != null) {
            throw new ConflictException("该裁判已经被邀请执法该赛事");
        }
        if (!eventRefereeRequestService.saveOrUpdateByMultiId(eventRefereeRequest)) {
            throw new RuntimeException("邀请失败");
        }
        return true;
    }

    @Override
    public List<Referee> getReferees(Long eventId) {
        return eventRefereeService.listWithReferee(eventId).stream().map(EventReferee::getReferee).toList();
    }

    @Override
    public boolean deleteReferee(EventReferee eventReferee) {
        if (eventRefereeService.selectByMultiId(eventReferee) == null) {
            throw new ResourceNotFoundException("该裁判不是该赛事的裁判");
        }
        if (!eventRefereeService.deleteByMultiId(eventReferee)) {
            throw new RuntimeException("删除赛事裁判失败");
        }
        return true;
    }
}