package sprng.boot.socialmediaapi.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;
import sprng.boot.socialmediaapi.models.FileData;
import sprng.boot.socialmediaapi.models.Post;
import sprng.boot.socialmediaapi.models.User;
import sprng.boot.socialmediaapi.repository.PostRepository;
import sprng.boot.socialmediaapi.services.PostService;
import sprng.boot.socialmediaapi.services.StorageService;
import sprng.boot.socialmediaapi.utils.Pagination;

import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private StorageService storageService;

    @InjectMocks
    private PostService postService;

    @Captor
    private ArgumentCaptor<Post> postCaptor;

    @Captor
    private ArgumentCaptor<Long> longCaptor;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSavePost() {
        Post post = new Post();
        post.setTitle("Test Post");

        postService.savePost(post);

        verify(postRepository).save(postCaptor.capture());
        Post capturedPost = postCaptor.getValue();
        Assertions.assertEquals(post, capturedPost);
    }

    @Test
    public void testGetAllPostByUser() {
        User user = new User();
        user.setId(1L);
        int page = 0;
        int size = 10;
        List<Post> expectedPosts = new ArrayList<>();
        expectedPosts.add(new Post());
        expectedPosts.add(new Post());

        when(postRepository.findAllByUserOrderByCreatedAtDesc(eq(user), any(PageRequest.class))).thenReturn(expectedPosts);

        List<Post> actualPosts = postService.getAllPostByUser(user, page, size);

        verify(postRepository).findAllByUserOrderByCreatedAtDesc(eq(user), any(PageRequest.class));
        Assertions.assertEquals(expectedPosts, actualPosts);
    }

    @Test
    public void testGetAllPosts() {
        List<Post> expectedPosts = new ArrayList<>();
        expectedPosts.add(new Post());
        expectedPosts.add(new Post());

        when(postRepository.findAll()).thenReturn(expectedPosts);

        List<Post> actualPosts = postService.getAllPosts();

        verify(postRepository).findAll();
        Assertions.assertEquals(expectedPosts, actualPosts);
    }

    @Test
    public void testGetPost() {
        long postId = 1L;
        Post expectedPost = new Post();
        expectedPost.setId(postId);

        when(postRepository.findById(postId)).thenReturn(expectedPost);

        Post actualPost = postService.getPost(postId);

        verify(postRepository).findById(postId);
        Assertions.assertEquals(expectedPost, actualPost);
    }

    @Test
    public void testDeletePostById() {
        long postId = 1L;
        Post post = new Post();
        post.setId(postId);

        when(postRepository.findById(postId)).thenReturn(post);

        postService.deletePostById(postId);

        verify(postRepository).findById(postId);
        verify(postRepository).delete(post);
    }

    @Test
    public void testEditPost() {
        Post existingPost = new Post();
        existingPost.setId(1L);
        existingPost.setTitle("Existing Title");
        existingPost.setContent("Existing Content");

        Post updatedPost = new Post();
        updatedPost.setTitle("Updated Title");
        updatedPost.setContent("Updated Content");

        when(postRepository.save(any(Post.class))).thenReturn(updatedPost);

        postService.editPost(existingPost, updatedPost);

        verify(postRepository).save(postCaptor.capture());
        Post capturedPost = postCaptor.getValue();
        Assertions.assertEquals(updatedPost.getTitle(), capturedPost.getTitle());
        Assertions.assertEquals(updatedPost.getContent(), capturedPost.getContent());
    }

    @Test
    public void testGetPostForActivityLent() {
        User user = Mockito.mock(User.class);
        Set<User> userList = new HashSet<>();
        userList.add(Mockito.mock(User.class)); // Add some mock users to the userList
        Mockito.when(user.getSubscriptions()).thenReturn(userList);

        List<Post> activityLent = new ArrayList<>();
        for (User subUser : userList) {
            activityLent.addAll(postRepository.findAllByUser(subUser));
        }

        Comparator<Post> sortByCreatedAt = Comparator.comparing(Post::getCreatedAt).reversed();
        activityLent.sort(sortByCreatedAt);

        int page = 0;
        int size = 10;
        Pagination pagination = Mockito.mock(Pagination.class);
        Mockito.when(pagination.paginate(activityLent, page, size)).thenReturn(activityLent);

        List<Post> result = postService.getPostForActivityLent(user, page, size);

        Assertions.assertEquals(activityLent, result);
    }

    @Test
    public void testAddImageToPost() throws Exception {
        Long postId = 1L;
        MultipartFile file = Mockito.mock(MultipartFile.class);
        Post post = createPost(postId, "Test Post", LocalDateTime.now());

        when(postRepository.findById(postId)).thenReturn(post);

        FileData fileData = new FileData();
        fileData.setId(1L);

        when(storageService.uploadImageToFileSystem(file)).thenReturn(fileData);

        postService.addImageToPost(postId, file);

        verify(postRepository).findById(longCaptor.capture());
        verify(storageService).uploadImageToFileSystem(file);
        verify(postRepository).save(postCaptor.capture());

        Long capturedPostId = longCaptor.getValue();
        Post capturedPost = postCaptor.getValue();

        Assertions.assertEquals(postId, capturedPostId);
        Assertions.assertEquals(post, capturedPost);
        Assertions.assertEquals(post, fileData.getPost());
    }

    private Post createPost(Long id, String title, LocalDateTime createdAt) {
        Post post = new Post();
        post.setId(id);
        post.setTitle(title);
        post.setCreatedAt(createdAt);
        return post;
    }
}