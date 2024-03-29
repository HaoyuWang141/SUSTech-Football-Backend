package com.sustech.football.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoTeamPlayer {
    private Long playerId;
    private String name;
    private String photoUrl;
    private Integer number;
    private Integer appearances;
    private Integer goals;
    private Integer assists;
    private Integer yellowCards;
    private Integer redCards;
}
