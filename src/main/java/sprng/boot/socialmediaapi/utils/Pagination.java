package sprng.boot.socialmediaapi.utils;

import sprng.boot.socialmediaapi.models.Post;

import java.util.ArrayList;
import java.util.List;

public class Pagination {


    public List<Post> paginate(List<Post> items, int page, int size) {
        int startIndex = page  * size;
        int endIndex = Math.min(startIndex + size, items.size());

        return items.subList(startIndex, endIndex);
    }

}
