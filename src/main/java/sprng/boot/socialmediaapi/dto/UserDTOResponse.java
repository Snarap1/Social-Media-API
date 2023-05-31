package sprng.boot.socialmediaapi.dto;


import lombok.NoArgsConstructor;


@NoArgsConstructor
public class UserDTOResponse {
    private  String nickname;
    private  String email;


    public UserDTOResponse(String username, String email) {
        this.nickname = username;
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
