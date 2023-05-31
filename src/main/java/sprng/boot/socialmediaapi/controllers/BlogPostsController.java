package sprng.boot.socialmediaapi.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sprng.boot.socialmediaapi.dto.PostDTO;
import sprng.boot.socialmediaapi.dto.PostDTOResponse;
import sprng.boot.socialmediaapi.models.Post;
import sprng.boot.socialmediaapi.models.User;
import sprng.boot.socialmediaapi.services.PostService;
import sprng.boot.socialmediaapi.services.StorageService;
import sprng.boot.socialmediaapi.services.UserService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
@Tag(name = "Post Controller",description = "Контроллер для взаимодействия с постами")
public class BlogPostsController {

    private final PostService postService;
    private  final ModelMapper modelMapper;
    private  final UserService userService;
    private  final StorageService  storageService;

    @Autowired
    public BlogPostsController(PostService postService, ModelMapper modelMapper, UserService userService, StorageService storageService) {
        this.postService = postService;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.storageService = storageService;
    }


    @Operation(
            summary = "Создание поста",
            description = "Позволяет создать пост"
    )
    @PostMapping("/{userId}")
    public ResponseEntity<String> createPost(@PathVariable @Parameter(description = "ID пользователя")  Long userId
            ,@RequestBody @Parameter(description = "Тайтл и наполнение поста")
                 @Valid PostDTO postDTO, BindingResult bindingResult) throws BindException {
        userService.check(bindingResult);

        User user = userService.getUserById(userId);
        Post post = modelMapper.map(postDTO, Post.class);
        post.setUser(user);
        postService.savePost(post);

        return ResponseEntity.ok("Post created");
    }

    @Operation(
            summary = "Изменение поста",
            description = "Позволяет поменять значения поста"
    )
    @PatchMapping("/{postId}")
    public  ResponseEntity<String> editPostMapping (
            @PathVariable @Parameter(description = "ID поста") Long postId,
            @RequestBody @Parameter(description = "Параметры, которые изменяем в посте") @Valid PostDTO postDTO,
            BindingResult bindingResult ) throws BindException {
        userService.check(bindingResult);

        Post postForSave = postService.getPost(postId);
        Post post = modelMapper.map(postDTO, Post.class);
        postService.editPost(post,postForSave);

        return  ResponseEntity.ok("Post edited");
    }


    @Operation(
            summary = "Удаление поста",
            description = "Позволяет удалить пост"
    )
    @DeleteMapping( "/{postId}")
    public  ResponseEntity<String> deletePost(@PathVariable @Parameter(description = "ID поста")Long postId  ){
        postService.deletePostById(postId);
       return ResponseEntity.ok("Post deleted");
    }


    @Operation(
            summary = "Получение поста",
            description = "Позволяет получить отдельный пост"
    )
    @GetMapping("/{postId}")
    public  ResponseEntity<?> getPost(@PathVariable @Parameter(description = "ID поста") Long postId)  {

        Post post = postService.getPost(postId);
        PostDTOResponse postDTOResponse = modelMapper.map(post, PostDTOResponse.class);

        return ResponseEntity.ok(postDTOResponse);
    }


    @Operation(
            summary = "Получение постов пользователя",
            description = "Позволяет получить посты пользователя отсортированные по дате"
    )
    @GetMapping("/of/{userId}")
    public  ResponseEntity<?> getUsersPosts(
            @PathVariable @Parameter(description = "ID пользователя") long userId){

        User user = userService.getUserById(userId);
        List<PostDTOResponse>   responses = postService.getAllPostByUser(user)
                .stream().map((element) -> modelMapper
                .map(element, PostDTOResponse.class))
                .toList();

        return ResponseEntity.ok(responses);
    }


    @Operation(
            summary = "Лента активности для пользователя",
            description = "Отображает полсдение посты от пользователей, на которых он подписан"
    )
    @GetMapping("/activity/{userId}")
    public ResponseEntity<?> getSubscriptionsPosts(
            @PathVariable @Parameter(description = "ID пользователя") long userId) {

        User user = userService.getUserById(userId);
        List<PostDTOResponse> subPosts = postService.getPostForActivityLent(user)
                    .stream().map((element) -> modelMapper
                    .map(element, PostDTOResponse.class))
                    .collect(Collectors.toList());

        return ResponseEntity.ok(subPosts);
    }

    // Пользователи могу прикрепляя изображения
    @Operation(
            summary = "Прикрепление изображений",
            description = "Позволяет прикрепить изображение к посту" +
                    "Что бы добавить изображение, в postman -> body-> form-data: " +
                    "для ключа 'image' выбрать и добавить файл из системы"
    )
    @PostMapping("/image/{postId}")
    public  ResponseEntity<String> attachImageToPost(
            @PathVariable @Parameter(description = "ID поста") long postId,
             @RequestParam("image") @Parameter(description = "файл") MultipartFile file) throws Exception {
      postService.addImageToPost(postId,file);
      return   ResponseEntity.ok("Image attached");
    }


    @Operation(
            summary = "Получить изображение",
            description = "Позволяет получить изображение в новом окне"
    )
    @GetMapping("/fileSystem/{fileName}")
    public ResponseEntity<?> downloadImageFromFileSystem(@PathVariable String fileName) throws IOException {
        byte[] imageData=storageService.downloadImageFromFileSystem(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }



}
