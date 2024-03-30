package com.sustech.football.model.match;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoMatchTeam {
    Long teamId;
    String name;
    String logoUrl;
    Integer score;
    Integer penalty;
    List<VoMatchPlayer> players;
}
