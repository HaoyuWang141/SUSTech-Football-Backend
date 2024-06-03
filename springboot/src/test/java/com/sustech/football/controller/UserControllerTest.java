package com.sustech.football.controller;

import com.sustech.football.entity.*;
import com.sustech.football.exception.*;
import com.sustech.football.service.*;
import com.sustech.football.utils.WXBizDataCrypt;

import org.json.JSONObject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * AI-generated-content
 * tool: ChatGPT
 * version: 3.5 turbo
 * usage: I give it the class and method implementation it writes the tests for me.
 */


class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void login_validParameters_returnsResponseEntity() {
        String openid = "test_openid";
        String sessionKey = "test_session_key";
        User user = new User(openid, sessionKey);

        when(userService.getOne(any())).thenReturn(null);
        when(userService.save(any())).thenReturn(true);

        ResponseEntity<User> responseEntity = userController.login(openid, sessionKey);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(user, responseEntity.getBody());
    }

    @Test
    void login_existingUser_returnsResponseEntity() {
        String openid = "test_openid";
        String sessionKey = "test_session_key";
        User existingUser = new User(openid, sessionKey);

        when(userService.getOne(any())).thenReturn(existingUser);
        when(userService.update(any())).thenReturn(true);

        ResponseEntity<User> responseEntity = userController.login(openid, sessionKey);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(existingUser, responseEntity.getBody());
    }

    @Test
    void login_nullParameters_throwsBadRequestException() {
        assertThrows(BadRequestException.class, () -> {
            userController.login(null, "session_key");
        });
        assertThrows(BadRequestException.class, () -> {
            userController.login("openid", null);
        });
    }

    @Test
    void login_saveFails_throwsInternalServerErrorException() {
        String openid = "test_openid";
        String sessionKey = "test_session_key";

        when(userService.getOne(any())).thenReturn(null);
        when(userService.save(any())).thenReturn(false);

        assertThrows(InternalServerErrorException.class, () -> {
            userController.login(openid, sessionKey);
        });
    }

    @Test
    void login_updateFails_throwsInternalServerErrorException() {
        String openid = "test_openid";
        String sessionKey = "test_session_key";

        when(userService.getOne(any())).thenReturn(new User(openid, sessionKey));
        when(userService.update(any())).thenReturn(false);

        assertThrows(InternalServerErrorException.class, () -> {
            userController.login(openid, sessionKey);
        });
    }

    @Test
    void getUser_validUserId_returnsUser() {
        Long userId = 1L;
        User user = new User("test_openid", "test_session_key");

        when(userService.getById(userId)).thenReturn(user);

        User result = userController.getUser(userId);

        assertEquals(user, result);
    }

    @Test
    void getUser_nullUserId_throwsBadRequestException() {
        assertThrows(BadRequestException.class, () -> {
            userController.getUser(null);
        });
    }

    @Test
    void getUser_nonExistentUser_throwsResourceNotFoundException() {
        Long userId = 1L;

        when(userService.getById(userId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            userController.getUser(userId);
        });
    }

    @Test
    void update_validParameters_updatesUser() {
        Long userId = 1L;
        String avatarUrl = "newAvatarUrl";
        String nickName = "newNickName";
        User user = new User();
        user.setUserId(userId);
        user.setAvatarUrl("oldAvatarUrl");
        user.setNickName("oldNickName");

        when(userService.getById(userId)).thenReturn(user);
        when(userService.updateById(user)).thenReturn(true);

        assertDoesNotThrow(() -> userController.update(userId, avatarUrl, nickName));

        assertEquals(avatarUrl, user.getAvatarUrl());
        assertEquals(nickName, user.getNickName());
    }

    @Test
    void update_nullParameters_throwsBadRequestException() {
        assertThrows(BadRequestException.class, () -> userController.update(null, null, null));
    }

    @Test
    void update_nonExistentUser_throwsResourceNotFoundException() {
        Long userId = 1L;
        when(userService.getById(userId)).thenReturn(null);
        assertThrows(ResourceNotFoundException.class, () -> userController.update(userId, "avatarUrl", "nickName"));
    }

    @Test
    void update_updateFails_throwsInternalServerErrorException() {
        Long userId = 1L;
        User user = new User();
        user.setUserId(userId);
        user.setAvatarUrl("avatarUrl");
        user.setNickName("nickName");

        when(userService.getById(userId)).thenReturn(user);
        when(userService.updateById(user)).thenReturn(false);

        assertThrows(InternalServerErrorException.class, () -> userController.update(userId, "newAvatarUrl", "newNickName"));
    }

    @Test
    void getData_invalidUserId_throwsUnauthorizedAccessException() {
        String encryptedData = "encryptedData";
        String iv = "iv";
        String userId = "1";

        when(userService.getById(Long.parseLong(userId))).thenReturn(null);

        assertThrows(UnauthorizedAccessException.class, () -> userController.getData(encryptedData, iv, userId));
    }

    @Test
    void getUserManageMatch_validUserId_returnsListOfMatches() {
        Long userId = 1L;
        List<Match> matches = new ArrayList<>();
        matches.add(new Match());
        matches.add(new Match());

        when(userService.getUserManageMatches(userId)).thenReturn(matches);

        List<Match> result = userController.getUserManageMatch(userId);

        assertEquals(matches.size(), result.size());
    }

    @Test
    void getUserManageMatch_nullUserId_throwsBadRequestException() {
        assertThrows(BadRequestException.class, () -> userController.getUserManageMatch(null));
    }

    @Test
    void getUserManageEvent_validUserId_returnsListOfEvents() {
        Long userId = 1L;
        List<Event> events = new ArrayList<>();
        events.add(new Event());
        events.add(new Event());

        when(userService.getUserManageEvents(userId)).thenReturn(events);

        List<Event> result = userController.getUserManageEvent(userId);

        assertEquals(2, result.size());
    }

    @Test
    void getUserManageEvent_nullUserId_throwsBadRequestException() {
        Long userId = null;

        assertThrows(BadRequestException.class, () -> {
            userController.getUserManageEvent(userId);
        });
    }

    @Test
    void getUserManageTeam_validUserId_returnsListOfTeams() {
        Long userId = 1L;
        List<Team> teams = new ArrayList<>();
        teams.add(new Team());
        teams.add(new Team());

        when(userService.getUserManageTeams(userId)).thenReturn(teams);

        List<Team> result = userController.getUserManageTeam(userId);

        assertEquals(2, result.size());
    }

    @Test
    void getUserManageTeam_nullUserId_throwsBadRequestException() {
        Long userId = null;

        assertThrows(BadRequestException.class, () -> {
            userController.getUserManageTeam(userId);
        });
    }

    @Test
    void getPlayerId_validUserId_returnsPlayerId() {
        Long userId = 1L;
        when(userService.getById(userId)).thenReturn(new User());
        when(userService.getPlayerId(userId)).thenReturn(10L);

        Long result = userController.getPlayerId(userId);

        assertEquals(10L, result);
    }

    @Test
    void getPlayerId_nullUserId_throwsBadRequestException() {
        assertThrows(BadRequestException.class, () -> {
            userController.getPlayerId(null);
        });
    }

    @Test
    void getPlayerId_nonExistentUser_throwsResourceNotFoundException() {
        Long userId = 1L;
        when(userService.getById(userId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            userController.getPlayerId(userId);
        });
    }

    @Test
    void getCoachId_validUserId_returnsCoachId() {
        Long userId = 1L;
        when(userService.getById(userId)).thenReturn(new User());
        when(userService.getCoachId(userId)).thenReturn(20L);

        Long result = userController.getCoachId(userId);

        assertEquals(20L, result);
    }

    @Test
    void getCoachId_nullUserId_throwsBadRequestException() {
        assertThrows(BadRequestException.class, () -> {
            userController.getCoachId(null);
        });
    }

    @Test
    void getCoachId_nonExistentUser_throwsResourceNotFoundException() {
        Long userId = 1L;
        when(userService.getById(userId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            userController.getCoachId(userId);
        });
    }

    @Test
    void getRefereeId_validUserId_returnsRefereeId() {
        Long userId = 1L;
        when(userService.getById(userId)).thenReturn(new User());
        when(userService.getRefereeId(userId)).thenReturn(30L);

        Long result = userController.getRefereeId(userId);

        assertEquals(30L, result);
    }

    @Test
    void getRefereeId_nullUserId_throwsBadRequestException() {
        assertThrows(BadRequestException.class, () -> {
            userController.getRefereeId(null);
        });
    }

    @Test
    void getRefereeId_nonExistentUser_throwsResourceNotFoundException() {
        Long userId = 1L;
        when(userService.getById(userId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            userController.getRefereeId(userId);
        });
    }

    @Test
    void getRoleUserById_validUserId_returnsUserRole() {
        Long userId = 1L;
        UserRole expectedUserRole = new UserRole();
        when(userService.getRoleUserById(userId)).thenReturn(expectedUserRole);

        UserRole result = userController.getRoleUserById(userId);

        assertEquals(expectedUserRole, result);
    }

    @Test
    void getRoleUserById_nullUserId_throwsBadRequestException() {
        assertThrows(BadRequestException.class, () -> {
            userController.getRoleUserById(null);
        });
    }
}