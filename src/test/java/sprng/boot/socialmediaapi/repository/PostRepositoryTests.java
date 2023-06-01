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
        // Создаем пользователя и сохраняем его в базу данных
        User user = new User();
        user.setNickname("Bob");
        user.setEmail("bob@mail.com");
        user.setPassword("password");
        userRepository.save(user);


        // Создаем несколько постов для данного пользователя и сохраняем их в базу данных
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

        // Сохраняем посты в базу данных
        postRepository.save(post1);
        postRepository.save(post2);

        // Вызываем метод, который тестируем
        List<Post> posts = postRepository.findAllByUserOrderByCreatedAtDesc(user, Pageable.unpaged());

        // Проверяем, что количество найденных постов соответствует ожидаемому
        Assertions.assertEquals(2, posts.size());

    }

    @Test
    public void testFindAllByUser() {
        // Создаем пользователя и сохраняем его в базу данных
        User user = new User();
        user.setNickname("Bob");
        user.setEmail("bob@mail.com");
        user.setPassword("qwerty123");
        userRepository.save(user);


        // Создаем несколько постов для данного пользователя и сохраняем их в базу данных
        Post post1 = new Post();
        post1.setTitle("post1");
        post1.setContent("post1");
        post1.setUser(user);

        Post post2 = new Post();
        post2.setTitle("post1");
        post2.setContent("post1");
        post2.setUser(user);

        // Сохраняем посты в базу данных
        postRepository.save(post1);
        postRepository.save(post2);

        // Вызываем метод, который тестируем
        List<Post> posts = postRepository.findAllByUser(user);

        // Проверяем, что количество найденных постов соответствует ожидаемому
        Assertions.assertEquals(2, posts.size());

    }

}
