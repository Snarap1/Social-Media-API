package sprng.boot.socialmediaapi.dto;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserDTO {
    private  String nickname;

    @Email(message = "should be VALID email")
    private  String email;

    @Size(max = 16,min = 7,message = "Password should be betweeen 7 and 16 sybmols")
    private  String password;
}
