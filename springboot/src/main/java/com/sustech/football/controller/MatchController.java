package com.sustech.football.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sustech.football.entity.*;
import com.sustech.football.exception.BadRequestException;
import com.sustech.football.exception.InternalServerErrorException;
import com.sustech.football.exception.ResourceNotFoundException;
import com.sustech.football.model.VoTeam;
import com.sustech.football.model.match.*;
import com.sustech.football.service.*;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/match")
public class MatchController {
    @Autowired
    private MatchService matchService;
    @Autowired
    private UserService userService;
    @Autowired
    private TeamService teamService;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private MatchLiveService matchLiveService;
    @Autowired
    private MatchVideoService matchVideoService;
    @Autowired
    private MatchPlayerService matchPlayerService;
    @Autowired
    private RefereeService refereeService;

    @PostMapping("/create")
    @Transactional
    public Match createMatch(Long ownerId, @RequestBody Match match) {
        if (ownerId == null) {
            throw new BadRequestException("比赛所有者ID不能为空");
        }
        if (userService.getById(ownerId) == null) {
            throw new BadRequestException("比赛所有者不存在");
        }
        if (match == null) {
            throw new BadRequestException("比赛信息不能为空");
        }
        if (match.getMatchId() != null) {
            throw new BadRequestException("新建的比赛, 其ID不能存在");
        }
        if (!matchService.save(match)) {
            throw new BadRequestException("创建比赛失败");
        }
        if (!matchService.inviteManager(new MatchManager(match.getMatchId(), ownerId, true))) {
            throw new BadRequestException("创建比赛失败");
        }
        return match;
    }

