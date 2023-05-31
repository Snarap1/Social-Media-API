package sprng.boot.socialmediaapi.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import jdk.jfr.Timespan;
import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "title")
    @Size(max = 100, message = "Title must be less than or equal to 100 characters")
    private String title;

    @Column(name = "content")
    @Size(max = 500, message = "Content must be less than or equal to 500 characters")
    private String content;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<FileData> fileDataList;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    //ставим значение NOW
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }


    public void addFileData(FileData fileData) {
        if (fileDataList == null) {
            fileDataList = new ArrayList<>();
        }
        fileDataList.add(fileData);
        fileData.setPost(this);
    }

}