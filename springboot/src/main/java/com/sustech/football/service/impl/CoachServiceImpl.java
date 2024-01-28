
package com.sustech.football.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sustech.football.entity.Coach;
import com.sustech.football.entity.Team;
import com.sustech.football.entity.TeamCoach;
import com.sustech.football.entity.TeamCoachRequest;
import com.sustech.football.exception.BadRequestException;
import com.sustech.football.exception.ConflictException;
import com.sustech.football.mapper.CoachMapper;
import com.sustech.football.service.CoachService;
import com.sustech.football.service.TeamCoachRequestService;
import com.sustech.football.service.TeamCoachService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CoachServiceImpl extends ServiceImpl<CoachMapper, Coach> implements CoachService {

    @Autowired
    private TeamCoachRequestService teamCoachRequestService;

    @Autowired
    private TeamCoachService teamCoachService;

    @Override
    @Transactional
    public void replyTeamInvitation(Long teamId, Long coachId, Boolean accept) {
        TeamCoach teamCoach = new TeamCoach(teamId, coachId);
        if (teamCoachService.selectByMultiId(teamCoach) != null) {
            throw new ConflictException("教练已经在球队中");
        }

        String status = accept ? TeamCoachRequest.STATUS_ACCEPTED : TeamCoachRequest.STATUS_REJECTED;
        TeamCoachRequest teamCoachRequest = new TeamCoachRequest(coachId, teamId, status);
        if (teamCoachRequestService.selectByMultiId(teamCoachRequest) == null) {
            throw new BadRequestException("教练未收到邀请");
        }
        if (!teamCoachRequestService.updateById(teamCoachRequest)) {
            throw new RuntimeException("更新邀请失败");
        }
        if (accept) {
            if (!teamCoachService.save(teamCoach)) {
                throw new RuntimeException("加入球队失败");
            }
        }
    }

    @Override
    public List<Team> getTeams(Long coachId) {
        List<TeamCoach> teamCoachList = teamCoachService.listWithTeam(coachId);
        return teamCoachList.stream().map(TeamCoach::getTeam).toList();
    }
}