    @GetMapping("/get")
    public VoMatch getMatch(Long id) {
        if (id == null) {
            throw new BadRequestException("比赛ID不能为空");
        }
        VoMatch voMatch = new VoMatch();
        voMatch.setMatchId(id);

        // 比赛基本信息
        Match match = matchService.getById(id);
        if (match == null) {
            throw new BadRequestException("比赛不存在");
        }
        voMatch.setTime(match.getTime());
        voMatch.setStatus(match.getStatus());

        // 主队
        Team homeTeam = teamService.getById(match.getHomeTeamId());
        List<MatchPlayer> homePlayers = matchPlayerService.list(new QueryWrapper<MatchPlayer>().eq("match_id", id).eq("team_id", match.getHomeTeamId()));
        List<VoMatchPlayer> voHomePlayers = new ArrayList<>();
        for (MatchPlayer matchPlayer : homePlayers) {
            Player player = playerService.getById(matchPlayer.getPlayerId());
            VoMatchPlayer voMatchPlayer = new VoMatchPlayer();
            voMatchPlayer.setPlayerId(player.getPlayerId());
            voMatchPlayer.setNumber(matchPlayer.getNumber());
            voMatchPlayer.setName(player.getName());
            voMatchPlayer.setPhotoUrl(player.getPhotoUrl());
            voMatchPlayer.setIsStart(matchPlayer.getIsStart());
            voHomePlayers.add(voMatchPlayer);
        }
        VoMatchTeam voHomeTeam = new VoMatchTeam();
        voHomeTeam.setTeamId(homeTeam.getTeamId());
        voHomeTeam.setName(homeTeam.getName());
        voHomeTeam.setLogoUrl(homeTeam.getLogoUrl());
        voHomeTeam.setScore(match.getHomeTeamScore());
        voHomeTeam.setPenalty(match.getHomeTeamPenalty());
        voHomeTeam.setPlayers(voHomePlayers);
        voMatch.setHomeTeam(voHomeTeam);

        // 客队
        Team awayTeam = teamService.getById(match.getAwayTeamId());
        List<MatchPlayer> awayPlayers = matchPlayerService.list(new QueryWrapper<MatchPlayer>().eq("match_id", id).eq("team_id", match.getAwayTeamId()));
        List<VoMatchPlayer> voAwayPlayers = new ArrayList<>();
        for (MatchPlayer matchPlayer : awayPlayers) {
            Player player = playerService.getById(matchPlayer.getPlayerId());
            VoMatchPlayer voMatchPlayer = new VoMatchPlayer();
            voMatchPlayer.setPlayerId(player.getPlayerId());
            voMatchPlayer.setNumber(matchPlayer.getNumber());
            voMatchPlayer.setName(player.getName());
            voMatchPlayer.setPhotoUrl(player.getPhotoUrl());
            voMatchPlayer.setIsStart(matchPlayer.getIsStart());
            voAwayPlayers.add(voMatchPlayer);
        }
        VoMatchTeam voAwayTeam = new VoMatchTeam();
        voAwayTeam.setTeamId(awayTeam.getTeamId());
        voAwayTeam.setName(awayTeam.getName());
        voAwayTeam.setLogoUrl(awayTeam.getLogoUrl());
        voAwayTeam.setScore(match.getAwayTeamScore());
        voAwayTeam.setPenalty(match.getAwayTeamPenalty());
        voAwayTeam.setPlayers(voAwayPlayers);
        voMatch.setAwayTeam(voAwayTeam);

        // 管理员
        List<Long> managerList = matchService.getManagers(id);
        voMatch.setManagerList(managerList);

        // 裁判
        List<Referee> refereeList = matchService.getReferees(id);
        List<Long> refereeIdList = refereeList.stream().map(Referee::getRefereeId).toList();
        voMatch.setRefereeList(refereeIdList);

        // 比赛事件
        List<MatchPlayerAction> matchPlayerActions = matchService.getMatchPlayerActions(id);
        List<VoMatchPlayerAction> voMatchPlayerActions = new ArrayList<>();
        for (MatchPlayerAction matchPlayerAction : matchPlayerActions) {
            VoMatchPlayerAction voMatchPlayerAction = new VoMatchPlayerAction();
            voMatchPlayerAction.setTeamId(matchPlayerAction.getTeamId());
            VoMatchPlayer voMatchPlayer;
            if (matchPlayerAction.getTeamId().equals(match.getHomeTeamId())) {
                voMatchPlayer = voHomePlayers.stream().filter(voMatchPlayer1 -> voMatchPlayer1.getPlayerId().equals(matchPlayerAction.getPlayerId())).findFirst().orElse(null);
            } else {
                voMatchPlayer = voAwayPlayers.stream().filter(voMatchPlayer1 -> voMatchPlayer1.getPlayerId().equals(matchPlayerAction.getPlayerId())).findFirst().orElse(null);
            }
            if (voMatchPlayer == null) {
                throw new InternalServerErrorException("比赛球员动作信息错误，未找到对应球员");
            }
            voMatchPlayerAction.setPlayer(voMatchPlayer);
            voMatchPlayerAction.setTime(matchPlayerAction.getTime());
            voMatchPlayerAction.setAction(matchPlayerAction.getAction());
            voMatchPlayerActions.add(voMatchPlayerAction);
        }
        voMatchPlayerActions.sort(Comparator.comparing(VoMatchPlayerAction::getTime));
        voMatch.setActions(voMatchPlayerActions);

        // 所属赛事
        MatchEvent matchEvent = matchService.findMatchEvent(match);
        VoMatchEvent voMatchEvent = new VoMatchEvent();
        voMatchEvent.setEventId(matchEvent.getEventId());
        voMatchEvent.setEventName(matchEvent.getEventName());
        voMatchEvent.setStage(matchEvent.getMatchStage());
        voMatchEvent.setTag(matchEvent.getMatchTag());
        voMatch.setEvent(voMatchEvent);

        return voMatch;
    }

    @GetMapping("/getAll")
    public List<Match> getAllMatches() {
        List<Match> matchList = matchService.list();
        matchList.forEach(match -> {
            match.setMatchEvent(matchService.findMatchEvent(match));
            match.setHomeTeam(teamService.getById(match.getHomeTeamId()));
            match.setAwayTeam(teamService.getById(match.getAwayTeamId()));
        });
        return matchList;
    }

