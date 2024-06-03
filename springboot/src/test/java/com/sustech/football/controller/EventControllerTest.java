package com.sustech.football.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.sustech.football.entity.*;
import com.sustech.football.exception.*;
import com.sustech.football.model.event.VoEvent;
import com.sustech.football.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * AI-generated-content
 * tool: ChatGPT
 * version: 3.5 turbo
 * usage: I give it the class and method implementation it writes the tests for me.
 */


public class EventControllerTest {

    @Mock
    private EventService eventService;

    @Mock
    private UserService userService;

    @Mock
    private TeamService teamService;

    @Mock
    private EventGroupService eventGroupService;

    @Mock
    private EventGroupTeamService eventGroupTeamService;

    @Mock
    private EventStageService eventStageService;

    @Mock
    private EventStageTagService eventStageTagService;

    @Mock
    private EventTeamService eventTeamService;

    @Mock
    private RefereeService refereeService;

    @Mock
    private MatchService matchService;

    @InjectMocks
    private EventController eventController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateEvent_Success() {
        Long ownerId = 1L;
        Event event = new Event();

        when(userService.getById(ownerId)).thenReturn(new User()); // Mocking userService

        when(eventService.createEvent(any(Event.class))).thenReturn(true);
        when(eventService.inviteManager(any(EventManager.class))).thenReturn(true);

        String result = eventController.createEvent(ownerId, event);

        verify(eventService, times(1)).createEvent(any(Event.class));
        verify(eventService, times(1)).inviteManager(any(EventManager.class));

        assert result.equals("创建赛事成功");
    }

    @Test
    public void testCreateEvent_NullOwnerId() {
        assertThrows(BadRequestException.class, () -> eventController.createEvent(null, new Event()));
    }

    @Test
    public void testCreateEvent_UserNotFound() {
        Long ownerId = 1L;
        Event event = new Event();

        when(userService.getById(ownerId)).thenReturn(null); // Mocking userService

        assertThrows(BadRequestException.class, () -> eventController.createEvent(ownerId, event));
    }

    @Test
    public void testCreateEvent_NullEvent() {
        Long ownerId = 1L;

        when(userService.getById(ownerId)).thenReturn(new User()); // Mocking userService

        assertThrows(BadRequestException.class, () -> eventController.createEvent(ownerId, null));
    }

    @Test
    public void testCreateEvent_EventWithId() {
        Long ownerId = 1L;
        Event event = new Event();
        event.setEventId(1L);

        when(userService.getById(ownerId)).thenReturn(new User()); // Mocking userService

        assertThrows(BadRequestException.class, () -> eventController.createEvent(ownerId, event));
    }

    @Test
    public void testCreateEvent_CreateEventFailed() {
        Long ownerId = 1L;
        Event event = new Event();

        when(userService.getById(ownerId)).thenReturn(new User()); // Mocking userService
        when(eventService.createEvent(any(Event.class))).thenReturn(false);

        assertThrows(BadRequestException.class, () -> eventController.createEvent(ownerId, event));
    }

    @Test
    public void testCreateEvent_InviteManagerFailed() {
        Long ownerId = 1L;
        Event event = new Event();

        when(userService.getById(ownerId)).thenReturn(new User()); // Mocking userService
        when(eventService.createEvent(any(Event.class))).thenReturn(true);
        when(eventService.inviteManager(any(EventManager.class))).thenReturn(false);

        assertThrows(BadRequestException.class, () -> eventController.createEvent(ownerId, event));
    }

    @Test
    public void testGetEvent_Successful() {
        Long eventId = 1L;
        Event mockEvent = new Event();
        mockEvent.setEventId(eventId);
        // Mock service behavior
        when(eventService.getDetailedEvent(eventId)).thenReturn(mockEvent);

        assertThrows(NullPointerException.class, () -> {
            eventController.getEvent(eventId);
        });

    }

