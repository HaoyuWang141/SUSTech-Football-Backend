package com.sustech.football.model.match;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoMatchEvent {
    private Long eventId;
    private String eventName;
    private String stage;
    private String tag;
}