    @GetMapping("/getByIdList")
    public List<Match> getMatchByIdList(@RequestParam List<Long> idList) {
        if (idList == null) {
            throw new BadRequestException("比赛ID列表不能为空");
        }
        return matchService.getMatchByIdList(idList);
    }

    @PutMapping("/update")
    public void updateMatch(@RequestBody Match match) {
        if (match == null) {
            throw new BadRequestException("比赛信息不能为空");
        }
        if (match.getMatchId() == null) {
            throw new BadRequestException("更新的比赛, 其ID不能为空");
        }
        if (!matchService.updateById(match)) {
            throw new BadRequestException("更新比赛失败");
        }
    }

    @DeleteMapping("/delete")
    public void deleteMatch(Long matchId) {
        if (matchId == null) {
            throw new BadRequestException("比赛ID不能为空");
        }
        if (!matchService.removeById(matchId)) {
            throw new BadRequestException("删除比赛失败");
        }
    }

    @PostMapping("/manager/invite")
    public void inviteManager(Long matchId, Long managerId) {
        if (matchId == null || managerId == null) {
            throw new BadRequestException("比赛ID和管理员ID不能为空");
        }
        if (matchService.getById(matchId) == null) {
            throw new ResourceNotFoundException("比赛不存在");
        }
        if (userService.getById(managerId) == null) {
            throw new ResourceNotFoundException("管理员不存在");
        }
        if (matchService.inviteManager(new MatchManager(matchId, managerId, false))) {
            throw new RuntimeException("邀请管理员失败");
        }
    }

    @GetMapping("/manager/getAll")
    public List<Long> getManagers(Long matchId) {
        if (matchId == null) {
            throw new BadRequestException("比赛ID不能为空");
        }
        if (matchService.getById(matchId) == null) {
            throw new BadRequestException("比赛不存在");
        }
        return matchService.getManagers(matchId);
    }

    @DeleteMapping("/manager/delete")
    public void deleteManager(Long matchId, Long managerId) {
        if (matchId == null || managerId == null) {
            throw new BadRequestException("比赛ID和管理员ID不能为空");
        }
        if (matchService.getById(matchId) == null) {
            throw new BadRequestException("比赛不存在");
        }
        if (userService.getById(managerId) == null) {
            throw new BadRequestException("管理员不存在");
        }
        if (!matchService.deleteManager(new MatchManager(matchId, managerId, false))) {
            throw new BadRequestException("删除管理员失败");
        }
    }

    @PostMapping("/team/invite")
    public void inviteTeam(Long matchId, Long teamId, Boolean isHomeTeam) {
        if (matchId == null || teamId == null || isHomeTeam == null) {
            throw new BadRequestException("传入的参数不能为空");
        }
        if (matchService.getById(matchId) == null) {
            throw new BadRequestException("比赛不存在");
        }
        if (teamService.getById(teamId) == null) {
            throw new BadRequestException("球队不存在");
        }
        String type = isHomeTeam ? MatchTeamRequest.TYPE_HOME : MatchTeamRequest.TYPE_AWAY;
        if (!matchService.inviteTeam(new MatchTeamRequest(matchId, teamId, type))) {
            throw new BadRequestException("邀请球队失败");
        }
    }

    @GetMapping("/team/getInvitations")
    public List<MatchTeamRequest> getTeamInvitations(Long teamId) {
        if (teamId == null) {
            throw new BadRequestException("球队ID不能为空");
        }
        if (teamService.getById(teamId) == null) {
            throw new BadRequestException("球队不存在");
        }
        return matchService.getTeamInvitations(teamId);
    }

