package ru.hogwarts.school.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.Exception.EmptyException;
import ru.hogwarts.school.Model.Avatar;
import ru.hogwarts.school.Model.Student;
import ru.hogwarts.school.Repository.AvatarRepository;
import ru.hogwarts.school.Repository.StudentRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

@Service
public class AvatarServiceImpl implements AvatarService {

    @Autowired
    private final AvatarRepository avatarRepository;

    private final StudentRepository studentRepository;

    private final Logger logger = LoggerFactory.getLogger(AvatarServiceImpl.class);

    public AvatarServiceImpl(AvatarRepository avatarRepository, StudentRepository studentRepository) {
        this.avatarRepository = avatarRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public Avatar upload(Long studentId, MultipartFile file) throws IOException {
        logger.info("Avatar upload is running");
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new EmptyException("Student not found"));
        Path filePath = Path.of("./avatar", student + "." + getExtension(file.getOriginalFilename()));

        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (InputStream is = file.getInputStream();
             OutputStream os = Files.newOutputStream(filePath, StandardOpenOption.CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }
        Avatar avatar = avatarRepository.findByStudentId(studentId).orElse(new Avatar());
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setData(file.getBytes());
        avatarRepository.save(avatar);
        return avatar;
    }

    @Override
    public Avatar getAvatar(Long id) {
        logger.info("Avatar getAvatar is running");
        return avatarRepository.findById(id).orElseThrow(() -> new EmptyException("Avatar not found"));
    }

    @Override
    public List<Avatar> getAllAvatars(Integer pageNumber, Integer pageSize) {
        logger.info("Avatar getAllAvatars is running");
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return avatarRepository.findAll(pageRequest).getContent();
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
