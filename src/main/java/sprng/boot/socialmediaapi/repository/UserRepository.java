package sprng.boot.socialmediaapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sprng.boot.socialmediaapi.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
