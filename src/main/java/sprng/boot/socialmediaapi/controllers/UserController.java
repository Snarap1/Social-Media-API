package sprng.boot.socialmediaapi.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sprng.boot.socialmediaapi.dto.UserDTO;
import sprng.boot.socialmediaapi.dto.UserDTOResponse;
import sprng.boot.socialmediaapi.models.User;
import sprng.boot.socialmediaapi.services.UserService;

import java.util.HashSet;
import java.util.Set;


@RestController
@RequestMapping("/users")
@Tag(name = "User Controller",description = "Контроллер для взаимодействия пользователей")
public class UserController {

    private  final  ModelMapper modelMapper;
    private  final  UserService userService;

    @Autowired
    public UserController(ModelMapper modelMapper, UserService userService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @Operation(
            summary = "Получить пользователя",
            description = "Позволяет получить пользователя по его ID"
    )
    @GetMapping("/{userId}")
    public  ResponseEntity<UserDTO> getUser(
            @PathVariable @Parameter(description = "ID пользователя") Long userId
    ){
        User user = userService.getUserById(userId);
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);

        return ResponseEntity.ok(userDTO);
    }

    @Operation(
            summary = "Добавить пользователя (только для внутренних тестов)",
            description = "Позволяет добавить пользователя, однако не авторизовывая его"
    )
    @PostMapping("/add")
    public  ResponseEntity<String> addUser(
            @RequestBody @Parameter(description = "Параметры пользователя") @Valid UserDTO userDTO
            ,BindingResult bindingResult) throws BindException {
      userService.check(bindingResult);

        User user = modelMapper.map(userDTO, User.class);
        userService.saveUser(user);
            return ResponseEntity.ok("User created");
    }

    @Operation(
            summary = "Удалить пользователя",
            description = "Позволяет удалить пользователя"
    )
    @DeleteMapping("/{userId}")
    public  ResponseEntity<String> removeUser(@PathVariable @Parameter(description = "ID пользователя") Long userId){
        userService.delete(userId);
        return ResponseEntity.ok("User deleted");
    }


    @Operation(
            summary = "Отправить заявку в друзья",
            description = "Позволяет добавиться в друзья"
    )
    @PostMapping("/{senderId}/friend-request/{receiverId}")
    public ResponseEntity<String> sendFriendRequest(
            @PathVariable @Parameter(description = "ID пользователя отправляющего заявку") Long senderId,
            @PathVariable @Parameter(description = "ID пользователя получающего заявку") Long receiverId) {

        User sender = userService.getSenderById(senderId);
        User receiver = userService.getReceiverById(receiverId);

        userService.getFriendRequest(sender,receiver);

        return ResponseEntity.ok("Friend request sent");
    }


    @Operation(
            summary = "Принять заявку",
            description = "Позволяет принять заявку в друзья"
    )
    @PostMapping("/{receiverId}/accept-friend-request/{senderId}")
    public ResponseEntity<String> acceptFriendRequest(
            @PathVariable @Parameter(description = "ID пользователя получившего заявку") Long receiverId,
            @PathVariable @Parameter(description = "ID пользователя отправившего заявку")  Long senderId) {

        User receiver = userService.getReceiverById(receiverId);
        User sender = userService.getSenderById(senderId);

        userService.acceptFriend(receiver,sender);

        return ResponseEntity.ok("Friend request accepted");
    }

    @Operation(
            summary = "Отклонить заявку заявку",
            description = "Позволяет отклонить заявку в друзья"
    )
    @PostMapping("/{receiverId}/reject-friend-request/{senderId}")
    public ResponseEntity<String> rejectFriendRequest(
            @PathVariable @Parameter(description = "ID пользователя получившего заявку")  Long receiverId,
            @PathVariable @Parameter(description = "ID пользователя отправившего заявку") Long senderId) {

        User receiver = userService.getReceiverById(receiverId);
        User sender = userService.getSenderById(senderId);

        userService.rejectFriend(receiver,sender);

        return ResponseEntity.ok("Friend request rejected");
    }

    @Operation(
            summary = "Удалить из друзей",
            description = "Позволяет удалить пользователя из друзей"
    )
    @PostMapping("/{userId}/remove-friend/{friendId}")
    public ResponseEntity<String> removeFriend(
            @PathVariable @Parameter(description = "ID пользователя")  Long userId,
            @PathVariable @Parameter(description = "ID друга")  Long friendId) {

        User user = userService.getUserById(userId);
        User friend = userService.getUserById(friendId);

        userService.removeFriend(user,friend);

        return ResponseEntity.ok("Friend removed");
    }

    @Operation(
            summary = "Отписаться",
            description = "Позволяет пользователю отписаться от другого пользователя"
    )
    @PostMapping("/{userId}/unfollow/{subscriptionId}")
    public ResponseEntity<String> unfollowRequest(
            @PathVariable @Parameter(description = "ID пользователя") Long userId,
            @PathVariable @Parameter(description = "ID пользователя от которого надо отписатсья") Long subscriptionId) {

        User user = userService.getUserById(userId);
        User sub = userService.getUserById(subscriptionId);

        userService.unfollow(user,sub);

        return ResponseEntity.ok("Unfollowed");
    }

    @Operation(
            summary = "Список подписчиков",
            description = "Позволяет получить список подписчиков пользователя"
    )
    @GetMapping("/{userId}/followers")
    public ResponseEntity<?> getFriendRequestsList(
            @PathVariable @Parameter(description = "ID пользователя") Long userId) {

        User user = userService.getUserById(userId);

        Set<UserDTOResponse> followers = new HashSet<>();
            for (User element : user.getFollowers()) {
                UserDTOResponse map = modelMapper.map(element, UserDTOResponse.class);
                followers.add(map);
            }

        return ResponseEntity.ok(followers);
    }

    @Operation(
            summary = "Список друзей",
            description = "Позволяет получить список друзей пользователя"
    )
    @GetMapping("/{userId}/friends")
    public ResponseEntity<?> getFriends(
            @PathVariable @Parameter(description = "ID пользователя") Long userId)  {

        User user = userService.getUserById(userId);

        Set<UserDTOResponse>  friends = new HashSet<>();
            for (User element : user.getFriends()) {
                UserDTOResponse map = modelMapper.map(element, UserDTOResponse.class);
                friends.add(map);
            }

        return ResponseEntity.ok(friends);
    }

    @Operation(
            summary = "Запросы в друзья",
            description = "Позволяет получить список запросов в друзья для пользователя"
    )
    @GetMapping("/{userId}/friendRequests")
    public ResponseEntity<?> getFriendRequests(
            @PathVariable @Parameter(description = "ID пользователя") Long userId)  {

        User user = userService.getUserById(userId);

        Set<UserDTOResponse>  friendRequests = new HashSet<>();
            for (User element : user.getFriendRequests()) {
                UserDTOResponse map = modelMapper.map(element, UserDTOResponse.class);
                friendRequests.add(map);
            }

        return ResponseEntity.ok(friendRequests);
    }

    @Operation(
            summary = "Подписки",
            description = "Позволяет получить список тех на кого подписан пользователь"
    )
    @GetMapping("/{userId}/subscriptions")
    public ResponseEntity<?> getSubscriptions(
            @PathVariable @Parameter(description = "ID пользователя") Long userId) {

        User user = userService.getUserById(userId);

        Set<UserDTOResponse> subscriptions = new HashSet<>();
            for (User element : user.getSubscriptions()) {
                UserDTOResponse map = modelMapper.map(element, UserDTOResponse.class);
                subscriptions.add(map);
            }

        return ResponseEntity.ok(subscriptions);
    }




}
