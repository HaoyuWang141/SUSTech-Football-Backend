package com.sustech.football.model.referee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoRefereeEvent {
    Long eventId;
    String eventName;
    String stage;
    String tag;
}
