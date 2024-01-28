package com.sustech.football.service;

import java.util.List;

import com.github.jeffreyning.mybatisplus.service.IMppService;
import com.sustech.football.entity.TeamCoachRequest;

public interface TeamCoachRequestService extends IMppService<TeamCoachRequest> {
    List<TeamCoachRequest> listWithTeam(Long coachId);
    List<TeamCoachRequest> listWithCoach(Long teamId);
}
