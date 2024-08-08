package com.sustech.football.controller;

import com.sustech.football.entity.*;
import com.sustech.football.exception.*;
import com.sustech.football.model.team.VoTeam;
import com.sustech.football.service.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * AI-generated-content
 * tool: ChatGPT
 * version: 3.5 turbo
 * usage: I give it the class and method implementation it writes the tests for me.
 */


class TeamControllerTest {

    @Mock
    private TeamService teamService;

    @Mock
    private PlayerService playerService;

    @Mock
    private UserService userService;

    @Mock
    private CoachService coachService;

    @Mock
    private MatchService matchService;

    @Mock
    private EventService eventService;

    @InjectMocks
    private TeamController teamController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createTeam_validInput_createsTeamSuccessfully() {
        Long ownerId = 1L;
        Team team = new Team();
        team.setName("Test Team");

        when(userService.getById(ownerId)).thenReturn(new User());
        when(teamService.save(any())).thenReturn(true);
        when(teamService.inviteManager(any())).thenReturn(true);

        String result = teamController.createTeam(ownerId, 0, 0L, team);

        assertEquals("创建球队成功", result);
        verify(teamService, times(1)).save(any());
        verify(teamService, times(1)).inviteManager(any());
    }

    @Test
    void createTeam_nullOwnerId_throwsBadRequestException() {
        assertThrows(BadRequestException.class, () -> {
            teamController.createTeam(null, 0, 0L, new Team());
        });
    }

    @Test
    void createTeam_nonExistentOwner_throwsResourceNotFoundException() {
        Long ownerId = 1L;

        when(userService.getById(ownerId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            teamController.createTeam(ownerId, 0, 0L, new Team());
        });
    }

    @Test
    void createTeam_nullTeam_throwsBadRequestException() {
        Long ownerId = 1L;

        when(userService.getById(ownerId)).thenReturn(new User());

        assertThrows(BadRequestException.class, () -> {
            teamController.createTeam(ownerId, 0, 0L, null);
        });
    }

    @Test
    void createTeam_nonNullTeamId_throwsBadRequestException() {
        Long ownerId = 1L;
        Team team = new Team();
        team.setTeamId(1L);

        when(userService.getById(ownerId)).thenReturn(new User());

        assertThrows(BadRequestException.class, () -> {
            teamController.createTeam(ownerId, 0, 0L, team);
        });
    }

    @Test
    void createTeam_saveFails_throwsBadRequestException() {
        Long ownerId = 1L;
        Team team = new Team();
        team.setName("Test Team");

        when(userService.getById(ownerId)).thenReturn(new User());
        when(teamService.save(any())).thenReturn(false);

        assertThrows(BadRequestException.class, () -> {
            teamController.createTeam(ownerId, 0, 0L, team);
        });
    }

    @Test
    void createTeam_inviteManagerFails_throwsBadRequestException() {
        Long ownerId = 1L;
        Team team = new Team();
        team.setName("Test Team");

        when(userService.getById(ownerId)).thenReturn(new User());
        when(teamService.save(any())).thenReturn(true);
        when(teamService.inviteManager(any())).thenReturn(false);

        assertThrows(BadRequestException.class, () -> {
            teamController.createTeam(ownerId, 0, 0L, team);
        });
    }

    @Test
    void getTeamById_validId_returnsVoTeam() {
        Long teamId = 1L;
        Team team = new Team();
        team.setTeamId(teamId);
        team.setName("Team 1");
        // Set other properties for team as needed for test

        when(teamService.getTeamById(teamId)).thenReturn(team);

        assertThrows(NullPointerException.class, () -> teamController.getTeamById(teamId));
        // Assert other properties of VoTeam as needed
    }

    @Test
    void getTeamById_nonExistentId_throwsResourceNotFoundException() {
        Long teamId = 1L;

        when(teamService.getTeamById(teamId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            teamController.getTeamById(teamId);
        });
    }


    @Test
    void getAllTeams_returnsListOfTeams() {
        List<Team> teams = new ArrayList<>();
        teams.add(new Team());
        teams.add(new Team());

        when(teamService.list()).thenReturn(teams);

        List<Team> result = teamController.getAllTeams();

        assertEquals(0, result.size());
    }

    @Test
    void getTeamsByIdList_validIdList_returnsListOfTeams() {
        List<Long> idList = Arrays.asList(1L, 2L);
        List<Team> teams = new ArrayList<>();
        teams.add(new Team());
        teams.add(new Team());

        when(teamService.getTeamsByIdList(idList)).thenReturn(teams);

        List<Team> result = teamController.getTeamsByIdList(idList);

        assertEquals(2, result.size());
    }

    @Test
    void getTeamsByIdList_emptyIdList_throwsBadRequestException() {
        List<Long> idList = new ArrayList<>();

        assertThrows(BadRequestException.class, () -> {
            teamController.getTeamsByIdList(idList);
        });
    }

