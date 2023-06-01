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
        // Создаем
        User user = new User();
        user.setId(1L);
        user.setNickname("Alice");
        user.setEmail("alice@example.com");
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User retrievedUser = userService.getUserById(1L);

        Assertions.assertNotNull(retrievedUser);
        Assertions.assertEquals("Alice", retrievedUser.getNickname());

        // Проверяем
        Mockito.verify(userRepository).findById(1L);
    }

    @Test
    public void testGetUsers() {
        // Создаем
        User user1 = new User();
        user1.setId(1L);
        user1.setNickname("Alice");
        user1.setEmail("alice@example.com");

        User user2 = new User();
        user2.setId(2L);
        user2.setNickname("Bob");
        user2.setEmail("bob@example.com");

        Mockito.when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));
        List<User> userList = userService.getUsers();
        Assertions.assertNotNull(userList);
        Assertions.assertEquals(2, userList.size());

        // Проверяем
        Mockito.verify(userRepository).findAll();
    }

    @Test
    public void testGetSenderById() {
        User user = new User();
        user.setId(1L);
        user.setNickname("Alice");
        user.setEmail("alice@example.com");

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        User sender = userService.getSenderById(1L);
        Assertions.assertNotNull(sender);
        Assertions.assertEquals("Alice", sender.getNickname());

        Mockito.verify(userRepository).findById(1L);
    }

    @Test
    public void testGetReceiverById() {
        User user = new User();
        user.setId(2L);
        user.setNickname("Bob");
        user.setEmail("bob@example.com");

        Mockito.when(userRepository.findById(2L)).thenReturn(Optional.of(user));
        User receiver = userService.getReceiverById(2L);

        Assertions.assertNotNull(receiver);
        Assertions.assertEquals("Bob", receiver.getNickname());

        Mockito.verify(userRepository).findById(2L);
    }

    @Test
    public void testSaveUser() {
        User user = new User();
        user.setNickname("Alice");
        user.setEmail("alice@example.com");

        Mockito.when(userRepository.save(user)).thenReturn(user);

        userService.saveUser(user);

        Mockito.verify(userRepository).save(user);
    }

    @Test
    public void testSaveUsers() {
        User receiver = new User();
        receiver.setNickname("Alice");
        receiver.setEmail("alice@example.com");

        User sender = new User();
        sender.setNickname("Bob");
        sender.setEmail("bob@example.com");

        Mockito.when(userRepository.saveAll(Arrays.asList(receiver, sender))).thenReturn(Arrays.asList(receiver, sender));

        userService.saveUsers(receiver, sender);

        Mockito.verify(userRepository).saveAll(Arrays.asList(receiver, sender));
    }

    @Test
    public void testDelete() {
        userService.delete(1L);

        Mockito.verify(userRepository).deleteById(1L);
    }

    @Test
    public void testGetSubscriptions() {
        User user = new User();
        user.setId(1L);
        user.setNickname("Alice");
        user.setEmail("alice@example.com");

        User subscription1 = new User();
        subscription1.setId(2L);
        subscription1.setNickname("Bob");
        subscription1.setEmail("bob@example.com");

        User subscription2 = new User();
        subscription2.setId(3L);
        subscription2.setNickname("Charlie");
        subscription2.setEmail("charlie@example.com");

        Set<User> subscriptions = new HashSet<>();
        subscriptions.add(subscription1);
        subscriptions.add(subscription2);
        user.setSubscriptions(subscriptions);

        Set<User> retrievedSubscriptions = userService.getSubscriptions(user);

        Assertions.assertNotNull(retrievedSubscriptions);
        Assertions.assertEquals(2, retrievedSubscriptions.size());

        Assertions.assertTrue(retrievedSubscriptions.contains(subscription1));
        Assertions.assertTrue(retrievedSubscriptions.contains(subscription2));
    }

    @Test
    public void testGetFriendRequest() {
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

        Mockito.when(userRepository.saveAll(Arrays.asList(sender, receiver))).thenReturn(Arrays.asList(sender, receiver));

        userService.getFriendRequest(sender, receiver);

        Mockito.verify(userRepository).saveAll(Arrays.asList(sender, receiver));

        Assertions.assertTrue(sender.getSubscriptions().contains(receiver));
        Assertions.assertTrue(receiver.getFollowers().contains(sender));
    }

    @Test
    public void testAcceptFriend() {
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

        Mockito.when(userRepository.saveAll(Arrays.asList(sender, receiver))).thenReturn(Arrays.asList(sender, receiver));

        userService.getFriendRequest(sender,receiver);
        userService.acceptFriend(receiver, sender);

        Mockito.verify(userRepository).saveAll(Arrays.asList(receiver, sender));

        Assertions.assertFalse(receiver.getFriendRequests().contains(sender));

        Assertions.assertTrue(receiver.getFriends().contains(sender));
        Assertions.assertTrue(receiver.getSubscriptions().contains(sender));
        Assertions.assertTrue(sender.getFriends().contains(receiver));
        Assertions.assertTrue(sender.getFollowers().contains(receiver));
    }

}