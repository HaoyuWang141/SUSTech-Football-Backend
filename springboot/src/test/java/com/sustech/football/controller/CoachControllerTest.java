package com.sustech.football.controller;

import com.sustech.football.entity.*;

import com.sustech.football.exception.BadRequestException;
import com.sustech.football.exception.ResourceNotFoundException;
import com.sustech.football.service.CoachService;
import com.sustech.football.service.TeamCoachRequestService;
import com.sustech.football.service.TeamService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

/**
 * AI-generated-content
 * tool: ChatGPT
 * version: 3.5 turbo
 * usage: I give it the class and method implementation it writes the tests for me.
 */


class CoachControllerTest {

    @Mock
    private CoachService coachService;

    @Mock
    private TeamService teamService;

    @Mock
    private TeamCoachRequestService teamCoachRequestService;

    @InjectMocks
    private CoachController coachController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCoach_ValidCoach_ReturnsCreatedCoach() {
        Coach coach = new Coach();
        coach.setName("John Doe");

        when(coachService.save(any(Coach.class))).thenReturn(true);

        Coach createdCoach = coachController.createCoach(coach);

        assertNotNull(createdCoach);
        assertEquals("John Doe", createdCoach.getName());
    }

    @Test
    void createCoach_NullCoach_ThrowsBadRequestException() {
        assertThrows(BadRequestException.class, () -> coachController.createCoach(null));
    }

    @Test
    void createCoach_NonNullId_ThrowsBadRequestException() {
        Coach coach = new Coach();
        coach.setCoachId(1L);

        assertThrows(BadRequestException.class, () -> coachController.createCoach(coach));
    }

    @Test
    void createCoach_FailureToSave_ThrowsBadRequestException() {
        Coach coach = new Coach();
        coach.setName("John Doe");

        when(coachService.save(any(Coach.class))).thenReturn(false);

        assertThrows(BadRequestException.class, () -> coachController.createCoach(coach));
    }

    @Test
    void getCoachById_ExistingId_ReturnsCoach() {
        Long id = 1L;
        Coach mockCoach = new Coach();
        mockCoach.setCoachId(id);
        when(coachService.getById(id)).thenReturn(mockCoach);

        Coach coach = coachController.getCoachById(id);

        assertNotNull(coach);
        assertEquals(id, coach.getCoachId());
    }

    @Test
    void getCoachById_NonExistingId_ThrowsResourceNotFoundException() {
        Long id = 1L;
        when(coachService.getById(id)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> coachController.getCoachById(id));
    }

    @Test
    void getCoachById_NullId_ThrowsBadRequestException() {
        assertThrows(BadRequestException.class, () -> coachController.getCoachById(null));
    }

    @Test
    void getAllCoaches_ReturnsListOfCoaches() {
        List<Coach> mockCoaches = new ArrayList<>();
        mockCoaches.add(new Coach());
        mockCoaches.add(new Coach());
        when(coachService.list()).thenReturn(mockCoaches);

        List<Coach> coaches = coachController.getAllCoaches();

        assertNotNull(coaches);
        assertEquals(2, coaches.size());
    }

    @Test
    void updateCoach_ValidCoach_ReturnsUpdatedCoach() {
        Coach coach = new Coach();
        coach.setCoachId(1L);

        when(coachService.updateById(any(Coach.class))).thenReturn(true);

        Coach updatedCoach = coachController.updateCoach(coach);

        assertNotNull(updatedCoach);
        assertEquals(coach, updatedCoach);
    }

    @Test
    void updateCoach_NullCoach_ThrowsBadRequestException() {
        assertThrows(BadRequestException.class, () -> coachController.updateCoach(null));
    }

    @Test
    void updateCoach_NullCoachId_ThrowsBadRequestException() {
        Coach coach = new Coach();
        assertThrows(BadRequestException.class, () -> coachController.updateCoach(coach));
    }

    @Test
    void updateCoach_UpdateFailure_ThrowsBadRequestException() {
        Coach coach = new Coach();
        coach.setCoachId(1L);

        when(coachService.updateById(any(Coach.class))).thenReturn(false);

        assertThrows(BadRequestException.class, () -> coachController.updateCoach(coach));
    }

    @Test
    void deleteCoach_ValidId_DeletesCoach() {
        Long id = 1L;
        when(coachService.removeById(id)).thenReturn(true);

        assertDoesNotThrow(() -> coachController.deleteCoach(id));
    }

    @Test
    void deleteCoach_NonExistingId_ThrowsBadRequestException() {
        Long id = 1L;
        when(coachService.removeById(id)).thenReturn(false);

        assertThrows(BadRequestException.class, () -> coachController.deleteCoach(id));
    }

