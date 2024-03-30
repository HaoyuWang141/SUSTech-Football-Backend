package com.sustech.football.model.match;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoMatchPlayer {
    Long playerId;
    Integer number;
    String name;
    String photoUrl;
    Boolean isStart;
}
