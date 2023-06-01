package sprng.boot.socialmediaapi.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sprng.boot.socialmediaapi.models.User;
import sprng.boot.socialmediaapi.repository.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest2 {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserById_ValidId_ReturnsUser() {
        Long id = 1L;
        User user = new User();
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        User result = userService.getUserById(id);

        assertEquals(user, result);
    }

    @Test
    void testGetUserById_InvalidId_ThrowsException() {
        Long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> userService.getUserById(id));
    }

    @Test
    void testGetUsers_ReturnsListOfUsers() {
        List<User> users = Arrays.asList(new User(), new User());
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getUsers();

        assertEquals(users, result);
    }

    @Test
    void testGetSenderById_ValidId_ReturnsUser() {
        long id = 1L;
        User user = new User();
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        User result = userService.getSenderById(id);

        assertEquals(user, result);
    }

    @Test
    void testGetSenderById_InvalidId_ThrowsException() {
        long id = 1L;

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> userService.getSenderById(id));
    }

    @Test
    void testGetReceiverById_ValidId_ReturnsUser() {
        long id = 1L;
        User user = new User();
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        User result = userService.getReceiverById(id);

        assertEquals(user, result);
    }

    @Test
    void testGetReceiverById_InvalidId_ThrowsException() {
        long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> userService.getReceiverById(id));
    }

    @Test
    void testSaveUser_CallsUserRepositorySave() {
        User user = new User();

        userService.saveUser(user);

        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testSaveUsers_CallsUserRepositorySaveAll() {
        // создаём
        User receiver = new User();
        User sender = new User();

        userService.saveUsers(receiver, sender);

        verify(userRepository, times(1)).saveAll(Arrays.asList(receiver, sender));
    }

    @Test
    void testGetSubscriptions_ValidUserId_ReturnsUserSubscriptions() {
        // Arrange
        Long userId = 1L;
        User user = new User();
        Set<User> subscriptions = Set.of(new User(), new User());
        user.setSubscriptions(subscriptions);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));


        Set<User> result = userService.getSubscriptions(user);

        assertEquals(subscriptions, result);
    }

}
