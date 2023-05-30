package sprng.boot.socialmediaapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sprng.boot.socialmediaapi.models.FileData;
import sprng.boot.socialmediaapi.models.Post;
import sprng.boot.socialmediaapi.models.User;
import sprng.boot.socialmediaapi.repository.PostRepository;

import java.io.IOException;
import java.util.*;

@Service
public class PostService {
    private  final PostRepository postRepository;
    private  final  StorageService storageService;

    @Autowired
    public PostService(PostRepository postRepository, StorageService storageService) {
        this.postRepository = postRepository;
        this.storageService = storageService;
    }

    public  void  savePost(Post post){
        postRepository.save(post);
    }

    // method dlya user profile
    public List<Post> getAllPostByUser(User user){
        return postRepository.findAllByUserOrderByCreatedAt(user);
    }

    public  List<Post> getAllPosts(){
        return postRepository.findAll();
    }

    public  Post getPost(long id){
        return postRepository.findById(id);
    }

    public  void deletePostById(long id){
        postRepository.deleteById(id);
    }

    //для ленты активностей
    public List<Post> getPostForActivityLent(User user){
        List<Post> activityLent = new ArrayList<>();
        Set<User> userList = user.getSubscriptions();
        for(User subUsers : userList){
            activityLent.addAll(postRepository.findAllByUser(subUsers));
        }
        //в порядке возрастания
        // Comparator<Post> sortByCreatedAt = Comparator.comparing(Post::getCreatedAt);

        // в порядке убывания
        Comparator<Post> sortByCreatedAt = Comparator.comparing(Post::getCreatedAt).reversed();
        activityLent.sort(sortByCreatedAt);
        return  activityLent;
    }

    //добавить изображение к посту
    public  void  addImageToPost(Long post_id, MultipartFile file) throws Exception {
        Post post = postRepository.findById(post_id);

        if (post==null){
            throw  new Exception("post didnt found");
        }

        FileData fileData = storageService.uploadImageToFileSystem(file);
        post.addFileData(fileData);
        fileData.setPost(post);
        postRepository.save(post);

    }

}