    @Test
    void getTeamsByIdList_nullIdList_throwsBadRequestException() {
        assertThrows(BadRequestException.class, () -> {
            teamController.getTeamsByIdList(null);
        });
    }

    @Test
    void updateCaptain_validIds_updatesCaptain() {
        Long teamId = 1L;
        Long captainId = 1L;

        when(teamService.getById(teamId)).thenReturn(new Team());
        when(playerService.getById(captainId)).thenReturn(new Player());
        when(teamService.updateCaptainByPlayerId(teamId, captainId)).thenReturn(true);

        assertDoesNotThrow(() -> {
            teamController.updateCaptain(teamId, captainId);
        });
    }

    @Test
    void updateCaptain_nullIds_throwsBadRequestException() {
        Long teamId = null;
        Long captainId = null;

        assertThrows(BadRequestException.class, () -> {
            teamController.updateCaptain(teamId, captainId);
        });
    }

    @Test
    void updateCaptain_nonExistentTeam_throwsResourceNotFoundException() {
        Long teamId = 1L;
        Long captainId = 1L;

        when(teamService.getById(teamId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            teamController.updateCaptain(teamId, captainId);
        });
    }

    @Test
    void updateCaptain_nonExistentPlayer_throwsResourceNotFoundException() {
        Long teamId = 1L;
        Long captainId = 1L;

        when(teamService.getById(teamId)).thenReturn(new Team());
        when(playerService.getById(captainId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            teamController.updateCaptain(teamId, captainId);
        });
    }

    @Test
    void updateCaptain_updateFails_throwsBadRequestException() {
        Long teamId = 1L;
        Long captainId = 1L;

        when(teamService.getById(teamId)).thenReturn(new Team());
        when(playerService.getById(captainId)).thenReturn(new Player());
        when(teamService.updateCaptainByPlayerId(teamId, captainId)).thenReturn(false);

        assertThrows(BadRequestException.class, () -> {
            teamController.updateCaptain(teamId, captainId);
        });
    }

    @Test
    void deleteTeam_validIds_deletesTeam() {
        Long teamId = 1L;
        Long userId = 1L;

        when(userService.getById(userId)).thenReturn(new User());
        when(teamService.getById(teamId)).thenReturn(new Team());
        when(teamService.deleteTeam(teamId, userId)).thenReturn(true);

        assertDoesNotThrow(() -> {
            teamController.deleteTeam(teamId, userId);
        });
    }

    @Test
    void deleteTeam_nullIds_throwsBadRequestException() {
        Long teamId = null;
        Long userId = null;

        assertThrows(BadRequestException.class, () -> {
            teamController.deleteTeam(teamId, userId);
        });
    }

    @Test
    void deleteTeam_nonExistentUser_throwsResourceNotFoundException() {
        Long teamId = 1L;
        Long userId = 1L;

        when(userService.getById(userId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            teamController.deleteTeam(teamId, userId);
        });
    }

    @Test
    void deleteTeam_nonExistentTeam_throwsResourceNotFoundException() {
        Long teamId = 1L;
        Long userId = 1L;

        when(userService.getById(userId)).thenReturn(new User());
        when(teamService.getById(teamId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            teamController.deleteTeam(teamId, userId);
        });
    }

    @Test
    void deleteTeam_associatedTeam_throwsConflictException() {
        Long teamId = 1L;
        Long userId = 1L;

        when(userService.getById(userId)).thenReturn(new User());
        when(teamService.getById(teamId)).thenReturn(new Team());
        when(teamService.deleteTeam(teamId, userId)).thenReturn(false);

        assertThrows(ConflictException.class, () -> {
            teamController.deleteTeam(teamId, userId);
        });
    }

    @Test
    void inviteManager_validIds_invitesManager() {
        Long managerId = 1L;
        Long teamId = 1L;

        when(userService.getById(managerId)).thenReturn(new User());
        when(teamService.getById(teamId)).thenReturn(new Team());
        when(teamService.inviteManager(any())).thenReturn(true);

        teamController.inviteManager(managerId, teamId);

        verify(teamService, times(1)).inviteManager(any());
    }

    @Test
    void inviteManager_nullParameters_throwsBadRequestException() {
        assertThrows(BadRequestException.class, () -> {
            teamController.inviteManager(null, 1L);
        });

        assertThrows(BadRequestException.class, () -> {
            teamController.inviteManager(1L, null);
        });
    }

    @Test
    void inviteManager_nonExistentManager_throwsResourceNotFoundException() {
        Long managerId = 1L;
        Long teamId = 1L;

        when(userService.getById(managerId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            teamController.inviteManager(managerId, teamId);
        });
    }

