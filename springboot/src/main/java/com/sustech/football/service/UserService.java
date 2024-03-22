
package com.sustech.football.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sustech.football.entity.*;

import java.util.List;

public interface UserService extends IService<User> {
    List<Match> getUserManageMatches(Long userId);

    List<Event> getUserManageEvents(Long userId);

    List<Team> getUserManageTeams(Long userId);

    Long getPlayerId(Long userId);

    Long getCoachId(Long userId);

    Long getRefereeId(Long userId);
}