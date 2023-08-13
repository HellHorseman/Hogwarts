package ru.hogwarts.school.Service;

import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.Model.Avatar;

import java.io.IOException;

public interface AvatarService {

    Avatar upload(Long studentId, MultipartFile file) throws IOException;

    Avatar getAvatar(Long id);


}
