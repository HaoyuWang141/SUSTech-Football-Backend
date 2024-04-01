package com.sustech.football.model.referee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoRefereeTeam {
    Long teamId;
    String name;
    String logoUrl;
    Integer score;
    Integer penalty;
}
