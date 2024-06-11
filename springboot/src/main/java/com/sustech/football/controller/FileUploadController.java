package com.sustech.football.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sustech.football.entity.FileHash;
import com.sustech.football.exception.BadRequestException;
import com.sustech.football.exception.InternalServerErrorException;
import com.sustech.football.exception.ResourceNotFoundException;

import com.sustech.football.service.FileHashService;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

@RestController
@Tag(name = "File Upload Controller", description = "文件上传下载接口")
public class FileUploadController {
    private FileHashService fileHashService;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @PostMapping("/upload")
    @Operation(summary = "上传文件", description = "上传文件到服务器")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            throw new BadRequestException("文件为空，请选择一个文件上传");
        }

        try {
            // 保存文件到服务器
            String originalFileName = file.getOriginalFilename();
            if (originalFileName == null) {
                throw new BadRequestException("文件名为空");
            }
            String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
            if (extension.isEmpty()) {
                throw new BadRequestException("文件扩展名为空");
            }

            String uniqueFileName = UUID.randomUUID() + extension;

            File saveFile = new File(uploadDir + uniqueFileName);
            file.transferTo(saveFile);

            // 返回文件访问的URL
            return uniqueFileName;
        } catch (IOException e) {
            e.printStackTrace();
            throw new BadRequestException("文件上传失败：" + e.getMessage());
        }
    }

    @GetMapping("/download")
    @Operation(summary = "下载文件", description = "下载服务器上的文件")
    public void download(String filename, HttpServletResponse response) {
        try {
            // path是指想要下载的文件的路径
            Path path = Paths.get(uploadDir).resolve(filename);
            File file = new File(path.toString());
            if (!file.exists()) {
                throw new ResourceNotFoundException("文件不存在：" + filename);
            }
            // 获取文件后缀名
            String ext = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();

            // 将文件写入输入流
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStream fis = new BufferedInputStream(fileInputStream);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();

            // 清空response
            response.reset();
            // 设置response的Header
            response.setCharacterEncoding("UTF-8");
            //Content-Disposition的作用：告知浏览器以何种方式显示响应返回的文件，用浏览器打开还是以附件的形式下载到本地保存
            //attachment表示以附件方式下载   inline表示在线打开   "Content-Disposition: inline; filename=文件名.mp3"
            // filename表示文件的默认名称，因为网络传输只支持URL编码的相关支付，因此需要将文件名URL编码后进行传输,前端收到后需要反编码才能获取到真正的名称
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
            // 告知浏览器文件的大小
            response.addHeader("Content-Length", "" + file.length());
            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            outputStream.write(buffer);
            outputStream.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new InternalServerErrorException("文件下载失败：" + ex.getMessage());
        }
    }

    @GetMapping("/download/{fileName:.+}")
    @Operation(summary = "下载文件", description = "下载服务器上的文件")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) throws MalformedURLException {
        Path file = Paths.get(uploadDir).resolve(fileName);
        Resource resource = new UrlResource(file.toUri());

        if (resource.exists() || resource.isReadable()) {
            return ResponseEntity.ok().body(resource);
        } else {
            throw new ResourceNotFoundException("文件不存在：" + fileName);
        }
    }


    public static String calculateSHA256(MultipartFile file) throws IOException, NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        try (InputStream is = file.getInputStream()) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                md.update(buffer, 0, bytesRead);
            }
        }

        byte[] bytes = md.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
