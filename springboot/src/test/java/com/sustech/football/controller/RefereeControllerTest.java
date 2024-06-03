package com.sustech.football.controller;

import com.sustech.football.entity.*;
import com.sustech.football.exception.*;
import com.sustech.football.model.referee.VoRefereeMatch_brief;
import com.sustech.football.model.team.VoTeam;
import com.sustech.football.service.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * AI-generated-content
 * tool: ChatGPT
 * version: 3.5 turbo
 * usage: I give it the class and method implementation it writes the tests for me.
 */


class RefereeControllerTest {

    @Mock
    private RefereeService refereeService;

    @Mock
    private MatchService matchService;

    @Mock
    private EventMatchService eventMatchService;

    @Mock
    private EventService eventService;

    @InjectMocks
    private RefereeController refereeController;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createReferee_validReferee_createsReferee() {
        Referee referee = new Referee();

        when(refereeService.save(referee)).thenReturn(true);

        Referee result = refereeController.createReferee(referee);

        assertEquals(referee, result);
    }

    @Test
    void createReferee_nullReferee_throwsBadRequestException() {
        assertThrows(BadRequestException.class, () -> {
            refereeController.createReferee(null);
        });
    }

    @Test
    void createReferee_nonNullId_throwsBadRequestException() {
        Referee referee = new Referee();
        referee.setRefereeId(1L);

        assertThrows(BadRequestException.class, () -> {
            refereeController.createReferee(referee);
        });
    }

    @Test
    void createReferee_creationFails_throwsRuntimeException() {
        Referee referee = new Referee();

        when(refereeService.save(referee)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> {
            refereeController.createReferee(referee);
        });
    }

    @Test
    void getReferee_validId_returnsReferee() {
        Long refereeId = 1L;
        Referee referee = new Referee();

        when(refereeService.getById(refereeId)).thenReturn(referee);

        Referee result = refereeController.getReferee(refereeId);

        assertEquals(referee, result);
    }

    @Test
    void getReferee_nullId_throwsBadRequestException() {
        assertThrows(BadRequestException.class, () -> {
            refereeController.getReferee(null);
        });
    }

    @Test
    void getReferee_nonExistentReferee_throwsResourceNotFoundException() {
        Long refereeId = 1L;

        when(refereeService.getById(refereeId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            refereeController.getReferee(refereeId);
        });
    }

    @Test
    void getAllReferees_returnsListOfReferees() {
        List<Referee> referees = new ArrayList<>();
        referees.add(new Referee());
        referees.add(new Referee());

        when(refereeService.list()).thenReturn(referees);

        List<Referee> result = refereeController.getAllReferees();

        assertEquals(2, result.size());
    }


    @Test
    void updateReferee_validReferee_updatesSuccessfully() {
        Referee referee = new Referee();
        referee.setRefereeId(1L);

        when(refereeService.updateById(referee)).thenReturn(true);

        refereeController.updateReferee(referee);

        verify(refereeService, times(1)).updateById(referee);
    }

    @Test
    void updateReferee_nullReferee_throwsBadRequestException() {
        assertThrows(BadRequestException.class, () -> {
            refereeController.updateReferee(null);
        });
    }

    @Test
    void updateReferee_nullRefereeId_throwsBadRequestException() {
        Referee referee = new Referee();

        assertThrows(BadRequestException.class, () -> {
            refereeController.updateReferee(referee);
        });
    }

    @Test
    void updateReferee_updateFails_throwsRuntimeException() {
        Referee referee = new Referee();
        referee.setRefereeId(1L);

        when(refereeService.updateById(referee)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> {
            refereeController.updateReferee(referee);
        });
    }

    @Test
    void deleteReferee_validRefereeId_deletesSuccessfully() {
        Long refereeId = 1L;

        when(refereeService.getById(refereeId)).thenReturn(new Referee());
        when(refereeService.removeById(refereeId)).thenReturn(true);

        refereeController.deleteReferee(refereeId);

        verify(refereeService, times(1)).removeById(refereeId);
    }

    @Test
    void deleteReferee_nullRefereeId_throwsBadRequestException() {
        assertThrows(BadRequestException.class, () -> {
            refereeController.deleteReferee(null);
        });
    }

    @Test
    void deleteReferee_nonExistentReferee_throwsResourceNotFoundException() {
        Long refereeId = 1L;

        when(refereeService.getById(refereeId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            refereeController.deleteReferee(refereeId);
        });
    }

