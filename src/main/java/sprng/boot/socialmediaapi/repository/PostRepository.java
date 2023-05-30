package sprng.boot.socialmediaapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sprng.boot.socialmediaapi.models.Post;
import sprng.boot.socialmediaapi.models.User;

import java.util.List;
import java.util.Set;

@Repository
public interface PostRepository extends JpaRepository<Post,Integer> {
    List<Post> findAllByUserOrderByCreatedAt(User user);
    List<Post> findAllByUser(User user);
    //List<Post> findAllByUserSubscriptionsOrderByCreatedAt(Set<User> user_subscriptions);


    void deleteById(long id);

    Post findById(long id);
}
