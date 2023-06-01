package sprng.boot.socialmediaapi.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import sprng.boot.socialmediaapi.models.User;
import sprng.boot.socialmediaapi.repository.UserRepository;

import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class UserRepositoryTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByEmail() {
        // Создаем нового пользователя
        User user = new User();
        user.setNickname("John");
        user.setEmail("john@example.com");
        user.setPassword("password");
        userRepository.save(user);

        // Вызываем тестируемый метод
        Optional<User> foundUser = userRepository.findByEmail("john@example.com");

        // Проверяем
        Assertions.assertTrue(foundUser.isPresent());
        Assertions.assertEquals("John", foundUser.get().getNickname());
    }

    @Test
    public void testSave() {
        // Создаем нового пользователя
        User user = new User();
        user.setNickname("Alice");
        user.setEmail("alice@gmail.com");
        user.setPassword("password");

        // Сохраняем пользователя с помощью репозитория
        User savedUser = userRepository.save(user);

        // Получаем сохраненного пользователя из репозитория
        User retrievedUser = userRepository.findById(savedUser.getId()).orElse(null);

        // Проверяем
        Assertions.assertNotNull(retrievedUser);
        Assertions.assertEquals("Alice", retrievedUser.getNickname());
    }
}