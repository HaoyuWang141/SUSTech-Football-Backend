package com.sustech.football.model.match;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoMatch {
    Long matchId;
    Timestamp time;
    String status;
    VoMatchTeam homeTeam;
    VoMatchTeam awayTeam;
    List<Long> managerList;
    List<VoMatchReferee> refereeList;
    List<VoMatchPlayerAction> matchPlayerActionList;
    VoMatchEvent matchEvent;
}
