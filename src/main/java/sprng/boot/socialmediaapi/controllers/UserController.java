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
        userService.saveUser(sender);
        userService.saveUser(receiver);

        return ResponseEntity.ok("Friend request sent");
    }

    @PostMapping("/{receiverId}/accept-friend-request/{senderId}")
    public ResponseEntity<String> acceptFriendRequest(
            @PathVariable Long receiverId,
            @PathVariable Long senderId) {

        User receiver = userService.getReceiverById(receiverId);

        User sender = userService.getSenderById(senderId);

        receiver.getFollowers().remove(sender);
        receiver.getFriends().add(sender);

        sender.getFollowers().remove(receiver);
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


        receiver.getFollowers().remove(sender);
        userService.saveUser(receiver);

        return ResponseEntity.ok("Friend request rejected");
    }


    // get followers and friends // and subscriptions
    @GetMapping("/{userId}/followers")
    public ResponseEntity<Set<User>> getFollowers(@PathVariable Long userId) {
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

    @GetMapping("/{userId}/subscriptions")
    public ResponseEntity<Set<User>> getSubscriptions(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getSubscriptions(userId));

    }

}
