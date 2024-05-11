package VJames.Development.NingnenCo_BE.Application.Feature.Authen.Login;

import VJames.Development.NingnenCo_BE.Application.Feature.Authen.Login.Response.Body;
import VJames.Development.NingnenCo_BE.Application.Feature.Authen.Login.Response.UserCredential;
import lombok.*;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {
    private LoginResponseStatusCode statusCode;
    private HttpStatus httpStatus;
    private Body body;
    private UserCredential userCredential;
}

