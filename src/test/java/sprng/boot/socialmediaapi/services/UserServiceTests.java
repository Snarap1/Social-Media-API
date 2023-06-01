package sprng.boot.socialmediaapi.services;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import sprng.boot.socialmediaapi.models.User;
import sprng.boot.socialmediaapi.repository.UserRepository;
import sprng.boot.socialmediaapi.services.UserService;

import java.util.*;

@SpringBootTest
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void testGetUserById() {
        // Создаем тестового пользователя
        User user = new User();
        user.setId(1L);
        user.setNickname("Alice");
        user.setEmail("alice@example.com");

        // Устанавливаем поведение мока UserRepository
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Получаем пользователя с помощью UserService
        User retrievedUser = userService.getUserById(1L);

        // Проверяем, что полученный пользователь не является null и имеет правильное имя пользователя
        Assertions.assertNotNull(retrievedUser);
        Assertions.assertEquals("Alice", retrievedUser.getNickname());

        // Проверяем, что метод findById репозитория был вызван с правильным аргументом
        Mockito.verify(userRepository).findById(1L);
    }

    @Test
    public void testGetUsers() {
        // Создаем тестовых пользователей
        User user1 = new User();
        user1.setId(1L);
        user1.setNickname("Alice");
        user1.setEmail("alice@example.com");

        User user2 = new User();
        user2.setId(2L);
        user2.setNickname("Bob");
        user2.setEmail("bob@example.com");

        // Устанавливаем поведение мока UserRepository
        Mockito.when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        // Получаем список пользователей с помощью UserService
        List<User> userList = userService.getUsers();

        // Проверяем, что список пользователей не является null и содержит ожидаемое количество пользователей
        Assertions.assertNotNull(userList);
        Assertions.assertEquals(2, userList.size());

        // Проверяем, что метод findAll репозитория был вызван
        Mockito.verify(userRepository).findAll();
    }

    @Test
    public void testGetSenderById() {
        // Создаем тестового пользователя
        User user = new User();
        user.setId(1L);
        user.setNickname("Alice");
        user.setEmail("alice@example.com");

        // Устанавливаем поведение мока UserRepository
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Получаем отправителя с помощью UserService
        User sender = userService.getSenderById(1L);

        // Проверяем, что полученный отправитель не является null и имеет правильное имя пользователя
        Assertions.assertNotNull(sender);
        Assertions.assertEquals("Alice", sender.getNickname());

        // Проверяем, что метод findById репозитория был вызван с правильным аргументом
        Mockito.verify(userRepository).findById(1L);
    }

    @Test
    public void testGetReceiverById() {
        // Создаем тестового пользователя
        User user = new User();
        user.setId(2L);
        user.setNickname("Bob");
        user.setEmail("bob@example.com");

        // Устанавливаем поведение мока UserRepository
        Mockito.when(userRepository.findById(2L)).thenReturn(Optional.of(user));

        // Получаем получателя с помощью UserService
        User receiver = userService.getReceiverById(2L);

        // Проверяем, что полученный получатель не является null и имеет правильное имя пользователя
        Assertions.assertNotNull(receiver);
        Assertions.assertEquals("Bob", receiver.getNickname());

        // Проверяем, что метод findById репозитория был вызван с правильным аргументом
        Mockito.verify(userRepository).findById(2L);
    }

    @Test
    public void testSaveUser() {
        // Создаем тестового пользователя
        User user = new User();
        user.setNickname("Alice");
        user.setEmail("alice@example.com");

        // Устанавливаем поведение мока UserRepository
        Mockito.when(userRepository.save(user)).thenReturn(user);

        // Сохраняем пользователя с помощью UserService
        userService.saveUser(user);

        // Проверяем, что метод save репозитория был вызван с правильным аргументом
        Mockito.verify(userRepository).save(user);
    }

    @Test
    public void testSaveUsers() {
        // Создаем тестовых пользователей
        User receiver = new User();
        receiver.setNickname("Alice");
        receiver.setEmail("alice@example.com");

        User sender = new User();
        sender.setNickname("Bob");
        sender.setEmail("bob@example.com");

        // Устанавливаем поведение мока UserRepository
        Mockito.when(userRepository.saveAll(Arrays.asList(receiver, sender))).thenReturn(Arrays.asList(receiver, sender));

        // Сохраняем пользователей с помощью UserService
        userService.saveUsers(receiver, sender);

        // Проверяем, что метод saveAll репозитория был вызван с правильными аргументами
        Mockito.verify(userRepository).saveAll(Arrays.asList(receiver, sender));
    }

    @Test
    public void testDelete() {
        // Удаляем пользователя с помощью UserService
        userService.delete(1L);

        // Проверяем, что метод deleteById репозитория был вызван с правильным аргументом
        Mockito.verify(userRepository).deleteById(1L);
    }

    @Test
    public void testGetSubscriptions() {
        // Создаем тестового пользователя
        User user = new User();
        user.setId(1L);
        user.setNickname("Alice");
        user.setEmail("alice@example.com");

        // Создаем тестовые подписки пользователя
        User subscription1 = new User();
        subscription1.setId(2L);
        subscription1.setNickname("Bob");
        subscription1.setEmail("bob@example.com");

        User subscription2 = new User();
        subscription2.setId(3L);
        subscription2.setNickname("Charlie");
        subscription2.setEmail("charlie@example.com");

        // Добавляем подписки пользователю
        Set<User> subscriptions = new HashSet<>();
        subscriptions.add(subscription1);
        subscriptions.add(subscription2);
        user.setSubscriptions(subscriptions);

        // Получаем подписки пользователя с помощью UserService
        Set<User> retrievedSubscriptions = userService.getSubscriptions(user);

        // Проверяем, что полученные подписки не являются null и содержат ожидаемое количество пользователей
        Assertions.assertNotNull(retrievedSubscriptions);
        Assertions.assertEquals(2, retrievedSubscriptions.size());
        // Проверяем, что полученные подписки содержат ожидаемых пользователей
        Assertions.assertTrue(retrievedSubscriptions.contains(subscription1));
        Assertions.assertTrue(retrievedSubscriptions.contains(subscription2));
    }

    @Test
    public void testGetFriendRequest() {
        // Создаем тестовых пользователей
        User sender = new User();
        sender.setId(1L);
        sender.setNickname("Alice");
        sender.setEmail("alice@example.com");

        User receiver = new User();
        receiver.setId(2L);
        receiver.setNickname("Bob");
        receiver.setEmail("bob@example.com");
        receiver.setFollowers(new HashSet<>());
        receiver.setFriendRequests(new HashSet<>());

        // Устанавливаем поведение мока UserRepository
        Mockito.when(userRepository.saveAll(Arrays.asList(sender, receiver))).thenReturn(Arrays.asList(sender, receiver));

        // Отправляем запрос на дружбу с помощью UserService
        userService.getFriendRequest(sender, receiver);

        // Проверяем, что метод saveAll репозитория был вызван с правильными аргументами
        Mockito.verify(userRepository).saveAll(Arrays.asList(sender, receiver));

        // Проверяем, что получатель добавлен в подписки отправителя и отправитель добавлен в подписчики получателя
        Assertions.assertTrue(sender.getSubscriptions().contains(receiver));
        Assertions.assertTrue(receiver.getFollowers().contains(sender));
    }

    @Test
    public void testAcceptFriend() {
        // Создаем тестовых пользователей
        User sender = new User();
        sender.setId(1L);
        sender.setNickname("Alice");
        sender.setEmail("alice@example.com");
        sender.setFriends(new HashSet<>());
        sender.setFollowers(new HashSet<>());

        User receiver = new User();
        receiver.setId(2L);
        receiver.setNickname("Bob");
        receiver.setEmail("bob@example.com");
        receiver.setFriends(new HashSet<>());
        receiver.setFollowers(new HashSet<>());
        receiver.setFriendRequests(new HashSet<>());

        // Устанавливаем поведение мока UserRepository
        Mockito.when(userRepository.saveAll(Arrays.asList(sender, receiver))).thenReturn(Arrays.asList(sender, receiver));

        // Принимаем запрос на дружбу с помощью UserService
        userService.getFriendRequest(sender,receiver);
        userService.acceptFriend(receiver, sender);

        // Проверяем, что метод saveAll репозитория был вызван с правильными аргументами
        Mockito.verify(userRepository).saveAll(Arrays.asList(receiver, sender));

        // Проверяем, что отправитель удален из запросов на дружбу получателя
        Assertions.assertFalse(receiver.getFriendRequests().contains(sender));

        // Проверяем, что получатель добавлен в друзья отправителя и в подписки отправителя,
        // а отправитель добавлен в друзья получателя и в подписчики получателя
        Assertions.assertTrue(receiver.getFriends().contains(sender));
        Assertions.assertTrue(receiver.getSubscriptions().contains(sender));
        Assertions.assertTrue(sender.getFriends().contains(receiver));
        Assertions.assertTrue(sender.getFollowers().contains(receiver));
    }

}