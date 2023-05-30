package sprng.boot.socialmediaapi.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sprng.boot.socialmediaapi.dto.UserDTO;
import sprng.boot.socialmediaapi.models.User;
import sprng.boot.socialmediaapi.services.UserService;

import java.util.Set;


@RestController
@RequestMapping("/users")
public class UserController {

    private  final ModelMapper modelMapper;
    private  final  UserService userService;

    @Autowired
    public UserController(ModelMapper modelMapper, UserService userService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    // DTO converters
    private  User  convertToUser(UserDTO userDTO){
        return modelMapper.map(userDTO, User.class);
    }

    private UserDTO convertToDTO(User user){
         return modelMapper.map(user,UserDTO.class);
    }

    //crud dlya usera
    @GetMapping("/{userId}")
    public  ResponseEntity<UserDTO> getUser(
            @PathVariable Long userId
    ){
        User user = userService.getUserById(userId);
        UserDTO userDTO = convertToDTO(user);

        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/add")
    public  ResponseEntity<String> addUser(@RequestBody UserDTO userDTO){
        System.out.println(userDTO.getUsername());
        User user = convertToUser(userDTO);
        System.out.println(user.getEmail());
        userService.saveUser(user);
        return ResponseEntity.ok("User created");
    }


    // communication between users: dobavlyaem to friend, accept a reject/ POST methods tyt bydyt
    @PostMapping("/{senderId}/friend-request/{receiverId}")
    public ResponseEntity<String> sendFriendRequest(
            @PathVariable Long senderId,
            @PathVariable Long receiverId) {

        User sender = userService.getSenderById(senderId);
        User receiver = userService.getReceiverById(receiverId);

        sender.addSubscription(receiver);
        receiver.getFollowers().add(sender);

        receiver.getFriendRequests().add(sender);

        userService.saveUsers(receiver,sender);
        return ResponseEntity.ok("Friend request sent");
    }

    @PostMapping("/{receiverId}/accept-friend-request/{senderId}")
    public ResponseEntity<String> acceptFriendRequest(
            @PathVariable Long receiverId,
            @PathVariable Long senderId) {

        User receiver = userService.getReceiverById(receiverId);

        User sender = userService.getSenderById(senderId);

        receiver.getFriendRequests().remove(sender);

        receiver.getFriends().add(sender);
        receiver.addSubscription(sender);

        sender.getFollowers().add(receiver);
        sender.getFriends().add(receiver);

       userService.saveUsers(receiver,sender);

        return ResponseEntity.ok("Friend request accepted");
    }

    @PostMapping("/{receiverId}/reject-friend-request/{senderId}")
    public ResponseEntity<String> rejectFriendRequest(
            @PathVariable Long receiverId,
            @PathVariable Long senderId) {

        User receiver = userService.getReceiverById(receiverId);

        User sender = userService.getSenderById(senderId);

        receiver.getFriendRequests().remove(sender);

        userService.saveUser(receiver);

        return ResponseEntity.ok("Friend request rejected");
    }

    //удалить из друзей
    @PostMapping("/{userId}/remove-friend/{friendId}")
    public ResponseEntity<String> removeFriend(
            @PathVariable Long userId,
            @PathVariable Long friendId) {

        User user = userService.getUserById(userId);
        User friend = userService.getUserById(friendId);

        user.getSubscriptions().remove(friend);
        user.getFriends().remove(friend);

        friend.getFriends().remove(user);
        friend.getFollowers().remove(user);


        userService.saveUsers(user,friend);
        return ResponseEntity.ok("Friend removed");
    }

    //отписаться
    @PostMapping("/{userId}/unfollow/{subscriptionId}")
    public ResponseEntity<String> unfollow(
            @PathVariable Long userId,
            @PathVariable Long subscriptionId) {

        User user = userService.getUserById(userId);
        User sub = userService.getUserById(subscriptionId);

        user.getSubscriptions().remove(sub);

        sub.getFollowers().remove(user);

        userService.saveUsers(user,sub);
        return ResponseEntity.ok("Unfollowed");
    }



    // get followers and friends // and subscriptions
    @GetMapping("/{userId}/followers")
    public ResponseEntity<Set<User>> getFriendRequestsList(@PathVariable Long userId) {
        User user = userService.getUserById(userId);

        Set<User> followers = user.getFollowers();

        return ResponseEntity.ok(followers);
    }

    @GetMapping("/{userId}/friends")
    public ResponseEntity<Set<User>> getFriends(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        Set<User> friends = user.getFriends();

        return ResponseEntity.ok(friends);
    }

    @GetMapping("/{userId}/friendRequests")
    public ResponseEntity<Set<User>> getFriendRequests(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        Set<User> friendRequests = user.getFriendRequests();

        return ResponseEntity.ok(friendRequests);
    }

    @GetMapping("/{userId}/subscriptions")
    public ResponseEntity<Set<User>> getSubscriptions(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getSubscriptions(userId));
    }

}