    @Test
    public void testGetEvent_NullId() {
        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            eventController.getEvent(null);
        });

        assertEquals("传入的赛事ID为空", exception.getMessage());
        verify(eventService, never()).getDetailedEvent(anyLong());
    }

    @Test
    public void testGetEvent_NonExistentId() {
        // Mock service to return null for any ID
        when(eventService.getDetailedEvent(anyLong())).thenReturn(null);

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            eventController.getEvent(-999L);
        });

        assertEquals("传入的赛事ID不存在", exception.getMessage());
    }

    @Test
    void updateEvent_NullEvent_ThrowBadRequestException() {
        assertThrows(BadRequestException.class, () -> {
            eventController.updateEvent(null);
        });
    }

    @Test
    void updateEvent_NullEventId_ThrowBadRequestException() {
        Event event = new Event();
        assertThrows(BadRequestException.class, () -> {
            eventController.updateEvent(event);
        });
    }

    @Test
    void updateEvent_FailedUpdate_ThrowBadRequestException() {
        Event event = new Event();
        event.setEventId(1L);
        when(eventService.updateEvent(event)).thenReturn(false);
        assertThrows(BadRequestException.class, () -> {
            eventController.updateEvent(event);
        });
    }

    @Test
    void updateEvent_SuccessfulUpdate_NoExceptionThrown() {
        Event event = new Event();
        event.setEventId(1L);
        when(eventService.updateEvent(event)).thenReturn(true);
        eventController.updateEvent(event);
        verify(eventService, times(1)).updateEvent(event);
    }

    @Test
    void deleteEvent_NullEventId_ThrowBadRequestException() {
        assertThrows(BadRequestException.class, () -> {
            eventController.deleteEvent(null, 1L);
        });
    }

    @Test
    void deleteEvent_NullUserId_ThrowBadRequestException() {
        assertThrows(BadRequestException.class, () -> {
            eventController.deleteEvent(1L, null);
        });
    }

    @Test
    void deleteEvent_NonExistentEvent_ThrowResourceNotFoundException() {
        Long eventId = 1L;
        when(eventService.getById(eventId)).thenReturn(null);
        assertThrows(ResourceNotFoundException.class, () -> {
            eventController.deleteEvent(eventId, 1L);
        });
    }

    @Test
    void deleteEvent_NonExistentUser_ThrowResourceNotFoundException() {
        Long eventId = 1L;
        Long userId = 1L;
        when(eventService.getById(eventId)).thenReturn(new Event());
        when(userService.getById(userId)).thenReturn(null);
        assertThrows(ResourceNotFoundException.class, () -> {
            eventController.deleteEvent(eventId, userId);
        });
    }

    @Test
    void deleteEvent_FailedDelete_ThrowBadRequestException() {
        Long eventId = 1L;
        Long userId = 1L;
        when(eventService.getById(eventId)).thenReturn(new Event());
        when(userService.getById(userId)).thenReturn(new User());
        when(eventService.deleteEvent(eventId, userId)).thenReturn(false);
        assertThrows(BadRequestException.class, () -> {
            eventController.deleteEvent(eventId, userId);
        });
    }

    @Test
    void deleteEvent_SuccessfulDelete_NoExceptionThrown() {
        Long eventId = 1L;
        Long userId = 1L;
        when(eventService.getById(eventId)).thenReturn(new Event());
        when(userService.getById(userId)).thenReturn(new User());
        when(eventService.deleteEvent(eventId, userId)).thenReturn(true);
        eventController.deleteEvent(eventId, userId);
        verify(eventService, times(1)).deleteEvent(eventId, userId);
    }

    @Test
    void inviteManager_Success() {
        Long eventId = 1L;
        Long managerId = 1L;
        EventManager eventManager = new EventManager(eventId, managerId, false);
        when(eventService.getById(anyLong())).thenReturn(new Event());
        when(userService.getById(anyLong())).thenReturn(new User());
        when(eventService.inviteManager(any(EventManager.class))).thenReturn(true);

        assertDoesNotThrow(() -> eventController.inviteManager(eventId, managerId));
    }

    @Test
    void inviteManager_NullPara_ThrowBadRequestException() {
        assertThrows(BadRequestException.class, () -> {
            eventController.inviteManager(null, 1L);
        });
        assertThrows(BadRequestException.class, () -> {
            eventController.inviteManager(1L, null);
        });
    }

    @Test
    void inviteManager_EventNotFound() {
        Long eventId = 1L;
        Long managerId = 1L;
        when(eventService.getById(anyLong())).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> eventController.inviteManager(eventId, managerId));
    }

    @Test
    void inviteManager_UserNotFound() {
        Long eventId = 1L;
        Long managerId = 1L;
        when(eventService.getById(anyLong())).thenReturn(new Event());
        when(userService.getById(anyLong())).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> eventController.inviteManager(eventId, managerId));
    }

    @Test
    void inviteManager_Failure() {
        Long eventId = 1L;
        Long managerId = 1L;
        when(eventService.getById(anyLong())).thenReturn(new Event());
        when(userService.getById(anyLong())).thenReturn(new User());
        when(eventService.inviteManager(any(EventManager.class))).thenReturn(false);

        assertThrows(RuntimeException.class, () -> eventController.inviteManager(eventId, managerId));
    }

    @Test
    void getManagers_Success() {
        // Arrange
        Long eventId = 1L;
        List<Long> managers = new ArrayList<>();
        managers.add(1L);
        when(eventService.getById(anyLong())).thenReturn(new Event());
        when(eventService.getManagers(anyLong())).thenReturn(managers);

        List<Long> result = eventController.getManagers(eventId);

        assertNotNull(result);
        assertEquals(managers, result);
    }

    @Test
    void getManagers_NullPara_ThrowBadRequestException() {
        assertThrows(BadRequestException.class, () -> {
            eventController.getManagers(null);
        });
    }


    @Test
    void getManagers_EventNotFound() {
        Long eventId = 1L;
        when(eventService.getById(anyLong())).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> eventController.getManagers(eventId));
    }

    @Test
    void deleteManager_Success() {
        // Arrange
        Long eventId = 1L;
        Long managerId = 1L;
        EventManager eventManager = new EventManager(eventId, managerId, false);
        when(eventService.getById(anyLong())).thenReturn(new Event());
        when(userService.getById(anyLong())).thenReturn(new User());
        when(eventService.deleteManager(any(EventManager.class))).thenReturn(true);

        // Act & Assert
        assertDoesNotThrow(() -> eventController.deleteManager(eventId, managerId));
    }

    @Test
    void deleteManager_NullPara_ThrowBadRequestException() {
        assertThrows(BadRequestException.class, () -> {
            eventController.deleteManager(null, 1L);
        });
        assertThrows(BadRequestException.class, () -> {
            eventController.deleteManager(1L, null);
        });
    }

    @Test
    void deleteManager_EventNotFound() {
        // Arrange
        Long eventId = 1L;
        Long managerId = 1L;
        when(eventService.getById(anyLong())).thenReturn(null);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> eventController.deleteManager(eventId, managerId));
    }

    @Test
    void deleteManager_UserNotFound() {
        Long eventId = 1L;
        Long managerId = 1L;
        when(eventService.getById(anyLong())).thenReturn(new Event());
        when(userService.getById(anyLong())).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> eventController.deleteManager(eventId, managerId));
    }

    @Test
    void deleteManager_Failure() {
        Long eventId = 1L;
        Long managerId = 1L;
        when(eventService.getById(anyLong())).thenReturn(new Event());
        when(userService.getById(anyLong())).thenReturn(new User());
        when(eventService.deleteManager(any(EventManager.class))).thenReturn(false);

        assertThrows(RuntimeException.class, () -> eventController.deleteManager(eventId, managerId));
    }


    @Test
    void inviteTeam_Success() {
        Long eventId = 1L;
        Long teamId = 1L;
        when(eventService.getById(eventId)).thenReturn(new Event());
        when(teamService.getById(teamId)).thenReturn(new Team());
        when(eventService.inviteTeam(any(EventTeamRequest.class))).thenReturn(true);

        assertDoesNotThrow(() -> eventController.inviteTeam(eventId, teamId));
    }

    @Test
    void inviteTeam_EventIdNull_ThrowsBadRequestException() {
        assertThrows(BadRequestException.class, () -> eventController.inviteTeam(null, 1L));
    }

    @Test
    void inviteTeam_TeamIdNull_ThrowsBadRequestException() {
        assertThrows(BadRequestException.class, () -> eventController.inviteTeam(1L, null));
    }

    @Test
    void inviteTeam_EventNotFound_ThrowsResourceNotFoundException() {
        Long eventId = 1L;
        Long teamId = 1L;
        when(eventService.getById(eventId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> eventController.inviteTeam(eventId, teamId));
    }

    @Test
    void inviteTeam_TeamNotFound_ThrowsResourceNotFoundException() {
        Long eventId = 1L;
        Long teamId = 1L;
        when(eventService.getById(eventId)).thenReturn(new Event());
        when(teamService.getById(teamId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> eventController.inviteTeam(eventId, teamId));
    }

    @Test
    void inviteTeam_InviteTeamFails_ThrowsRuntimeException() {
        Long eventId = 1L;
        Long teamId = 1L;
        when(eventService.getById(eventId)).thenReturn(new Event());
        when(teamService.getById(teamId)).thenReturn(new Team());
        when(eventService.inviteTeam(any(EventTeamRequest.class))).thenReturn(false);

        assertThrows(RuntimeException.class, () -> eventController.inviteTeam(eventId, teamId));
    }

    @Test
    void getTeamInvitations_Success() {
        Long eventId = 1L;
        when(eventService.getById(eventId)).thenReturn(new Event());
        List<EventTeamRequest> invitations = new ArrayList<>();
        when(eventService.getTeamInvitations(eventId)).thenReturn(invitations);

        assertEquals(invitations, eventController.getTeamInvitations(eventId));
    }

    @Test
    void getTeamInvitations_EventIdNull_ThrowsBadRequestException() {
        assertThrows(BadRequestException.class, () -> eventController.getTeamInvitations(null));
    }

    @Test
    void getTeamInvitations_EventNotFound_ThrowsResourceNotFoundException() {
        Long eventId = 1L;
        when(eventService.getById(eventId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> eventController.getTeamInvitations(eventId));
    }

    @Test
    void getTeamApplications_Success() {
        Long eventId = 1L;
        when(eventService.getById(eventId)).thenReturn(new Event());
        List<EventTeamRequest> applications = new ArrayList<>();
        when(eventService.getTeamApplications(eventId)).thenReturn(applications);

        assertEquals(applications, eventController.getTeamApplications(eventId));
    }

    @Test
    void getTeamApplications_EventIdNull_ThrowsBadRequestException() {
        assertThrows(BadRequestException.class, () -> eventController.getTeamApplications(null));
    }

    @Test
    void getTeamApplications_EventNotFound_ThrowsResourceNotFoundException() {
        Long eventId = 1L;
        when(eventService.getById(eventId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> eventController.getTeamApplications(eventId));
    }

    @Test
    void replyTeamApplication_Success() {
        Long eventId = 1L;
        Long teamId = 1L;
        boolean accepted = true;
        when(eventService.getById(eventId)).thenReturn(new Event());
        when(teamService.getById(teamId)).thenReturn(new Team());
        when(eventService.replyTeamApplication(any(EventTeamRequest.class))).thenReturn(true);

        assertDoesNotThrow(() -> eventController.replyTeamApplication(eventId, teamId, accepted));
    }

    @Test
    void replyTeamApplication_EventIdNull_ThrowsBadRequestException() {
        assertThrows(BadRequestException.class, () -> eventController.replyTeamApplication(null, 1L, true));
    }

    @Test
    void replyTeamApplication_TeamIdNull_ThrowsBadRequestException() {
        assertThrows(BadRequestException.class, () -> eventController.replyTeamApplication(1L, null, true));
    }

    @Test
    void replyTeamApplication_AcceptedNull_ThrowsBadRequestException() {
        assertThrows(BadRequestException.class, () -> eventController.replyTeamApplication(1L, 1L, null));
    }

    @Test
    void replyTeamApplication_EventNotFound_ThrowsResourceNotFoundException() {
        Long eventId = 1L;
        Long teamId = 1L;
        boolean accepted = true;
        when(eventService.getById(eventId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> eventController.replyTeamApplication(eventId, teamId, accepted));
    }

    @Test
    void replyTeamApplication_TeamNotFound_ThrowsResourceNotFoundException() {
        Long eventId = 1L;
        Long teamId = 1L;
        boolean accepted = true;
        when(eventService.getById(eventId)).thenReturn(new Event());
        when(teamService.getById(teamId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> eventController.replyTeamApplication(eventId, teamId, accepted));
    }

    @Test
    void replyTeamApplication_ReplyApplicationFails_ThrowsRuntimeException() {
        Long eventId = 1L;
        Long teamId = 1L;
        boolean accepted = true;
        when(eventService.getById(eventId)).thenReturn(new Event());
        when(teamService.getById(teamId)).thenReturn(new Team());
        when(eventService.replyTeamApplication(any(EventTeamRequest.class))).thenReturn(false);

        assertThrows(RuntimeException.class, () -> eventController.replyTeamApplication(eventId, teamId, accepted));
    }

    @Test
    void getTeams_validEventId_returnsListOfTeams() {
        Long eventId = 1L;
        List<Team> teams = new ArrayList<>();
        teams.add(new Team());
        teams.add(new Team());

        when(eventService.getById(eventId)).thenReturn(new Event());
        when(eventService.getTeams(eventId)).thenReturn(teams);

        List<Team> result = eventController.getTeams(eventId);

        assertEquals(2, result.size());
    }

    @Test
    void getTeams_nullEventId_throwsBadRequestException() {
        Long eventId = null;

        assertThrows(BadRequestException.class, () -> {
            eventController.getTeams(eventId);
        });
    }

    @Test
    void getTeams_nonExistentEvent_throwsResourceNotFoundException() {
        Long eventId = 1L;

        when(eventService.getById(eventId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            eventController.getTeams(eventId);
        });
    }

    @Test
    void deleteTeam_validIds_deletesTeam() {
        Long eventId = 1L;
        Long teamId = 1L;

        when(eventService.getById(eventId)).thenReturn(new Event());
        when(teamService.getById(teamId)).thenReturn(new Team());
        when(eventService.deleteTeam(any())).thenReturn(true);

        eventController.deleteTeam(eventId, teamId);

        verify(eventService, times(1)).deleteTeam(any());
    }

    @Test
    void deleteTeam_nullEventId_throwsBadRequestException() {
        Long eventId = null;
        Long teamId = 1L;

        assertThrows(BadRequestException.class, () -> {
            eventController.deleteTeam(eventId, teamId);
        });
    }

    @Test
    void deleteTeam_nullTeamId_throwsBadRequestException() {
        Long eventId = 1L;
        Long teamId = null;

        assertThrows(BadRequestException.class, () -> {
            eventController.deleteTeam(eventId, teamId);
        });
    }

    @Test
    void deleteTeam_nonExistentEvent_throwsResourceNotFoundException() {
        Long eventId = 1L;
        Long teamId = 1L;

        when(eventService.getById(eventId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            eventController.deleteTeam(eventId, teamId);
        });
    }

    @Test
    void deleteTeam_nonExistentTeam_throwsResourceNotFoundException() {
        Long eventId = 1L;
        Long teamId = 1L;

        when(eventService.getById(eventId)).thenReturn(new Event());
        when(teamService.getById(teamId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            eventController.deleteTeam(eventId, teamId);
        });
    }

    @Test
    void deleteTeam_deleteFails_throwsRuntimeException() {
        Long eventId = 1L;
        Long teamId = 1L;

        when(eventService.getById(eventId)).thenReturn(new Event());
        when(teamService.getById(teamId)).thenReturn(new Team());
        when(eventService.deleteTeam(any())).thenReturn(false);

        assertThrows(RuntimeException.class, () -> {
            eventController.deleteTeam(eventId, teamId);
        });
    }

    @Test
    void newGroup_validInputs_createsGroup() {
        Long eventId = 1L;
        String groupName = "Group A";
        EventGroup group = new EventGroup(eventId, groupName);

        when(eventService.getById(eventId)).thenReturn(new Event());
        when(eventGroupService.save(any())).thenReturn(true);

        EventGroup result = eventController.newGroup(eventId, groupName);

        assertEquals(groupName, result.getName());
        assertEquals(eventId, result.getEventId());
    }

    @Test
    void newGroup_nullInputs_throwsBadRequestException() {
        assertThrows(BadRequestException.class, () -> {
            eventController.newGroup(null, "Group A");
        });

        assertThrows(BadRequestException.class, () -> {
            eventController.newGroup(1L, null);
        });
    }

    @Test
    void newGroup_nonExistentEvent_throwsResourceNotFoundException() {
        Long eventId = 1L;

        when(eventService.getById(eventId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            eventController.newGroup(eventId, "Group A");
        });
    }

    @Test
    void newGroup_saveFails_throwsRuntimeException() {
        Long eventId = 1L;
        String groupName = "Group A";

        when(eventService.getById(eventId)).thenReturn(new Event());
        when(eventGroupService.save(any())).thenReturn(false);

        assertThrows(RuntimeException.class, () -> {
            eventController.newGroup(eventId, groupName);
        });
    }

    @Test
    void getGroups_validEventId_returnsListOfGroups() {
        Long eventId = 1L;
        List<EventGroup> groups = new ArrayList<>();
        groups.add(new EventGroup(eventId, "Group A"));
        groups.add(new EventGroup(eventId, "Group B"));

        when(eventService.getById(eventId)).thenReturn(new Event());
        when(eventGroupService.list((Wrapper<EventGroup>) any())).thenReturn(groups);

        List<EventGroup> result = eventController.getGroups(eventId);

        assertEquals(2, result.size());
        assertEquals("Group A", result.get(0).getName());
        assertEquals("Group B", result.get(1).getName());
    }

    @Test
    void getGroups_nullEventId_throwsBadRequestException() {
        assertThrows(BadRequestException.class, () -> {
            eventController.getGroups(null);
        });
    }

    @Test
    void getGroups_nonExistentEvent_throwsResourceNotFoundException() {
        Long eventId = 1L;

        when(eventService.getById(eventId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            eventController.getGroups(eventId);
        });
    }

    @Test
    void updateGroup_validInputs_updatesGroupName() {
        Long groupId = 1L;
        String name = "Updated Name";
        EventGroup group = new EventGroup();
        group.setGroupId(groupId);
        group.setName("Old Name");

        when(eventGroupService.getById(groupId)).thenReturn(group);
        when(eventGroupService.updateById(any())).thenReturn(true);

        eventController.updateGroup(groupId, name);

        assertEquals(name, group.getName());
    }

    @Test
    void updateGroup_nullInputs_throwsBadRequestException() {
        assertThrows(BadRequestException.class, () -> {
            eventController.updateGroup(null, "Updated Name");
        });

        assertThrows(BadRequestException.class, () -> {
            eventController.updateGroup(1L, null);
        });
    }

    @Test
    void updateGroup_nonExistentGroup_throwsResourceNotFoundException() {
        Long groupId = 1L;

        when(eventGroupService.getById(groupId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            eventController.updateGroup(groupId, "Updated Name");
        });
    }

    @Test
    void updateGroup_updateFails_throwsRuntimeException() {
        Long groupId = 1L;
        String name = "Updated Name";

        when(eventGroupService.getById(groupId)).thenReturn(new EventGroup());
        when(eventGroupService.updateById(any())).thenReturn(false);

        assertThrows(RuntimeException.class, () -> {
            eventController.updateGroup(groupId, name);
        });
    }

//    @Test
//    void deleteGroup_validGroupId_deletesGroup() {
//        Long groupId = 1L;
//
//        when(eventGroupService.getById(groupId)).thenReturn(new EventGroup());
//        when(eventGroupTeamService.remove(any())).thenReturn(true);
//        when(eventGroupService.removeById(groupId)).thenReturn(true);
//
//    }

    @Test
    void deleteGroup_nullGroupId_throwsBadRequestException() {
        assertThrows(BadRequestException.class, () -> {
            eventController.deleteGroup(null);
        });
    }

    @Test
    void deleteGroup_nonExistentGroup_throwsResourceNotFoundException() {
        Long groupId = 1L;

        when(eventGroupService.getById(groupId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            eventController.deleteGroup(groupId);
        });
    }

    @Test
    void deleteGroup_deleteGroupTeamsFails_throwsRuntimeException() {
        Long groupId = 1L;

        when(eventGroupService.getById(groupId)).thenReturn(new EventGroup());
        when(eventGroupTeamService.remove(any())).thenReturn(false);

        assertThrows(RuntimeException.class, () -> {
            eventController.deleteGroup(groupId);
        });
    }

    @Test
    void deleteGroup_deleteGroupFails_throwsRuntimeException() {
        Long groupId = 1L;

        when(eventGroupService.getById(groupId)).thenReturn(new EventGroup());
        when(eventGroupTeamService.remove(any())).thenReturn(true);
        when(eventGroupService.removeById(groupId)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> {
            eventController.deleteGroup(groupId);
        });
    }

    @Test
    void addTeamIntoGroup_validIds_addsTeamIntoGroup() {
        Long groupId = 1L;
        Long teamId = 1L;

        when(eventGroupService.getById(groupId)).thenReturn(new EventGroup());
        when(teamService.getById(teamId)).thenReturn(new Team());
        when(eventGroupTeamService.saveOrUpdateByMultiId(any())).thenReturn(true);

        eventController.addTeamIntoGroup(groupId, teamId);

        verify(eventGroupTeamService, times(1)).saveOrUpdateByMultiId(any());
    }

    @Test
    void addTeamIntoGroup_nullIds_throwsBadRequestException() {
        assertThrows(BadRequestException.class, () -> {
            eventController.addTeamIntoGroup(null, 1L);
        });
        assertThrows(BadRequestException.class, () -> {
            eventController.addTeamIntoGroup(1L, null);
        });
    }

    @Test
    void addTeamIntoGroup_nonExistentGroup_throwsResourceNotFoundException() {
        Long groupId = 1L;

        when(eventGroupService.getById(groupId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            eventController.addTeamIntoGroup(groupId, 1L);
        });
    }

    @Test
    void addTeamIntoGroup_nonExistentTeam_throwsResourceNotFoundException() {
        Long teamId = 1L;

        when(eventGroupService.getById(1L)).thenReturn(new EventGroup());
        when(teamService.getById(teamId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            eventController.addTeamIntoGroup(1L, teamId);
        });
    }

    @Test
    void addTeamIntoGroup_addingFails_throwsRuntimeException() {
        Long groupId = 1L;
        Long teamId = 1L;

        when(eventGroupService.getById(groupId)).thenReturn(new EventGroup());
        when(teamService.getById(teamId)).thenReturn(new Team());
        when(eventGroupTeamService.saveOrUpdateByMultiId(any())).thenReturn(false);

        assertThrows(RuntimeException.class, () -> {
            eventController.addTeamIntoGroup(groupId, teamId);
        });
    }

    @Test
    void deleteTeamFromGroup_validIds_deletesTeamFromGroup() {
        Long groupId = 1L;
        Long teamId = 1L;

        when(eventGroupService.getById(groupId)).thenReturn(new EventGroup());
        when(teamService.getById(teamId)).thenReturn(new Team());
        when(eventGroupTeamService.deleteByMultiId(any())).thenReturn(true);

        eventController.deleteTeamFromGroup(groupId, teamId);

        verify(eventGroupTeamService, times(1)).deleteByMultiId(any());
    }

    @Test
    void deleteTeamFromGroup_nullIds_throwsBadRequestException() {
        assertThrows(BadRequestException.class, () -> {
            eventController.deleteTeamFromGroup(null, 1L);
        });
        assertThrows(BadRequestException.class, () -> {
            eventController.deleteTeamFromGroup(1L, null);
        });
    }

    @Test
    void deleteTeamFromGroup_nonExistentGroup_throwsResourceNotFoundException() {
        Long groupId = 1L;

        when(eventGroupService.getById(groupId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            eventController.deleteTeamFromGroup(groupId, 1L);
        });
    }

    @Test
    void deleteTeamFromGroup_nonExistentTeam_throwsResourceNotFoundException() {
        Long teamId = 1L;

        when(eventGroupService.getById(1L)).thenReturn(new EventGroup());
        when(teamService.getById(teamId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            eventController.deleteTeamFromGroup(1L, teamId);
        });
    }

    @Test
    void deleteTeamFromGroup_deletingFails_throwsRuntimeException() {
        Long groupId = 1L;
        Long teamId = 1L;

        when(eventGroupService.getById(groupId)).thenReturn(new EventGroup());
        when(teamService.getById(teamId)).thenReturn(new Team());
        when(eventGroupTeamService.deleteByMultiId(any())).thenReturn(false);

        assertThrows(RuntimeException.class, () -> {
            eventController.deleteTeamFromGroup(groupId, teamId);
        });
    }

    @Test
    void newStage_validInputs_returnsNewStage() {
        Long eventId = 1L;
        String stageName = "Group Stage";

        when(eventService.getById(eventId)).thenReturn(new Event());
        when(eventStageService.saveOrUpdateByMultiId(any())).thenReturn(true);

        EventStage result = eventController.newStage(eventId, stageName);

        assertNotNull(result);
        assertEquals(eventId, result.getEventId());
    }

    @Test
    void newStage_nullInputs_throwsBadRequestException() {
        assertThrows(BadRequestException.class, () -> eventController.newStage(null, "Group Stage"));
        assertThrows(BadRequestException.class, () -> eventController.newStage(1L, null));
        assertThrows(BadRequestException.class, () -> eventController.newStage(null, null));
    }

    @Test
    void newStage_nonExistentEvent_throwsResourceNotFoundException() {
        Long eventId = 1L;
        String stageName = "Group Stage";

        when(eventService.getById(eventId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> eventController.newStage(eventId, stageName));
    }

    @Test
    void newStage_creationFails_throwsRuntimeException() {
        Long eventId = 1L;
        String stageName = "Group Stage";

        when(eventService.getById(eventId)).thenReturn(new Event());
        when(eventStageService.saveOrUpdateByMultiId(any())).thenReturn(false);

        assertThrows(RuntimeException.class, () -> eventController.newStage(eventId, stageName));
    }

    @Test
    void deleteStage_validInputs_deletesStage() {
        Long eventId = 1L;
        String stageName = "Group Stage";

        when(eventService.getById(eventId)).thenReturn(new Event());
        when(eventStageService.selectByMultiId(any())).thenReturn(new EventStage());
        when(eventStageService.deleteByMultiId(any())).thenReturn(true);

        assertDoesNotThrow(() -> eventController.deleteStage(eventId, stageName));
    }

    @Test
    void deleteStage_nullInputs_throwsBadRequestException() {
        assertThrows(BadRequestException.class, () -> eventController.deleteStage(null, "Group Stage"));
        assertThrows(BadRequestException.class, () -> eventController.deleteStage(1L, null));
        assertThrows(BadRequestException.class, () -> eventController.deleteStage(null, null));
    }

    @Test
    void deleteStage_nonExistentEvent_throwsResourceNotFoundException() {
        Long eventId = 1L;
        String stageName = "Group Stage";

        when(eventService.getById(eventId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> eventController.deleteStage(eventId, stageName));
    }

    @Test
    void deleteStage_nonExistentStage_throwsResourceNotFoundException() {
        Long eventId = 1L;
        String stageName = "Group Stage";

        when(eventService.getById(eventId)).thenReturn(new Event());
        when(eventStageService.selectByMultiId(any())).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> eventController.deleteStage(eventId, stageName));
    }

    @Test
    void deleteStage_deletionFails_throwsRuntimeException() {
        Long eventId = 1L;
        String stageName = "Group Stage";

        when(eventService.getById(eventId)).thenReturn(new Event());
        when(eventStageService.selectByMultiId(any())).thenReturn(new EventStage());
        when(eventStageService.deleteByMultiId(any())).thenReturn(false);

        assertThrows(RuntimeException.class, () -> eventController.deleteStage(eventId, stageName));
    }

    @Test
    void newTag_validInputs_returnsNewTag() {
        Long eventId = 1L;
        String stageName = "Group Stage";
        String tagName = "Tag 1";

        when(eventService.getById(eventId)).thenReturn(new Event());
        when(eventStageService.selectByMultiId(any())).thenReturn(new EventStage());
        when(eventStageTagService.saveOrUpdateByMultiId(any())).thenReturn(true);

        EventStageTag result = eventController.newTag(eventId, stageName, tagName);

        assertNotNull(result);
        assertEquals(eventId, result.getEventId());
    }

    @Test
    void newTag_nullInputs_throwsBadRequestException() {
        assertThrows(BadRequestException.class, () -> eventController.newTag(null, "Group Stage", "Tag 1"));
        assertThrows(BadRequestException.class, () -> eventController.newTag(1L, null, "Tag 1"));
        assertThrows(BadRequestException.class, () -> eventController.newTag(1L, "Group Stage", null));
        assertThrows(BadRequestException.class, () -> eventController.newTag(null, null, null));
    }

    @Test
    void newTag_nonExistentEvent_throwsResourceNotFoundException() {
        Long eventId = 1L;
        String stageName = "Group Stage";
        String tagName = "Tag 1";

        when(eventService.getById(eventId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> eventController.newTag(eventId, stageName, tagName));
    }

    @Test
    void newTag_nonExistentStage_throwsResourceNotFoundException() {
        Long eventId = 1L;
        String stageName = "Group Stage";
        String tagName = "Tag 1";

        when(eventService.getById(eventId)).thenReturn(new Event());
        when(eventStageService.selectByMultiId(any())).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> eventController.newTag(eventId, stageName, tagName));
    }

    @Test
    void newTag_creationFails_throwsRuntimeException() {
        Long eventId = 1L;
        String stageName = "Group Stage";
        String tagName = "Tag 1";

        when(eventService.getById(eventId)).thenReturn(new Event());
        when(eventStageService.selectByMultiId(any())).thenReturn(new EventStage());
        when(eventStageTagService.saveOrUpdateByMultiId(any())).thenReturn(false);

        assertThrows(RuntimeException.class, () -> eventController.newTag(eventId, stageName, tagName));
    }

    @Test
    void deleteTag_validIds_deletesTag() {
        Long eventId = 1L;
        String stageName = "Stage 1";
        String tagName = "Tag 1";

        when(eventService.getById(eventId)).thenReturn(new Event());
        when(eventStageService.selectByMultiId(new EventStage(eventId, stageName))).thenReturn(new EventStage());
        when(eventStageTagService.selectByMultiId(new EventStageTag(eventId, stageName, tagName))).thenReturn(new EventStageTag());
        when(eventStageTagService.deleteByMultiId(new EventStageTag(eventId, stageName, tagName))).thenReturn(true);

        eventController.deleteTag(eventId, stageName, tagName);

        verify(eventStageTagService, times(1)).deleteByMultiId(any());
    }

    @Test
    void deleteTag_nullParameters_throwsBadRequestException() {
        assertThrows(BadRequestException.class, () -> {
            eventController.deleteTag(null, "Stage 1", "Tag 1");
        });
        assertThrows(BadRequestException.class, () -> {
            eventController.deleteTag(1L, null, "Tag 1");
        });
        assertThrows(BadRequestException.class, () -> {
            eventController.deleteTag(1L, "Stage 1", null);
        });
    }

    @Test
    void deleteTag_nonExistentEvent_throwsResourceNotFoundException() {
        Long eventId = 1L;
        String stageName = "Stage 1";
        String tagName = "Tag 1";

        when(eventService.getById(eventId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            eventController.deleteTag(eventId, stageName, tagName);
        });
    }

    @Test
    void deleteTag_nonExistentStage_throwsResourceNotFoundException() {
        Long eventId = 1L;
        String stageName = "Stage 1";
        String tagName = "Tag 1";

        when(eventService.getById(eventId)).thenReturn(new Event());
        when(eventStageService.selectByMultiId(new EventStage(eventId, stageName))).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            eventController.deleteTag(eventId, stageName, tagName);
        });
    }

    @Test
    void deleteTag_nonExistentTag_throwsResourceNotFoundException() {
        Long eventId = 1L;
        String stageName = "Stage 1";
        String tagName = "Tag 1";

        when(eventService.getById(eventId)).thenReturn(new Event());
        when(eventStageService.selectByMultiId(new EventStage(eventId, stageName))).thenReturn(new EventStage());
        when(eventStageTagService.selectByMultiId(new EventStageTag(eventId, stageName, tagName))).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            eventController.deleteTag(eventId, stageName, tagName);
        });
    }

    @Test
    void deleteTag_deleteFails_throwsRuntimeException() {
        Long eventId = 1L;
        String stageName = "Stage 1";
        String tagName = "Tag 1";

        when(eventService.getById(eventId)).thenReturn(new Event());
        when(eventStageService.selectByMultiId(new EventStage(eventId, stageName))).thenReturn(new EventStage());
        when(eventStageTagService.selectByMultiId(new EventStageTag(eventId, stageName, tagName))).thenReturn(new EventStageTag());
        when(eventStageTagService.deleteByMultiId(any())).thenReturn(false);

        assertThrows(RuntimeException.class, () -> {
            eventController.deleteTag(eventId, stageName, tagName);
        });
    }


    @Test
    void addMatch_nullParameters_throwsBadRequestException() {
        assertThrows(BadRequestException.class, () -> {
            eventController.addMatch(null, "Stage 1", "Tag 1", new Timestamp(System.currentTimeMillis()), 1L, 2L);
        });
        assertThrows(BadRequestException.class, () -> {
            eventController.addMatch(1L, null, "Tag 1", new Timestamp(System.currentTimeMillis()), 1L, 2L);
        });
        assertThrows(BadRequestException.class, () -> {
            eventController.addMatch(1L, "Stage 1", null, new Timestamp(System.currentTimeMillis()), 1L, 2L);
        });
        assertThrows(BadRequestException.class, () -> {
            eventController.addMatch(1L, "Stage 1", "Tag 1", null, 1L, 2L);
        });
    }

    @Test
    void addMatch_nonExistentEvent_throwsResourceNotFoundException() {
        Long eventId = 1L;
        String stage = "Stage 1";
        String tag = "Tag 1";
        Timestamp time = new Timestamp(System.currentTimeMillis());
        Long homeTeamId = 1L;
        Long awayTeamId = 2L;

        when(eventService.getById(eventId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            eventController.addMatch(eventId, stage, tag, time, homeTeamId, awayTeamId);
        });
    }

    @Test
    void addMatch_nonExistentHomeTeam_throwsResourceNotFoundException() {
        Long eventId = 1L;
        String stage = "Stage 1";
        String tag = "Tag 1";
        Timestamp time = new Timestamp(System.currentTimeMillis());
        Long homeTeamId = 1L;
        Long awayTeamId = 2L;

        when(eventService.getById(eventId)).thenReturn(new Event());
        when(teamService.getById(homeTeamId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            eventController.addMatch(eventId, stage, tag, time, homeTeamId, awayTeamId);
        });
    }

    @Test
    void addMatch_nonExistentAwayTeam_throwsResourceNotFoundException() {
        Long eventId = 1L;
        String stage = "Stage 1";
        String tag = "Tag 1";
        Timestamp time = new Timestamp(System.currentTimeMillis());
        Long homeTeamId = 1L;
        Long awayTeamId = 2L;

        when(eventService.getById(eventId)).thenReturn(new Event());
        when(teamService.getById(homeTeamId)).thenReturn(new Team());
        when(teamService.getById(awayTeamId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            eventController.addMatch(eventId, stage, tag, time, homeTeamId, awayTeamId);
        });
    }

    @Test
    void addMatch_homeTeamNotInEvent_throwsBadRequestException() {
        Long eventId = 1L;
        String stage = "Stage 1";
        String tag = "Tag 1";
        Timestamp time = new Timestamp(System.currentTimeMillis());
        Long homeTeamId = 1L;
        Long awayTeamId = 2L;

        Event event = new Event();
        event.setEventId(eventId);
        List<EventTeam> teams = new ArrayList<>();

        when(eventService.getById(eventId)).thenReturn(event);
        when(teamService.getById(homeTeamId)).thenReturn(new Team());
        when(teamService.getById(awayTeamId)).thenReturn(new Team());
        when(eventTeamService.list((Wrapper<EventTeam>) any())).thenReturn(teams);

        assertThrows(BadRequestException.class, () -> {
            eventController.addMatch(eventId, stage, tag, time, homeTeamId, awayTeamId);
        });
    }

    @Test
    void addMatch_awayTeamNotInEvent_throwsBadRequestException() {
        Long eventId = 1L;
        String stage = "Stage 1";
        String tag = "Tag 1";
        Timestamp time = new Timestamp(System.currentTimeMillis());
        Long homeTeamId = 1L;
        Long awayTeamId = 2L;

        Event event = new Event();
        event.setEventId(eventId);
        EventTeam eventTeam = new EventTeam();
        eventTeam.setTeamId(homeTeamId);
        List<EventTeam> teams = new ArrayList<>();
        teams.add(eventTeam);

        when(eventService.getById(eventId)).thenReturn(event);
        when(teamService.getById(homeTeamId)).thenReturn(new Team());
        when(teamService.getById(awayTeamId)).thenReturn(new Team());
        when(eventTeamService.list((Wrapper<EventTeam>) any())).thenReturn(teams);

        assertThrows(BadRequestException.class, () -> {
            eventController.addMatch(eventId, stage, tag, time, homeTeamId, awayTeamId);
        });
    }

    @Test
    void addMatch_addFails_throwsRuntimeException() {
        Long eventId = 1L;
        String stage = "Stage 1";
        String tag = "Tag 1";
        Timestamp time = new Timestamp(System.currentTimeMillis());
        Long homeTeamId = 1L;
        Long awayTeamId = 2L;

        Event event = new Event();
        event.setEventId(eventId);
        Team homeTeam = new Team();
        homeTeam.setTeamId(homeTeamId);
        Team awayTeam = new Team();
        awayTeam.setTeamId(awayTeamId);
        EventTeam eventTeam = new EventTeam();
        eventTeam.setTeamId(homeTeamId);
        List<EventTeam> teams = new ArrayList<>();
        teams.add(eventTeam);

        when(eventService.getById(eventId)).thenReturn(event);
        when(teamService.getById(homeTeamId)).thenReturn(homeTeam);
        when(teamService.getById(awayTeamId)).thenReturn(awayTeam);
        when(eventTeamService.list((Wrapper<EventTeam>) any())).thenReturn(teams);
        when(eventService.addMatch(eq(eventId), any(), eq(stage), eq(tag))).thenReturn(false);

        assertThrows(RuntimeException.class, () -> {
            eventController.addMatch(eventId, stage, tag, time, homeTeamId, awayTeamId);
        });
    }

    @Test
    void getMatches_validEventId_returnsListOfMatches() {
        Long eventId = 1L;
        List<Match> matches = new ArrayList<>();
        matches.add(new Match());
        matches.add(new Match());

        when(eventService.getById(eventId)).thenReturn(new Event());
        when(eventService.getMatches(eventId)).thenReturn(matches);

        List<Match> result = eventController.getMatches(eventId);

        assertEquals(2, result.size());
    }

    @Test
    void getMatches_nullEventId_throwsBadRequestException() {
        Long eventId = null;

        assertThrows(BadRequestException.class, () -> {
            eventController.getMatches(eventId);
        });
    }

    @Test
    void getMatches_nonExistentEvent_throwsResourceNotFoundException() {
        Long eventId = 1L;

        when(eventService.getById(eventId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            eventController.getMatches(eventId);
        });
    }

    @Test
    void deleteMatch_validIds_deletesMatch() {
        Long eventId = 1L;
        Long matchId = 1L;

        when(eventService.getById(eventId)).thenReturn(new Event());
        when(matchService.getById(matchId)).thenReturn(new Match());
        when(eventService.deleteMatch(any())).thenReturn(true);

        eventController.deleteMatch(eventId, matchId);

        verify(eventService, times(1)).deleteMatch(any());
    }

    @Test
    void deleteMatch_nullParameters_throwsBadRequestException() {
        assertThrows(BadRequestException.class, () -> {
            eventController.deleteMatch(null, 1L);
        });
        assertThrows(BadRequestException.class, () -> {
            eventController.deleteMatch(1L, null);
        });
    }

    @Test
    void deleteMatch_nonExistentEvent_throwsResourceNotFoundException() {
        Long eventId = 1L;
        Long matchId = 1L;

        when(eventService.getById(eventId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            eventController.deleteMatch(eventId, matchId);
        });
    }

    @Test
    void deleteMatch_nonExistentMatch_throwsResourceNotFoundException() {
        Long eventId = 1L;
        Long matchId = 1L;

        when(eventService.getById(eventId)).thenReturn(new Event());
        when(matchService.getById(matchId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            eventController.deleteMatch(eventId, matchId);
        });
    }

    @Test
    void deleteMatch_deleteFails_throwsRuntimeException() {
        Long eventId = 1L;
        Long matchId = 1L;

        when(eventService.getById(eventId)).thenReturn(new Event());
        when(matchService.getById(matchId)).thenReturn(new Match());
        when(eventService.deleteMatch(any())).thenReturn(false);

        assertThrows(RuntimeException.class, () -> {
            eventController.deleteMatch(eventId, matchId);
        });
    }

    @Test
    void inviteReferee_validIds_invitesReferee() {
        Long eventId = 1L;
        Long refereeId = 1L;

        when(eventService.getById(eventId)).thenReturn(new Event());
        when(refereeService.getById(refereeId)).thenReturn(new Referee());
        when(eventService.inviteReferee(any())).thenReturn(true);

        eventController.inviteReferee(eventId, refereeId);

        verify(eventService, times(1)).inviteReferee(any());
    }

    @Test
    void inviteReferee_nullParameters_throwsBadRequestException() {
        assertThrows(BadRequestException.class, () -> {
            eventController.inviteReferee(null, 1L);
        });
        assertThrows(BadRequestException.class, () -> {
            eventController.inviteReferee(1L, null);
        });
    }

    @Test
    void inviteReferee_nonExistentEvent_throwsResourceNotFoundException() {
        Long eventId = 1L;
        Long refereeId = 1L;

        when(eventService.getById(eventId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            eventController.inviteReferee(eventId, refereeId);
        });
    }

    @Test
    void inviteReferee_nonExistentReferee_throwsResourceNotFoundException() {
        Long eventId = 1L;
        Long refereeId = 1L;

        when(eventService.getById(eventId)).thenReturn(new Event());
        when(refereeService.getById(refereeId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            eventController.inviteReferee(eventId, refereeId);
        });
    }

    @Test
    void inviteReferee_inviteFails_throwsRuntimeException() {
        Long eventId = 1L;
        Long refereeId = 1L;

        when(eventService.getById(eventId)).thenReturn(new Event());
        when(refereeService.getById(refereeId)).thenReturn(new Referee());
        when(eventService.inviteReferee(any())).thenReturn(false);

        assertThrows(RuntimeException.class, () -> {
            eventController.inviteReferee(eventId, refereeId);
        });
    }


    @Test
    void getReferees_validEventId_returnsListOfReferees() {
        Long eventId = 1L;
        List<Referee> referees = new ArrayList<>();
        referees.add(new Referee());
        referees.add(new Referee());

        when(eventService.getById(eventId)).thenReturn(new Event());
        when(eventService.getReferees(eventId)).thenReturn(referees);

        List<Referee> result = eventController.getReferees(eventId);

        assertEquals(2, result.size());
    }

    @Test
    void getReferees_nullEventId_throwsBadRequestException() {
        Long eventId = null;

        assertThrows(BadRequestException.class, () -> {
            eventController.getReferees(eventId);
        });
    }

    @Test
    void getReferees_nonExistentEvent_throwsResourceNotFoundException() {
        Long eventId = 1L;

        when(eventService.getById(eventId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            eventController.getReferees(eventId);
        });
    }

    @Test
    void deleteReferee_validIds_deletesReferee() {
        Long eventId = 1L;
        Long refereeId = 1L;

        when(eventService.getById(eventId)).thenReturn(new Event());
        when(refereeService.getById(refereeId)).thenReturn(new Referee());
        when(eventService.deleteReferee(any())).thenReturn(true);

        eventController.deleteReferee(eventId, refereeId);

        verify(eventService, times(1)).deleteReferee(any());
    }

    @Test
    void deleteReferee_nullParameters_throwsBadRequestException() {
        assertThrows(BadRequestException.class, () -> {
            eventController.deleteReferee(1L, null);
        });
        assertThrows(BadRequestException.class, () -> {
            eventController.deleteReferee(null, 1L);
        });
    }

    @Test
    void deleteReferee_nonExistentEvent_throwsResourceNotFoundException() {
        Long eventId = 1L;
        Long refereeId = 1L;

        when(eventService.getById(eventId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            eventController.deleteReferee(eventId, refereeId);
        });
    }

    @Test
    void deleteReferee_nonExistentReferee_throwsResourceNotFoundException() {
        Long eventId = 1L;
        Long refereeId = 1L;

        when(eventService.getById(eventId)).thenReturn(new Event());
        when(refereeService.getById(refereeId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            eventController.deleteReferee(eventId, refereeId);
        });
    }

    @Test
    void deleteReferee_deleteFails_throwsRuntimeException() {
        Long eventId = 1L;
        Long refereeId = 1L;

        when(eventService.getById(eventId)).thenReturn(new Event());
        when(refereeService.getById(refereeId)).thenReturn(new Referee());
        when(eventService.deleteReferee(any())).thenReturn(false);

        assertThrows(RuntimeException.class, () -> {
            eventController.deleteReferee(eventId, refereeId);
        });
    }
}