package VJames.Development.NingnenCo_BE.Application.Feature.Authen.ResendVerifyEmail;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Builder
@Getter
public class ResendVerifyEmailResponse {
    private ResendVerifyEmailResponseStatusCode statusCode;
    private HttpStatus httpStatus;
}
