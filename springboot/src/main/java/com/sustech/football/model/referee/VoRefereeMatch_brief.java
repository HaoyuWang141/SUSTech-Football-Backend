package com.sustech.football.model.referee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoRefereeMatch_brief {
    Long matchId;
    Timestamp time;
    String status;
    VoRefereeTeam homeTeam;
    VoRefereeTeam awayTeam;
    VoRefereeEvent event;
}
