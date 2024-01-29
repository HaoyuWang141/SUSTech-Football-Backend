
package com.sustech.football.service;

import java.util.List;

import com.github.jeffreyning.mybatisplus.service.IMppService;
import com.sustech.football.entity.TeamCoach;

public interface TeamCoachService extends IMppService<TeamCoach> {
    List<TeamCoach> listWithTeam(Long coachId);
    List<TeamCoach> listWithCoach(Long teamId);
}