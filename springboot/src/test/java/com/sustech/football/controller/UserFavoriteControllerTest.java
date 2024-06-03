package com.sustech.football.controller;

import com.sustech.football.entity.*;
import com.sustech.football.exception.*;
import com.sustech.football.service.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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


class UserFavoriteControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private UserFavoriteService userFavoriteService;

    @InjectMocks
    private UserFavoriteController userFavoriteController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void favorite_validParams_callsCorrectServiceMethod() {
        Long userId = 1L;
        String type = "team";
        Long id = 1L;

        when(userService.getById(userId)).thenReturn(new User());
        userFavoriteController.favorite(userId, type, id);

        verify(userFavoriteService, times(1)).favoriteTeam(userId, id);
    }

    @Test
    void favorite_nullParams_throwsBadRequestException() {
        assertThrows(BadRequestException.class, () -> {
            userFavoriteController.favorite(null, "team", 1L);
        });

        assertThrows(BadRequestException.class, () -> {
            userFavoriteController.favorite(1L, null, 1L);
        });

        assertThrows(BadRequestException.class, () -> {
            userFavoriteController.favorite(1L, "team", null);
        });
    }

    @Test
    void favorite_invalidUser_throwsBadRequestException() {
        Long userId = 1L;
        String type = "team";
        Long id = 1L;

        when(userService.getById(userId)).thenReturn(null);

        assertThrows(BadRequestException.class, () -> {
            userFavoriteController.favorite(userId, type, id);
        });
    }

    @Test
    void favorite_invalidType_throwsBadRequestException() {
        Long userId = 1L;
        String type = "invalidType";
        Long id = 1L;

        when(userService.getById(userId)).thenReturn(new User());

        assertThrows(BadRequestException.class, () -> {
            userFavoriteController.favorite(userId, type, id);
        });
    }

    @Test
    void unfavorite_validParams_callsCorrectServiceMethod() {
        Long userId = 1L;
        String type = "team";
        Long id = 1L;

        when(userService.getById(userId)).thenReturn(new User());
        userFavoriteController.unfavorite(userId, type, id);

        verify(userFavoriteService, times(1)).unfavoriteTeam(userId, id);
    }

    @Test
    void unfavorite_nullParams_throwsBadRequestException() {
        assertThrows(BadRequestException.class, () -> {
            userFavoriteController.unfavorite(null, "team", 1L);
        });

        assertThrows(BadRequestException.class, () -> {
            userFavoriteController.unfavorite(1L, null, 1L);
        });

        assertThrows(BadRequestException.class, () -> {
            userFavoriteController.unfavorite(1L, "team", null);
        });
    }

    @Test
    void unfavorite_invalidUser_throwsBadRequestException() {
        Long userId = 1L;
        String type = "team";
        Long id = 1L;

        when(userService.getById(userId)).thenReturn(null);

        assertThrows(BadRequestException.class, () -> {
            userFavoriteController.unfavorite(userId, type, id);
        });
    }

    @Test
    void unfavorite_invalidType_throwsBadRequestException() {
        Long userId = 1L;
        String type = "invalidType";
        Long id = 1L;

        when(userService.getById(userId)).thenReturn(new User());

        assertThrows(BadRequestException.class, () -> {
            userFavoriteController.unfavorite(userId, type, id);
        });
    }

    @Test
    void getFavorite_validParams_returnsListOfFavorite() {
        Long userId = 1L;
        String type = "team";

        when(userService.getById(userId)).thenReturn(new User());
        when(userFavoriteService.getFavoriteTeams(userId)).thenReturn(new ArrayList<>());

        List<?> result = userFavoriteController.getFavorite(userId, type);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getFavorite_nullUserId_throwsBadRequestException() {
        String type = "team";

        assertThrows(BadRequestException.class, () -> {
            userFavoriteController.getFavorite(null, type);
        });
    }

    @Test
    void getFavorite_nullType_throwsBadRequestException() {
        Long userId = 1L;

        assertThrows(BadRequestException.class, () -> {
            userFavoriteController.getFavorite(userId, null);
        });
    }

    @Test
    void getFavorite_userNotFound_throwsBadRequestException() {
        Long userId = 1L;
        String type = "team";

        when(userService.getById(userId)).thenReturn(null);

        assertThrows(BadRequestException.class, () -> {
            userFavoriteController.getFavorite(userId, type);
        });
    }

    @Test
    void isFavorite_validParams_returnsBoolean() {
        Long userId = 1L;
        String type = "team";
        Long id = 1L;

        when(userService.getById(userId)).thenReturn(new User());
        when(userFavoriteService.isFavoriteTeam(userId, id)).thenReturn(true);

        boolean result = userFavoriteController.isFavorite(userId, type, id);

        assertTrue(result);
    }

    @Test
    void isFavorite_nullUserId_throwsBadRequestException() {
        String type = "team";
        Long id = 1L;

        assertThrows(BadRequestException.class, () -> {
            userFavoriteController.isFavorite(null, type, id);
        });
    }

    @Test
    void isFavorite_nullType_throwsBadRequestException() {
        Long userId = 1L;
        Long id = 1L;

        assertThrows(BadRequestException.class, () -> {
            userFavoriteController.isFavorite(userId, null, id);
        });
    }

    @Test
    void isFavorite_nullId_throwsBadRequestException() {
        Long userId = 1L;
        String type = "team";

        assertThrows(BadRequestException.class, () -> {
            userFavoriteController.isFavorite(userId, type, null);
        });
    }

    @Test
    void isFavorite_userNotFound_throwsBadRequestException() {
        Long userId = 1L;
        String type = "team";
        Long id = 1L;

        when(userService.getById(userId)).thenReturn(null);

        assertThrows(BadRequestException.class, () -> {
            userFavoriteController.isFavorite(userId, type, id);
        });
    }
}