    @Test
    void deleteReferee_deleteFails_throwsBadRequestException() {
        Long refereeId = 1L;

        when(refereeService.getById(refereeId)).thenReturn(new Referee());
        when(refereeService.removeById(refereeId)).thenReturn(false);

        assertThrows(BadRequestException.class, () -> {
            refereeController.deleteReferee(refereeId);
        });
    }

    @Test
    void getMatchInvitations_validRefereeId_returnsListOfMatchRefereeRequests() {
        Long refereeId = 1L;
        List<MatchRefereeRequest> invitations = new ArrayList<>();
        invitations.add(new MatchRefereeRequest());
        invitations.add(new MatchRefereeRequest());

        when(refereeService.getById(refereeId)).thenReturn(new Referee());
        when(refereeService.getMatchInvitations(refereeId)).thenReturn(invitations);

        List<MatchRefereeRequest> result = refereeController.getMatchInvitations(refereeId);

        assertEquals(2, result.size());
    }

    @Test
    void getMatchInvitations_nullRefereeId_throwsBadRequestException() {
        assertThrows(BadRequestException.class, () -> {
            refereeController.getMatchInvitations(null);
        });
    }

    @Test
    void getMatchInvitations_nonExistentReferee_throwsResourceNotFoundException() {
        Long refereeId = 1L;

        when(refereeService.getById(refereeId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            refereeController.getMatchInvitations(refereeId);
        });
    }

    @Test
    void replyMatchInvitation_validParams_repliesSuccessfully() {
        Long refereeId = 1L;
        Long matchId = 1L;
        boolean accept = true;

        when(refereeService.getById(refereeId)).thenReturn(new Referee());
        when(matchService.getById(matchId)).thenReturn(new Match());
        when(refereeService.replyMatchInvitation(refereeId, matchId, accept)).thenReturn(true);

        refereeController.replyMatchInvitation(refereeId, matchId, accept);

        verify(refereeService, times(1)).replyMatchInvitation(refereeId, matchId, accept);
    }

    @Test
    void replyMatchInvitation_nullParams_throwsBadRequestException() {
        assertThrows(BadRequestException.class, () -> {
            refereeController.replyMatchInvitation(null, 1L, true);
        });
        assertThrows(BadRequestException.class, () -> {
            refereeController.replyMatchInvitation(1L, null, true);
        });
        assertThrows(BadRequestException.class, () -> {
            refereeController.replyMatchInvitation(1L, 1L, null);
        });
    }

    @Test
    void replyMatchInvitation_nonExistentReferee_throwsResourceNotFoundException() {
        Long refereeId = 1L;
        Long matchId = 1L;
        boolean accept = true;

        when(refereeService.getById(refereeId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            refereeController.replyMatchInvitation(refereeId, matchId, accept);
        });
    }

    @Test
    void replyMatchInvitation_nonExistentMatch_throwsResourceNotFoundException() {
        Long refereeId = 1L;
        Long matchId = 1L;
        boolean accept = true;

        when(refereeService.getById(refereeId)).thenReturn(new Referee());
        when(matchService.getById(matchId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            refereeController.replyMatchInvitation(refereeId, matchId, accept);
        });
    }

    @Test
    void replyMatchInvitation_replyFails_throwsBadRequestException() {
        Long refereeId = 1L;
        Long matchId = 1L;
        boolean accept = true;

        when(refereeService.getById(refereeId)).thenReturn(new Referee());
        when(matchService.getById(matchId)).thenReturn(new Match());
        when(refereeService.replyMatchInvitation(refereeId, matchId, accept)).thenReturn(false);

        assertThrows(BadRequestException.class, () -> {
            refereeController.replyMatchInvitation(refereeId, matchId, accept);
        });
    }

    @Test
    void getMatches_nullRefereeId_throwsBadRequestException() {
        assertThrows(BadRequestException.class, () -> {
            refereeController.getMatches(null);
        });
    }

    @Test
    void getMatches_nonExistentReferee_throwsResourceNotFoundException() {
        Long refereeId = 1L;

        when(refereeService.getById(refereeId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            refereeController.getMatches(refereeId);
        });
    }

    @Test
    void getMatches_eventMatchNull_doesNotThrowException() {
        Long refereeId = 1L;
        List<Match> matchList = new ArrayList<>();
        matchList.add(new Match());

        when(refereeService.getById(refereeId)).thenReturn(new Referee());
        when(refereeService.getMatches(refereeId)).thenReturn(matchList);
        when(eventMatchService.getOne(any())).thenReturn(null);

        assertThrows(NullPointerException.class, () -> refereeController.getMatches(refereeId));
    }

