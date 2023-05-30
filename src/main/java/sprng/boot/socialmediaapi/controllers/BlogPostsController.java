package sprng.boot.socialmediaapi.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sprng.boot.socialmediaapi.models.Post;
import sprng.boot.socialmediaapi.models.User;
import sprng.boot.socialmediaapi.services.PostService;
import sprng.boot.socialmediaapi.services.StorageService;
import sprng.boot.socialmediaapi.services.UserService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class BlogPostsController {

    private final PostService postService;


    private  final UserService userService;

    private  final StorageService  storageService;

    @Autowired
    public BlogPostsController(PostService postService, UserService userService, StorageService storageService) {
        this.postService = postService;
        this.userService = userService;
        this.storageService = storageService;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<String> createPost(@PathVariable Long userId, @RequestBody Post post){
        User user = userService.getUserById(userId);
        post.setUser(user);

        postService.savePost(post);
        return ResponseEntity.ok("Post created");
    }

    @PatchMapping("/{postId}")
    public  ResponseEntity<String> editPost (@PathVariable Long postId, @RequestBody Post post ){
        Post postForSave = postService.getPost(postId);
        postForSave.setTitle(post.getTitle());
        postForSave.setContent(post.getContent());
        postService.savePost(postForSave);
        return  ResponseEntity.ok("Post edited");
    }

    @DeleteMapping( "/{postId}")
    public  ResponseEntity<String> deletePost(@PathVariable Long postId){
        postService.deletePostById(postId);
       return ResponseEntity.ok("Post deleted");
    }

   // Пользователи могут просматривать посты других пользователей
    @GetMapping("/{userId}")
    public  ResponseEntity<List<Post>> getUsersPosts(@PathVariable long userId){
        User user = userService.getUserById(userId);
        return  ResponseEntity.ok(postService.getAllPostByUser(user));
    }

    //Лента активности пользователя должна отображать последние посты от пользователей, на которых он подписан
    @GetMapping("/activity/{userId}")
    public ResponseEntity<List<Post>> getSubscriptionsPosts(@PathVariable long userId){
        User user = userService.getUserById(userId);
        List<Post> subPosts = postService.getPostForActivityLent(user);
        return ResponseEntity.ok(subPosts);
    }

    // Пользователи могу прикрепляя изображения
    @PostMapping("/image/{postId}")
    public  ResponseEntity<String> attachImageToPost(@PathVariable long postId,
                                                     @RequestParam("image") MultipartFile file) throws Exception {
      postService.addImageToPost(postId,file);
      return   ResponseEntity.ok("Image attached");
    }

    //получить отдельно изображение
    @GetMapping("/fileSystem/{fileName}")
    public ResponseEntity<?> downloadImageFromFileSystem(@PathVariable String fileName) throws IOException {
        byte[] imageData=storageService.downloadImageFromFileSystem(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }


}
