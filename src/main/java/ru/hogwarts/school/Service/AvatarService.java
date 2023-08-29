package ru.hogwarts.school.Service;

import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.Model.Avatar;

import java.io.IOException;
import java.util.List;

public interface AvatarService {

    Avatar upload(Long studentId, MultipartFile file) throws IOException;

    Avatar getAvatar(Long id);


    List<Avatar> getAllAvatars(Integer pageNumber, Integer pageSize);
}
