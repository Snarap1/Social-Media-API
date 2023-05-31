package sprng.boot.socialmediaapi.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import sprng.boot.socialmediaapi.models.User;
import sprng.boot.socialmediaapi.repository.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    private  final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(Long id){
        return  userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public List<User> getUsers(){
        return  userRepository.findAll();
    }

    public User  getSenderById(long id){
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Sender not found"));
    }

    public  User getReceiverById(long id){
        return  userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Receiver not found"));
    }

    public  void  saveUser(User user){
        userRepository.save(user);
    }

    public  void  saveUsers(User receiver, User sender ){
        userRepository.saveAll(Arrays.asList(receiver, sender));
    }

    public  void delete(Long userId){
        userRepository.deleteById(userId);
    }


    public Set<User> getSubscriptions(User user) {
        return user.getSubscriptions();
    }

    public  void getFriendRequest(User sender, User receiver){

        sender.addSubscription(receiver);
        receiver.getFollowers().add(sender);

        receiver.getFriendRequests().add(sender);

        saveUsers(sender,receiver);
    }

    public  void acceptFriend(User receiver,User sender){

        receiver.getFriendRequests().remove(sender);

        receiver.getFriends().add(sender);
        receiver.addSubscription(sender);

        sender.getFollowers().add(receiver);
        sender.getFriends().add(receiver);

        saveUsers(receiver,sender);
    }

    public  void rejectFriend(User receiver,User sender){

        receiver.getFriendRequests().remove(sender);

        saveUser(receiver);
    }

    public  void removeFriend(User user, User friend){

        user.getSubscriptions().remove(friend);
        user.getFriends().remove(friend);

        friend.getFriends().remove(user);
        friend.getFollowers().remove(user);


        saveUsers(user,friend);

    }

    public  void unfollow(User user, User sub){

        user.getSubscriptions().remove(sub);

        sub.getFollowers().remove(user);

        saveUsers(user,sub);

    }

    public  void  check(BindingResult bindingResult) throws BindException {
        if(bindingResult.hasErrors())
            throw new BindException(bindingResult);
    }



}
