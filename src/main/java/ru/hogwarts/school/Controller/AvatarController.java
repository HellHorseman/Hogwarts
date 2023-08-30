package ru.hogwarts.school.Controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.Model.Avatar;
import ru.hogwarts.school.Service.AvatarService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RestController
public class AvatarController {

    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @PostMapping(value = "/students/{id}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Avatar upload(@PathVariable Long id, @RequestParam MultipartFile file) throws IOException {
        return avatarService.upload(id, file);
    }

    @GetMapping("/avatar/{avatarId}/fromDB")
    public ResponseEntity<byte[]> loadFromDB(@PathVariable Long avatarId) {
        Avatar avatar = avatarService.getAvatar(avatarId);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        httpHeaders.setContentLength(avatar.getFileSize());
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(avatar.getData());
    }

    @GetMapping("/avatar/{avatarId}/fromFile")
    public void loadFromFile(@PathVariable Long avatarId, HttpServletResponse response) throws IOException {
        Avatar avatar = avatarService.getAvatar(avatarId);
        Path path = Path.of(avatar.getFilePath());
        try (InputStream is = Files.newInputStream(path);
             OutputStream os = response.getOutputStream()) {
            response.setStatus(HttpStatus.OK.value());
            response.setContentType(avatar.getMediaType());
            response.setContentLength(Math.toIntExact(avatar.getFileSize()));
            is.transferTo(os);
        }
    }

    @GetMapping
    public ResponseEntity<List<Avatar>> getAllAvatar(@RequestParam("page") Integer pageNumber, @RequestParam("size") Integer pageSize) {
        List<Avatar> avatars = avatarService.getAllAvatars(pageNumber, pageSize);
        return ResponseEntity.ok(avatars);
    }
}
