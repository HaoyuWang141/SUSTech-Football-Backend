package com.sustech.football.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.sustech.football.service.*;
import com.sustech.football.entity.*;
import com.sustech.football.exception.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * AI-generated-content
 * tool: ChatGPT
 * version: 3.5 turbo
 * usage: I give it the class and method implementation it writes the tests for me.
 */


class PlayerControllerTest {

    @Mock
    private PlayerService playerService;

    @Mock
    private TeamService teamService;

    @Mock
    private TeamPlayerService teamPlayerService;

    @Mock
    private TeamPlayerRequestService teamPlayerRequestService;

    @InjectMocks
    private PlayerController playerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createPlayer_validPlayer_createsPlayer() {
        Player player = new Player();
        when(playerService.save(player)).thenReturn(true);

        Player result = playerController.createPlayer(player);

        assertNotNull(result);
        assertEquals(player, result);
    }

    @Test
    void createPlayer_nullPlayer_throwsBadRequestException() {
        assertThrows(BadRequestException.class, () -> {
            playerController.createPlayer(null);
        });
    }

    @Test
    void createPlayer_nonNullId_throwsBadRequestException() {
        Player player = new Player();
        player.setPlayerId(1L);

        assertThrows(BadRequestException.class, () -> {
            playerController.createPlayer(player);
        });
    }

    @Test
    void createPlayer_creationFails_throwsBadRequestException() {
        Player player = new Player();
        when(playerService.save(player)).thenReturn(false);

        assertThrows(BadRequestException.class, () -> {
            playerController.createPlayer(player);
        });
    }

    @Test
    void getPlayerById_existingPlayer_returnsPlayer() {
        Long playerId = 1L;
        Player player = new Player();
        player.setPlayerId(playerId);

        when(playerService.getById(playerId)).thenReturn(player);

        Player result = playerController.getPlayerById(playerId);

        assertNotNull(result);
        assertEquals(playerId, result.getPlayerId());
    }

    @Test
    void getPlayerById_nonExistingPlayer_throwsResourceNotFoundException() {
        Long playerId = 1L;
        when(playerService.getById(playerId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            playerController.getPlayerById(playerId);
        });
    }

