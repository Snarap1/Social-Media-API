package sprng.boot.socialmediaapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sprng.boot.socialmediaapi.models.FileData;
import sprng.boot.socialmediaapi.repository.FileDataRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

@Service
public class StorageService {


    @Autowired
    private FileDataRepository fileDataRepository;

    private final String FOLDER_PATH="D:\\Social-media-api\\Social-Media-API\\src\\main\\resources\\SocialMediaApiImages\\";

    public FileData uploadImageToFileSystem(MultipartFile file) throws IOException {
        String filePath=FOLDER_PATH +file.getOriginalFilename();

        FileData fileData=fileDataRepository.save(FileData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .filePath(filePath).build());

        file.transferTo(new File(filePath));

        if (fileData != null) {
            return fileData;
        }

        return null;
    }

    public byte[] downloadImageFromFileSystem(String fileName) throws IOException {
        Optional<FileData> fileData = fileDataRepository.findByName(fileName);
        String filePath=fileData.get().getFilePath();
        byte[] images = Files.readAllBytes(new File(filePath).toPath());
        return images;
    }



}
