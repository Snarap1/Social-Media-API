package sprng.boot.socialmediaapi.auth;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sprng.boot.socialmediaapi.models.Role;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

  @NotBlank
  private String username;

  @NotBlank
  @Email(message = "Email should be VALID ")
  private String email;

  @NotBlank
  private String password;
  private Role role;
}