    @Test
    void getAllPlayers_returnsListOfPlayers() {
        List<Player> players = new ArrayList<>();
        players.add(new Player());
        players.add(new Player());

        when(playerService.list()).thenReturn(players);

        List<Player> result = playerController.getAllPlayers();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void getPlayersByIds_returnsListOfPlayers() {
        List<Long> idList = new ArrayList<>();
        idList.add(1L);
        idList.add(2L);

        List<Player> players = new ArrayList<>();
        players.add(new Player());
        players.add(new Player());

        when(playerService.listByIds(idList)).thenReturn(players);

        List<Player> result = playerController.getPlayersByIds(idList);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void updatePlayer_validPlayer_returnsUpdatedPlayer() {
        Player player = new Player();
        player.setPlayerId(1L);

        when(playerService.updateById(player)).thenReturn(true);

        Player result = playerController.updatePlayer(player);

        assertEquals(player, result);
    }

    @Test
    void updatePlayer_nullPlayer_throwsBadRequestException() {
        assertThrows(BadRequestException.class, () -> {
            playerController.updatePlayer(null);
        });
    }

    @Test
    void updatePlayer_nullPlayerId_throwsBadRequestException() {
        Player player = new Player();

        assertThrows(BadRequestException.class, () -> {
            playerController.updatePlayer(player);
        });
    }

    @Test
    void updatePlayer_updateFailed_throwsBadRequestException() {
        Player player = new Player();
        player.setPlayerId(1L);

        when(playerService.updateById(player)).thenReturn(false);

        assertThrows(BadRequestException.class, () -> {
            playerController.updatePlayer(player);
        });
    }

    @Test
    void applyJoinTeam_validInput_successful() {
        Long playerId = 1L;
        Long teamId = 1L;

        when(playerService.getById(playerId)).thenReturn(new Player());
        when(teamService.getById(teamId)).thenReturn(new Team());
        when(teamPlayerService.selectByMultiId(any())).thenReturn(null);
        when(teamPlayerRequestService.selectByMultiId(any())).thenReturn(null);
        when(teamPlayerRequestService.save(any())).thenReturn(true);

        assertDoesNotThrow(() -> playerController.applyJoinTeam(playerId, teamId));
    }

    @Test
    void applyJoinTeam_nullParams_throwsBadRequestException() {
        assertThrows(BadRequestException.class, () -> playerController.applyJoinTeam(null, 1L));
        assertThrows(BadRequestException.class, () -> playerController.applyJoinTeam(1L, null));
    }

    @Test
    void applyJoinTeam_nonExistentPlayer_throwsResourceNotFoundException() {
        Long playerId = 1L;
        Long teamId = 1L;

        when(playerService.getById(playerId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> playerController.applyJoinTeam(playerId, teamId));
    }

    @Test
    void applyJoinTeam_nonExistentTeam_throwsResourceNotFoundException() {
        Long playerId = 1L;
        Long teamId = 1L;

        when(playerService.getById(playerId)).thenReturn(new Player());
        when(teamService.getById(teamId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> playerController.applyJoinTeam(playerId, teamId));
    }

    @Test
    void applyJoinTeam_playerAlreadyInTeam_throwsConflictException() {
        Long playerId = 1L;
        Long teamId = 1L;

        when(playerService.getById(playerId)).thenReturn(new Player());
        when(teamService.getById(teamId)).thenReturn(new Team());
        when(teamPlayerService.selectByMultiId(any())).thenReturn(new TeamPlayer());

        assertThrows(ConflictException.class, () -> playerController.applyJoinTeam(playerId, teamId));
    }

    @Test
    void applyJoinTeam_playerAlreadyApplied_throwsConflictException() {
        Long playerId = 1L;
        Long teamId = 1L;

        when(playerService.getById(playerId)).thenReturn(new Player());
        when(teamService.getById(teamId)).thenReturn(new Team());
        when(teamPlayerService.selectByMultiId(any())).thenReturn(null);
        when(teamPlayerRequestService.selectByMultiId(any())).thenReturn(new TeamPlayerRequest());

        assertThrows(ConflictException.class, () -> playerController.applyJoinTeam(playerId, teamId));
    }

    @Test
    void applyJoinTeam_applicationFails_throwsRuntimeException() {
        Long playerId = 1L;
        Long teamId = 1L;

        when(playerService.getById(playerId)).thenReturn(new Player());
        when(teamService.getById(teamId)).thenReturn(new Team());
        when(teamPlayerService.selectByMultiId(any())).thenReturn(null);
        when(teamPlayerRequestService.selectByMultiId(any())).thenReturn(null);
        when(teamPlayerRequestService.save(any())).thenReturn(false);

        assertThrows(RuntimeException.class, () -> playerController.applyJoinTeam(playerId, teamId));
    }

    @Test
    void getTeamApplications_validPlayerId_returnsListOfApplications() {
        Long playerId = 1L;
        List<TeamPlayerRequest> applications = new ArrayList<>();
        applications.add(new TeamPlayerRequest());
        applications.add(new TeamPlayerRequest());

        when(playerService.getById(playerId)).thenReturn(new Player());
        when(teamPlayerRequestService.listWithTeam(playerId, TeamPlayerRequest.TYPE_APPLICATION)).thenReturn(applications);

        List<TeamPlayerRequest> result = playerController.getTeamApplications(playerId);

        assertEquals(2, result.size());
    }

    @Test
    void getTeamApplications_nullPlayerId_throwsBadRequestException() {
        assertThrows(BadRequestException.class, () -> playerController.getTeamApplications(null));
    }

    @Test
    void getTeamApplications_nonExistentPlayer_throwsResourceNotFoundException() {
        Long playerId = 1L;

        when(playerService.getById(playerId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> playerController.getTeamApplications(playerId));
    }

    @Test
    void getTeamInvitations_validPlayerId_returnsListOfInvitations() {
        Long playerId = 1L;
        List<TeamPlayerRequest> invitations = new ArrayList<>();
        invitations.add(new TeamPlayerRequest());
        invitations.add(new TeamPlayerRequest());

        when(playerService.getById(playerId)).thenReturn(new Player());
        when(teamPlayerRequestService.listWithTeam(playerId, TeamPlayerRequest.TYPE_INVITATION)).thenReturn(invitations);

        List<TeamPlayerRequest> result = playerController.getTeamInvitations(playerId);

        assertEquals(2, result.size());
    }

    @Test
    void getTeamInvitations_nullPlayerId_throwsBadRequestException() {
        assertThrows(BadRequestException.class, () -> playerController.getTeamInvitations(null));
    }

    @Test
    void getTeamInvitations_nonExistentPlayer_throwsResourceNotFoundException() {
        Long playerId = 1L;

        when(playerService.getById(playerId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> playerController.getTeamInvitations(playerId));
    }

    @Test
    void replyTeamInvitation_validParams_acceptInvitation() {
        Long playerId = 1L;
        Long teamId = 1L;
        boolean accept = true;

        when(playerService.getById(playerId)).thenReturn(new Player());
        when(teamService.getById(teamId)).thenReturn(new Team());
        when(playerService.replyTeamInvitation(playerId, teamId, accept)).thenReturn(true);

        playerController.replyTeamInvitation(playerId, teamId, accept);

        verify(playerService, times(1)).replyTeamInvitation(playerId, teamId, accept);
    }

    @Test
    void replyTeamInvitation_nullParams_throwsBadRequestException() {
        assertThrows(BadRequestException.class, () -> {
            playerController.replyTeamInvitation(null, 1L, true);
        });
        assertThrows(BadRequestException.class, () -> {
            playerController.replyTeamInvitation(1L, null, true);
        });
        assertThrows(BadRequestException.class, () -> {
            playerController.replyTeamInvitation(1L, 1L, null);
        });
    }

    @Test
    void replyTeamInvitation_nonExistentPlayer_throwsResourceNotFoundException() {
        Long playerId = 1L;
        Long teamId = 1L;
        boolean accept = true;

        when(playerService.getById(playerId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            playerController.replyTeamInvitation(playerId, teamId, accept);
        });
    }

    @Test
    void replyTeamInvitation_nonExistentTeam_throwsResourceNotFoundException() {
        Long playerId = 1L;
        Long teamId = 1L;
        boolean accept = true;

        when(playerService.getById(playerId)).thenReturn(new Player());
        when(teamService.getById(teamId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            playerController.replyTeamInvitation(playerId, teamId, accept);
        });
    }

    @Test
    void replyTeamInvitation_processingFails_throwsBadRequestException() {
        Long playerId = 1L;
        Long teamId = 1L;
        boolean accept = true;

        when(playerService.getById(playerId)).thenReturn(new Player());
        when(teamService.getById(teamId)).thenReturn(new Team());
        when(playerService.replyTeamInvitation(playerId, teamId, accept)).thenReturn(false);

        assertThrows(BadRequestException.class, () -> {
            playerController.replyTeamInvitation(playerId, teamId, accept);
        });
    }

    @Test
    void getTeams_validPlayerId_returnsListOfTeams() {
        Long playerId = 1L;
        List<TeamPlayer> teamPlayers = new ArrayList<>();
        teamPlayers.add(new TeamPlayer(playerId, 1L));
        teamPlayers.add(new TeamPlayer(playerId, 2L));
        List<Team> teams = new ArrayList<>();
        teams.add(new Team());
        teams.add(new Team());

        when(playerService.getById(playerId)).thenReturn(new Player());
        when(teamPlayerService.list((Wrapper<TeamPlayer>) any())).thenReturn(teamPlayers);
        when(teamService.listByIds(any())).thenReturn(teams);

        List<Team> result = playerController.getTeams(playerId);

        assertEquals(2, result.size());
    }

    @Test
    void getTeams_nullPlayerId_throwsBadRequestException() {
        assertThrows(BadRequestException.class, () -> {
            playerController.getTeams(null);
        });
    }

    @Test
    void getTeams_nonExistentPlayer_throwsResourceNotFoundException() {
        Long playerId = 1L;

        when(playerService.getById(playerId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            playerController.getTeams(playerId);
        });
    }

    @Test
    void getTeams_noTeamPlayers_returnsEmptyList() {
        Long playerId = 1L;

        when(playerService.getById(playerId)).thenReturn(new Player());
        when(teamPlayerService.list((Wrapper<TeamPlayer>) any())).thenReturn(new ArrayList<>());

        List<Team> result = playerController.getTeams(playerId);

        assertEquals(0, result.size());
    }

    @Test
    void getMatches_validPlayerId_returnsListOfMatches() {
        Long playerId = 1L;
        List<Match> matches = new ArrayList<>();
        matches.add(new Match());
        matches.add(new Match());

        when(playerService.getById(playerId)).thenReturn(new Player());
        when(playerService.getPlayerMatches(playerId)).thenReturn(matches);

        List<Match> result = playerController.getMatches(playerId);

        assertEquals(2, result.size());
    }

    @Test
    void getMatches_nullPlayerId_throwsBadRequestException() {
        assertThrows(BadRequestException.class, () -> {
            playerController.getMatches(null);
        });
    }

    @Test
    void getMatches_nonExistentPlayer_throwsResourceNotFoundException() {
        Long playerId = 1L;

        when(playerService.getById(playerId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            playerController.getMatches(playerId);
        });
    }

    @Test
    void getEvents_validPlayerId_returnsListOfEvents() {
        Long playerId = 1L;
        List<TeamPlayer> teamPlayers = new ArrayList<>();
        teamPlayers.add(new TeamPlayer(1L, playerId));
        teamPlayers.add(new TeamPlayer(2L, playerId));

        List<Long> teamIds = teamPlayers.stream().map(TeamPlayer::getTeamId).collect(Collectors.toList());

        List<Event> events = new ArrayList<>();
        events.add(new Event());
        events.add(new Event());
        events.get(0).setEventId(0L);
        events.get(1).setEventId(1L);

        when(playerService.getById(playerId)).thenReturn(new Player());
        when(teamPlayerService.list((Wrapper<TeamPlayer>) any())).thenReturn(teamPlayers);
        when(teamService.getEvents(any())).thenReturn(events);

        List<Event> result = playerController.getEvents(playerId);

        assertEquals(2, result.size());
    }

    @Test
    void getEvents_nullPlayerId_throwsBadRequestException() {
        assertThrows(BadRequestException.class, () -> {
            playerController.getEvents(null);
        });
    }

    @Test
    void getEvents_nonExistentPlayer_throwsResourceNotFoundException() {
        Long playerId = 1L;

        when(playerService.getById(playerId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            playerController.getEvents(playerId);
        });
    }
}
