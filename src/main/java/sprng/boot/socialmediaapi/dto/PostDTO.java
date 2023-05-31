package sprng.boot.socialmediaapi.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sprng.boot.socialmediaapi.models.FileData;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostDTO {

    @Size(max = 100, message = "Title must be less than or equal to 100 characters")
    private String title;

    @Size(max = 500, message = "Content must be less than or equal to 500 characters")
    private String content;
}