    @GetMapping("/team/get")
    public VoMatchTeam getTeam(Long matchId, Boolean isHomeTeam) {
        if (matchId == null || isHomeTeam == null) {
            throw new BadRequestException("比赛ID和主客队标识不能为空");
        }
        Match match = matchService.getById(matchId);
        if (match == null) {
            throw new BadRequestException("比赛不存在");
        }

        Long teamId = isHomeTeam ? match.getHomeTeamId() : match.getAwayTeamId();
        Team team = teamService.getById(teamId);
        if (team == null) {
            throw new BadRequestException("球队不存在");
        }

        List<MatchPlayer> players = matchPlayerService.list(new QueryWrapper<MatchPlayer>().eq("match_id", matchId).eq("team_id", teamId));
        List<VoMatchPlayer> voPlayers = new ArrayList<>();
        for (MatchPlayer matchPlayer : players) {
            Player player = playerService.getById(matchPlayer.getPlayerId());
            VoMatchPlayer voMatchPlayer = new VoMatchPlayer();
            voMatchPlayer.setPlayerId(player.getPlayerId());
            voMatchPlayer.setNumber(matchPlayer.getNumber());
            voMatchPlayer.setName(player.getName());
            voMatchPlayer.setPhotoUrl(player.getPhotoUrl());
            voMatchPlayer.setIsStart(matchPlayer.getIsStart());
            voPlayers.add(voMatchPlayer);
        }

        VoMatchTeam voTeam = new VoMatchTeam();
        voTeam.setTeamId(team.getTeamId());
        voTeam.setName(team.getName());
        voTeam.setLogoUrl(team.getLogoUrl());
        voTeam.setScore(match.getHomeTeamScore());
        voTeam.setPenalty(match.getHomeTeamPenalty());
        voTeam.setPlayers(voPlayers);

        return voTeam;
    }

    @DeleteMapping("/team/delete")
    public void deleteTeam(Long matchId, Boolean isHomeTeam) {
        if (matchId == null) {
            throw new BadRequestException("比赛ID不能为空");
        }
        if (matchService.getById(matchId) == null) {
            throw new BadRequestException("比赛不存在");
        }
        String type = isHomeTeam ? MatchTeamRequest.TYPE_HOME : MatchTeamRequest.TYPE_AWAY;
        if (!matchService.deleteTeam(matchId, isHomeTeam)) {
            throw new BadRequestException("删除球队失败");
        }
    }

    @PostMapping("/referee/invite")
    public void inviteReferee(Long matchId, Long refereeId) {
        if (matchId == null || refereeId == null) {
            throw new BadRequestException("比赛ID和裁判ID不能为空");
        }
        if (matchService.getById(matchId) == null) {
            throw new BadRequestException("比赛不存在");
        }
        if (userService.getById(refereeId) == null) {
            throw new BadRequestException("裁判不存在");
        }
        if (!matchService.inviteReferee(new MatchReferee(matchId, refereeId))) {
            throw new BadRequestException("邀请裁判失败");
        }

    }

    @GetMapping("/referee/getAll")
    public List<Referee> getReferees(Long matchId) {
        if (matchId == null) {
            throw new BadRequestException("比赛ID不能为空");
        }
        if (matchService.getById(matchId) == null) {
            throw new BadRequestException("比赛不存在");
        }
        return matchService.getReferees(matchId);
    }

    @DeleteMapping("/referee/delete")
    public void deleteReferee(Long matchId, Long refereeId) {
        if (matchId == null || refereeId == null) {
            throw new BadRequestException("比赛ID和裁判ID不能为空");
        }
        if (matchService.getById(matchId) == null) {
            throw new BadRequestException("比赛不存在");
        }
        if (userService.getById(refereeId) == null) {
            throw new BadRequestException("裁判不存在");
        }
        if (!matchService.deleteReferee(new MatchReferee(matchId, refereeId))) {
            throw new BadRequestException("删除裁判失败");
        }
    }

