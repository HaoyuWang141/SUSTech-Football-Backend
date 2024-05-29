package com.sustech.football.model.team;

import com.sustech.football.entity.Coach;
import com.sustech.football.entity.Event;
import com.sustech.football.entity.Match;
import com.sustech.football.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoTeam {
    private Long teamId;
    private String name;
    private String logoUrl;
    private String description;
    private Long captainId;
    private List<Coach> coachList;
    private List<VoTeamPlayer> playerList;
    private List<Event> eventList;
    private List<Match> matchList;
    private List<User> managerList;
}
