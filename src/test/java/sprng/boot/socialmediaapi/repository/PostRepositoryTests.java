package sprng.boot.socialmediaapi.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.web.servlet.MockMvc;
import sprng.boot.socialmediaapi.models.Post;
import sprng.boot.socialmediaapi.models.User;
import sprng.boot.socialmediaapi.repository.PostRepository;
import sprng.boot.socialmediaapi.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class PostRepositoryTests {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindAllByUserOrderByCreatedAtDesc() {
        // Arrange
        User user = new User();
        user.setNickname("Bob");
        user.setEmail("bob@mail.com");
        user.setPassword("password");
        userRepository.save(user);

        Post post1 = new Post();
        post1.setTitle("post1");
        post1.setContent("post1");
        post1.setUser(user);
        post1.setCreatedAt(LocalDateTime.now());

        Post post2 = new Post();
        post2.setTitle("post1");
        post2.setContent("post1");
        post2.setUser(user);
        post1.setCreatedAt(LocalDateTime.now());

        postRepository.save(post1);
        postRepository.save(post2);

        // act
        List<Post> posts = postRepository.findAllByUserOrderByCreatedAtDesc(user, Pageable.unpaged());

        // assert
        Assertions.assertEquals(2, posts.size());

    }

    @Test
    public void testFindAllByUser() {
        //создаём
        User user = new User();
        user.setNickname("Bob");
        user.setEmail("bob@mail.com");
        user.setPassword("qwerty123");
        userRepository.save(user);

        Post post1 = new Post();
        post1.setTitle("post1");
        post1.setContent("post1");
        post1.setUser(user);

        Post post2 = new Post();
        post2.setTitle("post1");
        post2.setContent("post1");
        post2.setUser(user);

        postRepository.save(post1);
        postRepository.save(post2);

        // Вызываем метод
        List<Post> posts = postRepository.findAllByUser(user);

        // Проверяем
        Assertions.assertEquals(2, posts.size());

    }

}
