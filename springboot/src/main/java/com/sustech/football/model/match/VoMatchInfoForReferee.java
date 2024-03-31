package com.sustech.football.model.match;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoMatchInfoForReferee {
    Long matchId;
    String status;
    Integer homeTeamScore;
    Integer awayTeamScore;
    Integer homeTeamPenalty;
    Integer awayTeamPenalty;
}
