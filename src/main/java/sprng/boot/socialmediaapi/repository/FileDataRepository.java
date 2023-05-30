package sprng.boot.socialmediaapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sprng.boot.socialmediaapi.models.FileData;

import java.util.Optional;

public interface FileDataRepository extends JpaRepository<FileData,Integer> {
    Optional<FileData> findByName(String fileName);
}