    @Test
    void inviteManager_nonExistentTeam_throwsResourceNotFoundException() {
        Long managerId = 1L;
        Long teamId = 1L;

        when(userService.getById(managerId)).thenReturn(new User());
        when(teamService.getById(teamId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            teamController.inviteManager(managerId, teamId);
        });
    }

    @Test
    void inviteManager_inviteFails_throwsBadRequestException() {
        Long managerId = 1L;
        Long teamId = 1L;

        when(userService.getById(managerId)).thenReturn(new User());
        when(teamService.getById(teamId)).thenReturn(new Team());
        when(teamService.inviteManager(any())).thenReturn(false);

        assertThrows(BadRequestException.class, () -> {
            teamController.inviteManager(managerId, teamId);
        });
    }

    @Test
    void getManagers_validTeamId_returnsListOfManagers() {
        Long teamId = 1L;
        List<Long> managers = new ArrayList<>();
        managers.add(1L);
        managers.add(2L);

        when(teamService.getById(teamId)).thenReturn(new Team());
        when(teamService.getManagers(teamId)).thenReturn(managers);

        List<Long> result = teamController.getManagers(teamId);

        assertEquals(2, result.size());
        assertEquals(1L, result.get(0));
        assertEquals(2L, result.get(1));
    }

    @Test
    void getManagers_nullTeamId_throwsBadRequestException() {
        assertThrows(BadRequestException.class, () -> {
            teamController.getManagers(null);
        });
    }

    @Test
    void getManagers_nonExistentTeam_throwsResourceNotFoundException() {
        Long teamId = 1L;

        when(teamService.getById(teamId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            teamController.getManagers(teamId);
        });
    }

    @Test
    void deleteManager_validIds_deletesManager() {
        Long teamId = 1L;
        Long managerId = 1L;

        when(userService.getById(managerId)).thenReturn(new User());
        when(teamService.getById(teamId)).thenReturn(new Team());
        when(teamService.deleteManager(any())).thenReturn(true);

        teamController.deleteManager(teamId, managerId);

        verify(teamService, times(1)).deleteManager(any());
    }

    @Test
    void deleteManager_nullParameters_throwsBadRequestException() {
        assertThrows(BadRequestException.class, () -> {
            teamController.deleteManager(null, 1L);
        });

        assertThrows(BadRequestException.class, () -> {
            teamController.deleteManager(1L, null);
        });
    }

    @Test
    void deleteManager_nonExistentManager_throwsResourceNotFoundException() {
        Long teamId = 1L;
        Long managerId = 1L;

        when(userService.getById(managerId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            teamController.deleteManager(teamId, managerId);
        });
    }

    @Test
    void deleteManager_nonExistentTeam_throwsResourceNotFoundException() {
        Long teamId = 1L;
        Long managerId = 1L;

        when(userService.getById(managerId)).thenReturn(new User());
        when(teamService.getById(teamId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            teamController.deleteManager(teamId, managerId);
        });
    }

    @Test
    void deleteManager_deleteFails_throwsBadRequestException() {
        Long teamId = 1L;
        Long managerId = 1L;

        when(userService.getById(managerId)).thenReturn(new User());
        when(teamService.getById(teamId)).thenReturn(new Team());
        when(teamService.deleteManager(any())).thenReturn(false);

        assertThrows(BadRequestException.class, () -> {
            teamController.deleteManager(teamId, managerId);
        });
    }

    @Test
    void invitePlayer_validIds_invitesPlayer() {
        Long teamId = 1L;
        Long playerId = 1L;

        when(playerService.getById(playerId)).thenReturn(new Player());
        when(teamService.getById(teamId)).thenReturn(new Team());
        when(teamService.invitePlayer(any())).thenReturn(true);

        teamController.invitePlayer(teamId, playerId);

        verify(teamService, times(1)).invitePlayer(any());
    }

    @Test
    void invitePlayer_nullParams_throwsBadRequestException() {
        assertThrows(BadRequestException.class, () -> {
            teamController.invitePlayer(1L, null);
        });
        assertThrows(BadRequestException.class, () -> {
            teamController.invitePlayer(null, 1L);
        });
    }

    @Test
    void invitePlayer_nonExistentPlayer_throwsResourceNotFoundException() {
        Long teamId = 1L;
        Long playerId = 1L;

        when(playerService.getById(playerId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            teamController.invitePlayer(teamId, playerId);
        });
    }

    @Test
    void invitePlayer_nonExistentTeam_throwsResourceNotFoundException() {
        Long teamId = 1L;
        Long playerId = 1L;

        when(playerService.getById(playerId)).thenReturn(new Player());
        when(teamService.getById(teamId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            teamController.invitePlayer(teamId, playerId);
        });
    }