    @GetMapping("/referee/getPlayerList")
    public List<VoMatchPlayer> getPlayerList(Long refereeId, Long matchId, Long teamId) {
        if (refereeId == null || matchId == null || teamId == null) {
            throw new BadRequestException("裁判ID和比赛ID和球队ID不能为空");
        }
        Match match = matchService.getById(matchId);
        if (match == null) {
            throw new BadRequestException("比赛不存在");
        }
        if (teamService.getById(teamId) == null) {
            throw new BadRequestException("球队不存在");
        }
        // 球队存在于比赛中
        if (!match.getHomeTeamId().equals(teamId) && !match.getAwayTeamId().equals(teamId)) {
            throw new BadRequestException("球队不在比赛中");
        }
        // 裁判是比赛的裁判
        Referee referee = refereeService.getById(refereeId);
        if (referee == null) {
            throw new BadRequestException("裁判不存在");
        }
        List<Referee> refereeList = matchService.getReferees(matchId);
        if (!refereeList.contains(referee)) {
            throw new BadRequestException("裁判不是比赛的裁判");
        }

        // 获取球员列表
        List<MatchPlayer> homePlayers = matchPlayerService.list(new QueryWrapper<MatchPlayer>().eq("match_id", matchId).eq("team_id", match.getHomeTeamId()));
        List<VoMatchPlayer> voMatchPlayerList = new ArrayList<>();
        for (MatchPlayer matchPlayer : homePlayers) {
            Player player = playerService.getById(matchPlayer.getPlayerId());
            VoMatchPlayer voMatchPlayer = new VoMatchPlayer();
            voMatchPlayer.setPlayerId(player.getPlayerId());
            voMatchPlayer.setNumber(matchPlayer.getNumber());
            voMatchPlayer.setName(player.getName());
            voMatchPlayer.setPhotoUrl(player.getPhotoUrl());
            voMatchPlayer.setIsStart(matchPlayer.getIsStart());
            voMatchPlayerList.add(voMatchPlayer);
        }
        return voMatchPlayerList;
    }

    @PostMapping("/referee/setPlayerList")
    @Transactional
    @Operation(summary = "设置比赛球员列表", description = "将删除对应于该比赛、该球队的原有球员列表，然后插入新的球员列表")
    public void setPlayerList(Long refereeId, Long matchId, Long teamId, @RequestBody List<VoMatchPlayer> voMatchPlayerList) {
        if (refereeId == null || matchId == null || teamId == null) {
            throw new BadRequestException("裁判ID和比赛ID和球队ID不能为空");
        }
        Match match = matchService.getById(matchId);
        if (match == null) {
            throw new BadRequestException("比赛不存在");
        }
        if (teamService.getById(teamId) == null) {
            throw new BadRequestException("球队不存在");
        }
        // 球队存在于比赛中
        if (!match.getHomeTeamId().equals(teamId) && !match.getAwayTeamId().equals(teamId)) {
            throw new BadRequestException("球队不在比赛中");
        }
        // 裁判是比赛的裁判
        Referee referee = refereeService.getById(refereeId);
        if (referee == null) {
            throw new BadRequestException("裁判不存在");
        }
        List<Referee> refereeList = matchService.getReferees(matchId);
        if (!refereeList.contains(referee)) {
            throw new BadRequestException("裁判不是比赛的裁判");
        }

        // 维护待插入的球员列表
        List<MatchPlayer> matchPlayerList = new ArrayList<>();
        for (VoMatchPlayer voMatchPlayer : voMatchPlayerList) {
            MatchPlayer matchPlayer = new MatchPlayer();
            matchPlayer.setMatchId(matchId);
            matchPlayer.setTeamId(teamId);
            matchPlayer.setPlayerId(voMatchPlayer.getPlayerId());
            matchPlayer.setNumber(voMatchPlayer.getNumber());
            matchPlayer.setIsStart(voMatchPlayer.getIsStart());
            matchPlayerList.add(matchPlayer);
        }

        // 删除该match该team的原有球员列表
        QueryWrapper<MatchPlayer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("match_id", matchId).eq("team_id", teamId);
        if (!matchPlayerService.remove(queryWrapper)) {
            throw new InternalServerErrorException("删除原有球员列表失败");
        }

        // 插入新的球员列表
        if (!matchPlayerService.saveOrUpdateBatchByMultiId(matchPlayerList)) {
            throw new InternalServerErrorException("插入新球员列表失败");
        }
    }