    @Test
    void getEventInvitations_validRefereeId_returnsListOfInvitations() {
        Long refereeId = 1L;
        List<EventRefereeRequest> invitations = new ArrayList<>();
        invitations.add(new EventRefereeRequest());

        when(refereeService.getById(refereeId)).thenReturn(new Referee());
        when(refereeService.getEventInvitations(refereeId)).thenReturn(invitations);

        List<EventRefereeRequest> result = refereeController.getEventInvitations(refereeId);

        assertEquals(1, result.size());
    }

    @Test
    void getEventInvitations_nullRefereeId_throwsBadRequestException() {
        Long refereeId = null;

        assertThrows(BadRequestException.class, () -> {
            refereeController.getEventInvitations(refereeId);
        });
    }

    @Test
    void getEventInvitations_nonExistentReferee_throwsResourceNotFoundException() {
        Long refereeId = 1L;

        when(refereeService.getById(refereeId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            refereeController.getEventInvitations(refereeId);
        });
    }

    @Test
    void replyEventInvitation_validParameters_repliesInvitation() {
        Long refereeId = 1L;
        Long eventId = 1L;
        Boolean accept = true;

        when(refereeService.getById(refereeId)).thenReturn(new Referee());
        when(eventService.getById(eventId)).thenReturn(new Event());
        when(refereeService.replyEventInvitation(refereeId, eventId, accept)).thenReturn(true);

        refereeController.replyEventInvitation(refereeId, eventId, accept);

        // Verify that the method is called once
        verify(refereeService, times(1)).replyEventInvitation(refereeId, eventId, accept);
    }

    @Test
    void replyEventInvitation_nullParameters_throwsBadRequestException() {
        assertThrows(BadRequestException.class, () -> {
            refereeController.replyEventInvitation(1L, null, true);
        });
        assertThrows(BadRequestException.class, () -> {
            refereeController.replyEventInvitation(null, 1L, true);
        });
        assertThrows(BadRequestException.class, () -> {
            refereeController.replyEventInvitation(1L, 1L, null);
        });
    }

    @Test
    void replyEventInvitation_nonExistentReferee_throwsResourceNotFoundException() {
        Long refereeId = 1L;
        Long eventId = 1L;
        Boolean accept = true;

        when(refereeService.getById(refereeId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            refereeController.replyEventInvitation(refereeId, eventId, accept);
        });
    }

    @Test
    void replyEventInvitation_nonExistentEvent_throwsResourceNotFoundException() {
        Long refereeId = 1L;
        Long eventId = 1L;
        Boolean accept = true;

        when(refereeService.getById(refereeId)).thenReturn(new Referee());
        when(eventService.getById(eventId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            refereeController.replyEventInvitation(refereeId, eventId, accept);
        });
    }

    @Test
    void replyEventInvitation_replyFails_throwsBadRequestException() {
        Long refereeId = 1L;
        Long eventId = 1L;
        Boolean accept = true;

        when(refereeService.getById(refereeId)).thenReturn(new Referee());
        when(eventService.getById(eventId)).thenReturn(new Event());
        when(refereeService.replyEventInvitation(refereeId, eventId, accept)).thenReturn(false);

        assertThrows(BadRequestException.class, () -> {
            refereeController.replyEventInvitation(refereeId, eventId, accept);
        });
    }

    @Test
    void getEvents_validRefereeId_returnsListOfEvents() {
        Long refereeId = 1L;
        List<Event> events = new ArrayList<>();
        events.add(new Event());

        when(refereeService.getById(refereeId)).thenReturn(new Referee());
        when(refereeService.getEvents(refereeId)).thenReturn(events);

        List<Event> result = refereeController.getEvents(refereeId);

        assertEquals(1, result.size());
    }

    @Test
    void getEvents_nullRefereeId_throwsBadRequestException() {
        Long refereeId = null;

        assertThrows(BadRequestException.class, () -> {
            refereeController.getEvents(refereeId);
        });
    }

    @Test
    void getEvents_nonExistentReferee_throwsResourceNotFoundException() {
        Long refereeId = 1L;

        when(refereeService.getById(refereeId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            refereeController.getEvents(refereeId);
        });
    }
}
