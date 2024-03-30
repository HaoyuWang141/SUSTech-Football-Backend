package com.sustech.football.model.match;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoMatchPlayerAction {
    Long teamId;
    VoMatchPlayer player;
    Integer time;
    String action;
}
