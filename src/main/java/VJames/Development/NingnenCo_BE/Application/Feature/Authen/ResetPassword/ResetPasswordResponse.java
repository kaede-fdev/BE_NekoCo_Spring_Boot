package VJames.Development.NingnenCo_BE.Application.Feature.Authen.ResetPassword;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
@Builder
@Getter
public class ResetPasswordResponse {
    private ResetPasswordResponseStatusCode statusCode;
    private HttpStatus httpStatus;
}