    @Test
    void getTeamInvitations_ValidCoachId_ReturnsTeamCoachRequests() {
        Long coachId = 1L;
        Coach coach = new Coach();
        coach.setCoachId(coachId);
        List<TeamCoachRequest> teamCoachRequests = new ArrayList<>();
        when(coachService.getById(coachId)).thenReturn(coach);
        when(teamCoachRequestService.listWithTeam(coachId)).thenReturn(teamCoachRequests);

        List<TeamCoachRequest> result = coachController.getTeamInvitations(coachId);

        assertNotNull(result);
        assertEquals(teamCoachRequests, result);
    }

    @Test
    void getTeamInvitations_NullCoachId_ThrowsBadRequestException() {
        assertThrows(BadRequestException.class, () -> coachController.getTeamInvitations(null));
    }

    @Test
    void getTeamInvitations_NonExistingCoach_ThrowsResourceNotFoundException() {
        Long coachId = 1L;
        when(coachService.getById(coachId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> coachController.getTeamInvitations(coachId));
    }

    @Test
    void replyTeamInvitation_ValidParams_ProcessesInvitation() {
        Long coachId = 1L;
        Long teamId = 2L;
        Boolean accept = true;
        when(coachService.getById(coachId)).thenReturn(new Coach());
        when(teamService.getById(teamId)).thenReturn(new Team());
        when(coachService.replyTeamInvitation(coachId, teamId, accept)).thenReturn(true);

        assertDoesNotThrow(() -> coachController.replyTeamInvitation(coachId, teamId, accept));
    }

    @Test
    void replyTeamInvitation_NullParams_ThrowsBadRequestException() {
        assertThrows(BadRequestException.class, () -> coachController.replyTeamInvitation(null, null, null));
    }

    @Test
    void replyTeamInvitation_NonExistingCoach_ThrowsResourceNotFoundException() {
        Long coachId = 1L;
        Long teamId = 2L;
        Boolean accept = true;
        when(coachService.getById(coachId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> coachController.replyTeamInvitation(coachId, teamId, accept));
    }

    @Test
    void replyTeamInvitation_NonExistingTeam_ThrowsResourceNotFoundException() {
        Long coachId = 1L;
        Long teamId = 2L;
        Boolean accept = true;
        when(coachService.getById(coachId)).thenReturn(new Coach());
        when(teamService.getById(teamId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> coachController.replyTeamInvitation(coachId, teamId, accept));
    }

    @Test
    void getTeams_ValidCoachId_ReturnsTeams() {
        Long coachId = 1L;
        Coach coach = new Coach();
        coach.setCoachId(coachId);
        List<Team> teams = new ArrayList<>();
        when(coachService.getById(coachId)).thenReturn(coach);
        when(coachService.getTeams(coachId)).thenReturn(teams);

        List<Team> result = coachController.getTeams(coachId);

        assertNotNull(result);
        assertEquals(teams, result);
    }

    @Test
    void getTeams_NullCoachId_ThrowsBadRequestException() {
        assertThrows(BadRequestException.class, () -> coachController.getTeams(null));
    }

    @Test
    void getTeams_NonExistingCoach_ThrowsResourceNotFoundException() {
        Long coachId = 1L;
        when(coachService.getById(coachId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> coachController.getTeams(coachId));
    }

    @Test
    void getMatches_ValidCoachId_ReturnsMatches() {
        Long coachId = 1L;
        Coach coach = new Coach();
        coach.setCoachId(coachId);
        List<Match> matches = new ArrayList<>();
        when(coachService.getById(coachId)).thenReturn(coach);
        when(coachService.getMatches(coachId)).thenReturn(matches);

        List<Match> result = coachController.getMatches(coachId);

        assertNotNull(result);
        assertEquals(matches, result);
    }

    @Test
    void getMatches_NullCoachId_ThrowsBadRequestException() {
        assertThrows(BadRequestException.class, () -> coachController.getMatches(null));
    }

    @Test
    void getMatches_NonExistingCoach_ThrowsResourceNotFoundException() {
        Long coachId = 1L;
        when(coachService.getById(coachId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> coachController.getMatches(coachId));
    }

    @Test
    void getEvents_ValidCoachId_ReturnsEvents() {
        Long coachId = 1L;
        Coach coach = new Coach();
        coach.setCoachId(coachId);
        List<Event> events = new ArrayList<>();
        when(coachService.getById(coachId)).thenReturn(coach);
        when(coachService.getEvents(coachId)).thenReturn(events);

        List<Event> result = coachController.getEvents(coachId);

        assertNotNull(result);
        assertEquals(events, result);
    }

    @Test
    void getEvents_NullCoachId_ThrowsBadRequestException() {
        assertThrows(BadRequestException.class, () -> coachController.getEvents(null));
    }

    @Test
    void getEvents_NonExistingCoach_ThrowsResourceNotFoundException() {
        Long coachId = 1L;
        when(coachService.getById(coachId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> coachController.getEvents(coachId));
    }
}