    @PostMapping("/referee/updateResult")
    public void updateResult(Long refereeId, @RequestBody VoMatchInfoForReferee voMatch) {
        if (refereeId == null || voMatch == null) {
            throw new BadRequestException("裁判ID和比赛信息不能为空");
        }
        if (refereeService.getById(refereeId) == null) {
            throw new BadRequestException("裁判不存在");
        }

        // 获取比赛
        Match match = matchService.getById(voMatch.getMatchId());
        if (match == null) {
            throw new BadRequestException("比赛不存在");
        }

        // 维护待更新的比赛信息
        match.setHomeTeamScore(voMatch.getHomeTeamScore());
        match.setAwayTeamScore(voMatch.getAwayTeamScore());
        match.setHomeTeamPenalty(voMatch.getHomeTeamPenalty());
        match.setAwayTeamPenalty(voMatch.getAwayTeamPenalty());
        match.setStatus(voMatch.getStatus());

        // 更新比赛结果
        if (!matchService.updateResult(refereeId, match)) {
            throw new InternalServerErrorException("更新比赛结果失败");
        }
    }

    @PostMapping("/referee/addPlayerAction")
    public void updatePlayerAction(Long refereeId, @RequestBody MatchPlayerAction matchPlayerAction) {
        if (refereeId == null || matchPlayerAction == null) {
            throw new BadRequestException("传参不能为空");
        }
        if (refereeService.getById(refereeId) == null) {
            throw new BadRequestException("裁判不存在");
        }
        if (matchPlayerAction.getMatchId() == null) {
            throw new BadRequestException("比赛ID不能为空");
        }
        if (matchService.getById(matchPlayerAction.getMatchId()) == null) {
            throw new BadRequestException("比赛不存在");
        }
        if (matchPlayerAction.getTeamId() == null) {
            throw new BadRequestException("球队ID不能为空");
        }
        if (teamService.getById(matchPlayerAction.getTeamId()) == null) {
            throw new BadRequestException("球队不存在");
        }
        if (matchPlayerAction.getPlayerId() == null) {
            throw new BadRequestException("球员ID不能为空");
        }
        if (playerService.getById(matchPlayerAction.getPlayerId()) == null) {
            throw new BadRequestException("球员不存在");
        }
        if (matchPlayerAction.getAction() == null) {
            throw new BadRequestException("动作不能为空");
        }
        if (matchPlayerAction.getTime() == null) {
            throw new BadRequestException("时间不能为空");
        }

        if (!matchService.addPlayerAction(refereeId, matchPlayerAction)) {
            throw new BadRequestException("更新比赛球员动作失败");
        }
    }

    @DeleteMapping("/referee/deletePlayerAction")
    public void deletePlayerAction(Long refereeId, @RequestBody MatchPlayerAction matchPlayerAction) {
        if (refereeId == null || matchPlayerAction == null) {
            throw new BadRequestException("传参不能为空");
        }
        if (refereeService.getById(refereeId) == null) {
            throw new BadRequestException("裁判不存在");
        }
        if (!matchService.deletePlayerAction(refereeId, matchPlayerAction)) {
            throw new BadRequestException("删除比赛球员动作失败");
        }
    }

    @GetMapping("/getEvent")
    public Event getEvent(Long matchId) {
        if (matchId == null) {
            throw new BadRequestException("比赛ID不能为空");
        }
        if (matchService.getById(matchId) == null) {
            throw new ResourceNotFoundException("比赛不存在");
        }
        return matchService.getEvent(matchId);
    }

