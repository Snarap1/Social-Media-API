package sprng.boot.socialmediaapi.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    //@JsonIgnore
    private List<FileData> fileDataList;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "file_data_id")
//    private FileData fileData;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

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