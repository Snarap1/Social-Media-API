package sprng.boot.socialmediaapi.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sprng.boot.socialmediaapi.models.Post;
import sprng.boot.socialmediaapi.models.User;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Integer> {

    List<Post>findAllByUserOrderByCreatedAtDesc  (User user,Pageable pageable);

    List<Post> findAllByUser(User user);

    Post findById(long id);
}
