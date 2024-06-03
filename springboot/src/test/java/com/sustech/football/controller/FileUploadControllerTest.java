package com.sustech.football.controller;

import com.sustech.football.exception.BadRequestException;
import com.sustech.football.exception.InternalServerErrorException;
import com.sustech.football.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * AI-generated-content
 * tool: ChatGPT
 * version: 3.5 turbo
 * usage: I give it the class and method implementation it writes the tests for me.
 */


public class FileUploadControllerTest {

    @InjectMocks
    private FileUploadController fileUploadController;

    @Mock
    private MultipartFile multipartFile;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testUploadFile() {
        // Mocking file
        when(multipartFile.isEmpty()).thenReturn(false);
        when(multipartFile.getOriginalFilename()).thenReturn("test.txt");

        // Call the method
        String result = fileUploadController.uploadFile(multipartFile);

        // Verify the result
        assertNotNull(result);
        assertTrue(result.endsWith(".txt"));
    }

    @Test
    void testUploadFileEmpty() {
        // Mocking file
        when(multipartFile.isEmpty()).thenReturn(true);

        // Call the method and verify BadRequestException
        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            fileUploadController.uploadFile(multipartFile);
        });

        assertEquals("文件为空，请选择一个文件上传。", exception.getMessage());
    }

    @Test
    void testDownloadFileNotFound() throws MalformedURLException {
        // Mocking file name
        String fileName = "nonexistent.txt";

        // Call the method and verify ResourceNotFoundException
        assertThrows(NullPointerException.class, () -> {
            fileUploadController.downloadFile(fileName);
        });
    }
}