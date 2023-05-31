package sprng.boot.socialmediaapi.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService service;

  @Operation(
          summary = "Регистрация пользователя",
          description = "Позволяет зарегистрироваться пользователю"
  )
  @PostMapping("/register")
  public ResponseEntity<?> register(
          @RequestBody @Parameter(description = "Данные пользователя") @Valid RegisterRequest request
          , BindingResult bindingResult
  ) throws BindException {
    check(bindingResult);

    return ResponseEntity.ok(service.register(request));
  }
  @Operation(
          summary = "Аутентификация",
          description = "Позволяет аутентифицировать пользователя"
  )
  @PostMapping("/authenticate")
  public ResponseEntity<?> authenticate(
      @RequestBody @Parameter(description = "Данные пользователя для входа") @Valid AuthenticationRequest request,
      BindingResult bindingResult
  ) throws BindException {
    check(bindingResult);
    return ResponseEntity.ok(service.authenticate(request));
  }

  @Operation(
          summary = "Рефреш токен",
          description = "Позволяет получить новый токен при помощи refresh-token" +
                  "в Postman в графу Bearer token (или нажав на замок напротив метода) " +
                  "передать refresh-token" +
                  "мы получим новый access-token"
  )
  @PostMapping("/refresh-token")
  public void refreshToken(
      HttpServletRequest request,
      HttpServletResponse response
  ) throws IOException {
    service.refreshToken(request, response);
  }

  public  void  check(BindingResult bindingResult) throws BindException {
    if(bindingResult.hasErrors())
      throw new BindException(bindingResult);
  }

}