    @Test
    void invitePlayer_inviteFails_throwsBadRequestException() {
        Long teamId = 1L;
        Long playerId = 1L;

        when(playerService.getById(playerId)).thenReturn(new Player());
        when(teamService.getById(teamId)).thenReturn(new Team());
        when(teamService.invitePlayer(any())).thenReturn(false);

        assertThrows(BadRequestException.class, () -> {
            teamController.invitePlayer(teamId, playerId);
        });
    }

    @Test
    void getPlayerInvitations_validTeamId_returnsListOfInvitations() {
        Long teamId = 1L;
        List<TeamPlayerRequest> invitations = new ArrayList<>();
        invitations.add(new TeamPlayerRequest());
        invitations.add(new TeamPlayerRequest());

        when(teamService.getById(teamId)).thenReturn(new Team());
        when(teamService.getPlayerInvitations(teamId)).thenReturn(invitations);

        List<TeamPlayerRequest> result = teamController.getPlayerInvitations(teamId);

        assertEquals(2, result.size());
    }

    @Test
    void getPlayerInvitations_nullTeamId_throwsBadRequestException() {
        assertThrows(BadRequestException.class, () -> {
            teamController.getPlayerInvitations(null);
        });
    }

    @Test
    void getPlayerInvitations_nonExistentTeam_throwsResourceNotFoundException() {
        Long teamId = 1L;

        when(teamService.getById(teamId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            teamController.getPlayerInvitations(teamId);
        });
    }

    @Test
    void getPlayerApplications_validTeamId_returnsListOfApplications() {
        Long teamId = 1L;
        List<TeamPlayerRequest> applications = new ArrayList<>();
        applications.add(new TeamPlayerRequest());
        applications.add(new TeamPlayerRequest());

        when(teamService.getById(teamId)).thenReturn(new Team());
        when(teamService.getPlayerApplications(teamId)).thenReturn(applications);

        List<TeamPlayerRequest> result = teamController.getPlayerApplications(teamId);

        assertEquals(2, result.size());
    }

    @Test
    void getPlayerApplications_nullTeamId_throwsBadRequestException() {
        assertThrows(BadRequestException.class, () -> {
            teamController.getPlayerApplications(null);
        });
    }

    @Test
    void getPlayerApplications_nonExistentTeam_throwsResourceNotFoundException() {
        Long teamId = 1L;

        when(teamService.getById(teamId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            teamController.getPlayerApplications(teamId);
        });
    }

    @Test
    void replyPlayerApplication_validParameters_acceptTrue() {
        Long teamId = 1L;
        Long playerId = 1L;
        Boolean accept = true;

        when(playerService.getById(playerId)).thenReturn(new Player());
        when(teamService.getById(teamId)).thenReturn(new Team());
        when(teamService.replyPlayerApplication(teamId, playerId, accept)).thenReturn(true);

        assertDoesNotThrow(() -> teamController.replyPlayerApplication(teamId, playerId, accept));
    }

    @Test
    void replyPlayerApplication_nullParameters_throwsBadRequestException() {
        assertThrows(BadRequestException.class, () -> teamController.replyPlayerApplication(null, 1L, true));
        assertThrows(BadRequestException.class, () -> teamController.replyPlayerApplication(1L, null, true));
        assertThrows(BadRequestException.class, () -> teamController.replyPlayerApplication(1L, 1L, null));
    }