    @PostMapping("/live/add")
    public MatchLive addLive(Long matchId, String liveName, String liveUrl) {
        if (matchId == null || liveName == null || liveUrl == null) {
            throw new BadRequestException("比赛ID和直播名称和直播地址不能为空");
        }
        if (matchService.getById(matchId) == null) {
            throw new BadRequestException("比赛不存在");
        }
        MatchLive matchLive = new MatchLive(null, matchId, liveName, liveUrl);
        matchLiveService.save(matchLive);
        return matchLive;
    }

    @PutMapping("/live/update")
    public void updateLive(@RequestBody MatchLive matchLive) {
        if (matchLive == null) {
            throw new BadRequestException("直播信息不能为空");
        }
        if (matchLive.getLiveId() == null) {
            throw new BadRequestException("更新的直播, 其ID不能为空");
        }
        if (!matchLiveService.updateById(matchLive)) {
            throw new BadRequestException("更新直播失败");
        }
    }

    @DeleteMapping("/live/delete")
    public void deleteLive(Long liveId) {
        if (liveId == null) {
            throw new BadRequestException("直播ID不能为空");
        }
        if (!matchLiveService.removeById(liveId)) {
            throw new BadRequestException("删除直播失败");
        }
    }

    @GetMapping("/live/get")
    public MatchLive getLive(Long liveId) {
        if (liveId == null) {
            throw new BadRequestException("ID不能为空");
        }
        MatchLive matchLive = matchLiveService.getById(liveId);
        if (matchLive == null) {
            throw new ResourceNotFoundException("直播源不存在");
        }
        return matchLive;
    }

    @GetMapping("/live/getAll")
    public List<MatchLive> getAllLives(Long matchId) {
        if (matchId == null) {
            throw new BadRequestException("比赛ID不能为空");
        }
        if (matchService.getById(matchId) == null) {
            throw new ResourceNotFoundException("比赛不存在");
        }
        QueryWrapper<MatchLive> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("match_id", matchId);
        return matchLiveService.list(queryWrapper);
    }

    @PostMapping("/video/add")
    public MatchVideo addVideo(Long matchId, String videoName, String videoUrl) {
        if (matchId == null || videoName == null || videoUrl == null) {
            throw new BadRequestException("比赛ID和视频名称和视频地址不能为空");
        }
        if (matchService.getById(matchId) == null) {
            throw new BadRequestException("比赛不存在");
        }
        MatchVideo matchVideo = new MatchVideo(null, matchId, videoName, videoUrl);
        matchVideoService.save(matchVideo);
        return matchVideo;
    }

    @PutMapping("/video/update")
    public void updateVideo(@RequestBody MatchVideo matchVideo) {
        if (matchVideo == null) {
            throw new BadRequestException("视频信息不能为空");
        }
        if (matchVideo.getVideoId() == null) {
            throw new BadRequestException("更新的视频, 其ID不能为空");
        }
        if (!matchVideoService.updateById(matchVideo)) {
            throw new BadRequestException("更新视频失败");
        }
    }

    @DeleteMapping("/video/delete")
    public void deleteVideo(Long videoId) {
        if (videoId == null) {
            throw new BadRequestException("视频ID不能为空");
        }
        if (!matchVideoService.removeById(videoId)) {
            throw new BadRequestException("删除视频失败");
        }
    }

    @GetMapping("/video/get")
    public MatchVideo getVideo(Long videoId) {
        if (videoId == null) {
            throw new BadRequestException("ID不能为空");
        }
        MatchVideo matchVideo = matchVideoService.getById(videoId);
        if (matchVideo == null) {
            throw new ResourceNotFoundException("视频源不存在");
        }
        return matchVideo;
    }

    @GetMapping("/video/getAll")
    public List<MatchVideo> getAllVideos(Long matchId) {
        if (matchId == null) {
            throw new BadRequestException("比赛ID不能为空");
        }
        if (matchService.getById(matchId) == null) {
            throw new BadRequestException("比赛不存在");
        }
        QueryWrapper<MatchVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("match_id", matchId);
        return matchVideoService.list(queryWrapper);
    }
}
