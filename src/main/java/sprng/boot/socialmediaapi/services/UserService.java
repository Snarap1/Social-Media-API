package sprng.boot.socialmediaapi.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public Set<User> getSubscriptions(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return user.getSubscriptions();
    }
}
