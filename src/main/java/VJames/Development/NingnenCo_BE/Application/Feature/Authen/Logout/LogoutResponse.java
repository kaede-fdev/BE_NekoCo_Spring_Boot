package VJames.Development.NingnenCo_BE.Application.Feature.Authen.Logout;

import lombok.*;
import org.springframework.http.HttpStatus;
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogoutResponse {
    private LogoutResponseStatusCode statusCode;
    private HttpStatus httpStatus;
}
