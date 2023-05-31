package sprng.boot.socialmediaapi.dto;

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
public class PostDTOResponse {
    private String title;
    private String content;
    private  List<FileData> fileDataList;
}
