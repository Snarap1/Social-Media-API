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

class UserServiceTest {

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
        // Arrange
        Long id = 1L;
        User user = new User();
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        // Act
        User result = userService.getUserById(id);

        // Assert
        assertEquals(user, result);
    }

    @Test
    void testGetUserById_InvalidId_ThrowsException() {
        // Arrange
        Long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> userService.getUserById(id));
    }

    @Test
    void testGetUsers_ReturnsListOfUsers() {
        // Arrange
        List<User> users = Arrays.asList(new User(), new User());
        when(userRepository.findAll()).thenReturn(users);

        // Act
        List<User> result = userService.getUsers();

        // Assert
        assertEquals(users, result);
    }

    @Test
    void testGetSenderById_ValidId_ReturnsUser() {
        // Arrange
        long id = 1L;
        User user = new User();
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        // Act
        User result = userService.getSenderById(id);

        // Assert
        assertEquals(user, result);
    }

    @Test
    void testGetSenderById_InvalidId_ThrowsException() {
        // Arrange
        long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> userService.getSenderById(id));
    }

    @Test
    void testGetReceiverById_ValidId_ReturnsUser() {
        // Arrange
        long id = 1L;
        User user = new User();
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        // Act
        User result = userService.getReceiverById(id);

        // Assert
        assertEquals(user, result);
    }

    @Test
    void testGetReceiverById_InvalidId_ThrowsException() {
        // Arrange
        long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> userService.getReceiverById(id));
    }

    @Test
    void testSaveUser_CallsUserRepositorySave() {
        // Arrange
        User user = new User();

        // Act
        userService.saveUser(user);

        // Assert
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testSaveUsers_CallsUserRepositorySaveAll() {
        // Arrange
        User receiver = new User();
        User sender = new User();

        // Act
        userService.saveUsers(receiver, sender);

        // Assert
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

        // Act
        Set<User> result = userService.getSubscriptions(userId);

        // Assert
        assertEquals(subscriptions, result);
    }

    @Test
    void testGetSubscriptions_InvalidUserId_ThrowsException() {
        // Arrange
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> userService.getSubscriptions(userId));
    }
}