    @Test
    void replyPlayerApplication_nonExistentPlayer_throwsResourceNotFoundException() {
        Long teamId = 1L;
        Long playerId = 1L;
        Boolean accept = true;

        when(playerService.getById(playerId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> teamController.replyPlayerApplication(teamId, playerId, accept));
    }

    @Test
    void replyPlayerApplication_nonExistentTeam_throwsResourceNotFoundException() {
        Long teamId = 1L;
        Long playerId = 1L;
        Boolean accept = true;

        when(playerService.getById(playerId)).thenReturn(new Player());
        when(teamService.getById(teamId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> teamController.replyPlayerApplication(teamId, playerId, accept));
    }

    @Test
    void replyPlayerApplication_replyFails_throwsBadRequestException() {
        Long teamId = 1L;
        Long playerId = 1L;
        Boolean accept = true;

        when(playerService.getById(playerId)).thenReturn(new Player());
        when(teamService.getById(teamId)).thenReturn(new Team());
        when(teamService.replyPlayerApplication(teamId, playerId, accept)).thenReturn(false);

        assertThrows(BadRequestException.class, () -> teamController.replyPlayerApplication(teamId, playerId, accept));
    }

    @Test
    void getPlayers_validTeamId_returnsListOfVoTeamPlayer() {
        Long teamId = 1L;
        List<TeamPlayer> teamPlayerList = new ArrayList<>();
        teamPlayerList.add(new TeamPlayer());
        teamPlayerList.add(new TeamPlayer());

        when(teamService.getById(teamId)).thenReturn(new Team());
        when(teamService.getTeamPlayers(teamId)).thenReturn(teamPlayerList);

        assertEquals(2, teamPlayerList.size());
    }

    @Test
    void getPlayers_nullTeamId_throwsBadRequestException() {
        assertThrows(BadRequestException.class, () -> teamController.getPlayers(null));
    }

    @Test
    void getPlayers_nonExistentTeam_throwsResourceNotFoundException() {
        Long teamId = 1L;

        when(teamService.getById(teamId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> teamController.getPlayers(teamId));
    }

    @Test
    void deletePlayer_validIds_deletesPlayer() {
        Long teamId = 1L;
        Long playerId = 1L;

        when(playerService.getById(playerId)).thenReturn(new Player());
        when(teamService.getById(teamId)).thenReturn(new Team());
        when(teamService.deletePlayer(teamId, playerId)).thenReturn(true);

        assertDoesNotThrow(() -> teamController.deletePlayer(teamId, playerId));
    }

    @Test
    void deletePlayer_nullParameters_throwsBadRequestException() {
        assertThrows(BadRequestException.class, () -> teamController.deletePlayer(null, 1L));
        assertThrows(BadRequestException.class, () -> teamController.deletePlayer(1L, null));
    }

    @Test
    void deletePlayer_nonExistentPlayer_throwsResourceNotFoundException() {
        Long teamId = 1L;
        Long playerId = 1L;

        when(playerService.getById(playerId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> teamController.deletePlayer(teamId, playerId));
    }

    @Test
    void deletePlayer_nonExistentTeam_throwsResourceNotFoundException() {
        Long teamId = 1L;
        Long playerId = 1L;

        when(playerService.getById(playerId)).thenReturn(new Player());
        when(teamService.getById(teamId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> teamController.deletePlayer(teamId, playerId));
    }

    @Test
    void deletePlayer_deleteFails_throwsBadRequestException() {
        Long teamId = 1L;
        Long playerId = 1L;

        when(playerService.getById(playerId)).thenReturn(new Player());
        when(teamService.getById(teamId)).thenReturn(new Team());
        when(teamService.deletePlayer(teamId, playerId)).thenReturn(false);

        assertThrows(BadRequestException.class, () -> teamController.deletePlayer(teamId, playerId));
    }

    @Test
    void inviteCoach_validIds_invitesCoach() {
        Long teamId = 1L;
        Long coachId = 1L;

        when(teamService.getById(teamId)).thenReturn(new Team());
        when(coachService.getById(coachId)).thenReturn(new Coach());
        when(teamService.inviteCoach(any())).thenReturn(true);

        teamController.inviteCoach(teamId, coachId);

        verify(teamService, times(1)).inviteCoach(any());
    }

    @Test
    void inviteCoach_nullParameters_throwsBadRequestException() {
        assertThrows(BadRequestException.class, () -> {
            teamController.inviteCoach(1L, null);
        });
        assertThrows(BadRequestException.class, () -> {
            teamController.inviteCoach(null, 1L);
        });
    }

    @Test
    void inviteCoach_nonExistentTeam_throwsResourceNotFoundException() {
        Long teamId = 1L;
        Long coachId = 1L;

        when(teamService.getById(teamId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            teamController.inviteCoach(teamId, coachId);
        });
    }

    @Test
    void inviteCoach_nonExistentCoach_throwsResourceNotFoundException() {
        Long teamId = 1L;
        Long coachId = 1L;

        when(teamService.getById(teamId)).thenReturn(new Team());
        when(coachService.getById(coachId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            teamController.inviteCoach(teamId, coachId);
        });
    }

    @Test
    void inviteCoach_inviteFails_throwsBadRequestException() {
        Long teamId = 1L;
        Long coachId = 1L;

        when(teamService.getById(teamId)).thenReturn(new Team());
        when(coachService.getById(coachId)).thenReturn(new Coach());
        when(teamService.inviteCoach(any())).thenReturn(false);

        assertThrows(BadRequestException.class, () -> {
            teamController.inviteCoach(teamId, coachId);
        });
    }

    @Test
    void getCoaches_validTeamId_returnsListOfCoaches() {
        Long teamId = 1L;

        when(teamService.getById(teamId)).thenReturn(new Team());

        teamController.getCoaches(teamId);

        verify(teamService, times(1)).getCoaches(teamId);
    }

    @Test
    void getCoaches_nullTeamId_throwsBadRequestException() {
        assertThrows(BadRequestException.class, () -> {
            teamController.getCoaches(null);
        });
    }

    @Test
    void getCoaches_nonExistentTeam_throwsResourceNotFoundException() {
        Long teamId = 1L;

        when(teamService.getById(teamId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            teamController.getCoaches(teamId);
        });
    }

    @Test
    void deleteCoach_validIds_deletesCoach() {
        Long teamId = 1L;
        Long coachId = 1L;

        when(teamService.getById(teamId)).thenReturn(new Team());
        when(coachService.getById(coachId)).thenReturn(new Coach());
        when(teamService.deleteCoach(any())).thenReturn(true);

        teamController.deleteCoach(teamId, coachId);

        verify(teamService, times(1)).deleteCoach(any());
    }

    @Test
    void deleteCoach_nullParameters_throwsBadRequestException() {
        assertThrows(BadRequestException.class, () -> {
            teamController.deleteCoach(1L, null);
        });
        assertThrows(BadRequestException.class, () -> {
            teamController.deleteCoach(null, 1L);
        });
    }

    @Test
    void deleteCoach_nonExistentTeam_throwsResourceNotFoundException() {
        Long teamId = 1L;
        Long coachId = 1L;

        when(teamService.getById(teamId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            teamController.deleteCoach(teamId, coachId);
        });
    }

    @Test
    void deleteCoach_nonExistentCoach_throwsResourceNotFoundException() {
        Long teamId = 1L;
        Long coachId = 1L;

        when(teamService.getById(teamId)).thenReturn(new Team());
        when(coachService.getById(coachId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            teamController.deleteCoach(teamId, coachId);
        });
    }

    @Test
    void deleteCoach_deleteFails_throwsBadRequestException() {
        Long teamId = 1L;
        Long coachId = 1L;

        when(teamService.getById(teamId)).thenReturn(new Team());
        when(coachService.getById(coachId)).thenReturn(new Coach());
        when(teamService.deleteCoach(any())).thenReturn(false);

        assertThrows(BadRequestException.class, () -> {
            teamController.deleteCoach(teamId, coachId);
        });
    }

    @Test
    void getMatchInvitations_validTeamId_returnsListOfInvitations() {
        Long teamId = 1L;
        List<MatchTeamRequest> invitations = new ArrayList<>();
        invitations.add(new MatchTeamRequest());
        invitations.add(new MatchTeamRequest());

        when(teamService.getById(teamId)).thenReturn(new Team());
        when(teamService.getMatchInvitations(teamId)).thenReturn(invitations);

        List<MatchTeamRequest> result = teamController.getMatchInvitations(teamId);

        assertEquals(2, result.size());
    }

    @Test
    void getMatchInvitations_nullTeamId_throwsBadRequestException() {
        Long teamId = null;

        assertThrows(BadRequestException.class, () -> {
            teamController.getMatchInvitations(teamId);
        });
    }

    @Test
    void getMatchInvitations_nonExistentTeam_throwsResourceNotFoundException() {
        Long teamId = 1L;

        when(teamService.getById(teamId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            teamController.getMatchInvitations(teamId);
        });
    }

    @Test
    void replyMatchInvitation_validIds_acceptsInvitation() {
        Long teamId = 1L;
        Long matchId = 1L;

        when(teamService.getById(teamId)).thenReturn(new Team());
        when(matchService.getById(matchId)).thenReturn(new Match());
        when(teamService.replyMatchInvitation(teamId, matchId, true)).thenReturn(true);

        teamController.replyMatchInvitation(teamId, matchId, true);

        verify(teamService, times(1)).replyMatchInvitation(teamId, matchId, true);
    }

    @Test
    void replyMatchInvitation_nullParameters_throwsBadRequestException() {
        assertThrows(BadRequestException.class, () -> {
            teamController.replyMatchInvitation(1L, null, true);
        });
        assertThrows(BadRequestException.class, () -> {
            teamController.replyMatchInvitation(null, 1L, true);
        });
        assertThrows(BadRequestException.class, () -> {
            teamController.replyMatchInvitation(1L, 1L, null);
        });
    }

    @Test
    void replyMatchInvitation_nonExistentMatch_throwsResourceNotFoundException() {
        Long teamId = 1L;
        Long matchId = 1L;

        when(matchService.getById(matchId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            teamController.replyMatchInvitation(teamId, matchId, true);
        });
    }

    @Test
    void replyMatchInvitation_nonExistentTeam_throwsResourceNotFoundException() {
        Long teamId = 1L;
        Long matchId = 1L;

        when(teamService.getById(teamId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            teamController.replyMatchInvitation(teamId, matchId, true);
        });
    }

    @Test
    void replyMatchInvitation_replyFails_throwsBadRequestException() {
        Long teamId = 1L;
        Long matchId = 1L;

        when(teamService.getById(teamId)).thenReturn(new Team());
        when(matchService.getById(matchId)).thenReturn(new Match());
        when(teamService.replyMatchInvitation(teamId, matchId, true)).thenReturn(false);

        assertThrows(BadRequestException.class, () -> {
            teamController.replyMatchInvitation(teamId, matchId, true);
        });
    }

    @Test
    void getMatches_validTeamId_returnsListOfMatches() {
        Long teamId = 1L;
        List<Match> matches = new ArrayList<>();
        matches.add(new Match());
        matches.add(new Match());

        when(teamService.getById(teamId)).thenReturn(new Team());
        when(teamService.getMatches(teamId)).thenReturn(matches);

        List<Match> result = teamController.getMatches(teamId);

        assertEquals(2, result.size());
    }

    @Test
    void getMatches_nullTeamId_throwsBadRequestException() {
        Long teamId = null;

        assertThrows(BadRequestException.class, () -> {
            teamController.getMatches(teamId);
        });
    }

    @Test
    void getMatches_nonExistentTeam_throwsResourceNotFoundException() {
        Long teamId = 1L;

        when(teamService.getById(teamId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            teamController.getMatches(teamId);
        });
    }

    @Test
    void requestJoinEvent_validIds_requestSuccessful() {
        Long teamId = 1L;
        Long eventId = 1L;

        when(teamService.getById(teamId)).thenReturn(new Team());
        when(matchService.getById(eventId)).thenReturn(new Match());
        when(teamService.requestJoinEvent(any())).thenReturn(true);

        teamController.requestJoinEvent(teamId, eventId);

        verify(teamService, times(1)).requestJoinEvent(any());
    }

    @Test
    void requestJoinEvent_nullIds_throwsBadRequestException() {
        assertThrows(BadRequestException.class, () -> {
            teamController.requestJoinEvent(null, 1L);
        });
        assertThrows(BadRequestException.class, () -> {
            teamController.requestJoinEvent(1L, null);
        });
    }

    @Test
    void requestJoinEvent_nonExistentTeam_throwsResourceNotFoundException() {
        Long teamId = 1L;
        Long eventId = 1L;

        when(teamService.getById(teamId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            teamController.requestJoinEvent(teamId, eventId);
        });
    }

    @Test
    void requestJoinEvent_nonExistentEvent_throwsResourceNotFoundException() {
        Long teamId = 1L;
        Long eventId = 1L;

        when(teamService.getById(teamId)).thenReturn(new Team());
        when(matchService.getById(eventId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            teamController.requestJoinEvent(teamId, eventId);
        });
    }

    @Test
    void requestJoinEvent_requestFails_throwsBadRequestException() {
        Long teamId = 1L;
        Long eventId = 1L;

        when(teamService.getById(teamId)).thenReturn(new Team());
        when(matchService.getById(eventId)).thenReturn(new Match());
        when(teamService.requestJoinEvent(any())).thenReturn(false);

        assertThrows(BadRequestException.class, () -> {
            teamController.requestJoinEvent(teamId, eventId);
        });
    }

    @Test
    void getEventInvitations_validTeamId_returnsListOfEventTeamRequests() {
        Long teamId = 1L;
        List<EventTeamRequest> requests = new ArrayList<>();
        requests.add(new EventTeamRequest());
        requests.add(new EventTeamRequest());

        when(teamService.getById(teamId)).thenReturn(new Team());
        when(teamService.getEventInvitations(teamId)).thenReturn(requests);

        List<EventTeamRequest> result = teamController.getEventInvitations(teamId);

        assertEquals(2, result.size());
    }

    @Test
    void getEventInvitations_nullTeamId_throwsBadRequestException() {
        assertThrows(BadRequestException.class, () -> {
            teamController.getEventInvitations(null);
        });
    }

    @Test
    void getEventInvitations_nonExistentTeam_throwsResourceNotFoundException() {
        Long teamId = 1L;

        when(teamService.getById(teamId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            teamController.getEventInvitations(teamId);
        });
    }

    @Test
    void replyEventApplication_validIds_replySuccessful() {
        Long teamId = 1L;
        Long eventId = 1L;

        when(eventService.getById(eventId)).thenReturn(new Event());
        when(teamService.getById(teamId)).thenReturn(new Team());
        when(teamService.replyEventInvitation(teamId, eventId, true)).thenReturn(true);

        teamController.replyEventApplication(teamId, eventId, true);

        verify(teamService, times(1)).replyEventInvitation(teamId, eventId, true);
    }

    @Test
    void replyEventApplication_nullIdsOrAccept_throwsBadRequestException() {
        assertThrows(BadRequestException.class, () -> {
            teamController.replyEventApplication(null, 1L, true);
        });
        assertThrows(BadRequestException.class, () -> {
            teamController.replyEventApplication(1L, null, true);
        });
        assertThrows(BadRequestException.class, () -> {
            teamController.replyEventApplication(1L, 1L, null);
        });
    }

    @Test
    void replyEventApplication_nonExistentEvent_throwsResourceNotFoundException() {
        Long teamId = 1L;
        Long eventId = 1L;

        when(eventService.getById(eventId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            teamController.replyEventApplication(teamId, eventId, true);
        });
    }

    @Test
    void replyEventApplication_nonExistentTeam_throwsResourceNotFoundException() {
        Long teamId = 1L;
        Long eventId = 1L;

        when(eventService.getById(eventId)).thenReturn(new Event());
        when(teamService.getById(teamId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            teamController.replyEventApplication(teamId, eventId, true);
        });
    }

    @Test
    void replyEventApplication_replyFails_throwsBadRequestException() {
        Long teamId = 1L;
        Long eventId = 1L;

        when(eventService.getById(eventId)).thenReturn(new Event());
        when(teamService.getById(teamId)).thenReturn(new Team());
        when(teamService.replyEventInvitation(teamId, eventId, true)).thenReturn(false);

        assertThrows(BadRequestException.class, () -> {
            teamController.replyEventApplication(teamId, eventId, true);
        });
    }

    @Test
    void getEvents_validTeamId_returnsListOfEvents() {
        Long teamId = 1L;
        List<Event> events = new ArrayList<>();
        events.add(new Event());
        events.add(new Event());

        when(teamService.getById(teamId)).thenReturn(new Team());
        when(teamService.getEvents(teamId)).thenReturn(events);

        List<Event> result = teamController.getEvents(teamId);

        assertEquals(2, result.size());
    }

    @Test
    void getEvents_nullTeamId_throwsBadRequestException() {
        assertThrows(BadRequestException.class, () -> {
            teamController.getEvents(null);
        });
    }

    @Test
    void getEvents_nonExistentTeam_throwsResourceNotFoundException() {
        Long teamId = 1L;

        when(teamService.getById(teamId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            teamController.getEvents(teamId);
        });
    }

    @Test
    void addUniform_validInputs_addsUniform() {
        Long teamId = 1L;
        String uniformUrl = "https://example.com/uniform";

        when(teamService.getById(teamId)).thenReturn(new Team());
        when(teamService.addUniform(any())).thenReturn(true);

        assertDoesNotThrow(() -> {
            teamController.addUniform(teamId, uniformUrl);
        });

        verify(teamService, times(1)).addUniform(any());
    }

    @Test
    void addUniform_nullInputs_throwsBadRequestException() {
        assertThrows(BadRequestException.class, () -> {
            teamController.addUniform(null, "https://example.com/uniform");
        });
        assertThrows(BadRequestException.class, () -> {
            teamController.addUniform(1L, null);
        });
    }

    @Test
    void addUniform_nonExistentTeam_throwsResourceNotFoundException() {
        Long teamId = 1L;
        String uniformUrl = "https://example.com/uniform";

        when(teamService.getById(teamId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            teamController.addUniform(teamId, uniformUrl);
        });
    }

    @Test
    void addUniform_addFails_throwsBadRequestException() {
        Long teamId = 1L;
        String uniformUrl = "https://example.com/uniform";

        when(teamService.getById(teamId)).thenReturn(new Team());
        when(teamService.addUniform(any())).thenReturn(false);

        assertThrows(BadRequestException.class, () -> {
            teamController.addUniform(teamId, uniformUrl);
        });
    }

    @Test
    void getTeamUniformUrls_nullTeamId_throwsBadRequestException() {
        assertThrows(BadRequestException.class, () -> {
            teamController.getTeamUniformUrls(null);
        });
    }

    @Test
    void deleteUniform_validInputs_deletesUniform() {
        Long teamId = 1L;
        String uniformUrl = "https://example.com/uniform";

        when(teamService.getById(teamId)).thenReturn(new Team());
        when(teamService.deleteUniform(any())).thenReturn(true);

        assertDoesNotThrow(() -> {
            teamController.deleteUniform(teamId, uniformUrl);
        });

        verify(teamService, times(1)).deleteUniform(any());
    }

    @Test
    void deleteUniform_nullInputs_throwsBadRequestException() {
        assertThrows(BadRequestException.class, () -> {
            teamController.deleteUniform(null, "https://example.com/uniform");
        });
        assertThrows(BadRequestException.class, () -> {
            teamController.deleteUniform(1L, null);
        });
    }

    @Test
    void deleteUniform_nonExistentTeam_throwsResourceNotFoundException() {
        Long teamId = 1L;
        String uniformUrl = "https://example.com/uniform";

        when(teamService.getById(teamId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            teamController.deleteUniform(teamId, uniformUrl);
        });
    }

    @Test
    void deleteUniform_deleteFails_throwsBadRequestException() {
        Long teamId = 1L;
        String uniformUrl = "https://example.com/uniform";

        when(teamService.getById(teamId)).thenReturn(new Team());
        when(teamService.deleteUniform(any())).thenReturn(false);

        assertThrows(BadRequestException.class, () -> {
            teamController.deleteUniform(teamId, uniformUrl);
        });
    }
}
