package VJames.Development.NingnenCo_BE.Application.Feature.Authen.SendResetPasswordCode;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
@Builder
public class SendResetPasswordCodeResponse {
    private SendResetPasswordCodeStatusCode statusCode;
    private HttpStatus httpStatus;
}
